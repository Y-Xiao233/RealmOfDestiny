package net.yxiao233.realmofdestiny.helper.recipe;

import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

public class KeyToItemStackHelper {
    private ArrayList<String> keys;
    private ArrayList<ItemStack> itemStacks;
    public KeyToItemStackHelper(ArrayList<String> keys, ArrayList<ItemStack> itemStacks){
        this.keys = keys;
        this.itemStacks = itemStacks;
    }
    public ItemStack getCurrentItemStack(String key){
        if(keys.contains(key)){
            return itemStacks.get(keys.indexOf(key));
        }else{
            return null;
        }
    }
    public String getCurrentKey(ItemStack itemStack){
        if(itemStacks.contains(itemStack)){
            return keys.get(itemStacks.indexOf(itemStack));
        }else{
            return null;
        }
    }
    public ArrayList<String> getKeys(){
        return keys;
    }

    public ArrayList<ItemStack> getItemStacks(){
        return itemStacks;
    }
}
