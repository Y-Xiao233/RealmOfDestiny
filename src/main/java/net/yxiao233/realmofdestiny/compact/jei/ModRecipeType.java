package net.yxiao233.realmofdestiny.compact.jei;

import mezz.jei.api.recipe.RecipeType;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.recipe.PedestalGeneratorRecipe;

public class ModRecipeType {
    private static final String nameSpace = RealmOfDestiny.MODID;
    public static RecipeType<PedestalGeneratorRecipe> PEDESTAL_GENERATOR = RecipeType.create(nameSpace,"pedestal_generator", PedestalGeneratorRecipe.class);
}
