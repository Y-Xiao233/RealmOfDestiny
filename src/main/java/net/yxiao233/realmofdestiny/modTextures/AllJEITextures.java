package net.yxiao233.realmofdestiny.modTextures;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.modInterfaces.jei.ScreenElement;

public enum AllJEITextures implements ScreenElement {
    CHANCE_SLOT("widgets",20, 156, 18, 18),
    BASIC_SLOT("widgets",0,0,18,18),
    DOWN_ARROW("arrow",0, 0, 7, 25),
    UP_ARROW("arrow",0, 230, 7, 25),
    ENERGY_FILLED("energy_filled",0,0,14,42),
    BOLT_TREE("bolt_tree",0,0,77,91),
    TIP("widgets",249,0,7,7);
    public final ResourceLocation location;
    public final int width, height;
    public final int startX, startY;
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

    @Override
    public void render(GuiGraphics guiGraphics, int x, int y, int extraValue, ExtraType extraType) {
        if(extraType == ExtraType.HEIGHT){
            guiGraphics.blit(location, x, y, startX, startY, width, extraValue);
        }else{
            guiGraphics.blit(location, x, y, startX, startY, extraValue, height);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int x, int y, int width, int height) {
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
