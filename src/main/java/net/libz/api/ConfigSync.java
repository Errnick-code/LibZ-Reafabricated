package net.libz.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import me.shedaniel.autoconfig.ConfigData;

/**
 * Interface to get implemented by the config class for syncing certain config settings.
 *
 * @version 1.0
 */
public interface ConfigSync {

    /**
     * Update method to be overriden to update the mods config.
     *
     * <p>
     * An example can be found below:
     * 
     * @Override public void updateConfig(ConfigData data) {
     *           <p>
     *           ConfigInit.CONFIG = (LevelzConfig) data;
     *           <p>
     *           }
     *
     * @param data The data of the mods config.
     */
    public void updateConfig(ConfigData data);

    /**
     * Field descriptor to determine client only config settings.
     * <p>
     * Client only settings will not get synced.
     * <p>
     * Add the following above the config setting
     * 
     * @ConfigSync.ClientOnly
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface ClientOnly {
    }
}
