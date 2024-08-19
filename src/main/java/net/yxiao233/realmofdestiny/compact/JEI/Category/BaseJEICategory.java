package net.yxiao233.realmofdestiny.compact.JEI.Category;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotTooltipCallback;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.compact.JEI.AllJEITextures;
import org.jetbrains.annotations.Nullable;

public abstract class BaseJEICategory<T extends Recipe<?>> implements IRecipeCategory<T> {
    public final RecipeType<T> type;
    public final Component title;
    public final IDrawable background;
    public final IDrawable icon;

    public BaseJEICategory(IGuiHelper helper, RecipeType<T> type, Component title, Item icon, int width, int height) {
        ResourceLocation TEXTURE = new ResourceLocation(RealmOfDestiny.MODID,"textures/gui/empty.png");
        this.type = type;
        this.title = title;
        this.background = helper.createDrawable(TEXTURE,0,0,width,height);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(icon));
    }
    //IRecipeCategory
    @Override
    public RecipeType<T> getRecipeType() {
        return type;
    }
    @Override
    public Component getTitle() {
        return title;
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
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, T t, IFocusGroup iFocusGroup) {
    }

    //method
    public IDrawable drawChanceSlot(AllJEITextures texture){
        return new IDrawable() {
            @Override
            public int getWidth() {
                return texture.width;
            }

            @Override
            public int getHeight() {
                return texture.height;
            }

            @Override
            public void draw(GuiGraphics guiGraphics, int x, int y) {
                texture.render(guiGraphics,x,y);
            }
        };
    }
    public IDrawable drawBasiceSlot(AllJEITextures texture){
        return new IDrawable() {
            @Override
            public int getWidth() {
                return texture.width;
            }

            @Override
            public int getHeight() {
                return texture.height;
            }

            @Override
            public void draw(GuiGraphics guiGraphics, int x, int y) {
                texture.render(guiGraphics,x,y);
            }
        };
    }
    public IRecipeSlotTooltipCallback addChanceTooltip(double chance){
        return (view, tooltip) ->{
            tooltip.add(1,Component.translatable("recipe.realmofdestiny.changestone.chance", (chance >= 0.01 ? (int) (chance * 100) : "< 1") + "%").withStyle(ChatFormatting.GOLD));
        };
    }

}
