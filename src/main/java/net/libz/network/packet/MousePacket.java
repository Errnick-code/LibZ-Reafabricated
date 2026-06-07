package net.libz.network.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record MousePacket(int mouseX, int mouseY) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MousePacket> PACKET_ID = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath("libz", "mouse_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, MousePacket> PACKET_CODEC = StreamCodec.ofMember((value, buf) -> {
        buf.writeInt(value.mouseX);
        buf.writeInt(value.mouseY);
    }, buf -> new MousePacket(buf.readInt(), buf.readInt()));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_ID;
    }

}
