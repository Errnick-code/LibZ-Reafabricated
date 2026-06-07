package net.libz.network.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record ConfigPacket(String configName, boolean gson, byte[] bytes) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ConfigPacket> PACKET_ID = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath("libz", "config_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ConfigPacket> PACKET_CODEC = StreamCodec.ofMember((value, buf) -> {
        buf.writeUtf(value.configName);
        buf.writeBoolean(value.gson);
        buf.writeBytes(value.bytes);
    }, buf -> {
        String configName = buf.readUtf();
        boolean gson = buf.readBoolean();
        byte[] jsonBytes = new byte[buf.readableBytes()];
        buf.readBytes(jsonBytes);
        return new ConfigPacket(configName, gson, jsonBytes);
    });

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_ID;
    }

}
