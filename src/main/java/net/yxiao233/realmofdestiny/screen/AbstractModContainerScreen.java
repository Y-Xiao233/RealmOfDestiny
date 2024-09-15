package net.yxiao233.realmofdestiny.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.fluids.FluidStack;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.helper.screen.MouseHelper;
import net.yxiao233.realmofdestiny.screen.renderer.FluidTankRenderer;

import java.util.Optional;

public abstract class AbstractModContainerScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
    private ResourceLocation TEXTURE;
    public AbstractModContainerScreen(T pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    public void setTEXTURE(String path){
        this.TEXTURE = new ResourceLocation(RealmOfDestiny.MODID,path);
    }

    @Override
    protected void init() {
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected abstract void renderBg(GuiGraphics guiGraphics, float v, int i, int i1);

    public void basicRenderBG(GuiGraphics guiGraphics, float v, int i, int i1){
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
    }

    public void renderFluidAreaTooltips(GuiGraphics guiGraphics, FluidTankRenderer renderer, FluidStack stack, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(renderer,pMouseX, pMouseY, x, y, 77, 0)) {
            guiGraphics.renderTooltip(this.font,renderer.getTooltip(stack, TooltipFlag.Default.NORMAL),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    public boolean isMouseAboveArea(FluidTankRenderer renderer, int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY) {
        return MouseHelper.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, renderer.getWidth(), renderer.getHeight());
    }
}
