package com.ombremoon.playingcards.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ombremoon.playingcards.entity.EntityPokerChip;
import com.ombremoon.playingcards.item.ItemPokerChip;
import com.ombremoon.playingcards.util.CardHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

public class RenderEntityPokerChip extends EntityRenderer<EntityPokerChip> {
    private final Random random = new Random();
    public RenderEntityPokerChip(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityPokerChip pEntity) {
        return null;
    }

    @Override
    public void render(EntityPokerChip pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        pPoseStack.translate(0, 0.005, 0);
        pPoseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(pEntityYaw));
        pPoseStack.scale(0.5f, 0.5f, 0.5f);

        for (byte i = 0; i < pEntity.getStack().length; i++) {
            pPoseStack.pushPose();
            random.setSeed(pEntity.getId() + i);
            pPoseStack.translate(random.nextFloat() * 0.1 - 0.05, i * 0.1, random.nextFloat() * 0.1 - 0.05);
            CardHelper.renderItem(new ItemStack(ItemPokerChip.getItemFromID(pEntity.getStack()[i])), pEntity.level(), 0, 0, 0, pPoseStack, pBuffer, pPackedLight);
            pPoseStack.popPose();
        }

        pPoseStack.popPose();
    }
}
