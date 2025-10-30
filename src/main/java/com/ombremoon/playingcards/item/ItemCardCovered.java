package com.ombremoon.playingcards.item;

import com.ombremoon.playingcards.init.InitItems;
import com.ombremoon.playingcards.util.CardHelper;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;

import java.util.List;
import java.util.UUID;

public class ItemCardCovered extends Item {
    public ItemCardCovered() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        CustomData customData = pStack.get(DataComponents.CUSTOM_DATA);
        if (customData != null) {
            CompoundTag nbt = customData.copyTag();
            pTooltipComponents.add(Component.translatable("lore.cover").append(" ").append(Component.translatable(CardHelper.CARD_SKIN_NAMES[nbt.getByte("SkinID")])));
        }
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }

    public void flipCard(ItemStack heldItem, LivingEntity entity) {
        if (entity instanceof Player player) {
            CustomData customData = heldItem.get(DataComponents.CUSTOM_DATA);
            if (customData != null) {
                CompoundTag heldNBT = customData.copyTag();
                ItemStack newCard = new ItemStack(InitItems.CARD.get());
                newCard.setDamageValue(heldItem.getDamageValue());
                newCard.set(DataComponents.CUSTOM_DATA, CustomData.of(heldNBT));
                player.setItemInHand(InteractionHand.MAIN_HAND, newCard);
            }
        }
    }
}
