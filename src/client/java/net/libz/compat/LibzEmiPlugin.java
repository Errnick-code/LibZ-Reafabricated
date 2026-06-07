package net.libz.compat;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.mixin.accessor.HandledScreenAccessor;
import net.libz.LibzClient;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;

@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class LibzEmiPlugin implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        if (!LibzClient.inventoryTabs.isEmpty()) {
            registry.addExclusionArea(InventoryScreen.class, (screen, consumer) -> {
                int left = screen.width / 2 - 138;
                int top = screen.height / 2 - 83 - 21;
                consumer.accept(new Bounds(top, left, LibzClient.inventoryTabs.size() * 25, 21));
            });
        }
        LibzClient.otherTabs.forEach((screenClass, tabList) -> {
            registry.addExclusionArea((Class) screenClass.getClass(), (screen, consumer) -> {
                if (screen instanceof AbstractContainerScreen handledScreen) {
                    int left = ((HandledScreenAccessor) screen).getX();
                    int top = ((HandledScreenAccessor) screen).getY() - 21;
                    consumer.accept(new Bounds(left, top, tabList.size() * 25, 21));
                } else {
                    int left = screen.width / 2 - 138;
                    int top = screen.height / 2 - 83 - 21;
                    consumer.accept(new Bounds(left, top, tabList.size() * 25, 21));
                }
            });

        });
    }
}
