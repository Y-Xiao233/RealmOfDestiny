package net.yxiao233.realmofdestiny.recipe;

import com.hrznstudio.titanium.recipe.serializer.GenericSerializer;
import com.hrznstudio.titanium.recipe.serializer.SerializableRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.yxiao233.realmofdestiny.registry.ModRecipes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PedestalGeneratorRecipe extends SerializableRecipe {
    public static List<PedestalGeneratorRecipe> RECIPES = new ArrayList<>();
    public Ingredient.Value previewItem;
    public ItemStack[] output;
    public double outputChance;
    public double inputConsumeChance;
    public int processingTime;
    public int energy;
    public PedestalGeneratorRecipe(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    public PedestalGeneratorRecipe(ResourceLocation resourceLocation, double inputConsumeChance, Ingredient.Value previewItem, ItemStack[] output, double outputChance, int energy, int processingTime){
        super(resourceLocation);
        this.previewItem = previewItem;
        this.output = output;
        this.outputChance = outputChance;
        this.inputConsumeChance = inputConsumeChance;
        this.processingTime = processingTime;
        this.energy = energy;
        RECIPES.add(this);
    }
    @Override
    public boolean matches(Container container, Level level) {
        return false;
    }

    public boolean matches(IItemHandler handler){
        if(previewItem != null){
            Iterator<ItemStack> iterator = previewItem.getItems().iterator();
            boolean hasPreviewItem = false;
            while (iterator.hasNext()){
                ItemStack stack = iterator.next();
                hasPreviewItem = handler.getStackInSlot(0).is(stack.getItem());
                if(hasPreviewItem){
                    break;
                }
            }
            return hasPreviewItem;
        }else{
            return false;
        }
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return output[0];
    }

    @Override
    public GenericSerializer<? extends SerializableRecipe> getSerializer() {
        return (GenericSerializer<? extends SerializableRecipe>) ModRecipes.PEDESTAL_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.PEDESTAL_TYPE.get();
    }
}
