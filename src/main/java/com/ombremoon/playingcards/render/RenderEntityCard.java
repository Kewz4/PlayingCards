package com.ombremoon.playingcards.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ombremoon.playingcards.entity.EntityCard;
import com.ombremoon.playingcards.init.InitItems;
import com.ombremoon.playingcards.util.CardHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class RenderEntityCard extends EntityRenderer<EntityCard> {
    public RenderEntityCard(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityCard pEntity) {
        return null;
    }

    @Override
    public void render(EntityCard pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        ItemStack card = new ItemStack(InitItems.CARD.get());
        card.setDamageValue(pEntity.getStack()[0]);

        if (pEntity.isCovered()) {
            card = new ItemStack(InitItems.CARD_COVERED.get());
        }

        pPoseStack.pushPose();
        pPoseStack.translate(0, 0.5, 0);
        pPoseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(pEntity.getRotation()));
        pPoseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(90));
        pPoseStack.scale(0.5f, 0.5f, 0.5f);

        for (byte i = 0; i < pEntity.getStack().length; i++) {
            pPoseStack.pushPose();
            pPoseStack.translate(0, i * -0.01, 0);
            CardHelper.renderItem(card, pEntity.level(), 0, 0, 0, pPoseStack, pBuffer, pPackedLight);
            pPoseStack.popPose();
        }

        pPoseStack.popPose();
    }
}
