package com.ombremoon.playingcards.entity.data;

import com.ombremoon.playingcards.util.ArrayHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

public class PCDataSerializers {

    public static final EntityDataSerializer<Byte[]> STACK = new EntityDataSerializer<>() {

        @Override
        public void write(FriendlyByteBuf friendlyByteBuf, Byte[] bytes) {
            friendlyByteBuf.writeByteArray(ArrayHelper.toPrimitive(bytes));
        }

        @Override
        public Byte[] read(FriendlyByteBuf friendlyByteBuf) {
            return ArrayHelper.toObject(friendlyByteBuf.readByteArray());
        }

        @Override
        public Byte[] copy(Byte[] bytes) {
            return ArrayHelper.clone(bytes);
        }
    };
}
