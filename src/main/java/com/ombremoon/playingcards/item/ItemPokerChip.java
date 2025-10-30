package com.ombremoon.playingcards.item;

import com.ombremoon.playingcards.entity.EntityPokerChip;
import com.ombremoon.playingcards.init.InitItems;
import com.ombremoon.playingcards.item.base.ItemBase;
import com.ombremoon.playingcards.util.PCEntityTags;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemPokerChip extends ItemBase {
    private final byte colorID;
    private final int value;

    public ItemPokerChip(byte colorID, int value) {
        super(new Item.Properties());
        this.colorID = colorID;
        this.value = value;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("lore.poker_chip_value").append(" ").withStyle(ChatFormatting.GRAY).append(Component.literal("$" + getValue()).withStyle(ChatFormatting.GREEN)));
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (!pContext.getLevel().isClientSide) {
            EntityPokerChip pokerChip = new EntityPokerChip(pContext.getLevel(), pContext.getClickLocation(), new byte[]{getColorID()});
            pContext.getLevel().addFreshEntity(pokerChip);
            pContext.getItemInHand().shrink(1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.CONSUME;
    }

    public byte getColorID() {
        return colorID;
    }

    public int getValue() {
        return value;
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
