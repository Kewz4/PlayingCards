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

public class RenderEntityPokerChip extends EntityRenderer<EntityPokerChip> {
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
        pPoseStack.translate(0, 0.5, 0);
        pPoseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(pEntityYaw));
        pPoseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(90));
        pPoseStack.scale(0.5f, 0.5f, 0.5f);

        for (byte i = 0; i < pEntity.getStack().length; i++) {
            pPoseStack.pushPose();
            pPoseStack.translate(0, i * -0.01, 0);
            CardHelper.renderItem(new ItemStack(ItemPokerChip.getItemFromID(pEntity.getStack()[i])), pEntity.level(), 0, 0, i * 0.032D, pPoseStack, pBuffer, pPackedLight);
            pPoseStack.popPose();
        }

        pPoseStack.popPose();
    }
}
