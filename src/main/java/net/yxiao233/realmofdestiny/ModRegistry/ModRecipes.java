package net.yxiao233.realmofdestiny.ModRegistry;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.recipes.ChangeStoneRecipe;
import net.yxiao233.realmofdestiny.recipes.PedestalGeneratorRecipe;
import net.yxiao233.realmofdestiny.recipes.PedestalLightingRecipe;
import net.yxiao233.realmofdestiny.recipes.VoidPlantRecipe;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, RealmOfDestiny.MODID);

    public static final RegistryObject<RecipeSerializer<ChangeStoneRecipe>> BLOCK_CHANGE_SERIALIZER =
            SERIALIZERS.register("block_change", () -> ChangeStoneRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<PedestalGeneratorRecipe>> PEDESTAL_GENERATOR_SERIALIZER =
            SERIALIZERS.register("pedestal_generator", () -> PedestalGeneratorRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<PedestalLightingRecipe>> PEDESTAL_LIGHTING_SERIALIZER =
            SERIALIZERS.register("pedestal_lighting", () -> PedestalLightingRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<VoidPlantRecipe>> VOID_PLANT_SERIALIZER =
            SERIALIZERS.register("void_plant", () -> VoidPlantRecipe.Serializer.INSTANCE);
}
