package net.yxiao233.realmofdestiny.utils;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.yxiao233.realmofdestiny.modAbstracts.recipe.AbstractVoidMachineRecipe;

import java.util.ArrayList;

public class BufferUtil {
    private static AdvancedItemStack getInputsFromNetWork(FriendlyByteBuf buffer){
        ItemStack itemStack = buffer.readItem();
        double chance = buffer.readDouble();
        return new AdvancedItemStack(itemStack,chance);
    }

    private static <I extends AbstractVoidMachineRecipe> void inputsToNetWork(FriendlyByteBuf buffer, I recipe){
        AdvancedItemStack advancedItemStack = recipe.getInput();

        buffer.writeItemStack(advancedItemStack.getItemStack(),false);
        buffer.writeDouble(advancedItemStack.getChance());
    }


    private static ArrayList<AdvancedItemStack> getOutputsFromNetWork(FriendlyByteBuf buffer){
        int outputSize = buffer.readInt();
        ArrayList<AdvancedItemStack> itemStacks = new ArrayList<>();


        for (int i = 0; i < outputSize; i++) {
            ItemStack itemStack = buffer.readItem();
            double chance = buffer.readDouble();
            AdvancedItemStack adItemStack = new AdvancedItemStack(itemStack,chance);
            itemStacks.add(adItemStack);
        }

        return itemStacks;
    }

    private static <I extends AbstractVoidMachineRecipe> void OutputsToNetWork(FriendlyByteBuf buffer, I recipe){
        int outputSize = recipe.getOutputs().size();
        buffer.writeInt(outputSize);

        for (int i = 0; i < outputSize; i++) {
            buffer.writeItemStack(recipe.getOutputs().get(i).getItemStack(),false);
            buffer.writeDouble(recipe.getOutputs().get(i).getChance());
        }
    }

    private static int getTimeFromNetWork(FriendlyByteBuf buffer){
        return buffer.readInt();
    }

    private static <I extends AbstractVoidMachineRecipe> void timeToNetWork(FriendlyByteBuf buffer, I recipe){
        buffer.writeInt(recipe.getTime());
    }

    public static <I extends AbstractVoidMachineRecipe> void toNetWork(FriendlyByteBuf buffer, I recipe){
        inputsToNetWork(buffer,recipe);
        OutputsToNetWork(buffer,recipe);
        timeToNetWork(buffer,recipe);
    }

    public static RecipeList fromNetWork(FriendlyByteBuf buffer){
        AdvancedItemStack input = getInputsFromNetWork(buffer);
        ArrayList<AdvancedItemStack> outputs = getOutputsFromNetWork(buffer);
        int time = BufferUtil.getTimeFromNetWork(buffer);
        return new RecipeList(input,outputs,0,time);
    }
}
