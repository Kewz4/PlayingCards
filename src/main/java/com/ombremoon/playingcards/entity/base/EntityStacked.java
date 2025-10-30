package com.ombremoon.playingcards.entity.base;

import com.ombremoon.playingcards.entity.data.PCDataSerializers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class EntityStacked extends Entity {
    private static final EntityDataAccessor<byte[]> STACK = SynchedEntityData.defineId(EntityStacked.class, PCDataSerializers.STACK);

    public EntityStacked(EntityType<?> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    protected abstract void onHit();

    public boolean isStacked() {
        return getStack().length > 1;
    }

    public void ejectItems() {
        if (!this.level().isClientSide) {
            for (byte b : getStack()) {
                ejectItem(b);
            }
            this.discard();
        }
    }

    public void ejectItem(byte cardID) {
        if (!this.level().isClientSide) {
            ItemStack stack = getPickResult();
            stack.setDamageValue(cardID);
            ItemEntity itemEntity = new ItemEntity(this.level(), getX(), getY(), getZ(), stack);
            itemEntity.setDefaultPickUpDelay();
            this.level().addFreshEntity(itemEntity);
            removeStack(cardID);
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        setStack(pCompound.getByteArray("Stack"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putByteArray("Stack", getStack());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        pBuilder.define(STACK, new byte[0]);
    }

    public byte[] getStack() {
        return this.entityData.get(STACK);
    }

    public void setStack(byte[] stack) {
        this.entityData.set(STACK, stack);
    }

    public void addStack(byte value) {
        byte[] newStack = new byte[getStack().length + 1];
        System.arraycopy(getStack(), 0, newStack, 0, getStack().length);
        newStack[getStack().length] = value;
        setStack(newStack);
    }

    public void removeStack(byte value) {
        byte[] newStack = new byte[getStack().length - 1];
        int i = 0;
        for (byte b : getStack()) {
            if (b != value) {
                newStack[i] = b;
                i++;
            }
        }
        setStack(newStack);
    }
}
