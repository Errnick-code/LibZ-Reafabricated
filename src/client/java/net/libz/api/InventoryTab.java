package net.libz.api;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;


@Environment(EnvType.CLIENT)
public class InventoryTab {

    private final Class<?>[] screenClasses;
    private final Component title;
    @Nullable
    private final Identifier texture;
    private final int preferedPos;

    /**
     * Constructor to create a new inventory tab.
     * 
     * @param title         Text to be rendered on tab hover.
     * @param texture       Identifier of the tab icon texture which has a size of 14x14.
     * @param preferedPos   Number of the prefered position. 0 = far left.
     * @param screenClasses Screen class list of the inventory tab screen.
     */
    public InventoryTab(Component title, @Nullable Identifier texture, int preferedPos, Class<?>... screenClasses) {
        this.screenClasses = screenClasses;
        this.title = title;
        this.texture = texture;
        this.preferedPos = preferedPos;
    }

    public Component getTitle() {
        return this.title;
    }

    @Nullable
    public Identifier getTexture() {
        return this.texture;
    }

    @Nullable
    public ItemStack getItemStack(Minecraft client) {
        return null;
    }

    public int getPreferedPos() {
        return this.preferedPos;
    }

    public boolean shouldShow(Minecraft client) {
        return true;
    }

    public void onClick(Minecraft client) {
        client.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

    public boolean canClick(Class<?> screenClass, Minecraft client) {
        return !isSelectedScreen(screenClass);
    }

    public boolean isSelectedScreen(Class<?> screenClass) {
        for (int i = 0; i < screenClasses.length; i++) {
            if (screenClasses[i].equals(screenClass)) {
                return true;
            }
        }
        return false;
    }

}
