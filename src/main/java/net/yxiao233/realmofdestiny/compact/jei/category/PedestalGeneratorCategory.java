package net.yxiao233.realmofdestiny.compact.jei.category;

import com.hrznstudio.titanium.client.screen.addon.EnergyBarScreenAddon;
import com.hrznstudio.titanium.client.screen.asset.DefaultAssetProvider;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.yxiao233.realmofdestiny.api.jei.AbstractJEICategory;
import net.yxiao233.realmofdestiny.compact.jei.ModRecipeType;
import net.yxiao233.realmofdestiny.config.machine.PedestalConfig;
import net.yxiao233.realmofdestiny.gui.AllGuiTextures;
import net.yxiao233.realmofdestiny.recipe.PedestalGeneratorRecipe;
import net.yxiao233.realmofdestiny.registry.ModBlocks;
import net.yxiao233.realmofdestiny.registry.ModItems;
import net.yxiao233.realmofdestiny.registry.ModRecipes;
import net.yxiao233.realmofdestiny.registry.ModTags;
import net.yxiao233.realmofdestiny.util.TooltipCallBackHelper;

public class PedestalGeneratorCategory extends AbstractJEICategory<PedestalGeneratorRecipe> {
    public static final Component TITLE = Component.translatable("block.realmofdestiny.pedestal");
    public PedestalGeneratorCategory(IGuiHelper helper) {
        super(helper, ModRecipeType.PEDESTAL_GENERATOR, TITLE, ModBlocks.PEDESTAL.get().asItem(), 160, 82);
    }

    @Override
    public RecipeType getTypeInstance() {
        return ModRecipes.PEDESTAL_TYPE.get();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, PedestalGeneratorRecipe recipe, IFocusGroup iFocusGroup) {
        //Input
        double inputConsumeChance = recipe.inputConsumeChance;
        if(inputConsumeChance <= 0){
            builder.addSlot(RecipeIngredientRole.INPUT, 36, 26)
                    .addIngredients(Ingredient.of(recipe.previewItem.getItems().stream()))
                    .addTooltipCallback(addText(
                            new TooltipCallBackHelper("gui.realmofdestiny.on_pedestal",ChatFormatting.GOLD),
                            new TooltipCallBackHelper("gui.realmofdestiny.not_consume",ChatFormatting.RED)
                    )).setBackground(drawBasicSlot(),-1,-1);
        }else{
            builder.addSlot(RecipeIngredientRole.INPUT, 36, 26)
                    .addIngredients(Ingredient.of(recipe.previewItem.getItems().stream()))
                    .addTooltipCallback(addText(
                            new TooltipCallBackHelper("gui.realmofdestiny.on_pedestal",ChatFormatting.GOLD),
                            new TooltipCallBackHelper("gui.realmofdestiny.consume",ChatFormatting.RED,((inputConsumeChance >= 0.01 ? (int) (inputConsumeChance * 100) : "< 1") + "%"))
                    )).setBackground(drawSlot(inputConsumeChance),-1,-1);
        }

        //Output
        int x = 89;
        int y = 13;
        if(recipe.output.length <= 3){
            y = 33;
        }
        if(recipe.output.length == 1){
            x = 109;
        }
        double chance = recipe.outputChance;
        for (int i = 0; i < recipe.output.length; i++) {
            ItemStack stack = recipe.output[i];
            if(chance >= 1){
                builder.addSlot(RecipeIngredientRole.OUTPUT,x,y)
                        .addIngredient(VanillaTypes.ITEM_STACK,stack)
                        .setBackground(drawSlot(chance),-1,-1);
            }else{
                builder.addSlot(RecipeIngredientRole.OUTPUT,x,y)
                        .addIngredient(VanillaTypes.ITEM_STACK,stack)
                        .setBackground(drawSlot(chance),-1,-1)
                        .addTooltipCallback(addChanceTooltip(chance));
            }
            x += 20;
            if(i % 3 == 0 && i != 0){
                y += 20;
            }
        }

        //Upgrade
        builder.addSlot(RecipeIngredientRole.CATALYST,1,62)
                .addIngredients(Ingredient.of(ModTags.Items.PEDESTAL_UPGRADE_AUGMENT))
                .setBackground(drawBasicSlot(),-1,-1)
                .addTooltipCallback(addText("gui.realmofdestiny.pedestal.open_menu",ChatFormatting.BLUE));
    }

    @Override
    public void draw(PedestalGeneratorRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        //EnergyBar
        int energy = recipe.energy;
        if(energy > 0){
            EnergyBarScreenAddon.drawBackground(guiGraphics, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER, 0, 2, 0, 0);
            EnergyBarScreenAddon.drawForeground(guiGraphics, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER, 0, 2, 0, 0, energy, (int) Math.max(PedestalConfig.maxStoredPower, Math.ceil(energy)));
            addSimpleEnergyTooltip(guiGraphics,energy,18,56,0,2,mouseX,mouseY);
        }

        //ProgressBar
        drawTextureWithTooltip(guiGraphics, AllGuiTextures.ARROW_HORIZONTAL,Component.translatable("gui.realmofdestiny.processingTime",recipe.processingTime),60,33,mouseX,mouseY);

        //Pedestal
        guiGraphics.renderItem(ModItems.PEDESTAL_ITEM.get().getDefaultInstance(),36,42);
    }
}