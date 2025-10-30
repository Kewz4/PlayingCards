package com.ombremoon.playingcards.block;

import com.mojang.serialization.MapCodec;
import com.ombremoon.playingcards.block.base.BlockContainerBase;
import com.ombremoon.playingcards.init.InitTileEntityTypes;
import com.ombremoon.playingcards.tileentity.TileEntityPokerTable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BlockPokerTable extends BlockContainerBase {
    public static final MapCodec<BlockPokerTable> CODEC = simpleCodec(BlockPokerTable::new);

    public BlockPokerTable(Properties p_49224_) {
        super(p_49224_);
    }

    @Override
    protected MapCodec<? extends BlockContainerBase> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new TileEntityPokerTable(pPos, pState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, InitTileEntityTypes.POKER_TABLE.get(), TileEntityPokerTable::tick);
    }
}
