package net.yxiao233.realmofdestiny.helper.jei;

import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;

public class RegisterRecipesHelper {
    private IRecipeRegistration registration;
    private RecipeManager recipeManager;
    public RegisterRecipesHelper(IRecipeRegistration registration,RecipeManager recipeManager){
        this.registration = registration;
        this.recipeManager = recipeManager;
    }
    public IRecipeRegistration registration(){
        return this.registration;
    }
    public <C extends Container, T extends Recipe<C>> List<T> recipes(RecipeType<T> pRecipeType){
        return this.recipeManager.getAllRecipesFor(pRecipeType);
    }

    public <T> void addRecipes(mezz.jei.api.recipe.RecipeType<T> var1, List<T> var2) {
        registration.addRecipes(var1,var2);
    }
}
