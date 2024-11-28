package net.yxiao233.realmofdestiny.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.yxiao233.realmofdestiny.Entities.PedestalBlockEntity;
import net.yxiao233.realmofdestiny.ModRegistry.ModItems;
import net.yxiao233.realmofdestiny.helper.recipe.KeyToItemStackHelper;
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
    public ImageButton clearButton;
    private int yOffSet = 0;
    private int SCROLL_STEP = 3;
    public PedestalScreen(PedestalMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        setTEXTURE("textures/gui/pedestal_gui.png");
        addClearButton();
        addStructureButton();

        AtomicInteger size = new AtomicInteger();
        getRecipeList().forEach(recipe -> {
            if(recipe.getKeyItemStack() != KeyToItemStackHelper.EMPTY){
                size.getAndIncrement();
            }
        });
        this.SCROLL_STEP = (size.get()*24)/82;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        basicRenderBG(guiGraphics);
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
                Component component;
                if(Minecraft.getInstance().player.isCreative()){
                    component = Component.translatable("tip.realmofdestiny.structure_preview_creative");
                }else{
                    component = Component.translatable("tip.realmofdestiny.structure_preview");
                }
                guiGraphics.renderTooltip(Minecraft.getInstance().font,component,pMouseX-x,pMouseY-y);
            }
        });

        if(MouseHelper.isMouseOver(pMouseX,pMouseY,clearButton.getX(),clearButton.getY(),20,20)){
            int x1 = clearButton.getX()-x;
            int y1 = clearButton.getY()-y;
            clearButton.modImage.getPressTexture().render(guiGraphics,x1,y1);
            guiGraphics.renderTooltip(Minecraft.getInstance().font,Component.translatable("tip.realmofdestiny.structure_preview_clear"),pMouseX-x,pMouseY-y);
        }

        renderScrolledTexture(guiGraphics);

        addRecipesImages(guiGraphics);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double delta) {
        if(delta>0){
            yOffSet = Math.min(0,yOffSet+SCROLL_STEP);
        }else{
            yOffSet = Math.max(yOffSet-SCROLL_STEP,-56);
        }
        updateButton();
        return true;
    }


    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;


        if(MouseHelper.isMouseOver(pMouseX,pMouseY,x+155,y+9,12,72)){
            if(yOffSet >= -56 && yOffSet <= 0){
                if(pDragY>0){
                    yOffSet = Math.min(0,-(int)(pMouseY-y-13));
                    yOffSet = Math.max(yOffSet,-56);
                }else{
                    yOffSet = Math.max(-(int)(pMouseY-y-13),-56);
                    yOffSet = Math.min(yOffSet,0);
                }
            }
        }

        updateButton();

        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }


    @Override
    protected void containerTick() {
        super.containerTick();

        if(yOffSet > 0){
            yOffSet = 0;
        }else if(yOffSet < -56){
            yOffSet = -56;
        }

        updateButton();
    }

    private void addClearButton(){
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        ImageButton clearButton = new ImageButton(x + imageWidth,y + 1,Component.empty(), button -> {
            Minecraft.getInstance().player.sendSystemMessage(Component.translatable("tip.realmofdestiny.clear_structure").withStyle(ChatFormatting.GOLD));
            menu.blockEntity.setStructureId(-1);
            menu.blockEntity.setPressed(false);
        }, getImage(AllScreenTextures.BASIC_BUTTON,AllScreenTextures.PRESS_BASIC_BUTTON));


        this.addRenderableWidget(clearButton);
        this.clearButton = clearButton;
    }

    private void addStructureButton() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        List<PedestalGeneratorRecipe> recipeList = getRecipeList();

        AtomicInteger firstX = new AtomicInteger(x + (imageWidth / 2) - 30);
        AtomicInteger firstY = new AtomicInteger(y + 7);

        AtomicInteger i = new AtomicInteger(0);
        recipeList.forEach(recipe -> {
            final int currentI = i.get();
            if(recipe.getKeyItemStack() != KeyToItemStackHelper.EMPTY){
                ImageButton imageButton = new ImageButton(firstX.get(),firstY.get(),Component.empty(),button1 -> {
                    renderStructure(currentI);
                    setIfPlayerIsCreative(recipe);
                },getImage(AllScreenTextures.STRUCTURE_BUTTON,AllScreenTextures.PRESS_STRUCTURE_BUTTON));

                firstY.getAndAdd(24);

                this.addRenderableWidget(imageButton);
                addToMouseListener(imageButton);
            }
            i.getAndIncrement();
        });
    }

    private void renderScrolledTexture(GuiGraphics guiGraphics) {
        int x = (imageWidth / 2) + 68;
        AllScreenTextures.SCROLLED.render(guiGraphics,x,7-yOffSet);
    }

    public void addRecipesImages(GuiGraphics guiGraphics){
        int firstX = (imageWidth / 2) - 27;
        AtomicInteger firstY = new AtomicInteger(9);
        List<PedestalGeneratorRecipe> recipeList = getRecipeList();
        recipeList.forEach(recipe -> {
            if(recipe.getKeyItemStack() != KeyToItemStackHelper.EMPTY){
                int cY = firstY.get()+yOffSet*SCROLL_STEP;
                if(cY <= 7 || cY-16 >= 7 + imageHeight - 18*4 - 50){

                }else{
                    guiGraphics.renderItem(recipe.getPedestalItemStack(),firstX,cY);
                    AllScreenTextures.RIGHT_ARROW.render(guiGraphics,firstX+17,cY+4);
                    guiGraphics.renderItem(recipe.getResultItem(null),firstX+34,cY);
                }

                firstY.getAndAdd(24);
            }
        });
    }

    public void updateButton(){
        int y = (height - imageHeight) / 2;

        buttons.forEach(button ->{
            button.setY(button.finalY+yOffSet*SCROLL_STEP);

            if(button.getY() <= y+5 || button.getY()-button.getHeight() >= y + imageHeight - 18*4 - 50) {
                button.setY(10000);
            }
        });
    }

    public List<PedestalGeneratorRecipe> getRecipeList(){
        return menu.blockEntity.getPedestalGeneratorRecipeList();
    }

    public <T extends ImageButton> void addToMouseListener(T t){
        buttons.add(t);
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

    public void setIfPlayerIsCreative(PedestalGeneratorRecipe recipe){
        assert Minecraft.getInstance().player != null;
        if(Minecraft.getInstance().player.isCreative()){
            setStructure(menu.blockEntity,recipe);
        }
    }

    private void setStructure(PedestalBlockEntity blockEntity, PedestalGeneratorRecipe recipe){
        char[][][] patternsList = recipe.getPatternsList();
        int[] pedestal = findPedestal(patternsList);
        for (int y = 0; y < patternsList.length; y++) {
            for (int x = 0; x < patternsList[y].length; x++) {
                for (int z = 0; z < patternsList[y][x].length; z++) {
                    final int[] offSet = {-x,-y,-z};
                    setBlock(blockEntity,offSet,pedestal,recipe,patternsList[y][x][z]);
                }
            }
        }
    }

    private void setBlock(PedestalBlockEntity blockEntity, int[] offSet, int[] pedestal, PedestalGeneratorRecipe recipe, char key){
        Level level = blockEntity.getLevel();
        if(level.isClientSide()){
            ItemStack itemStack = recipe.getKeyItemStack().getCurrentItemStack(String.valueOf(key));
            if(itemStack != null && !itemStack.is(ModItems.PEDESTAL_ITEM.get())){
                BlockState blockState = Block.byItem(itemStack.getItem()).defaultBlockState();
                boolean b = level.setBlockAndUpdate(getCurrentBlockPos(blockEntity,offSet,pedestal),blockState);
            }
        }
    }

    private BlockPos getCurrentBlockPos(PedestalBlockEntity blockEntity, int[] offSet, int[] pedestal){
        BlockPos offSetPedestal = blockEntity.getBlockPos().offset(pedestal[0],pedestal[1],pedestal[2]);
        return offSetPedestal.offset(offSet[0],offSet[1],offSet[2]);
    }

    private int[] findPedestal(char[][][] patternsList) {
        for (int y = 0; y < patternsList.length; y++) {
            for (int x = 0; x < patternsList[y].length; x++) {
                for (int z = 0; z < patternsList[y][x].length; z++) {
                    if(String.valueOf(patternsList[y][x][z]).equals("P")){
                        return new int[]{x,y,z};
                    }
                }
            }
        }
        return new int[]{0,0,0};
    }
}