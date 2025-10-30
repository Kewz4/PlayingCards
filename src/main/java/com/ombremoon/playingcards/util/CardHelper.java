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
        itemRenderer.render(itemStack, ItemDisplayContext.GROUND, false, poseStack, buffer, packedLight, ItemRenderer.getFoilBufferDirect(buffer, model, 0, 0, false), model);
        poseStack.popPose();
    }

    public static Component getCardAmountComponent(int amount) {
        return Component.literal(String.valueOf(amount));
    }
}
