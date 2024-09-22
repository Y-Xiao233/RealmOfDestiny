package net.yxiao233.realmofdestiny.modTextures;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.modInterfaces.jei.ScreenElement;

public enum AllScreenTextures implements ScreenElement {
    BASIC_BUTTON("widgets",0, 0, 20, 20),
    PRESS_BASIC_BUTTON("widgets",0,20,20,20),
    STRUCTURE_BUTTON("widgets",0,40,55,20),
    PRESS_STRUCTURE_BUTTON("widgets",0,60,55,20),
    RIGHT_ARROW("widgets",0,249,15,7),
    SCROLLED("widgets",232,0,12,15);
    public final ResourceLocation location;
    public int width, height;
    public int startX, startY;
    private AllScreenTextures(String location, int startX, int startY, int width, int height) {
        this.location = new ResourceLocation(RealmOfDestiny.MODID, "textures/gui/" + location + ".png");
        this.width = width;
        this.height = height;
        this.startX = startX;
        this.startY = startY;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int x, int y) {
        guiGraphics.blit(location, x, y, startX, startY, width, height);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
