package com.ombremoon.playingcards.entity.data;

import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public class PCDataSerializers {
    public static final EntityDataSerializer<byte[]> STACK = EntityDataSerializer.simple(
            (buf, val) -> buf.writeByteArray(val),
            (buf) -> buf.readByteArray()
    );

    public static void init() {
        EntityDataSerializers.registerSerializer(STACK);
    }
}
