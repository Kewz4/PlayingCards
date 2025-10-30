package com.ombremoon.playingcards.entity.data;

import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public class PCDataSerializers {
    public static final EntityDataSerializer<byte[]> STACK = EntityDataSerializer.forValueType(ByteBufCodecs.BYTE_ARRAY);

    public static void init() {
        EntityDataSerializers.registerSerializer(STACK);
    }
}
