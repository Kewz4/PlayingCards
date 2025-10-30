package com.ombremoon.playingcards.recipes;

import com.ombremoon.playingcards.init.InitItems;
import com.ombremoon.playingcards.init.InitRecipes;
import com.ombremoon.playingcards.util.ItemHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class CardDeckRecipe extends CustomRecipe {
    public CardDeckRecipe(CraftingBookCategory pCategory) {
        super(pCategory);
    }

    @Override
    public boolean matches(CraftingInput pInv, Level pLevel) {
        boolean hasDye = false;
        int paperCount = 0;

        for (int i = 0; i < pInv.size(); i++) {
            ItemStack stack = pInv.getItem(i);
            if (stack.is(Items.PAPER)) {
                paperCount++;
            } else if (stack.is(Items.BLACK_DYE) || stack.is(Items.RED_DYE) || stack.is(Items.BLUE_DYE)) {
                if (hasDye) return false;
                hasDye = true;
            } else if (!stack.isEmpty()) {
                return false;
            }
        }
        return hasDye && paperCount >= 1;
    }

    @Override
    public ItemStack assemble(CraftingInput pInv, HolderLookup.Provider pRegistries) {
        ItemStack deck = new ItemStack(InitItems.CARD_DECK.get());
        byte skinID = 0;

        for (int i = 0; i < pInv.size(); i++) {
            ItemStack stack = pInv.getItem(i);
            if (stack.is(Items.RED_DYE)) {
                skinID = 1;
                break;
            } else if (stack.is(Items.BLUE_DYE)) {
                skinID = 2;
                break;
            }
        }

        final byte finalSkinID = skinID;
        deck.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY, customData -> {
            CompoundTag newTag = customData.copyTag();
            newTag.putByte("SkinID", finalSkinID);
            return CustomData.of(newTag);
        });

        return deck;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput pInv) {
        return NonNullList.withSize(pInv.size(), ItemStack.EMPTY);
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return InitRecipes.DECK.get();
    }
}
