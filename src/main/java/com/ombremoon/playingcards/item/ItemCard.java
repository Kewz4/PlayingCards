package com.ombremoon.playingcards.item;

import com.ombremoon.playingcards.item.base.ItemBase;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemCard extends ItemBase {
    public ItemCard() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return true;
    }
}
