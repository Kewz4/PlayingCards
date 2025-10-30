package com.ombremoon.playingcards.item;

import com.ombremoon.playingcards.init.InitItems;
import com.ombremoon.playingcards.item.base.ItemBase;
import com.ombremoon.playingcards.util.ItemHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemCardCovered extends ItemBase {
    public ItemCardCovered() {
        super(new Item.Properties().stacksTo(1));
    }

    public void flipCard(ItemStack stack, Player player) {
        if (!player.level().isClientSide) {
            ItemStack newStack = new ItemStack(InitItems.CARD.get(), 1);
            newStack.setDamageValue((int) (Math.random() * 52));
            ItemHelper.getNBT(newStack).putByte("SkinID", ItemHelper.getNBT(stack).getByte("SkinID"));
            player.getInventory().setItem(player.getInventory().selected, newStack);
        }
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }
}
