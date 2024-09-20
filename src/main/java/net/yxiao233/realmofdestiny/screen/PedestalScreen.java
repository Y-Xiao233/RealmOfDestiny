package net.yxiao233.realmofdestiny.screen;


import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.yxiao233.realmofdestiny.Entities.PedestalBlockEntity;
import net.yxiao233.realmofdestiny.helper.screen.MouseHelper;
import net.yxiao233.realmofdestiny.modAbstracts.screen.AbstractModContainerScreen;
import net.yxiao233.realmofdestiny.modTextures.AllScreenTextures;
import net.yxiao233.realmofdestiny.recipes.PedestalGeneratorRecipe;
import net.yxiao233.realmofdestiny.screen.button.ImageButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PedestalScreen extends AbstractModContainerScreen<PedestalMenu> {
    public ArrayList<ImageButton> buttons = new ArrayList<>();
    public PedestalScreen(PedestalMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        setTEXTURE("textures/gui/blank_gui.png");
        addClearButton();
        addButton();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        basicRenderBG(guiGraphics, v, i, i1);
    }

    private void addClearButton(){
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        ImageButton chooseRecipeButton = new ImageButton(x + imageWidth,y + 1,Component.empty(), button -> {
            Minecraft.getInstance().player.sendSystemMessage(Component.translatable("tip.realmofdestiny.clear_structure").withStyle(ChatFormatting.GOLD));
            menu.blockEntity.setStructureId(-1);
            menu.blockEntity.setPressed(false);
        }, getImage(AllScreenTextures.BASIC_BUTTON,AllScreenTextures.PRESS_BASIC_BUTTON));


        this.addRenderableWidget(chooseRecipeButton);
        addToMouseListener(chooseRecipeButton);
    }

    private void addButton() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        List<PedestalGeneratorRecipe> recipeList = getRecipeList();

        AtomicInteger firstX = new AtomicInteger(x + (imageWidth / 2) - 20);
        AtomicInteger firstY = new AtomicInteger(y + 2);

        AtomicInteger i = new AtomicInteger(0);
        recipeList.forEach(recipe -> {
            final int currentI = i.get();
            ImageButton imageButton = new ImageButton(firstX.get(),firstY.get(),Component.empty(),button1 -> {
                renderStructure(currentI);
            },getImage(AllScreenTextures.STRUCTURE_BUTTON,AllScreenTextures.PRESS_STRUCTURE_BUTTON));

            firstY.getAndAdd(24);

            i.getAndIncrement();
            this.addRenderableWidget(imageButton);
            addToMouseListener(imageButton);
        });
    }

    public void renderStructure(int i){
        Minecraft.getInstance().player.sendSystemMessage(Component.translatable("tip.realmofdestiny.render_structure").withStyle(ChatFormatting.GOLD));
        PedestalBlockEntity blockEntity = menu.blockEntity;

        if(blockEntity.isPressed() && blockEntity.getStructureId() != -1){
            blockEntity.setStructureId(i);
        }else{
            blockEntity.setPressed(!blockEntity.isPressed());
            blockEntity.setStructureId(i);
        }
    }

    @Override
    public void renderLabels(GuiGraphics guiGraphics, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        buttons.forEach(button -> {
            if(MouseHelper.isMouseOver(pMouseX,pMouseY,button.getX(),button.getY(),55,20) && button.modImage.getPressTexture() != null){
                int x1 = button.getX()-x;
                int y1 = button.getY()-y;
                button.modImage.getPressTexture().render(guiGraphics,x1,y1);
            }
        });

        addImage(guiGraphics);

//        addButtonToolTip();
    }

    public void addImage(GuiGraphics guiGraphics){
        int x = (width - imageWidth) / 2;
        int y = (width - imageHeight) / 2;

        int firstX = x + (imageWidth / 2) - 17;
        AtomicInteger firstY = new AtomicInteger(y + 4);
        List<PedestalGeneratorRecipe> recipeList = getRecipeList();
        recipeList.forEach(recipe -> {
            guiGraphics.renderItem(recipe.getPedestalItemStack(),firstX-x,firstY.get()-y);
            firstY.getAndAdd(24);
        });
    }

    public List<PedestalGeneratorRecipe> getRecipeList(){
        return menu.blockEntity.getPedestalGeneratorRecipeList();
    }

    public <T extends ImageButton> void addToMouseListener(T t){
        buttons.add(t);
    }

    //TODO
    //  为清除按钮添加文本提示
    //  为各个按钮添加鼠标悬浮Tooltip
    //  添加 Mouse Scrolled
}
