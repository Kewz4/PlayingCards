package com.ombremoon.playingcards.block.base;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BlockContainerBase extends BaseEntityBlock {
    protected BlockContainerBase(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    protected abstract MapCodec<? extends BaseEntityBlock> codec();

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
}
