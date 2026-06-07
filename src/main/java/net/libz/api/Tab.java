package net.libz.api;

import org.jetbrains.annotations.Nullable;

/**
 * Interface to get implemented by ScreenHandlers which will have a tab.
 *
 * @version 1.0
 */
public interface Tab {

    /**
     * Method to get overriden of the existing screen while returning this.getClass() and overriden by the new screen accessible via the tab return the parent class.
     * 
     * @return the parent screen class
     */
    @Nullable
    default Class<?> getParentScreenClass() {
        return null;
    }
}
