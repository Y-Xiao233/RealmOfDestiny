package net.yxiao233.realmofdestiny.compact.JEI.Category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.crafting.Recipe;

public interface IBaseJEICategory<T extends Recipe<?>> extends IRecipeCategory<T> {
    void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup var3);

    void draw(T recipe, IRecipeSlotsView slotsView, GuiGraphics guiGraphics, double mouseX, double mouseY);
}
