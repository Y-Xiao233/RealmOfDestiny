package net.yxiao233.realmofdestiny.utils;

import net.minecraft.world.item.ItemStack;

public class AdvancedItemStack {
    private ItemStack itemStack;
    private double chance;

    public AdvancedItemStack(ItemStack itemStack,double chance){
        this.itemStack = itemStack;
        this.chance = chance;
    }

    public boolean isEmpty(){
        return itemStack.isEmpty();
    }

    public ItemStack getItemStack(){
        return this.itemStack;
    }

    public double getChance(){
        return this.chance;
    }

    public static final AdvancedItemStack EMPTY = new AdvancedItemStack(ItemStack.EMPTY,0);
}
