package net.yxiao233.realmofdestiny.compact.JEI;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ScreenElement {
    @OnlyIn(Dist.CLIENT)
    void render(GuiGraphics guiGraphics, int x, int y);
}
