package net.yxiao233.realmofdestiny.compact.JEI;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.yxiao233.realmofdestiny.ModRegistry.ModBlocks;
import net.yxiao233.realmofdestiny.ModRegistry.ModItems;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.recipes.ChangeStoneRecipe;
import net.yxiao233.realmofdestiny.recipes.GemPolishingRecipe;
import org.jetbrains.annotations.Nullable;

public class ChangeStoneCategory implements IRecipeCategory<ChangeStoneRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(RealmOfDestiny.MODID,"block_change");
    public static final ResourceLocation TEXTURE = new ResourceLocation(RealmOfDestiny.MODID,"textures/gui/change_block_gui.png");
    public static final RecipeType<ChangeStoneRecipe> BLOCK_CHANGE_TYPE = new RecipeType<>(UID, ChangeStoneRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;

    public ChangeStoneCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE,0,0,176,85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(ModItems.CHANGE_STONE.get()));
    }

    @Override
    public RecipeType<ChangeStoneRecipe> getRecipeType() {
        return BLOCK_CHANGE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("item.realmofdestiny.change_stone");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, ChangeStoneRecipe changeStoneRecipe, IFocusGroup iFocusGroup) {
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,80,11).addItemStack(changeStoneRecipe.getCheckBlockItem());
        int x = 34,y = 0;
        for (int i = 0; i < changeStoneRecipe.getIngredients().size(); i++) {
            x += 23;
            if(i % 3 == 0){
                y += 59;
            }
            ItemStack stack = changeStoneRecipe.getIngredients().get(i).getItems()[0];
            iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT,x,y).addItemStack(stack);
        }
    }

    @Override
    public void draw(ChangeStoneRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
    }
}
