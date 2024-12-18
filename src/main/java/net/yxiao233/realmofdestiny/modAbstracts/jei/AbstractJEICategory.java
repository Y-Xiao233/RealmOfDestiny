package net.yxiao233.realmofdestiny.modAbstracts.jei;

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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.helper.recipe.KeyToItemStackHelper;
import net.yxiao233.realmofdestiny.modTextures.AllJEITextures;
import net.yxiao233.realmofdestiny.helper.jei.TooltipCallBackHelper;
import net.yxiao233.realmofdestiny.recipes.PedestalGeneratorRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractJEICategory<T extends Recipe<?>> implements IRecipeCategory<T> {
    public final RecipeType<T> type;
    public final Component title;
    public final IDrawable background;
    public final IDrawable icon;
    public static final double INEVITABLE = 1;
    public abstract net.minecraft.world.item.crafting.RecipeType<T> getTypeInstance();

    public AbstractJEICategory(IGuiHelper helper, RecipeType<T> type, Component title, Item icon, int width, int height) {
        ResourceLocation TEXTURE = new ResourceLocation(RealmOfDestiny.MODID,"textures/jei/empty.png");
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
    public abstract void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, T t, IFocusGroup iFocusGroup);

    @Override
    public abstract void draw(T recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY);

    //method
//    public IDrawable drawChanceSlot(){
//        AllJEITextures allJEITextures = AllJEITextures.CHANCE_SLOT;
//        return new IDrawable() {
//            @Override
//            public int getWidth() {
//                return allJEITextures.width;
//            }
//
//            @Override
//            public int getHeight() {
//                return allJEITextures.height;
//            }
//
//            @Override
//            public void draw(GuiGraphics guiGraphics, int x, int y) {
//                allJEITextures.render(guiGraphics,x,y);
//            }
//        };
//    }
//    public IDrawable drawBasiceSlot(){
//        AllJEITextures allJEITextures = AllJEITextures.BASIC_SLOT;
//        return new IDrawable() {
//            @Override
//            public int getWidth() {
//                return allJEITextures.width;
//            }
//
//            @Override
//            public int getHeight() {
//                return allJEITextures.height;
//            }
//
//            @Override
//            public void draw(GuiGraphics guiGraphics, int x, int y) {
//                allJEITextures.render(guiGraphics,x,y);
//            }
//        };
//    }

    public IDrawable drawSlot(double chance){
        AllJEITextures allJEITextures = null;

        if(chance >= INEVITABLE){
            allJEITextures = AllJEITextures.BASIC_SLOT;
        }else{
            allJEITextures = AllJEITextures.CHANCE_SLOT;
        }

        AllJEITextures finalAllJEITextures = allJEITextures;
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
            tooltip.add(1,Component.translatable("recipe.realmofdestiny.changestone.chance", (chance >= 0.01 ? (int) (chance * 100) : "< 1") + "%").withStyle(ChatFormatting.GOLD));
        };
    }

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

    public void drawDownArrow(GuiGraphics guiGraphics,int x, int y){
        AllJEITextures.DOWN_ARROW.render(guiGraphics,x,y);
    }

    public void drawUpArrow(GuiGraphics guiGraphics,int x, int y){
        AllJEITextures.UP_ARROW.render(guiGraphics,x,y);
    }

    public void drawTextureWithTooltip(GuiGraphics guiGraphics,AllJEITextures allJEITextures,Component component,int x, int y ,double mouseX, double mouseY){
        Font font = Minecraft.getInstance().font;

        allJEITextures.render(guiGraphics,x,y);

        if(mouseX >= x && mouseY >= y && mouseX <= allJEITextures.width + x && mouseY <= allJEITextures.height + y) {
            guiGraphics.renderTooltip(font, component, (int) mouseX, (int) mouseY);
        }
    }

    public <B extends PedestalGeneratorRecipe> void drawTextureWithTip(GuiGraphics guiGraphics, B recipe, int x, int y, double mouseX, double mouseY){
        boolean b = mouseX >= x && mouseY >= y && mouseX <= AllJEITextures.TIP.width + x && mouseY <= AllJEITextures.TIP.height + y;
        if(recipe.getKeyItemStack() == KeyToItemStackHelper.EMPTY){
            Font font = Minecraft.getInstance().font;
            AllJEITextures.TIP.render(guiGraphics,x,y);

            if(b) {
                guiGraphics.renderTooltip(font, Component.translatable("recipe.realmofdestiny.np_structure_required"), (int) mouseX, (int) mouseY);
            }
            return;
        }


        List<Component> components = new ArrayList<>();

        recipe.getKeyItemStack().getItemStacks().forEach(itemStack -> {
            String s = recipe.getKeyItemStack().getCurrentKey(itemStack);
            int count = 0;
            for (int i = 0; i < recipe.getPatternsList().length; i++) {
                for (int i1 = 0; i1 < recipe.getPatternsList()[i].length; i1++) {
                    for (int i2 = 0; i2 < recipe.getPatternsList()[i][i1].length; i2++) {
                        if(recipe.getPatternsList()[i][i1].length != ' ' && String.valueOf(recipe.getPatternsList()[i][i1][i2]).equals(s)){
                            count ++;
                        }
                    }
                }
            }

            if(!itemStack.isEmpty()){
                components.add(Component.literal(count+"x ").append(itemStack.getDisplayName()));
            }
        });

        Font font = Minecraft.getInstance().font;
        AllJEITextures.TIP.render(guiGraphics,x,y);

        if(b) {
            guiGraphics.renderTooltip(font, components, Optional.empty(), (int) mouseX, (int) mouseY);
        }
    }
}
