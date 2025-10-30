package com.ombremoon.playingcards.block;

import com.mojang.serialization.MapCodec;
import com.ombremoon.playingcards.block.base.BlockContainerBase;
import com.ombremoon.playingcards.init.InitTileEntityTypes;
import com.ombremoon.playingcards.tileentity.TileEntityPokerTable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockPokerTable extends BlockContainerBase {
    public static final MapCodec<BlockPokerTable> CODEC = simpleCodec(BlockPokerTable::new);
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty WEST = BooleanProperty.create("west");


    public BlockPokerTable(Properties p_49224_) {
        super(p_49224_);
    }

    @Override
    protected @NotNull MapCodec<? extends BlockContainerBase> codec() {
        return CODEC;
    }

    @Override
    public BlockState updateShape(BlockState pState, net.minecraft.core.Direction pFacing, BlockState pFacingState, net.minecraft.world.level.LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return getState(pState, (Level) pLevel, pCurrentPos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return getState(super.getStateForPlacement(pContext), pContext.getLevel(), pContext.getClickedPos());
    }

    private BlockState getState(BlockState state, Level world, BlockPos pos) {
        if (state == null) {
            return null;
        }

        return state
                .setValue(UP, world.getBlockState(pos.above()).getBlock() instanceof BlockPokerTable)
                .setValue(DOWN, world.getBlockState(pos.below()).getBlock() instanceof BlockPokerTable)
                .setValue(NORTH, world.getBlockState(pos.north()).getBlock() instanceof BlockPokerTable)
                .setValue(SOUTH, world.getBlockState(pos.south()).getBlock() instanceof BlockPokerTable)
                .setValue(EAST, world.getBlockState(pos.east()).getBlock() instanceof BlockPokerTable)
                .setValue(WEST, world.getBlockState(pos.west()).getBlock() instanceof BlockPokerTable);
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(UP, DOWN, NORTH, SOUTH, EAST, WEST);
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new TileEntityPokerTable(pPos, pState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, InitTileEntityTypes.POKER_TABLE.get(), TileEntityPokerTable::tick);
    }
}
