package com.ombremoon.playingcards.tileentity;

import com.ombremoon.playingcards.init.InitTileEntityTypes;
import com.ombremoon.playingcards.tileentity.base.TileEntityBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityPokerTable extends TileEntityBase {

    public TileEntityPokerTable(BlockPos p_155229_, BlockState p_155230_) {
        super(InitTileEntityTypes.POKER_TABLE.get(), p_155229_, p_155230_);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.poker_table");
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return null;
    }

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
    }
}
