package com.ombremoon.playingcards.item;

import com.ombremoon.playingcards.item.base.ItemBase;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import com.ombremoon.playingcards.util.CardHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
public class ItemCard extends ItemBase {
    public ItemCard() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(CardHelper.getCardName(pStack.getDamageValue()));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return true;
    }
}
