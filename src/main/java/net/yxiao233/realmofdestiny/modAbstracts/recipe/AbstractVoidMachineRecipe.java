package net.yxiao233.realmofdestiny.modAbstracts.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.yxiao233.realmofdestiny.utils.AdvancedItemStack;

import java.util.ArrayList;

public abstract class AbstractVoidMachineRecipe implements Recipe<SimpleContainer> {
    private final ItemStack nInput;
    private final AdvancedItemStack input;
    private final NonNullList<Ingredient> nOutputs;
    private final ResourceLocation id;
    private final int time;
    private final ArrayList<AdvancedItemStack> outputs;

    public AbstractVoidMachineRecipe(AdvancedItemStack input, ArrayList<AdvancedItemStack> outputs, int time, ResourceLocation id) {
        this.input = input;
        this.nInput = input.getItemStack();
        this.outputs = outputs;

        this.nOutputs = NonNullList.create();

        for (int i = 0; i < outputs.size(); i++) {
            this.nOutputs.add(Ingredient.of(this.outputs.get(i).getItemStack()));
        }

        this.id = id;
        this.time = time;
    }

    @Override
    public boolean matches(SimpleContainer simpleContainer, Level level) {
        if (level.isClientSide()) {
            return false;
        }
        return simpleContainer.getItem(0).is(nInput.getItem());
    }

    @Override
    public ItemStack assemble(SimpleContainer simpleContainer, RegistryAccess registryAccess) {
        return nOutputs.get(0).getItems()[0].copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return nOutputs.get(0).getItems()[0].copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public int getTime(){
        return time;
    }

    public AdvancedItemStack getInput(){
        return this.input;
    }
    public ArrayList<AdvancedItemStack> getOutputs(){
        return this.outputs;
    }

    @Override
    public abstract RecipeSerializer<?> getSerializer();

    @Override
    public abstract RecipeType<?> getType();
}
