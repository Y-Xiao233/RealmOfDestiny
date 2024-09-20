package net.yxiao233.realmofdestiny.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.yxiao233.realmofdestiny.helper.screen.MouseHelper;
import net.yxiao233.realmofdestiny.modAbstracts.screen.AbstractModContainerScreen;
import net.yxiao233.realmofdestiny.modInterfaces.screen.IFluidTankRenderer;
import net.yxiao233.realmofdestiny.modTextures.AllScreenTextures;
import net.yxiao233.realmofdestiny.screen.button.ImageButton;
import net.yxiao233.realmofdestiny.screen.renderer.FluidTankRenderer;

import java.util.ArrayList;

public class BaseFluidTankScreen extends AbstractModContainerScreen<BaseFluidTankMenu> implements IFluidTankRenderer {
    private FluidTankRenderer renderer;
    public BaseFluidTankScreen(BaseFluidTankMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        setTEXTURE("textures/gui/base_fluid_tank_gui.png");
        assignFluidRenderer();
    }

    @Override
    public void assignFluidRenderer() {
        renderer = new FluidTankRenderer(menu.blockEntity.getTankCap(), true, 16, 61,40);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        basicRenderBG(guiGraphics, v, i, i1);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        renderer.render(guiGraphics,x + 77,y,menu.getFluidStackInTank());
    }

    @Override
    public void renderLabels(GuiGraphics guiGraphics, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        renderFluidAreaTooltips(guiGraphics, renderer, menu.getFluidStackInTank(), pMouseX, pMouseY, x, y);
    }
}
