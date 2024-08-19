package net.yxiao233.realmofdestiny.compact.JEI;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.yxiao233.realmofdestiny.RealmOfDestiny;

public enum AllJEITextures implements ScreenElement {
    CHANCE_SLOT("widgets",20, 156, 18, 18),
    BASIC_SLOT("widgets",0,0,18,18),
    DOWN_ARROW("widgets",0, 21, 18, 14);
    public final ResourceLocation location;
    public int width, height;
    public int startX, startY;
    private AllJEITextures(String location, int startX, int startY, int width, int height) {
        this.location = new ResourceLocation(RealmOfDestiny.MODID, "textures/jei/" + location + ".png");
        this.width = width;
        this.height = height;
        this.startX = startX;
        this.startY = startY;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int x, int y) {
        guiGraphics.blit(location, x, y, startX, startY, width, height);
    }
}
