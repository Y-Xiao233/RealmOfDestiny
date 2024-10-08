package net.yxiao233.realmofdestiny.helper.jei;

import mezz.jei.api.registration.IRecipeCatalystRegistration;
import net.minecraft.world.item.Item;

public class RegisterRecipeCatalystHelper {
    private IRecipeCatalystRegistration registration;
    public RegisterRecipeCatalystHelper(IRecipeCatalystRegistration registration){
        this.registration = registration;
    }
    public void addRecipeCatalyst(Item item, mezz.jei.api.recipe.RecipeType<?> recipeType){
        registration.addRecipeCatalyst(item.getDefaultInstance(),recipeType);
    }
}
