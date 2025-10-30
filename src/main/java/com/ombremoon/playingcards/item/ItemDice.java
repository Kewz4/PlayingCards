package com.ombremoon.playingcards.item;

import com.ombremoon.playingcards.entity.EntityDice;
import com.ombremoon.playingcards.init.InitEntityTypes;
import com.ombremoon.playingcards.item.base.ItemBase;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemDice extends ItemBase {

    public ItemDice() {
        super(new Item.Properties().stacksTo(5));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);

        if (!pLevel.isClientSide) {
            EntityDice dice = new EntityDice(InitEntityTypes.DICE.get(), pLevel);
            dice.setPos(pPlayer.getEyePosition(1.0f));
            net.minecraft.world.phys.Vec3 lookVec = pPlayer.getLookAngle();
            net.minecraft.world.phys.Vec3 randomVec = new net.minecraft.world.phys.Vec3(
                dice.getRandom().triangle(0.0, 0.0172275D * 1.0F),
                dice.getRandom().triangle(0.0, 0.0172275D * 1.0F),
                dice.getRandom().triangle(0.0, 0.0172275D * 1.0F)
            );
            net.minecraft.world.phys.Vec3 finalVec = lookVec.add(randomVec).scale(1.5);
            dice.setDeltaMovement(finalVec);
            pLevel.addFreshEntity(dice);
        }
        stack.shrink(1);

        return InteractionResultHolder.success(stack);
    }
}
