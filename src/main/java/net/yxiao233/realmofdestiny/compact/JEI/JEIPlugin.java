package net.yxiao233.realmofdestiny.compact.JEI;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.yxiao233.realmofdestiny.ModRegistry.ModItems;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.compact.JEI.Category.ChangeStoneCategory;
import net.yxiao233.realmofdestiny.compact.JEI.Category.GemPolishingStationCategory;
import net.yxiao233.realmofdestiny.compact.JEI.Helper.RegisterRecipeCatalystHelper;
import net.yxiao233.realmofdestiny.compact.JEI.Helper.RegisterRecipesHelper;
import net.yxiao233.realmofdestiny.recipes.ChangeStoneRecipe;
import net.yxiao233.realmofdestiny.recipes.GemPolishingRecipe;
import net.yxiao233.realmofdestiny.screen.GemPolishingStationScreen;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(RealmOfDestiny.MODID,"jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();

        IRecipeCategory<?>[] list = {
                new GemPolishingStationCategory(helper),
                new ChangeStoneCategory(helper)
        };

        registration.addRecipeCategories(list);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RegisterRecipesHelper helper = new RegisterRecipesHelper(registration,Minecraft.getInstance().level.getRecipeManager());

        helper.addRecipes(GemPolishingStationCategory.GEM_POLISHING_TYPE,helper.recipes(GemPolishingRecipe.Type.INSTANCE));
        helper.addRecipes(ChangeStoneCategory.BLOCK_CHANGE_TYPE,helper.recipes(ChangeStoneRecipe.Type.INSTANCE));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        //Gem Polishing Station
        registration.addRecipeClickArea(GemPolishingStationScreen.class,70,30,20,30,
                GemPolishingStationCategory.GEM_POLISHING_TYPE);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        RegisterRecipeCatalystHelper helper = new RegisterRecipeCatalystHelper(registration);

        helper.addRecipeCatalyst(ModItems.GEM_POLISHING_STATION_ITEM.get(),GemPolishingStationCategory.GEM_POLISHING_TYPE);
        helper.addRecipeCatalyst(ModItems.CHANGE_STONE.get(),ChangeStoneCategory.BLOCK_CHANGE_TYPE);
    }
}
