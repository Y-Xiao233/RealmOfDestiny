package net.yxiao233.realmofdestiny.modAbstracts.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.fluids.FluidStack;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.helper.screen.MouseHelper;
import net.yxiao233.realmofdestiny.modInterfaces.jei.ScreenElement;
import net.yxiao233.realmofdestiny.modInterfaces.screen.IModImage;
import net.yxiao233.realmofdestiny.screen.renderer.FluidTankRenderer;
import org.cyclops.cyclopscore.client.gui.image.IImage;
import org.jetbrains.annotations.Nullable;

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
        renderTooltip(guiGraphics, mouseX, mouseY);
        super.render(guiGraphics, mouseX, mouseY, delta);
    }

    @Override
    protected abstract void renderBg(GuiGraphics guiGraphics, float v, int i, int i1);

    public void basicRenderBG(GuiGraphics guiGraphics){
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


    public <T extends ScreenElement> IModImage getImage(T t, @Nullable T pt){
        return new IModImage() {
            @Override
            public T getTexture() {
                return t;
            }

            @Override
            public T getPressTexture() {
                if(pt != null){
                    return pt;
                }
                return null;
            }

            @Override
            public void draw(GuiGraphics guiGraphics, int i, int i1) {
                t.render(guiGraphics,i,i1);
            }

            @Override
            public void drawWithColor(GuiGraphics guiGraphics, int i, int i1, float v, float v1, float v2, float v3) {

            }

            @Override
            public void drawWorldWithAlpha(TextureManager textureManager, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1, float v, float v1, float v2, float v3, float v4, float v5) {

            }

            @Override
            public int getWidth() {
                return t.getWidth();
            }

            @Override
            public int getHeight() {
                return t.getHeight();
            }
        };
    }


    public <T extends TextureAtlasSprite> IImage getBlockIconImage(T t){
        return new IImage() {
            @Override
            public void draw(GuiGraphics guiGraphics, int i, int i1) {
                guiGraphics.blit(i,i1,0,16,16,t);
            }

            @Override
            public void drawWithColor(GuiGraphics guiGraphics, int i, int i1, float v, float v1, float v2, float v3) {

            }

            @Override
            public void drawWorldWithAlpha(TextureManager textureManager, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1, float v, float v1, float v2, float v3, float v4, float v5) {

            }

            @Override
            public int getWidth() {
                return 16;
            }

            @Override
            public int getHeight() {
                return 16;
            }
        };
    }


    public IImage getItemStackImage(ItemStack stack){
        return new IImage() {
            @Override
            public void draw(GuiGraphics guiGraphics, int i, int i1) {
                guiGraphics.renderItem(stack,i,i1);
            }

            @Override
            public void drawWithColor(GuiGraphics guiGraphics, int i, int i1, float v, float v1, float v2, float v3) {

            }

            @Override
            public void drawWorldWithAlpha(TextureManager textureManager, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1, float v, float v1, float v2, float v3, float v4, float v5) {

            }

            @Override
            public int getWidth() {
                return 16;
            }

            @Override
            public int getHeight() {
                return 16;
            }
        };
    }
}
