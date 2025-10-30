package com.ombremoon.playingcards.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModNetworking {
    public static void register(final PayloadRegistrar registrar) {
        registrar.playToServer(PacketInteractCard.TYPE, PacketInteractCard.STREAM_CODEC, PacketInteractCard::handle);
    }

    public static <MSG extends CustomPacketPayload> void sendToServer(MSG message) {
        PacketDistributor.sendToServer(message);
    }

    public static <MSG extends CustomPacketPayload> void sendToPlayer(MSG message, ServerPlayer serverPlayer) {
        PacketDistributor.sendToPlayer(serverPlayer, message);
    }

    public static <MSG extends CustomPacketPayload> void sendToClients(MSG message) {
        PacketDistributor.sendToAllPlayers(message);
    }
}
