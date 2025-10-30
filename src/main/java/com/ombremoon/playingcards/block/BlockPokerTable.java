package com.ombremoon.playingcards.block;

import com.mojang.serialization.MapCodec;
import com.ombremoon.playingcards.block.base.BlockContainerBase;
import com.ombremoon.playingcards.init.InitTileEntityTypes;
import com.ombremoon.playingcards.tileentity.TileEntityPokerTable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockPokerTable extends BlockContainerBase {
    public static final MapCodec<BlockPokerTable> CODEC = simpleCodec(BlockPokerTable::new);
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty NORTHEAST = BooleanProperty.create("northeast");
    public static final BooleanProperty NORTHWEST = BooleanProperty.create("northwest");
    public static final BooleanProperty SOUTHEAST = BooleanProperty.create("southeast");
    public static final BooleanProperty SOUTHWEST = BooleanProperty.create("southwest");

    public BlockPokerTable(Properties p_49224_) {
        super(p_49224_);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(NORTHEAST, false)
                .setValue(NORTHWEST, false)
                .setValue(SOUTHEAST, false)
                .setValue(SOUTHWEST, false));
    }

    @Override
    protected @NotNull MapCodec<? extends BlockContainerBase> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return getState(pContext.getLevel(), pContext.getClickedPos());
    }

    private BlockState getState(LevelAccessor world, BlockPos pos) {
        boolean north = world.getBlockState(pos.north()).getBlock() instanceof BlockPokerTable;
        boolean east = world.getBlockState(pos.east()).getBlock() instanceof BlockPokerTable;
        boolean south = world.getBlockState(pos.south()).getBlock() instanceof BlockPokerTable;
        boolean west = world.getBlockState(pos.west()).getBlock() instanceof BlockPokerTable;

        boolean northeast = world.getBlockState(pos.north().east()).getBlock() instanceof BlockPokerTable;
        boolean northwest = world.getBlockState(pos.north().west()).getBlock() instanceof BlockPokerTable;
        boolean southeast = world.getBlockState(pos.south().east()).getBlock() instanceof BlockPokerTable;
        boolean southwest = world.getBlockState(pos.south().west()).getBlock() instanceof BlockPokerTable;

        return this.defaultBlockState()
                .setValue(NORTH, north)
                .setValue(EAST, east)
                .setValue(SOUTH, south)
                .setValue(WEST, west)
                .setValue(NORTHEAST, northeast)
                .setValue(NORTHWEST, northwest)
                .setValue(SOUTHEAST, southeast)
                .setValue(SOUTHWEST, southwest);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        return getState(pLevel, pCurrentPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST);
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
