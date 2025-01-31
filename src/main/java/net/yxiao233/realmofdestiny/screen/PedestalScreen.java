package net.yxiao233.realmofdestiny.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.yxiao233.realmofdestiny.block.entity.PedestalBlockEntity;
import net.yxiao233.realmofdestiny.gui.AllGuiTextures;

import java.util.concurrent.atomic.AtomicInteger;

public class PedestalScreen extends AbstractModContainerScreen<PedestalMenu> {
    public PedestalScreen(PedestalMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        setTEXTURE("textures/gui/blank_gui.png");
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        basicRenderBG(guiGraphics);

        final int x = (width - imageWidth) / 2;
        final int y = (height - imageHeight) / 2;

        AllGuiTextures.BASIC_SLOT.render(guiGraphics,x + 51,y + 29);
        AllGuiTextures.BASIC_SLOT.render(guiGraphics,x + 71,y + 29);
        AllGuiTextures.BASIC_SLOT.render(guiGraphics,x + 91,y + 29);
        AllGuiTextures.BASIC_SLOT.render(guiGraphics,x + 111,y + 29);
    }
}