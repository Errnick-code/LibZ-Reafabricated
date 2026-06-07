package net.libz.compat;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.screen.ExclusionZones;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import java.util.Collections;

@SuppressWarnings("unused")
@Environment(EnvType.CLIENT)
public class LibzReiPlugin implements REIClientPlugin {

    @Override
    public void registerExclusionZones(ExclusionZones zones) {
        // if (VillagerQuestsMain.CONFIG.exclusionZone)
        // zones.register(MerchantScreen.class, screen -> {
        // int i = (screen.width - 276) / 2;
        // int j = (screen.height - 166) / 2;
        // // return Collections.singleton(new Rectangle(i + 276 + VillagerQuestsMain.CONFIG.xIconPosition, j + VillagerQuestsMain.CONFIG.yIconPosition, 20, 20));
        // });
    }
}
