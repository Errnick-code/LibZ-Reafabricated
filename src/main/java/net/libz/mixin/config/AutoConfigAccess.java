package net.libz.mixin.config;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.ConfigHolder;

@Mixin(AutoConfig.class)
public interface AutoConfigAccess {

    @Accessor(value = "holders", remap = false)
    static Map<Class<? extends ConfigData>, ConfigHolder<?>> getHolders() {
        throw new AssertionError("This should not occur!");
    }
}
