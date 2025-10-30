package com.ombremoon.playingcards.block.base;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;

public class BlockBase extends Block {
    public static final MapCodec<BlockBase> CODEC = simpleCodec(BlockBase::new);

    public BlockBase(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }
}
