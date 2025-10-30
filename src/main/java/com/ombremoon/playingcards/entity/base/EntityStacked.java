package com.ombremoon.playingcards.entity.base;

import com.ombremoon.playingcards.entity.data.PCDataSerializers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class EntityStacked extends Entity {
    private static final EntityDataAccessor<byte[]> STACK = SynchedEntityData.defineId(EntityStacked.class, PCDataSerializers.BYTE_ARRAY);

    public EntityStacked(EntityType<?> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        pBuilder.define(STACK, new byte[0]);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        setStack(pCompound.getByteArray("Stack"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putByteArray("Stack", getStack());
    }

    public byte[] getStack() {
        return this.entityData.get(STACK);
    }

    public void setStack(byte[] stack) {
        this.entityData.set(STACK, stack);
    }

    public boolean isStacked() {
        return getStack().length > 1;
    }

    protected void ejectItems() {
        if (!this.level().isClientSide) {
            for (byte b : getStack()) {
                this.spawnAtLocation(new ItemStack(getPickResult().getItem(), 1));
            }
            this.discard();
        }
    }

    public abstract ItemStack getPickResult();
    protected abstract void onHit();
}
