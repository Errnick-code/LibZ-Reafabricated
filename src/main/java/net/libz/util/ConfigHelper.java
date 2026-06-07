package net.libz.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import org.jetbrains.annotations.Nullable;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.api.SyntaxError;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.util.Utils;
import net.libz.api.ConfigSync;

public class ConfigHelper {

    private static final Jankson JANKSON = Jankson.builder().build();

    public static void copyConfig(String configName, boolean gson) {
        Path configPath = getConfigPath(configName, gson);
        Path singleplayerConfigPath = configPath.resolveSibling("singleplayer_" + configPath.getFileName());
        try {
            Files.copy(configPath, singleplayerConfigPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readConfigFile(String configName, boolean gson, boolean excludeClientOnly) {
        try {
            return Files.readString(getConfigPath(configName, gson));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns set of field names annotated with @ConfigSync.ClientOnly
     * for a given config class. Used to exclude them from server sync.
     */
    public static Set<String> getClientOnlyFields(Class<? extends ConfigData> configClass) {
        Set<String> clientOnlyFields = new HashSet<>();
        for (Field field : configClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(ConfigSync.ClientOnly.class)) {
                clientOnlyFields.add(field.getName());
            }
        }
        return clientOnlyFields;
    }

    /**
     * Returns a JsonObject with client-only fields removed, for server sync.
     */
    @Nullable
    public static JsonObject getConfigNodeForSync(String configName, boolean gson, Class<? extends ConfigData> configClass) {
        JsonObject obj = getConfigNode(configName, gson, false);
        if (obj == null) return null;
        Set<String> clientOnly = getClientOnlyFields(configClass);
        for (String key : clientOnly) {
            obj.remove(key);
        }
        return obj;
    }

    @Nullable
    public static JsonObject getConfigNode(String configName, boolean gson, boolean excludeClientOnly) {
        try {
            String json = readConfigFile(configName, gson, excludeClientOnly);
            if (json == null) return null;
            return JANKSON.load(json);
        } catch (SyntaxError e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static byte[] getConfigBytes(String configName, boolean gson, boolean excludeClientOnly) {
        try {
            JsonObject obj = getConfigNode(configName, gson, excludeClientOnly);
            if (obj == null) return null;
            return obj.toJson().getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static JsonObject readJsonTree(byte[] bytes) {
        try {
            String json = new String(bytes, StandardCharsets.UTF_8);
            return JANKSON.load(json);
        } catch (SyntaxError e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Path getConfigPath(String configName, boolean gson) {
        return Utils.getConfigFolder().resolve(configName + (gson ? ".json" : ".json5"));
    }
}
