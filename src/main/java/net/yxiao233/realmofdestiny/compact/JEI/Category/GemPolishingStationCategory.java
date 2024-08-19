package net.yxiao233.realmofdestiny.compact.JEI.Category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.yxiao233.realmofdestiny.ModRegistry.ModItems;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.compact.JEI.AllJEITextures;
import net.yxiao233.realmofdestiny.recipes.ChangeStoneRecipe;
import net.yxiao233.realmofdestiny.recipes.GemPolishingRecipe;

public class GemPolishingStationCategory extends BaseJEICategory<GemPolishingRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(RealmOfDestiny.MODID,"gem_polishing");
    public static final RecipeType<GemPolishingRecipe> GEM_POLISHING_TYPE = new RecipeType<>(UID, GemPolishingRecipe.class);
    public static final Component TITLE = Component.translatable("block.realmofdestiny.gem_polishing_station");

    public GemPolishingStationCategory(IGuiHelper helper) {
        super(helper,GEM_POLISHING_TYPE,TITLE, ModItems.GEM_POLISHING_STATION_ITEM.get(),176,85);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, GemPolishingRecipe gemPolishingRecipe, IFocusGroup iFocusGroup) {
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,80,11)
                .addItemStack(gemPolishingRecipe.getItemStacks()[0])
                .setBackground(drawBasiceSlot(AllJEITextures.BASIC_SLOT),-1,-1);

        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT,80,59)
                .addItemStack(gemPolishingRecipe.getResultItem(null))
                .setBackground(drawBasiceSlot(AllJEITextures.BASIC_SLOT),-1,-1);;
    }

    @Override
    public void draw(GemPolishingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        AllJEITextures.DOWN_ARROW.render(guiGraphics,85,30);
    }
}
