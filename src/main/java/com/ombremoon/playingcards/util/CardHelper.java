package com.ombremoon.playingcards.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class CardHelper {
    public static final String[] CARD_SKIN_NAMES = new String[]{"item.card.skin_default", "item.card.skin_red", "item.card.skin_blue"};
    public static final String[] CARD_NAMES = new String[]{"item.card.ace", "item.card.two", "item.card.three", "item.card.four", "item.card.five", "item.card.six", "item.card.seven", "item.card.eight", "item.card.nine", "item.card.ten", "item.card.jack", "item.card.queen", "item.card.king"};
    public static final String[] CARD_SUITS = new String[]{"item.card.spades", "item.card.hearts", "item.card.clubs", "item.card.diamonds"};


    public static Component getCardName(int damageValue) {
        if (damageValue < 0 || damageValue >= 52) {
            return Component.literal("item.card.invalid");
        }
        return Component.translatable(CARD_NAMES[damageValue % 13]).append(" ").append(Component.translatable("misc.of")).append(" ").append(Component.translatable(CARD_SUITS[damageValue / 13]));
    }


    public static byte[] createShuffledDeck() {
        byte[] deck = new byte[52];
        for (byte i = 0; i < 52; i++) {
            deck[i] = i;
        }

        for (int i = 0; i < deck.length; i++) {
            int randomIndex = (int) (Math.random() * deck.length);
            byte temp = deck[i];
            deck[i] = deck[randomIndex];
            deck[randomIndex] = temp;
        }
        return deck;
    }

    public static byte[] combine(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    public static float getRotationAmount(Player pPlayer) {
        Vec3 lookVec = pPlayer.getLookAngle();
        double x = lookVec.x;
        double z = lookVec.z;
        float rotation = (float) (Math.atan2(x, z) * (180 / Math.PI));
        return (float) (Math.round(rotation / 45.0) * 45.0);
    }

    public static void renderItem(ItemStack itemStack, Level level, double x, double y, double z, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        BakedModel model = itemRenderer.getModel(itemStack, level, null, 0);
        poseStack.pushPose();
        poseStack.translate(x, y, z);
        itemRenderer.render(itemStack, ItemDisplayContext.GROUND, false, poseStack, buffer, packedLight, 0, model);
        poseStack.popPose();
    }

    public static Component getCardAmountComponent(int amount) {
        return Component.literal(String.valueOf(amount));
    }
}
