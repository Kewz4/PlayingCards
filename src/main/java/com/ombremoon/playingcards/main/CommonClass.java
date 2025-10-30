package com.ombremoon.playingcards.main;

import net.minecraft.resources.ResourceLocation;

public class CommonClass {
    public static ResourceLocation customLocation(String name) {
        return ResourceLocation.fromNamespaceAndPath(PCReference.MOD_ID, name);
    }
}
