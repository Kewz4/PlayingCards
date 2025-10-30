package com.ombremoon.playingcards.item;

import com.ombremoon.playingcards.entity.EntityPokerChip;
import com.ombremoon.playingcards.init.InitItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;

import java.util.List;

public class ItemPokerChip extends Item {
    private final byte chipID;
    private final int value;

    public ItemPokerChip(byte chipID, int value) {
        super(new Item.Properties());
        this.chipID = chipID;
        this.value = value;
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("lore.poker_chip_value").append(" ").append(Component.literal("$" + value)));
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (!pContext.getLevel().isClientSide) {
            EntityPokerChip pokerChip = new EntityPokerChip(pContext.getLevel(), pContext.getClickLocation(), new byte[]{chipID});
            pContext.getLevel().addFreshEntity(pokerChip);
            pContext.getItemInHand().shrink(1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.CONSUME;
    }

    public byte getChipID() {
        return chipID;
    }

    public static Item getItemFromID(byte id) {
        return switch (id) {
            case 1 -> InitItems.POKER_CHIP_RED.get();
            case 2 -> InitItems.POKER_CHIP_BLUE.get();
            case 3 -> InitItems.POKER_CHIP_GREEN.get();
            case 4 -> InitItems.POKER_CHIP_BLACK.get();
            default -> InitItems.POKER_CHIP_WHITE.get();
        };
    }
}
