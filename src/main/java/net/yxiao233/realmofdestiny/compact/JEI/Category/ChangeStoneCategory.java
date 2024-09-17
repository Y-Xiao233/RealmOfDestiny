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
import net.minecraft.world.item.ItemStack;
import net.yxiao233.realmofdestiny.ModRegistry.ModItems;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.recipes.ChangeStoneRecipe;

public class ChangeStoneCategory extends BaseJEICategory<ChangeStoneRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(RealmOfDestiny.MODID,"block_change");
    public static final RecipeType<ChangeStoneRecipe> BLOCK_CHANGE_TYPE = new RecipeType<>(UID, ChangeStoneRecipe.class);
    public static final Component TITLE = Component.translatable("item.realmofdestiny.change_stone");

    public ChangeStoneCategory(IGuiHelper helper) {
        super(helper,BLOCK_CHANGE_TYPE,TITLE,ModItems.CHANGE_STONE.get(),176,100);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, ChangeStoneRecipe changeStoneRecipe, IFocusGroup iFocusGroup) {
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,80,11)
                .addItemStack(changeStoneRecipe.getCheckBlockItem())
                .setBackground(drawSlot(INEVITABLE),-1,-1);
        int size = changeStoneRecipe.getIngredients().size();
        int y = 59;
        int x = size>= 3 ? 34 : 45;
        if(size == 1) x = 56;
        for (int i = 0; i < size; i++) {
            x += 23;
            if(i % 3 == 0 && i != 0){
                y += 23;
                x = 56;
            }
            ItemStack stack = changeStoneRecipe.getIngredients().get(i).getItems()[0];
            double chance = changeStoneRecipe.getChanceList().get(i);
            if(chance < 1){
                iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT,x,y)
                        .addItemStack(stack)
                        .addTooltipCallback(addChanceTooltip(chance))
                        .setBackground(drawSlot(chance),-1,-1);
            }else{
                iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT,x,y)
                        .addItemStack(stack)
                        .setBackground(drawSlot(INEVITABLE),-1,-1);;
            }
        }
    }

    @Override
    public void draw(ChangeStoneRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        drawDownArrow(guiGraphics,85,30);
    }
}
