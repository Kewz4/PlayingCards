package com.ombremoon.playingcards.main;

import com.ombremoon.playingcards.init.InitEntityTypes;
import com.ombremoon.playingcards.init.InitItems;
import com.ombremoon.playingcards.init.InitRecipes;
import com.ombremoon.playingcards.init.InitTileEntityTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;

public class CommonClass {

    public static void init(IEventBus modEventBus) {
        InitItems.init(modEventBus);
        InitEntityTypes.init(modEventBus);
        InitTileEntityTypes.init(modEventBus);
        InitRecipes.init(modEventBus);
    }

    public static ResourceLocation customLocation(String name) {
        return new ResourceLocation(PCReference.MOD_ID, name);
    }
}
