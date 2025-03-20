package net.yxiao233.realmofdestiny.util;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

public class ItemHandlerUtil {
    public static boolean canInsertItem(IItemHandler handler, List<ItemStack> itemStacks){
        boolean[] is = new boolean[itemStacks.size()];
        ArrayList<Integer> emptySlotWillBePutItem = new ArrayList<>();
        for (int i = 0; i < itemStacks.size(); i++) {
            for (int m = 0; m < handler.getSlots(); m++) {
                boolean canStack = handler.getStackInSlot(m).is(itemStacks.get(i).getItem()) && handler.getStackInSlot(m).getCount() + itemStacks.get(i).getCount() <= handler.getSlotLimit(i);
                is[i] = canStack;
                if(canStack){
                    break;
                }
            }
            if(!is[i]){
                for (int m = 0; m < handler.getSlots(); m++) {
                    if(emptySlotWillBePutItem.contains(m)){
                        continue;
                    }
                    boolean isEmpty = handler.getStackInSlot(m).isEmpty();
                    is[i] = isEmpty;
                    if(isEmpty){
                        emptySlotWillBePutItem.add(m);
                        break;
                    }
                }
            }
        }
        int t = 0;
        for (boolean b : is) {
            if (b) {
                t += 1;
            }
        }

        return t == is.length;
    }
}
