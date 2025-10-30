package com.ombremoon.playingcards.entity.data;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.Function;

public class PCDataSerializers {
    public static final StreamCodec<ByteBuf, byte[]> STACK = ByteBufCodecs.BYTE_ARRAY;
}
