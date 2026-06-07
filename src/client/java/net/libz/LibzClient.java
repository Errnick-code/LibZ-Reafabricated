package net.libz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.libz.api.*;
import net.libz.init.KeyInit;
import net.libz.network.LibzClientPacket;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class LibzClient implements ClientModInitializer {

    public static final List<InventoryTab> inventoryTabs = new ArrayList<InventoryTab>();
    public static final HashMap<Class<?>, List<InventoryTab>> otherTabs = new HashMap<Class<?>, List<InventoryTab>>();

    public static final Identifier tabTexture = Identifier.parse("libz:textures/gui/icons.png");

    public static final boolean isLibGuiLoaded = FabricLoader.getInstance().isModLoaded("libgui");

    @Override
    public void onInitializeClient() {
        LibzClientPacket.init();
        KeyInit.init();
    }

}
