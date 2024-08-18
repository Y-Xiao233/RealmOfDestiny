package net.yxiao233.realmofdestiny.compact.JEI;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.yxiao233.realmofdestiny.ModRegistry.ModBlocks;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.recipes.GemPolishingRecipe;
import org.jetbrains.annotations.Nullable;

public class GemPolishingStationCategory implements IRecipeCategory<GemPolishingRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(RealmOfDestiny.MODID,"gem_polishing");
    public static final ResourceLocation TEXTURE = new ResourceLocation(RealmOfDestiny.MODID,"textures/gui/gem_polishing_station_gui.png");
    public static final RecipeType<GemPolishingRecipe> GEM_POLISHING_TYPE = new RecipeType<>(UID, GemPolishingRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;

    public GemPolishingStationCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE,0,0,176,85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(ModBlocks.GEM_POLISHING_STATION.get()));
    }

    @Override
    public RecipeType getRecipeType() {
        return this.GEM_POLISHING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.realmofdestiny.gem_polishing_station");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, GemPolishingRecipe gemPolishingRecipe, IFocusGroup iFocusGroup) {
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,80,11).addItemStack(gemPolishingRecipe.getItemStacks()[0]);
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT,80,59).addItemStack(gemPolishingRecipe.getResultItem(null));
    }
}
