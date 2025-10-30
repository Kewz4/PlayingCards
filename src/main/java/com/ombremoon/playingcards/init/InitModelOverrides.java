package com.ombremoon.playingcards.init;

import com.ombremoon.playingcards.util.ItemHelper;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class InitModelOverrides {

    public static void init() {
        ItemProperties.register(InitItems.CARD.get(), ResourceLocation.withDefaultNamespace("value"), (stack, world, player, seed) -> stack.getDamageValue());
        ItemProperties.register(InitItems.CARD_COVERED.get(), ResourceLocation.withDefaultNamespace("skin"), (stack, world, player, seed) -> ItemHelper.getNBT(stack).getByte("SkinID"));
        ItemProperties.register(InitItems.CARD_DECK.get(), ResourceLocation.withDefaultNamespace("skin"), (stack, world, player, seed) -> ItemHelper.getNBT(stack).getByte("SkinID"));
    }
}
