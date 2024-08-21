package net.yxiao233.realmofdestiny.compact.JEI.Category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.yxiao233.realmofdestiny.ModRegistry.ModItems;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.compact.JEI.AllJEITextures;
import net.yxiao233.realmofdestiny.recipes.ChangeStoneRecipe;
import net.yxiao233.realmofdestiny.recipes.PedestalGeneratorRecipe;

public class PedestalGeneratorCategory extends BaseJEICategory<PedestalGeneratorRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(RealmOfDestiny.MODID,"pedestal_generator");
    public static final RecipeType<PedestalGeneratorRecipe> PEDESTAL_GENERATOR_TYPE = new RecipeType<>(UID, PedestalGeneratorRecipe.class);
    public static final Component TITLE = Component.translatable("block.realmofdestiny.pedestal");
    public PedestalGeneratorCategory(IGuiHelper helper) {
        super(helper,PEDESTAL_GENERATOR_TYPE,TITLE, ModItems.PEDESTAL_ITEM.get(),176,176);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, PedestalGeneratorRecipe recipe, IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT,80,110)
                .addTooltipCallback(addText("recipe.realmofdestiny.pedestal_generator.in_pedestal",ChatFormatting.RED))
                .addItemStack(recipe.getPedestalItemStack());

        int size = recipe.getOutputItemStack().length;
        int y = 49;
        int x = size>= 3 ? 34 : 45;
        if(size == 1) x = 58;
        for (int i = 0; i < size; i++) {
            x += 23;
            if(i % 3 == 0 && i != 0){
                y -= 23;
                x = 58;
            }
            ItemStack stack = recipe.getOutputItemStack()[i];
            double chance = recipe.getChanceList().get(i);
            if(chance != 1){
                builder.addSlot(RecipeIngredientRole.OUTPUT,x,y)
                        .addItemStack(stack)
                        .addTooltipCallback(addChanceTooltip(chance))
                        .setBackground(drawChanceSlot(AllJEITextures.CHANCE_SLOT),-1,-1);
            }else{
                builder.addSlot(RecipeIngredientRole.OUTPUT,x,y)
                        .addItemStack(stack)
                        .setBackground(drawBasiceSlot(AllJEITextures.BASIC_SLOT),-1,-1);;
            }
        }
    }

    @Override
    public void draw(PedestalGeneratorRecipe recipe, IRecipeSlotsView slotsView, GuiGraphics gui, double mouseX, double mouseY) {
        drawUpArrow(gui,85,80);
        gui.renderFakeItem(ModItems.PEDESTAL_ITEM.get().getDefaultInstance(),80,125);
    }
}
