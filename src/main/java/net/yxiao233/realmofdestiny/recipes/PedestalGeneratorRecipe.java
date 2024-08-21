package net.yxiao233.realmofdestiny.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.yxiao233.realmofdestiny.ModRegistry.ModBlocks;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.helper.recipe.KeyToItemStackHelper;
import net.yxiao233.realmofdestiny.helper.recipe.PatternHelper;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class PedestalGeneratorRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack pedestalItemStack;
    private final NonNullList<Ingredient> generateItems;
    private final ItemStack[] outputItemStack;
    private final ArrayList<Double> chanceList;
    private final char[][][] patternsList;
    private final KeyToItemStackHelper keyItemStack;

    public PedestalGeneratorRecipe(ResourceLocation id, ItemStack pedestalItemStack, NonNullList<Ingredient> generateItem, ItemStack[] outputItemStack, ArrayList<Double> chanceList, char[][][] patternsList, KeyToItemStackHelper keyItemStack) {
        this.id = id;
        this.pedestalItemStack = pedestalItemStack;
        this.generateItems = generateItem;
        this.outputItemStack = outputItemStack;
        this.chanceList = chanceList;
        this.patternsList = patternsList;
        this.keyItemStack = keyItemStack;
    }

    @Override
    public boolean matches(SimpleContainer simpleContainer, Level level) {
        if (level.isClientSide()) {
            return false;
        }
        return simpleContainer.getItem(0).is(pedestalItemStack.getItem()) && keyItemStack.getKeys().contains("P");
    }

    @Override
    public ItemStack assemble(SimpleContainer simpleContainer, RegistryAccess registryAccess) {
        return generateItems.get(0).getItems()[0].copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return generateItems.get(0).getItems()[0].copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }
    public ItemStack getPedestalItemStack(){
        return pedestalItemStack;
    }
    public NonNullList<Ingredient> getIngredients() {
        return generateItems;
    }
    public ArrayList<Double> getChanceList(){
        return this.chanceList;
    }

    public char[][][] getPatternsList() {
        return patternsList;
    }
    public KeyToItemStackHelper getKeyItemStack(){
        return keyItemStack;
    }
    public ItemStack[] getOutputItemStack(){
        return outputItemStack;
    }
    public int[] getCountList(){
        int[] countlist = new int[outputItemStack.length];
        for (int i = 0; i < outputItemStack.length; i++) {
            countlist[i] = outputItemStack[i].getCount();
        }
        return countlist;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<PedestalGeneratorRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "pedestal_generator";
    }

    public static class Serializer implements RecipeSerializer<PedestalGeneratorRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(RealmOfDestiny.MODID, "pedestal_generator");

        @Override
        public PedestalGeneratorRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            //pedestal_item
            ItemStack pedestalItemStack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject,"pedestal_item"));

            //output
            JsonArray output = GsonHelper.getAsJsonArray(jsonObject,"output");
            NonNullList<Ingredient> outputIngredient = NonNullList.create();

            for (int i = 0; i < output.size(); i++) {
                outputIngredient.add(Ingredient.fromJson(output.get(i)));
            }
            ItemStack[] outputItemStacks = new ItemStack[output.size()];
            for (int i = 0; i < outputIngredient.size(); i++) {
                Item item = outputIngredient.get(i).getItems()[0].getItem();
                JsonObject obj = output.get(i).getAsJsonObject();
                boolean hasCount = obj.has("count");
                int count = 1;
                if(hasCount){
                    JsonElement amount = obj.get("count");
                    count = Integer.valueOf(String.valueOf(amount)).intValue();
                }
                outputItemStacks[i] = new ItemStack(item,count);
            }

            //chance
            ArrayList<Double> chanceList = new ArrayList<>();
            for (int i = 0; i < output.size(); i++) {
                JsonObject obj = output.get(i).getAsJsonObject();
                boolean hasChance = obj.has("chance");
                double chance = 1;
                if(hasChance){
                    JsonElement amount = obj.get("chance");
                    chance = Double.valueOf(String.valueOf(amount)).doubleValue();
                }
                chanceList.add(chance);
            }

            //pattern
            JsonArray pattern = GsonHelper.getAsJsonArray(jsonObject,"pattern");
            ArrayList<String> patternList = new ArrayList<>();
            for (int i = 0; i < pattern.size(); i++) {
                patternList.add(pattern.get(i).toString());
            }

            PatternHelper helper = new PatternHelper(patternList);
            char[][][] patterns = helper.getPatternChars();

            //key
            JsonObject keyObject = GsonHelper.getAsJsonObject(jsonObject,"key");
            ArrayList<ItemStack> itemStacks = new ArrayList<>();
            ArrayList<String> keys = helper.getKeysMap();
            for (int i = 0; i < keys.size(); i++) {
                if(keys.get(i).equals("P")){
                    itemStacks.add(new ItemStack(ModBlocks.PEDESTAL.get()));
                    continue;
                }else if(keys.get(i).equals(" ")){
                    itemStacks.add(new ItemStack(Items.AIR));
                    continue;
                }
                ItemStack itemStack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(keyObject,keys.get(i)));
                itemStacks.add(itemStack);
            }
            KeyToItemStackHelper toHelper = new KeyToItemStackHelper(helper.getKeysMap(),itemStacks);


            return new PedestalGeneratorRecipe(resourceLocation,pedestalItemStack,outputIngredient,outputItemStacks,chanceList,patterns,toHelper);
        }

        @Override
        public @Nullable PedestalGeneratorRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf buffer) {
            NonNullList<Ingredient> outputs = NonNullList.withSize(buffer.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < outputs.size(); i++) {
                outputs.set(i, Ingredient.fromNetwork(buffer));
            }

            ItemStack pedestalItemStack = buffer.readItem();
            return new PedestalGeneratorRecipe(resourceLocation,pedestalItemStack,outputs,null , null, null, null);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, PedestalGeneratorRecipe pedestalGeneratorRecipe) {
            buffer.writeInt(pedestalGeneratorRecipe.generateItems.size());

            buffer.writeItemStack(pedestalGeneratorRecipe.pedestalItemStack,false);
        }
    }
}
