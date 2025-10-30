package com.ombremoon.playingcards.entity.data;

import com.ombremoon.playingcards.main.PCReference;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class PCDataSerializers {
    public static final DeferredRegister<EntityDataSerializer<?>> SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, PCReference.MOD_ID);

    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<byte[]>> STACK = SERIALIZERS.register("stack", () -> EntityDataSerializer.forValueType(ByteBufCodecs.BYTE_ARRAY));

    public static void init(IEventBus bus) {
        SERIALIZERS.register(bus);
    }
}
