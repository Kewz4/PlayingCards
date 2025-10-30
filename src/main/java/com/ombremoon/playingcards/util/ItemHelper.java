package com.ombremoon.playingcards.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class ItemHelper {
    public static CompoundTag getNBT(ItemStack stack) {
        return stack.getOrCreateTag();
    }
}
