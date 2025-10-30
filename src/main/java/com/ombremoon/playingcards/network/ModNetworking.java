package com.ombremoon.playingcards.network;

import com.ombremoon.playingcards.main.PCReference;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

public class ModNetworking {
    public static void register(final IPayloadRegistrar registrar) {
        registrar.play(PacketInteractCard.TYPE, PacketInteractCard::new, handler -> handler
                .server(PacketInteractCard::handle)
        );
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
