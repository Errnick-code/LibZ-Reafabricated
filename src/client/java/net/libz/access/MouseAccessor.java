package net.libz.access;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface MouseAccessor {

    public void errnicraft_libz$setMousePosition(int x, int y);

}
