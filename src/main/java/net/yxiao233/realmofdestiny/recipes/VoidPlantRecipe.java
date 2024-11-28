package net.yxiao233.realmofdestiny.recipes;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.modAbstracts.recipe.AbstractVoidMachineRecipe;
import net.yxiao233.realmofdestiny.utils.AdvancedItemStack;
import net.yxiao233.realmofdestiny.utils.BufferUtil;
import net.yxiao233.realmofdestiny.utils.RecipeList;
import net.yxiao233.realmofdestiny.utils.SerializerUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class VoidPlantRecipe extends AbstractVoidMachineRecipe {
    public VoidPlantRecipe(AdvancedItemStack input, ArrayList<AdvancedItemStack> outputs, int time, ResourceLocation id) {
        super(input, outputs, time, id);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<VoidPlantRecipe> {
        public static final VoidPlantRecipe.Type INSTANCE = new VoidPlantRecipe.Type();
        public static final String ID = "void_plant";
    }

    public static class Serializer implements RecipeSerializer<VoidPlantRecipe>{
        public static final VoidPlantRecipe.Serializer INSTANCE = new VoidPlantRecipe.Serializer();
        public static final ResourceLocation ID = new ResourceLocation(RealmOfDestiny.MODID, "void_plant");

        @Override
        public VoidPlantRecipe fromJson(ResourceLocation id, JsonObject jsonObject) {
            RecipeList list = SerializerUtil.listFromJson(jsonObject);
            return new VoidPlantRecipe(list.getInput(),list.getOutputs(),list.getTime(),id);
        }

        @Override
        public @Nullable VoidPlantRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            RecipeList list = BufferUtil.fromNetWork(buffer);
            return new VoidPlantRecipe(list.getInput(),list.getOutputs(),list.getTime(),id);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, VoidPlantRecipe recipe) {
            BufferUtil.toNetWork(buffer,recipe);
        }
    }
}
