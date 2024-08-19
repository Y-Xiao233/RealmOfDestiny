package net.yxiao233.realmofdestiny.compact.JEI;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotTooltipCallback;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.yxiao233.realmofdestiny.ModRegistry.ModItems;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.recipes.ChangeStoneRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

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
            double chance = changeStoneRecipe.getChanceList().get(i);
            iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT,x,y).addItemStack(stack)
                    .addTooltipCallback((view ,tooltip) -> {
                        if(chance != 1){
                            tooltip.add(1, Component.translatable("recipe.realmofdestiny.changestone.chance",
                                    (chance >= 0.01 ? (int) (chance * 100) : "<1") + "%")
                                    .withStyle(ChatFormatting.GOLD));
                        }
                    });
        }
    }
}
