package net.yxiao233.realmofdestiny.modInterfaces.jei;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ScreenElement {
    @OnlyIn(Dist.CLIENT)
    void render(GuiGraphics guiGraphics, int x, int y);
    int getWidth();
    int getHeight();
}
