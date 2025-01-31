package net.yxiao233.realmofdestiny.block.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.yxiao233.realmofdestiny.block.entity.PedestalBlockEntity;

import java.util.List;

public class PedestalRenderer implements BlockEntityRenderer<PedestalBlockEntity> {
    private float rotationAngle = 0.0f;
    public PedestalRenderer(BlockEntityRendererProvider.Context context){

    }
    @Override
    public void render(PedestalBlockEntity blockEntity, float v, PoseStack poseStack, MultiBufferSource buffer, int i, int i1) {
        ItemStack itemStack = blockEntity.getRenderStack();
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        rotationAngle += 0.5f;
        if (rotationAngle >= 360.0f) {
            rotationAngle = 0.0f;
        }

        poseStack.pushPose();
        poseStack.translate(0.5f,0.8f,0.5f);
        poseStack.scale(0.2f,0.2f,0.2f);
        poseStack.mulPose(Axis.YP.rotationDegrees(rotationAngle));

        itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED,getLightLevel(blockEntity.getLevel(),blockEntity.getBlockPos()),
                OverlayTexture.NO_OVERLAY,poseStack,buffer,blockEntity.getLevel(),1);
        poseStack.popPose();
    }


    private int getLightLevel(Level level, BlockPos blockPos){
        int bLight = level.getBrightness(LightLayer.BLOCK,blockPos);
        int sLight = level.getBrightness(LightLayer.SKY,blockPos);
        return LightTexture.pack(bLight,sLight);
    }
}