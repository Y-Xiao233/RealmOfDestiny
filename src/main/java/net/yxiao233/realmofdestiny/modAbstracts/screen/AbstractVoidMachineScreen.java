package net.yxiao233.realmofdestiny.modAbstracts.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.yxiao233.realmofdestiny.helper.screen.ProgressArrowHelper;
import net.yxiao233.realmofdestiny.modInterfaces.jei.ScreenElement;
import net.yxiao233.realmofdestiny.modTextures.AllScreenTextures;

public abstract class AbstractVoidMachineScreen<T extends AbstractVoidMachineMenu<?>> extends AbstractModContainerScreen<T>{
    public AbstractVoidMachineScreen(T pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1){
        //所有槽位的x,y都要比menu中少1
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        basicRenderBG(guiGraphics);
        renderSlot(guiGraphics);
        renderBasicArrow(guiGraphics,x,y);
        renderProgressArrow(guiGraphics,x,y);
        renderTitle(guiGraphics,x,y);
    }

    public abstract ProgressArrowHelper setProgressArrowLocation();
    public void renderProgressArrow(GuiGraphics guiGraphics,int x, int y){
        if(menu.isCrafting()) {
            ProgressArrowHelper helper = setProgressArrowLocation();
            int[] location = helper.getLocation();
            Axis axis = helper.getAxis();
            if(axis == Axis.X){
                renderXProgressArrow(guiGraphics,x,y,location);
            }else if(axis == Axis.Y){
                renderYProgressArrow(guiGraphics,x,y,location);
            }
        }
    }

    public void renderXProgressArrow(GuiGraphics guiGraphics, int x, int y, int[] location){
        AllScreenTextures.LONG_RIGHT_PROGRESS_ARROW.render(guiGraphics,x+location[0],y+location[1],menu.getScaledProgress(),ScreenElement.ExtraType.WIDTH);
    }

    public void renderYProgressArrow(GuiGraphics guiGraphics, int x, int y, int[] location){
        AllScreenTextures.LONG_RIGHT_PROGRESS_ARROW.render(guiGraphics,x+location[0],y+location[1],menu.getScaledProgress(),ScreenElement.ExtraType.HEIGHT);
    }

    public void renderBasicArrow(GuiGraphics guiGraphics, int x, int y){
        int[] location = setProgressArrowLocation().getLocation();
        AllScreenTextures.LONG_RIGHT_ARROW.render(guiGraphics,x+location[0],y+location[1]);
    }

    private void renderTitle(GuiGraphics guiGraphics, int x, int y) {
        String machineId = menu.blockEntity.getBlockState().getBlock().getDescriptionId();
        int greenColor = (255 << 24) | (0) | (255 << 8) | (0);
        Font font = Minecraft.getInstance().font;

        Component machineInfo = Component.translatable(machineId);

        guiGraphics.drawCenteredString(font,machineInfo,x+85,y+7,greenColor);
    }

    public void renderSlot(GuiGraphics guiGraphics) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        int startX = 89;
        int startY = 24;
        for (int i = 0; i < menu.blockEntity.itemHandler.getSlots(); i++) {
            if(i == 0){
                AllScreenTextures.BASIC_SLOT.render(guiGraphics,x+29,y+24);
            }else{
                AllScreenTextures.BASIC_SLOT.render(guiGraphics,x+startX,y+startY);
                startX += 20;
                if(i % 3 == 0){
                    startY += 20;
                    startX = 89;
                }
            }
        }
    }

    public enum Axis{
        X,
        Y;
    }
}
