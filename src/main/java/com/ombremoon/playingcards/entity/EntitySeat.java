package com.ombremoon.playingcards.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntitySeat extends Entity {

    public EntitySeat(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public EntitySeat(Level world) {
        super(com.ombremoon.playingcards.init.InitEntityTypes.SEAT.get(), world);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            if (this.getPassengers().isEmpty()) {
                this.discard();
            }
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this.getId(), this.getUUID(), this.getX(), this.getY(), this.getZ(), this.getXRot(), this.getYRot(), this.getType(), 0, Vec3.ZERO, this.getYHeadRot());
    }

    public static boolean create(Level world, double x, double y, double z, Player player) {
        if (!world.isClientSide()) {
            for (EntitySeat seat : world.getEntitiesOfClass(EntitySeat.class, player.getBoundingBox())) {
                if (seat.getX() == x && seat.getY() == y && seat.getZ() == z) {
                    return true;
                }
            }
            EntitySeat seat = new EntitySeat(world);
            seat.setPos(x, y, z);
            world.addFreshEntity(seat);
            player.startRiding(seat);
            return true;
        }
        return false;
    }
}
