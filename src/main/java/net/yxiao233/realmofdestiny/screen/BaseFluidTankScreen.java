package net.yxiao233.realmofdestiny.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import net.yxiao233.realmofdestiny.helper.screen.MouseHelper;
import net.yxiao233.realmofdestiny.screen.renderer.FluidTankRenderer;

import java.util.Optional;

public class BaseFluidTankScreen extends AbstractModContainerScreen<BaseFluidTankMenu> implements IFluidTankRenderer{
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
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        super.render(guiGraphics, mouseX, mouseY, delta);
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
