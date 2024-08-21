package net.yxiao233.realmofdestiny.ModRegistry;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.recipes.ChangeStoneRecipe;
import net.yxiao233.realmofdestiny.recipes.GemPolishingRecipe;
import net.yxiao233.realmofdestiny.recipes.PedestalGeneratorRecipe;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, RealmOfDestiny.MODID);

    public static final RegistryObject<RecipeSerializer<GemPolishingRecipe>> GEM_POLISHING_SERIALIZER =
            SERIALIZERS.register("gem_polishing", () -> GemPolishingRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<ChangeStoneRecipe>> BLOCK_CHANGE_SERIALIZER =
            SERIALIZERS.register("block_change", () -> ChangeStoneRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<PedestalGeneratorRecipe>> PEDESTAL_GENERATOR_SERIALIZER =
            SERIALIZERS.register("pedestal_generator", () -> PedestalGeneratorRecipe.Serializer.INSTANCE);
}
