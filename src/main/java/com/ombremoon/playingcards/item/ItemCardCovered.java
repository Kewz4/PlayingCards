package com.ombremoon.playingcards.item;

import com.ombremoon.playingcards.entity.EntityCard;
import com.ombremoon.playingcards.init.InitItems;
import com.ombremoon.playingcards.item.base.ItemBase;
import com.ombremoon.playingcards.util.ItemHelper;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class ItemCardCovered extends ItemBase {
    public ItemCardCovered() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level world = pContext.getLevel();
        if (!world.isClientSide) {
            EntityCard card = new EntityCard(world, pContext.getClickLocation(), pContext.getRotation(), ItemHelper.getNBT(pContext.getItemInHand()).getByte("SkinID"), new byte[0], true);
            world.addFreshEntity(card);
            pContext.getItemInHand().shrink(1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.CONSUME;
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
