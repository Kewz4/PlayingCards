package com.ombremoon.playingcards.entity;

import com.ombremoon.playingcards.entity.base.EntityStacked;
import com.ombremoon.playingcards.init.InitItems;
import com.ombremoon.playingcards.util.CardHelper;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.UUID;

public class EntityCard extends EntityStacked {

    private static final EntityDataAccessor<Float> ROTATION = SynchedEntityData.defineId(EntityCard.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Byte> SKIN_ID = SynchedEntityData.defineId(EntityCard.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Optional<UUID>> DECK_UUID = SynchedEntityData.defineId(EntityCard.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Boolean> COVERED = SynchedEntityData.defineId(EntityCard.class, EntityDataSerializers.BOOLEAN);

    public EntityCard(EntityType<?> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    public EntityCard(Level world, Vec3 pos, float rotation, byte skinID, byte[] stack, boolean isCovered) {
        super(com.ombremoon.playingcards.init.InitEntityTypes.CARD.get(), world);
        setPos(pos);
        setRotation(rotation);
        setSkinID(skinID);
        setStack(stack);
        setCovered(isCovered);
    }

    @Override
    public InteractionResult interactAt(Player pPlayer, Vec3 pVec, InteractionHand pHand) {
        ItemStack heldItem = pPlayer.getItemInHand(pHand);

        if (!heldItem.isEmpty() && (heldItem.getItem() == InitItems.CARD.get() || heldItem.getItem() == InitItems.CARD_COVERED.get())) {
            if (!this.level().isClientSide) {
                byte[] heldStack = new byte[]{(byte) heldItem.getDamageValue()};
                this.setStack(CardHelper.combine(this.getStack(), heldStack));
                heldItem.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }


        if (!isStacked()) {
            if (pPlayer.isShiftKeyDown()) {
                setCovered(!isCovered());
            } else {
                setRotation(getRotation() + CardHelper.getRotationAmount(pPlayer));
            }
        } else {
            if (!this.level().isClientSide) {
                byte topCard = this.getStack()[this.getStack().length - 1];
                ItemStack cardStack = new ItemStack(isCovered() ? InitItems.CARD_COVERED.get() : InitItems.CARD.get());
                if (!isCovered()) {
                    cardStack.setDamageValue(topCard);
                }
                pPlayer.addItem(cardStack);

                byte[] newStack = new byte[this.getStack().length - 1];
                System.arraycopy(this.getStack(), 0, newStack, 0, newStack.length);
                this.setStack(newStack);

                if (this.getStack().length == 0) {
                    this.discard();
                }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void onHit() {
        if (isStacked()) {
            ejectItems();
        } else {
            setCovered(!isCovered());
        }
    }

    @Override
    public ItemStack getPickResult() {
        ItemStack stack = new ItemStack(isCovered() ? InitItems.CARD_COVERED.get() : InitItems.CARD.get());

        if (!isCovered()) {
            stack.setDamageValue(getStack()[0]);
        }

        stack.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY, customData -> {
            CompoundTag newTag = customData.copyTag();
            newTag.putByte("SkinID", getSkinID());
            return CustomData.of(newTag);
        });

        return stack;
    }

    @Override
    public void onClientRemoval() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        setRotation(pCompound.getFloat("Rotation"));
        setSkinID(pCompound.getByte("SkinID"));
        setCovered(pCompound.getBoolean("IsCovered"));

        if (pCompound.contains("DeckUUID", Tag.TAG_INT_ARRAY)) {
            setDeckUUID(pCompound.getUUID("DeckUUID"));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putFloat("Rotation", getRotation());
        pCompound.putByte("SkinID", getSkinID());
        pCompound.putBoolean("IsCovered", isCovered());

        if (getDeckUUID() != null) {
            pCompound.putUUID("DeckUUID", getDeckUUID());
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(ROTATION, 0F);
        pBuilder.define(SKIN_ID, (byte) 0);
        pBuilder.define(DECK_UUID, Optional.empty());
        pBuilder.define(COVERED, false);
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

    public UUID getDeckUUID() {
        return this.entityData.get(DECK_UUID).orElse(null);
    }

    public void setDeckUUID(UUID uuid) {
        this.entityData.set(DECK_UUID, Optional.of(uuid));
    }

    public boolean isCovered() {
        return this.entityData.get(COVERED);
    }

    public void setCovered(boolean isCovered) {
        this.entityData.set(COVERED, isCovered);
    }

    @Override
    public boolean isCustomNameVisible() {
        return isStacked() && !this.level().isClientSide && hasCustomName() && this.isCustomNameVisible();
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public boolean canBeHitByProjectile() {
        return isStacked() && super.canBeHitByProjectile();
    }
}
