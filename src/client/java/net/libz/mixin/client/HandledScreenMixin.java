package net.libz.mixin.client;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.libz.util.DrawTabHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;

@Environment(EnvType.CLIENT)
@Mixin(AbstractContainerScreen.class)
public abstract class HandledScreenMixin extends Screen {

    @Shadow protected int leftPos;
    @Shadow protected int topPos;
    @Shadow @Nullable protected Slot hoveredSlot;

    public HandledScreenMixin(Component title) {
        super(title);
    }

    // render — имя на Mojmaps совпадает, remap=false чтобы не искать через старый refmap
    @Inject(method = "render", at = @At("TAIL"), remap = false)
    private void renderMixin(GuiGraphics context, int mouseX, int mouseY, float delta, CallbackInfo info) {
        DrawTabHelper.drawTab(minecraft, context, this, leftPos, topPos, mouseX, mouseY);
    }

    // В 1.21.11 mouseClicked(double,double,int) → mouseClicked(MouseButtonEvent, boolean)
    // remap=false — берём имя напрямую, не через refmap с дескриптором от 1.21.1
    @Inject(method = "mouseClicked", at = @At("HEAD"), remap = false)
    private void mouseClickedMixin(MouseButtonEvent click, boolean doubled, CallbackInfoReturnable<Boolean> cir) {
        double mouseX = click.x();
        double mouseY = click.y();
        DrawTabHelper.onTabButtonClick(minecraft, this, this.leftPos, this.topPos, mouseX, mouseY, this.hoveredSlot != null);
    }
}
