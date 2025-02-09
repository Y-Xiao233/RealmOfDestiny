package net.yxiao233.realmofdestiny.registry;

import com.hrznstudio.titanium.module.DeferredRegistryHelper;
import com.hrznstudio.titanium.recipe.serializer.GenericSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.api.module.IModule;
import net.yxiao233.realmofdestiny.recipe.PedestalGeneratorRecipe;

public class ModRecipes implements IModule {
    public static RegistryObject<RecipeSerializer<?>> PEDESTAL_SERIALIZER;
    public static RegistryObject<RecipeType<?>> PEDESTAL_TYPE;
    @Override
    public void generateFeatures(DeferredRegistryHelper helper) {
        PEDESTAL_SERIALIZER = helper.registerGeneric(ForgeRegistries.RECIPE_SERIALIZERS.getRegistryKey(), "pedestal_generator", () -> new GenericSerializer<>(PedestalGeneratorRecipe.class, PEDESTAL_TYPE));
        PEDESTAL_TYPE = helper.registerGeneric(ForgeRegistries.RECIPE_TYPES.getRegistryKey(), "pedestal_generator", () -> RecipeType.simple(new ResourceLocation(RealmOfDestiny.MODID, "pedestal_generator")));
    }
}
