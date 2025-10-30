package com.ombremoon.playingcards.event;

import com.ombremoon.playingcards.init.InitItems;
import com.ombremoon.playingcards.util.ItemHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class CraftingEvent {

    @SubscribeEvent
    public void onCrafted(PlayerEvent.ItemCraftedEvent event) {
        if (event.getCrafting().is(InitItems.CARD_DECK.get())) {
            CompoundTag nbt = ItemHelper.getNBT(event.getCrafting());
            if (!nbt.contains("Cards")) {
                nbt.putByteArray("Cards", new byte[0]);
            }
        }
    }
}
