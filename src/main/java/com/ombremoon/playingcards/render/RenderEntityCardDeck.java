package com.ombremoon.playingcards.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ombremoon.playingcards.entity.EntityCardDeck;
import com.ombremoon.playingcards.init.InitItems;
import com.ombremoon.playingcards.util.CardHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class RenderEntityCardDeck extends EntityRenderer<EntityCardDeck> {
    public RenderEntityCardDeck(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityCardDeck pEntity) {
        return null;
    }

    @Override
    public void render(EntityCardDeck pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        ItemStack card = new ItemStack(InitItems.CARD_COVERED.get());

        pPoseStack.pushPose();
        pPoseStack.translate(0, 0.005, 0);
        pPoseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(pEntity.getRotation()));
        pPoseStack.scale(0.5f, 0.5f, 0.5f);

        for (byte i = 0; i < pEntity.getStack().length + 2; i++) {
            pPoseStack.pushPose();
            pPoseStack.translate(0, i * -0.01, 0);
            CardHelper.renderItem(card, pEntity.level(), 0, 0, 0, pPoseStack, pBuffer, pPackedLight);
            pPoseStack.popPose();
        }

        pPoseStack.popPose();
    }
}
