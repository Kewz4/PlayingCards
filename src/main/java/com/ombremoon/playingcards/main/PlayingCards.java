package com.ombremoon.playingcards.main;

import com.ombremoon.playingcards.entity.data.PCDataSerializers;
import com.ombremoon.playingcards.event.CardInteractEvent;
import com.ombremoon.playingcards.init.InitEntityTypes;
import com.ombremoon.playingcards.init.InitItems;
import com.ombremoon.playingcards.init.InitRecipes;
import com.ombremoon.playingcards.init.InitTileEntityTypes;
import com.ombremoon.playingcards.network.ModNetworking;
import com.ombremoon.playingcards.render.*;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(PCReference.MOD_ID)
public class PlayingCards {

    public PlayingCards(IEventBus modEventBus) {
        InitItems.init(modEventBus);
        InitEntityTypes.init(modEventBus);
        InitTileEntityTypes.init(modEventBus);
        InitRecipes.init(modEventBus);
        PCDataSerializers.init(modEventBus);

        modEventBus.addListener(this::onCommonSetup);
        modEventBus.addListener(this::onClientSetup);
        modEventBus.addListener(this::registerPackets);
    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {
        NeoForge.EVENT_BUS.register(new CardInteractEvent());
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            EntityRenderers.register(InitEntityTypes.CARD.get(), RenderEntityCard::new);
            EntityRenderers.register(InitEntityTypes.CARD_DECK.get(), RenderEntityCardDeck::new);
            EntityRenderers.register(InitEntityTypes.POKER_CHIP.get(), RenderEntityPokerChip::new);
            EntityRenderers.register(InitEntityTypes.DICE.get(), RenderEntityDice::new);
            EntityRenderers.register(InitEntityTypes.SEAT.get(), RenderEntitySeat::new);
        });
    }

    public void registerPackets(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(PCReference.MOD_ID).versioned("1.0");
        ModNetworking.register(registrar);
    }
}
