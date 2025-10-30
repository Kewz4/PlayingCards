package com.ombremoon.playingcards.entity;

import com.ombremoon.playingcards.entity.base.EntityStacked;
import com.ombremoon.playingcards.item.ItemPokerChip;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.UUID;

public class EntityPokerChip extends EntityStacked {
    private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(EntityPokerChip.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<String> OWNER_NAME = SynchedEntityData.defineId(EntityPokerChip.class, EntityDataSerializers.STRING);

    public EntityPokerChip(EntityType<?> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    public EntityPokerChip(Level world, Vec3 pos, byte[] stack) {
        super(com.ombremoon.playingcards.init.InitEntityTypes.POKER_CHIP.get(), world);
        setPos(pos);
        setStack(stack);
    }

    @Override
    public InteractionResult interactAt(Player pPlayer, Vec3 pVec, InteractionHand pHand) {
        if (pPlayer.isShiftKeyDown()) {
            if (!this.level().isClientSide) {
                if (getOwnerUUID() != null && (pPlayer.getUUID().equals(getOwnerUUID()) || pPlayer.hasPermissions(2))) {
                    setOwnerUUID(null);
                    setOwnerName("");
                    pPlayer.sendSystemMessage(Component.translatable("chat.poker_chip.unclaim"));
                }
            }
        } else {
            if (getOwnerUUID() == null) {
                setOwnerUUID(pPlayer.getUUID());
                setOwnerName(pPlayer.getDisplayName().getString());
                pPlayer.sendSystemMessage(Component.translatable("chat.poker_chip.claim"));
            } else {
                pPlayer.sendSystemMessage(Component.translatable("chat.poker_chip.fail_claim").append(" ").append(getOwnerName()));
            }
        }

        return super.interactAt(pPlayer, pVec, pHand);
    }

    @Override
    protected void onHit() {
        if (isStacked()) {
            if (getOwnerUUID() != null) {
                Player player = this.level().getPlayerByUUID(getOwnerUUID());
                if (player != null) {
                    for (byte b : getStack()) {
                        player.getInventory().add(new ItemStack(ItemPokerChip.getItemFromID(b)));
                    }
                    playSound(SoundEvents.ITEM_PICKUP, 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    this.discard();
                }
            } else {
                ejectItems();
            }
        }
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(ItemPokerChip.getItemFromID(getStack()[0]));
    }


    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);

        if (pCompound.contains("OwnerUUID", Tag.TAG_INT_ARRAY)) {
            setOwnerUUID(pCompound.getUUID("OwnerUUID"));
        }
        if (pCompound.contains("OwnerName", Tag.TAG_STRING)) {
            setOwnerName(pCompound.getString("OwnerName"));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);

        if (getOwnerUUID() != null) {
            pCompound.putUUID("OwnerUUID", getOwnerUUID());
            pCompound.putString("OwnerName", getOwnerName());
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(OWNER_UUID, Optional.empty());
        pBuilder.define(OWNER_NAME, "");
    }

    public UUID getOwnerUUID() {
        return this.entityData.get(OWNER_UUID).orElse(null);
    }

    public void setOwnerUUID(UUID uuid) {
        this.entityData.set(OWNER_UUID, Optional.ofNullable(uuid));
    }

    public String getOwnerName() {
        return this.entityData.get(OWNER_NAME);
    }

    public void setOwnerName(String name) {
        this.entityData.set(OWNER_NAME, name);
    }

    @Override
    public boolean isCustomNameVisible() {
        return getOwnerUUID() != null;
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
