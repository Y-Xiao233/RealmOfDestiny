package net.yxiao233.realmofdestiny.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.recipes.container.EmptyContainer;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class ChangeStoneRecipe implements Recipe<EmptyContainer> {
    private final ResourceLocation id;
    private final ItemStack checkBlockItem;
    private final NonNullList<Ingredient> setBlockItem;
    private final ArrayList<Double> chanceList;

    public ChangeStoneRecipe(ResourceLocation id, ItemStack checkBlockItem, NonNullList<Ingredient> setBlockItemStacks, ArrayList<Double> chanceList) {
        this.id = id;
        this.checkBlockItem = checkBlockItem;
        this.setBlockItem = setBlockItemStacks;
        this.chanceList = chanceList;
    }

    @Override
    public boolean matches(EmptyContainer emptyContainer, Level level) {
        Player player = emptyContainer.getPlayer();
        if(player == null){
            return false;
        }
        BlockPos blockPos = emptyContainer.getBlockPos();

        return level.getBlockState(blockPos).getBlock() == Block.byItem(checkBlockItem.getItem());
    }

    @Override
    public ItemStack assemble(EmptyContainer emptyContainer, RegistryAccess registryAccess) {
        return setBlockItem.get(0).getItems()[0].copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return setBlockItem.get(0).getItems()[0].copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return setBlockItem;
    }

    public ItemStack getCheckBlockItem(){
        return this.checkBlockItem;
    }
    public ArrayList<Double> getChanceList(){
        return this.chanceList;
    }


    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<ChangeStoneRecipe>{
        public static final ChangeStoneRecipe.Type INSTANCE = new ChangeStoneRecipe.Type();
        public static final String ID = "block_change";
    }

    public static class Serializer implements RecipeSerializer<ChangeStoneRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(RealmOfDestiny.MODID, "block_change");

        @Override
        public ChangeStoneRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            ItemStack checkBlockItemStack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject,"checkBlock"));
            JsonArray setBlockIngredient = GsonHelper.getAsJsonArray(jsonObject,"setBlock");
            NonNullList<Ingredient> ingredients = NonNullList.create();

            for (int i = 0; i < setBlockIngredient.size(); i++) {
                ingredients.add(Ingredient.fromJson(setBlockIngredient.get(i)));
            }

            ArrayList<Double> chanceList = new ArrayList<>();
            for (int i = 0; i < setBlockIngredient.size(); i++) {
                JsonObject obj = setBlockIngredient.get(i).getAsJsonObject();
                boolean hasChance = obj.has("chance");
                double chance = 1;
                if(hasChance){
                    JsonElement amount = obj.get("chance");
                    chance = Double.valueOf(String.valueOf(amount)).doubleValue();
                }
                chanceList.add(chance);
            }

            return new ChangeStoneRecipe(resourceLocation,checkBlockItemStack,ingredients,chanceList);
        }

        @Override
        public @Nullable ChangeStoneRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf buffer) {
            NonNullList<Ingredient> outputs = NonNullList.withSize(buffer.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < outputs.size(); i++) {
                outputs.set(i, Ingredient.fromNetwork(buffer));
            }

            ItemStack checkBlockItem = buffer.readItem();
            return new ChangeStoneRecipe(resourceLocation,checkBlockItem,outputs,null);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ChangeStoneRecipe changeStoneRecipe) {
            buffer.writeInt(changeStoneRecipe.setBlockItem.size());

            buffer.writeItemStack(changeStoneRecipe.getCheckBlockItem(),false);
        }
    }
}
