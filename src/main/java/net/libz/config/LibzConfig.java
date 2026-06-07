package net.libz.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.libz.api.ConfigSync;
import net.libz.init.ConfigInit;

@Config(name = "libz")
@Config.Gui.Background("minecraft:textures/block/stone.png")
public class LibzConfig implements ConfigData, ConfigSync {

    @Comment("Show inventory tabs")
    public boolean inventoryButton = true;
    public boolean syncConfig = true;

    @Override
    public void updateConfig(ConfigData data) {
        ConfigInit.CONFIG = (LibzConfig) data;
    }

}