package com.ombremoon.playingcards.entity;

import com.ombremoon.playingcards.entity.base.EntityStacked;
import com.ombremoon.playingcards.init.InitItems;
import com.ombremoon.playingcards.util.CardHelper;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityCardDeck extends EntityStacked {

    private static final EntityDataAccessor<Float> ROTATION = SynchedEntityData.defineId(EntityCardDeck.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Byte> SKIN_ID = SynchedEntityData.defineId(EntityCardDeck.class, EntityDataSerializers.BYTE);

    public EntityCardDeck(EntityType<?> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    public EntityCardDeck(Level world, Vec3 pos, float rotation, byte skinID) {
        super(com.ombremoon.playingcards.init.InitEntityTypes.CARD_DECK.get(), world);
        setPos(pos);
        setRotation(rotation);
        setSkinID(skinID);
        setStack(CardHelper.createShuffledDeck());
        updateCardCount();
    }

    @Override
    public InteractionResult interactAt(Player pPlayer, Vec3 pVec, InteractionHand pHand) {

        if (!pPlayer.isShiftKeyDown() && !isStacked()) {
            byte cardID = getStack()[0];
            ejectItem(cardID);
            EntityCard card = new EntityCard(level(), position().add(0, 0.1, 0), getRotation(), getSkinID(), new byte[]{cardID}, false);
            card.setDeckUUID(getUUID());
            level().addFreshEntity(card);
            updateCardCount();
            playSound(SoundEvents.WOOL_PLACE, 1.0F, 1.0F);
        } else {
            setRotation(getRotation() + CardHelper.getRotationAmount(pPlayer));
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected void onHit() {
        if (isStacked()) {
            setStack(CardHelper.createShuffledDeck());
            playSound(SoundEvents.WOOL_PLACE, 1.0F, 1.0F);
        } else {
            ejectItems();
        }
    }

    @Override
    public ItemStack getPickResult() {
        ItemStack stack = new ItemStack(InitItems.CARD_DECK.get());
        stack.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY, customData -> {
            CompoundTag newTag = customData.copyTag();
            newTag.putByte("SkinID", getSkinID());
            return CustomData.of(newTag);
        });
        return stack;
    }

    public void updateCardCount() {
        if (getStack().length > 0) {
            setCustomNameVisible(true);
            setCustomName(CardHelper.getCardAmountComponent(getStack().length));
        } else {
            setCustomNameVisible(false);
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        setRotation(pCompound.getFloat("Rotation"));
        setSkinID(pCompound.getByte("SkinID"));
        updateCardCount();
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

    @Override
    public boolean isCustomNameVisible() {
        return this.level().isClientSide && hasCustomName() && this.isCustomNameVisible();
    }

    @Override
    public boolean isAttackable() {
        return true;
    }
}
