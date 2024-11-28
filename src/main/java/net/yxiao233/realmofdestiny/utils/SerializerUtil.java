package net.yxiao233.realmofdestiny.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;

import java.util.ArrayList;

import static net.minecraft.util.datafix.fixes.BlockEntitySignTextStrictJsonFix.GSON;

public class SerializerUtil {
    private static ArrayList<AdvancedItemStack> getOutputs(JsonObject jsonObject){
        ArrayList<AdvancedItemStack> list = new ArrayList<>();

        JsonArray outputArray = GsonHelper.getAsJsonArray(jsonObject,"output");
        outputArray.forEach(element -> {
            JsonObject obj = element.getAsJsonObject();
            if(obj.has("item")){
                ItemStack itemStack = Ingredient.fromJson(element).getItems()[0];
                double chance = 1.0D;
                int count = 1;
                CompoundTag nbt = null;
                if(obj.has("count")){
                    count = Integer.parseInt(String.valueOf(obj.get("count")));
                }
                if(obj.has("chance")){
                    chance = Double.parseDouble(String.valueOf(obj.get("chance")));
                }
                if(obj.has("nbt")){
                    JsonElement jsonNBT = obj.get("nbt");
                    try {
                        if (jsonNBT.isJsonObject()) {
                            nbt = TagParser.parseTag(GSON.toJson(jsonNBT));
                        } else {
                            nbt = TagParser.parseTag(GsonHelper.convertToString(jsonNBT,"nbt"));
                        }
                    } catch (CommandSyntaxException e) {
                        throw new RuntimeException(e);
                    }
                }
                list.add(new AdvancedItemStack(new ItemStack(itemStack.getItem(),count,nbt),chance));
            }
        });

        return list;
    }

    private static AdvancedItemStack getInput(JsonObject jsonObject){

        ItemStack input = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject,"input"));
        JsonObject object = jsonObject.getAsJsonObject("input");

        double chance = 1.0D;
        CompoundTag nbt = null;
        int count = 1;
        if(object.has("chance")){
            chance = Double.parseDouble(String.valueOf(object.get("chance")));
        }
        if(object.has("nbt")){
            JsonElement jsonNBT = object.get("nbt");
            try {
                if (jsonNBT.isJsonObject()) {
                    nbt = TagParser.parseTag(GSON.toJson(jsonNBT));
                } else {
                    nbt = TagParser.parseTag(GsonHelper.convertToString(jsonNBT,"nbt"));
                }
            } catch (CommandSyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        if(object.has("count")){
            count = Integer.parseInt(String.valueOf(object.get("count")));
        }

        return new AdvancedItemStack(new ItemStack(input.getItem(),count,nbt),chance);
    }

    private static int getEnergy(JsonObject jsonObject){
        if(jsonObject.has("energy")){
            return Integer.parseInt(String.valueOf(jsonObject.get("energy")));
        }
        return 0;
    }

    private static int getTime(JsonObject jsonObject){
        if(jsonObject.has("time")){
            return Integer.parseInt(String.valueOf(jsonObject.get("time")));
        }
        return 10;
    }

    public static RecipeList listFromJson(JsonObject jsonObject){
        int time = getTime(jsonObject);
        int energy = getEnergy(jsonObject);
        AdvancedItemStack input = getInput(jsonObject);
        ArrayList<AdvancedItemStack> outputs = getOutputs(jsonObject);

        return new RecipeList(input,outputs,energy,time);
    }
}
