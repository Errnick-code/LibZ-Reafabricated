package net.libz.util;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.ConfigHolder;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;

public class AutoConfigHelper {

    @SuppressWarnings("unchecked")
    public static Map<Class<? extends ConfigData>, ConfigHolder<?>> getHolders() {
        try {
            Field field = AutoConfig.class.getDeclaredField("holders");
            field.setAccessible(true);
            return (Map<Class<? extends ConfigData>, ConfigHolder<?>>) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }
}
