package net.yxiao233.realmofdestiny.compact.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotTooltipCallback;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.gui.AllGuiTextures;
import net.yxiao233.realmofdestiny.util.TooltipCallBackHelper;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractJEICategory<T extends Recipe<?>> implements IRecipeCategory<T> {
    public final RecipeType<T> type;
    public final Component title;
    public final IDrawable background;
    public final IDrawable icon;
    public static final double INEVITABLE = 1;
    public abstract net.minecraft.world.item.crafting.RecipeType<T> getTypeInstance();

    public AbstractJEICategory(IGuiHelper helper, RecipeType<T> type, Component title, Item icon, int width, int height) {
        ResourceLocation TEXTURE = new ResourceLocation(RealmOfDestiny.MODID,"textures/gui/empty.png");
        this.type = type;
        this.title = title;
        this.background = helper.createDrawable(TEXTURE,0,0,width,height);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(icon));
    }
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
    public abstract void setRecipe(IRecipeLayoutBuilder builder, T t, IFocusGroup iFocusGroup);
    @Override
    public abstract void draw(T recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY);
    //draw
    public void addTooltips(GuiGraphics guiGraphics, int width, int height, Component[] components, int x, int y,double mouseX, double mouseY){
        List<FormattedCharSequence> list = new ArrayList<>();
        Font font = Minecraft.getInstance().font;

        if(isMouseAbove(width,height,x,y,mouseX,mouseY)) {
            for (Component component : components) {
                list.add(component.getVisualOrderText());
            }

            guiGraphics.renderTooltip(font,list,(int) mouseX, (int) mouseY);
        }
    }

    public void addTooltipOnTexture(GuiGraphics guiGraphics, int width, int height, Component component, int x, int y,double mouseX, double mouseY){
        Font font = Minecraft.getInstance().font;
        if(isMouseAbove(width,height,x,y,mouseX,mouseY)) {
            guiGraphics.renderTooltip(font, component, (int) mouseX, (int) mouseY);
        }
    }
    public void drawTextureWithTooltip(GuiGraphics guiGraphics, AllGuiTextures allGuiTextures, Component component, int x, int y, double mouseX, double mouseY){
        allGuiTextures.render(guiGraphics,x,y);
        addTooltipOnTexture(guiGraphics,allGuiTextures.width,allGuiTextures.height,component,x,y,mouseX,mouseY);
    }

    public void addTooltip(GuiGraphics guiGraphics, int width, int height, Component component, int x, int y,double mouseX, double mouseY){
        Font font = Minecraft.getInstance().font;
        if(isMouseAbove(width,height,x,y,mouseX,mouseY)) {
            guiGraphics.renderTooltip(font, component, (int) mouseX, (int) mouseY);
        }
    }

    public void addSimpleEnergyTooltip(GuiGraphics guiGraphics, int energy, int width, int height, int x, int y, double mouseX, double mouseY){
        addTooltip(guiGraphics,width,height,Component.translatable("gui.realmofdestiny.consume_energy").withStyle(ChatFormatting.GOLD).append(Component.literal(String.valueOf(energy)).withStyle(ChatFormatting.WHITE)).append(Component.literal(" FE").withStyle(ChatFormatting.GOLD)),x,y,mouseX,mouseY);
    }

    private boolean isMouseAbove(int width, int height, int x, int y,double mouseX, double mouseY){
        return mouseX >= x && mouseY >= y && mouseX <= width + x && mouseY <= height + y;
    }

    //builder
    public IRecipeSlotTooltipCallback addText(String translatableKey, ChatFormatting style){
        return (view, tooltip) ->{
            tooltip.add(1,Component.translatable(translatableKey).withStyle(style));
        };
    }

    public IRecipeSlotTooltipCallback addText(TooltipCallBackHelper... tooltips){
        return (view, tooltip) ->{
            for (int i = 0; i < tooltips.length; i++) {
                tooltip.add(tooltips[i].getComponent());
            }
        };
    }

    public IDrawable drawSlot(double chance){
        AllGuiTextures allJEITextures = null;

        if(chance >= INEVITABLE){
            allJEITextures = AllGuiTextures.BASIC_SLOT;
        }else{
            allJEITextures = AllGuiTextures.CHANCE_SLOT;
        }

        AllGuiTextures finalAllJEITextures = allJEITextures;
        return new IDrawable() {
            @Override
            public int getWidth() {
                return finalAllJEITextures.width;
            }

            @Override
            public int getHeight() {
                return finalAllJEITextures.height;
            }

            @Override
            public void draw(GuiGraphics guiGraphics, int i, int i1) {
                finalAllJEITextures.render(guiGraphics,i,i1);
            }
        };
    }
    public IRecipeSlotTooltipCallback addChanceTooltip(double chance){
        if(chance >= 1){
            return null;
        }
        return (view, tooltip) ->{
            tooltip.add(1,Component.translatable("gui.realmofdestiny.chance", (chance >= 0.01 ? (int) (chance * 100) : "< 1") + "%").withStyle(ChatFormatting.GOLD));
        };
    }
}
