    package net.libz.util;

    import java.util.List;

    import com.mojang.blaze3d.pipeline.RenderPipeline;
    import net.fabricmc.api.EnvType;
    import net.fabricmc.api.Environment;
    import net.libz.LibzClient;
    import net.libz.api.InventoryTab;
    import net.libz.api.Tab;
    import net.libz.init.ConfigInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;

    @Environment(EnvType.CLIENT)
    public class DrawTabHelper {

        /**
         * Draw a tab on top of a screen.
         *
         * <p>
         * Required to call on client only screens. Not required on handled screens.
         *
         * @param client      A MinecraftClient instance.
         * @param context     The DrawContext of the render method.
         * @param screenClass The screen class (not the parent).
         * @param x           The left position of the screen.
         * @param y           The top position of the screen.
         * @param mouseX      The x mouse position.
         * @param mouseY      The y mouse position.
         */
        public static void drawTab(Minecraft client, GuiGraphics context, Screen screenClass, int x, int y, int mouseX, int mouseY) {
            if (client != null && client.player != null && ConfigInit.CONFIG.inventoryButton && (Object) screenClass instanceof Tab) {

                int xPos = x;
                Component shownTooltip = null;

                List<InventoryTab> list = null;
                if (((Tab) screenClass).getParentScreenClass() != null) {
                    if (LibzClient.otherTabs.isEmpty() || !LibzClient.otherTabs.containsKey(((Tab) screenClass).getParentScreenClass())) {
                        return;
                    }
                    list = LibzClient.otherTabs.get(((Tab) screenClass).getParentScreenClass());
                } else {
                    list = LibzClient.inventoryTabs;
                }
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        InventoryTab inventoryTab = list.get(i);
                        if (inventoryTab.shouldShow(client)) {

                            boolean isFirstTab = i == 0;
                            boolean isSelectedTab = inventoryTab.isSelectedScreen(screenClass.getClass());

                            int textureX = isFirstTab ? 24 : 72;
                            if (isSelectedTab) {
                                textureX -= 24;
                            }

                            context.blit(RenderPipelines.GUI_TEXTURED,LibzClient.tabTexture, xPos, isSelectedTab ? y - 23 : y - 21, textureX, 0, 24, isSelectedTab ? 27 : isFirstTab ? 25 : 21,256,256);
                            if (inventoryTab.getTexture() != null) {
                                context.blit(RenderPipelines.GUI_TEXTURED,inventoryTab.getTexture(), xPos + 5, y - 16, 0, 0, 14, 14, 14, 14);
                            } else if (inventoryTab.getItemStack(client) != null) {
                                context.renderItem(inventoryTab.getItemStack(client), xPos + 4, y - 17);
                            }

                            if (!isSelectedTab && isPointWithinBounds(x, y, xPos - x + 1, -20, 22, 19, (double) mouseX, (double) mouseY)) {
                                shownTooltip = inventoryTab.getTitle();
                            }
                            xPos += 25;
                        }
                    }
                }
                if (shownTooltip != null) {
                    context.setTooltipForNextFrame(client.font, shownTooltip, mouseX, mouseY);
                }
            }
        }

        /**
         * Tab button click method. Call it at mouseClicked method.
         *
         * <p>
         * Required to call on client only screens. Not required on handled screens.
         *
         * @param client      A MinecraftClient instance.
         * @param screenClass The screen class (not the parent).
         * @param x           The left position of the screen.
         * @param y           The top position of the screen.
         * @param mouseX      The x mouse position.
         * @param mouseY      The y mouse position.
         * @param focused     If another child is focused.
         */
        public static void onTabButtonClick(Minecraft client, Screen screenClass, int x, int y, double mouseX, double mouseY, boolean focused) {
            if (client != null && ConfigInit.CONFIG.inventoryButton && !focused && screenClass instanceof Tab) {
                int xPos = x;

                List<InventoryTab> list = null;
                if (((Tab) screenClass).getParentScreenClass() != null) {
                    if (LibzClient.otherTabs.isEmpty() || !LibzClient.otherTabs.containsKey(((Tab) screenClass).getParentScreenClass())) {
                        return;
                    }
                    list = LibzClient.otherTabs.get(((Tab) screenClass).getParentScreenClass());
                } else {
                    list = LibzClient.inventoryTabs;
                }
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        InventoryTab inventoryTab = list.get(i);
                        if (inventoryTab.shouldShow(client)) {
                            boolean isSelectedTab = inventoryTab.isSelectedScreen(screenClass.getClass());
                            if (inventoryTab.canClick(screenClass.getClass(), client)
                                    && isPointWithinBounds(x, y, xPos - x + 1, isSelectedTab ? -24 : -20, 22, isSelectedTab ? 23 : 19, (double) mouseX, (double) mouseY)) {
                                inventoryTab.onClick(client);
                            }
                            xPos += 25;
                        }
                    }
                }
            }
        }

        private static boolean isPointWithinBounds(int xPos, int yPos, int x, int y, int width, int height, double pointX, double pointY) {
            return (pointX -= (double) xPos) >= (double) (x - 1) && pointX < (double) (x + width + 1) && (pointY -= (double) yPos) >= (double) (y - 1) && pointY < (double) (y + height + 1);
        }
    }
