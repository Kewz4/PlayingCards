package com.ombremoon.playingcards.entity;

import com.ombremoon.playingcards.entity.base.EntityStacked;
import com.ombremoon.playingcards.init.InitEntityTypes;
import com.ombremoon.playingcards.init.InitItems;
import com.ombremoon.playingcards.util.CardHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityCardDeck extends EntityStacked {

    private static final EntityDataAccessor<Float> ROTATION = SynchedEntityData.defineId(EntityCardDeck.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Byte> SKIN_ID = SynchedEntityData.defineId(EntityCardDeck.class, EntityDataSerializers.BYTE);

    public EntityCardDeck(EntityType<?> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    public EntityCardDeck(Level world, Vec3 pos, float rotation, byte skinID) {
        super(InitEntityTypes.CARD_DECK.get(), world);
        setPos(pos);
        this.setStack(CardHelper.createShuffledDeck());
        this.setRotation(rotation);
        this.setSkinID(skinID);
    }

    @Override
    public InteractionResult interactAt(Player pPlayer, Vec3 pVec, InteractionHand pHand) {
        if (this.getStack().length > 0) {
            if (!this.level().isClientSide) {
                byte topCard = this.getStack()[this.getStack().length - 1];
                ItemStack cardStack = new ItemStack(InitItems.CARD_COVERED.get());
                cardStack.setDamageValue(topCard);
                pPlayer.getInventory().add(cardStack);

                byte[] newStack = new byte[this.getStack().length - 1];
                System.arraycopy(this.getStack(), 0, newStack, 0, newStack.length);
                this.setStack(newStack);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @Override
    protected void onHit() {
        if (isStacked()) {
            this.setStack(CardHelper.createShuffledDeck());
        } else {
            if (!this.level().isClientSide) {
                this.spawnAtLocation(new ItemStack(InitItems.CARD_DECK.get()));
                this.discard();
            }
        }
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(InitItems.CARD_DECK.get());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        setRotation(pCompound.getFloat("Rotation"));
        setSkinID(pCompound.getByte("SkinID"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putFloat("Rotation", getRotation());
        pCompound.putByte("SkinID", getSkinID());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(ROTATION, 0F);
        pBuilder.define(SKIN_ID, (byte) 0);
    }

    public float getRotation() {
        return this.entityData.get(ROTATION);
    }

    public void setRotation(float rotation) {
        this.entityData.set(ROTATION, rotation);
    }

    public byte getSkinID() {
        return this.entityData.get(SKIN_ID);
    }

    public void setSkinID(byte id) {
        this.entityData.set(SKIN_ID, id);
    }
}
