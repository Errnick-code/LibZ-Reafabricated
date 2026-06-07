package net.libz;

import net.fabricmc.api.ModInitializer;
import net.libz.init.*;
import net.libz.network.LibzServerPacket;

public class LibzMain implements ModInitializer {

    @Override
    public void onInitialize() {
        ConfigInit.init();
        LibzServerPacket.init();
        EventInit.init();
    }

}
