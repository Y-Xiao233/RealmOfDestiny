package net.yxiao233.realmofdestiny.compact.JEI;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.recipes.ChangeStoneRecipe;
import net.yxiao233.realmofdestiny.recipes.GemPolishingRecipe;
import net.yxiao233.realmofdestiny.screen.ChangeStoneScreen;
import net.yxiao233.realmofdestiny.screen.GemPolishingStationScreen;

import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(RealmOfDestiny.MODID,"jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        //Gem Polishing Station
        registration.addRecipeCategories(new GemPolishingStationCategory(registration.getJeiHelpers().getGuiHelper()));
        //Change Stone
        registration.addRecipeCategories(new ChangeStoneCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
        //Gem Polishing Station
        List<GemPolishingRecipe> polishingRecipes = recipeManager.getAllRecipesFor(GemPolishingRecipe.Type.INSTANCE);
        registration.addRecipes(GemPolishingStationCategory.GEM_POLISHING_TYPE,polishingRecipes);
        //Change Stone
        List<ChangeStoneRecipe> changeStoneRecipes = recipeManager.getAllRecipesFor(ChangeStoneRecipe.Type.INSTANCE);
        registration.addRecipes(ChangeStoneCategory.BLOCK_CHANGE_TYPE,changeStoneRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        //Gem Polishing Station
        registration.addRecipeClickArea(GemPolishingStationScreen.class,70,30,20,30,
                GemPolishingStationCategory.GEM_POLISHING_TYPE);
        //Change Stone
        registration.addRecipeClickArea(ChangeStoneScreen.class,70,30,20,30,
                ChangeStoneCategory.BLOCK_CHANGE_TYPE);
    }
}
