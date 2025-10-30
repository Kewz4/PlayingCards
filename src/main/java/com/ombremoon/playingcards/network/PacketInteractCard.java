package com.ombremoon.playingcards.network;

import com.ombremoon.playingcards.item.ItemCardCovered;
import com.ombremoon.playingcards.main.CommonClass;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PacketInteractCard(String command) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<PacketInteractCard> TYPE = new CustomPacketPayload.Type<>(CommonClass.customLocation("interact_card"));
    public static final StreamCodec<ByteBuf, PacketInteractCard> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            PacketInteractCard::command,
            PacketInteractCard::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final PacketInteractCard packet, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                if (packet.command.equalsIgnoreCase("flipinv")) {
                    Item item = player.getMainHandItem().getItem();
                    if (item instanceof ItemCardCovered card) {
                        card.flipCard(player.getMainHandItem(), player);
                    }
                }
            }
        });
    }
}
