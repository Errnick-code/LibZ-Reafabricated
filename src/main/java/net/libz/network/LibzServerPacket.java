package net.libz.network;

import me.shedaniel.autoconfig.ConfigData;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.libz.network.packet.ConfigPacket;
import net.libz.network.packet.MousePacket;
import net.libz.util.ConfigHelper;
import net.minecraft.server.level.ServerPlayer;

public class LibzServerPacket {

    public static void init() {
        PayloadTypeRegistry.playS2C().register(ConfigPacket.PACKET_ID, ConfigPacket.PACKET_CODEC);
        PayloadTypeRegistry.playS2C().register(MousePacket.PACKET_ID, MousePacket.PACKET_CODEC);
    }

    // Перегрузка с фильтрацией @ClientOnly полей через reflection
    public static void writeS2CConfigPacket(ServerPlayer player, String configName, boolean gson, Class<? extends ConfigData> configClass) {
        var obj = ConfigHelper.getConfigNodeForSync(configName, gson, configClass);
        if (obj != null) {
            byte[] bytes = obj.toJson().getBytes(java.nio.charset.StandardCharsets.UTF_8);
            ServerPlayNetworking.send(player, new ConfigPacket(configName, gson, bytes));
        }
    }

    // Оригинальная перегрузка — без фильтрации (на случай обратной совместимости)
    public static void writeS2CConfigPacket(ServerPlayer player, String configName, boolean gson) {
        byte[] bytes = ConfigHelper.getConfigBytes(configName, gson, false);
        if (bytes != null) {
            ServerPlayNetworking.send(player, new ConfigPacket(configName, gson, bytes));
        }
    }

    public static void writeS2CMousePositionPacket(ServerPlayer player, int mouseX, int mouseY) {
        ServerPlayNetworking.send(player, new MousePacket(mouseX, mouseY));
    }
}
