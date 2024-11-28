package net.yxiao233.realmofdestiny.compact.JEI.Category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.yxiao233.realmofdestiny.ModRegistry.ModItems;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.modAbstracts.jei.AbstractJEICategory;
import net.yxiao233.realmofdestiny.modTextures.AllJEITextures;
import net.yxiao233.realmofdestiny.recipes.VoidPlantRecipe;

public class VoidPlantMachineCategory extends AbstractJEICategory<VoidPlantRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(RealmOfDestiny.MODID,"void_plant");
    public static final mezz.jei.api.recipe.RecipeType<VoidPlantRecipe> VOID_PLANT_TYPE = new mezz.jei.api.recipe.RecipeType<>(UID, VoidPlantRecipe.class);
    public static final Component TITLE = Component.translatable("recipe.realmofdestiny.void_plant_machine");
    public VoidPlantMachineCategory(IGuiHelper helper) {
        super(helper, VOID_PLANT_TYPE, TITLE, ModItems.VOID_PLANT_MACHINE_ITEM.get(), 176, 100);
    }
    @Override
    public RecipeType<VoidPlantRecipe> getTypeInstance() {
        return VoidPlantRecipe.Type.INSTANCE;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, VoidPlantRecipe recipe, IFocusGroup iFocusGroup) {

        double inputChance = recipe.getInput().getChance();
        if(inputChance < 1){
            builder.addSlot(RecipeIngredientRole.INPUT,80,11)
                    .addTooltipCallback(addChanceTooltip(inputChance))
                    .addItemStack(recipe.getInput().getItemStack())
                    .setBackground(drawSlot(inputChance),-1,-1);
        }else{
            builder.addSlot(RecipeIngredientRole.OUTPUT,80,11)
                    .addItemStack(recipe.getInput().getItemStack())
                    .setBackground(drawSlot(inputChance),-1,-1);
        }

        int size = recipe.getOutputs().size();
        int y = 59;
        int x = size>= 3 ? 34 : 45;
        if(size == 1) x = 56;
        for (int i = 0; i < size; i++) {
            double chance = recipe.getOutputs().get(i).getChance();
            x += 23;
            if(i % 3 == 0 && i != 0){
                y += 23;
                x = 56;
            }
            if(chance < 1){
                builder.addSlot(RecipeIngredientRole.OUTPUT,x,y)
                        .addTooltipCallback(addChanceTooltip(chance))
                        .addItemStack(recipe.getOutputs().get(i).getItemStack())
                        .setBackground(drawSlot(chance),-1,-1);
            }else{
                builder.addSlot(RecipeIngredientRole.OUTPUT,x,y)
                        .addItemStack(recipe.getOutputs().get(i).getItemStack())
                        .setBackground(drawSlot(chance),-1,-1);
            }
        }
    }

    @Override
    public void draw(VoidPlantRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {

        drawTextureWithTooltip(guiGraphics, AllJEITextures.DOWN_ARROW,
                Component.translatable("recipe.realmofdestiny.progress",recipe.getTime()),
                85,30,mouseX,mouseY);
    }
}
