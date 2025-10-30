package com.ombremoon.playingcards.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ombremoon.playingcards.entity.EntityDice;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class RenderEntityDice extends EntityRenderer<EntityDice> {
    public RenderEntityDice(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityDice pEntity) {
        return null;
    }

    @Override
    public void render(EntityDice pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        pPoseStack.translate(0, 0.5, 0);
        pPoseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(pEntityYaw));
        pPoseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(90));
        pPoseStack.scale(0.5f, 0.5f, 0.5f);
        com.ombremoon.playingcards.util.CardHelper.renderItem(new net.minecraft.world.item.ItemStack(com.ombremoon.playingcards.init.InitItems.DICE.get()), pEntity.level(), 0, 0, 0, pPoseStack, pBuffer, pPackedLight);
        pPoseStack.popPose();
    }
}
