package com.ombremoon.playingcards.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ombremoon.playingcards.entity.EntitySeat;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class RenderEntitySeat extends EntityRenderer<EntitySeat> {
    public RenderEntitySeat(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public ResourceLocation getTextureLocation(EntitySeat pEntity) {
        return null;
    }

    @Override
    public void render(EntitySeat pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}
