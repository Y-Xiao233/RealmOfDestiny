package net.yxiao233.realmofdestiny.recipes;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import org.jetbrains.annotations.Nullable;

public class PedestalLightingRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack pedestalItemStack;
    private final int energy;
    private final int time;
    private final ItemStack output;
    private final double chance;

    public PedestalLightingRecipe(ResourceLocation id, ItemStack pedestalItemStack, int energy, int time, ItemStack output, double chance) {
        this.id = id;
        this.pedestalItemStack = pedestalItemStack;
        this.energy = energy;
        this.time = time;
        this.output = output;
        this.chance = chance;
    }

    @Override
    public boolean matches(SimpleContainer simpleContainer, Level level) {
        if (level.isClientSide()) {
            return false;
        }
        return simpleContainer.getItem(0).is(this.pedestalItemStack.getItem());
    }

    @Override
    public ItemStack assemble(SimpleContainer simpleContainer, RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public double getChance(){
        return chance;
    }

    public ItemStack getOutput(){
        return output;
    }

    public ItemStack getPedestalItemStack(){
        return pedestalItemStack;
    }

    public int getTime(){
        return time;
    }

    public int getEnergy(){
        return energy;
    }
    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<PedestalLightingRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "pedestal_lighting";
    }

    public static class Serializer implements RecipeSerializer<PedestalLightingRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(RealmOfDestiny.MODID, "pedestal_lighting");

        @Override
        public PedestalLightingRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            //pedestal_item
            ItemStack pedestalItemStack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject,"pedestal_item"));

            //output
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject,"output"));

            //energy
            int energy = Integer.valueOf(String.valueOf(jsonObject.get("energy"))).intValue();

            //time
            int time = Integer.valueOf(String.valueOf(jsonObject.get("time"))).intValue();

            //chance
            double chance = Double.valueOf(String.valueOf(jsonObject.get("chance")));


            return new PedestalLightingRecipe(resourceLocation,pedestalItemStack,energy,time,output,chance);
        }

        @Override
        public @Nullable PedestalLightingRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf buffer) {
            ItemStack pedestalItemStack = buffer.readItem();
            ItemStack output = buffer.readItem();
            int energy = buffer.readInt();
            int time = buffer.readInt();
            double chance = buffer.readDouble();

            return new PedestalLightingRecipe(resourceLocation,pedestalItemStack,energy,time,output,chance);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, PedestalLightingRecipe recipe) {
            buffer.writeItem(recipe.pedestalItemStack);
            buffer.writeItem(recipe.output);
            buffer.writeInt(recipe.energy);
            buffer.writeInt(recipe.time);
            buffer.writeDouble(recipe.chance);
        }
    }
}
