package com.ombremoon.playingcards.block;

import com.mojang.serialization.MapCodec;
import com.ombremoon.playingcards.block.base.BlockBase;
import com.ombremoon.playingcards.entity.EntitySeat;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockBarStool extends BlockBase {
    public static final MapCodec<BlockBarStool> CODEC = simpleCodec(BlockBarStool::new);
    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 11.0D, 12.0D);

    public BlockBarStool(Properties p_49224_) {
        super(p_49224_);
    }

    @Override
    protected MapCodec<? extends BlockBase> codec() {
        return CODEC;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        return EntitySeat.create(pLevel, pPos.getX() + 0.5D, pPos.getY() + 0.5D, pPos.getZ() + 0.5D, pPlayer) ? InteractionResult.SUCCESS : InteractionResult.FAIL;
    }
}
