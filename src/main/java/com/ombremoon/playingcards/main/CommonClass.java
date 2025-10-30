package com.ombremoon.playingcards.main;

import com.ombremoon.playingcards.init.InitEntityTypes;
import com.ombremoon.playingcards.init.InitItems;
import com.ombremoon.playingcards.init.InitRecipes;
import com.ombremoon.playingcards.init.InitTileEntityTypes;
import com.ombremoon.playingcards.network.ModNetworking;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

public class CommonClass {

    public static void init(IEventBus modEventBus) {
        InitItems.init(modEventBus);
        InitEntityTypes.init(modEventBus);
        InitTileEntityTypes.init(modEventBus);
        InitRecipes.init(modEventBus);
        modEventBus.addListener(CommonClass::registerPackets);
    }

    private static void registerPackets(final RegisterPayloadHandlerEvent event) {
        final IPayloadRegistrar registrar = event.registrar(PCReference.MOD_ID).versioned("1.0");
        ModNetworking.register(registrar);
    }

    public static ResourceLocation customLocation(String name) {
        return ResourceLocation.fromNamespaceAndPath(PCReference.MOD_ID, name);
    }
}
