package net.yxiao233.realmofdestiny.compact.JEI.Category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.yxiao233.realmofdestiny.ModRegistry.ModItems;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.compact.JEI.AllJEITextures;
import net.yxiao233.realmofdestiny.helper.jei.TooltipCallBackHelper;
import net.yxiao233.realmofdestiny.recipes.PedestalLightingRecipe;

public class PedestalLightingCategory extends BaseJEICategory<PedestalLightingRecipe> implements IBaseJEICategory<PedestalLightingRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(RealmOfDestiny.MODID,"pedestal_lighting");
    public static final RecipeType<PedestalLightingRecipe> PEDESTAL_LIGHTING_TYPE = new RecipeType<>(UID, PedestalLightingRecipe.class);
    public static final Component TITLE = Component.translatable("recipe.realmofdestiny.pedestal_lighting");
    public PedestalLightingCategory(IGuiHelper helper) {
        super(helper, PEDESTAL_LIGHTING_TYPE, TITLE, ModItems.PEDESTAL_ITEM.get(), 176, 176);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, PedestalLightingRecipe recipe, IFocusGroup iFocusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT,120,110)
                .addTooltipCallback(addText(
                        new TooltipCallBackHelper("recipe.realmofdestiny.pedestal_generator.in_pedestal", ChatFormatting.RED),
                        new TooltipCallBackHelper("recipe.realmofdestiny.pedestal_lighting.leaves",ChatFormatting.GOLD))
                )
                .addItemStack(recipe.getPedestalItemStack())
                .setBackground(drawBasiceSlot(),-1,-1);

        if(recipe.getChance() >= 1){
            builder.addSlot(RecipeIngredientRole.OUTPUT,120,49)
                    .addItemStack(recipe.getOutput())
                    .setBackground(drawBasiceSlot(),-1,-1);
        }else{
            builder.addSlot(RecipeIngredientRole.OUTPUT,120,49)
                    .addTooltipCallback(addChanceTooltip(recipe.getChance()))
                    .addItemStack(recipe.getOutput())
                    .setBackground(drawChanceSlot(),-1,-1);
        }

        builder.addSlot(RecipeIngredientRole.INPUT,0,158)
                .addItemStack(ModItems.BOLT_SAPLING_ITEM.get().getDefaultInstance())
                .setBackground(drawBasiceSlot(),-1,-1);
    }

    @Override
    public void draw(PedestalLightingRecipe recipe, IRecipeSlotsView slotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiGraphics.renderFakeItem(ModItems.PEDESTAL_ITEM.get().getDefaultInstance(),120,125);

        drawTextureWithTooltip(guiGraphics,AllJEITextures.UP_ARROW,
                Component.translatable("recipe.realmofdestiny.progress",recipe.getTime()),
                125,78,mouseX,mouseY);

        if(recipe.getEnergy() > 0){
            drawTextureWithTooltip(guiGraphics,AllJEITextures.ENERGY_FILLED,
                    Component.translatable("recipe.realmofdestiny.energy",recipe.getEnergy()),
                    155,70,mouseX,mouseY);
        }

        AllJEITextures.BOLT_TREE.render(guiGraphics,20,40);
    }
}
