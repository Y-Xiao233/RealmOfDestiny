package net.yxiao233.realmofdestiny.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.yxiao233.realmofdestiny.api.item.custom.AddonItem;

public class UpgradeUtil {
    public static int indexOf(ItemStackHandler handler, AddonItem.Type type){
        for (int i = 0; i < handler.getSlots(); i++) {
            if(handler.getStackInSlot(i).getItem() instanceof AddonItem addonItem && addonItem.type.equals(type)){
                return i;
            }
        }
        return -1;
    }
    public static boolean contains(ItemStackHandler handler, AddonItem.Type type){
        return indexOf(handler,type) != -1;
    }

    public static boolean canAccept(ItemStackHandler handler, AddonItem.Type type){
        return !contains(handler,type);
    }

    public static boolean canAccept(ItemStackHandler handler, ItemStack stack){
        if(stack.getItem() instanceof AddonItem addonItem){
            return canAccept(handler,addonItem.type);
        }
        return false;
    }

    public static boolean canAccept(ItemStackHandler handler, Item item){
        return canAccept(handler,item.getDefaultInstance());
    }
}
