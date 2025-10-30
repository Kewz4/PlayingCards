package com.ombremoon.playingcards.recipes;

import com.ombremoon.playingcards.init.InitItems;
import com.ombremoon.playingcards.util.CardHelper;
import com.ombremoon.playingcards.util.ItemHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class CardDeckRecipe extends CustomRecipe {
    public CardDeckRecipe(CraftingBookCategory pCategory) {
        super(pCategory);
    }

    @Override
    public boolean matches(CraftingContainer pInv, Level pLevel) {
        boolean hasDeck = false;
        boolean hasCard = false;

        for (int i = 0; i < pInv.getContainerSize(); i++) {
            ItemStack stack = pInv.getItem(i);
            if (stack.is(InitItems.CARD_DECK.get())) {
                if (hasDeck) return false;
                hasDeck = true;
            } else if (stack.is(InitItems.CARD.get())) {
                hasCard = true;
            } else if (!stack.isEmpty()) {
                return false;
            }
        }
        return hasDeck && hasCard;
    }

    @Override
    public ItemStack assemble(CraftingContainer pInv, HolderLookup.Provider pRegistries) {
        ItemStack deck = ItemStack.EMPTY;

        for (int i = 0; i < pInv.getContainerSize(); i++) {
            ItemStack stack = pInv.getItem(i);
            if (stack.is(InitItems.CARD_DECK.get())) {
                deck = stack.copy();
                break;
            }
        }

        byte[] cards = new byte[0];
        for (int i = 0; i < pInv.getContainerSize(); i++) {
            ItemStack stack = pInv.getItem(i);
            if (stack.is(InitItems.CARD.get())) {
                byte[] newCards = new byte[cards.length + 1];
                System.arraycopy(cards, 0, newCards, 0, cards.length);
                newCards[cards.length] = (byte) stack.getDamageValue();
                cards = newCards;
            }
        }

        CompoundTag nbt = ItemHelper.getNBT(deck);
        nbt.putByteArray("Cards", CardHelper.combine(nbt.getByteArray("Cards"), cards));
        return deck;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer pInv) {
        return NonNullList.withSize(pInv.getContainerSize(), ItemStack.EMPTY);
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
