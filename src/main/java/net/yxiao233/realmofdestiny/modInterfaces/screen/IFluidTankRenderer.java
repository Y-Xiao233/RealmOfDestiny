package net.yxiao233.realmofdestiny.modInterfaces.screen;

import net.minecraft.client.gui.GuiGraphics;

public interface IFluidTankRenderer {
    void assignFluidRenderer();
    void renderLabels(GuiGraphics guiGraphics, int pMouseX, int pMouseY);
}
