package net.yxiao233.realmofdestiny.Entities.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.yxiao233.realmofdestiny.Entities.BaseFluidTankBlockEntity;
import org.apache.commons.lang3.tuple.Triple;
import org.cyclops.cyclopscore.helper.Helpers;
import org.cyclops.cyclopscore.helper.RenderHelpers;
import org.joml.Matrix4f;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class BaseFluidTankRenderer implements BlockEntityRenderer<BaseFluidTankBlockEntity> {
    public BaseFluidTankRenderer(BlockEntityRendererProvider.Context context){

    }

    @Override
    public void render(BaseFluidTankBlockEntity entity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int combinedLight, int combinedOverlay) {
        FluidStack fluid = entity.getFluidStackInTank();
        RenderHelpers.renderFluidContext(fluid, poseStack, () -> {
            float height = ((fluid.getAmount() * 1.0F) / entity.getTankCap());
            height = Math.min(height, 0.995F);
            int brightness = Math.max(combinedLight, fluid.getFluid().getFluidType().getLightLevel(fluid));
            int l2 = brightness >> 0x10 & 0xFFFF;
            int i3 = brightness & 0xFFFF;

            TextureAtlasSprite icon = RenderHelpers.getFluidIcon(fluid, Direction.UP);
            IClientFluidTypeExtensions renderProperties = IClientFluidTypeExtensions.of(fluid.getFluid());
            Triple<Float, Float, Float> color = Helpers.intToRGB(renderProperties.getTintColor(fluid.getFluid().defaultFluidState(), entity.getLevel(), entity.getBlockPos()));

            VertexConsumer vb = multiBufferSource.getBuffer(RenderType.text(icon.atlasLocation()));
            Matrix4f matrix = poseStack.last().pose();

            renderer(vb,matrix,height,color,icon,l2,i3);
        });
    }

    public void renderer(VertexConsumer vb,Matrix4f matrix, float height, Triple<Float,Float,Float> color, TextureAtlasSprite icon,int l2, int i3){

        // Top face
        vb.vertex(matrix, 0.005F, height, 0.005F).color(color.getLeft(), color.getMiddle(), color.getRight(), 1).uv(icon.getU0(), icon.getV1()).uv2(l2, i3).endVertex();
        vb.vertex(matrix, 0.005F, height, 0.995F).color(color.getLeft(), color.getMiddle(), color.getRight(), 1).uv(icon.getU0(), icon.getV0()).uv2(l2, i3).endVertex();
        vb.vertex(matrix, 0.995F, height, 0.995F).color(color.getLeft(), color.getMiddle(), color.getRight(), 1).uv(icon.getU1(), icon.getV0()).uv2(l2, i3).endVertex();
        vb.vertex(matrix, 0.995F, height, 0.005F).color(color.getLeft(), color.getMiddle(), color.getRight(), 1).uv(icon.getU1(), icon.getV1()).uv2(l2, i3).endVertex();

        // Bottom face
        vb.vertex(matrix, 0.005F, 0.005F, 0.005F).color(color.getLeft(), color.getMiddle(), color.getRight(), 1).uv(icon.getU0(), icon.getV1()).uv2(l2, i3).endVertex();
        vb.vertex(matrix, 0.995F, 0.005F, 0.005F).color(color.getLeft(), color.getMiddle(), color.getRight(), 1).uv(icon.getU0(), icon.getV0()).uv2(l2, i3).endVertex();
        vb.vertex(matrix, 0.995F, 0.005F, 0.995F).color(color.getLeft(), color.getMiddle(), color.getRight(), 1).uv(icon.getU1(), icon.getV0()).uv2(l2, i3).endVertex();
        vb.vertex(matrix, 0.005F, 0.005F, 0.995F).color(color.getLeft(), color.getMiddle(), color.getRight(), 1).uv(icon.getU1(), icon.getV1()).uv2(l2, i3).endVertex();

        // South face
        vb.vertex(matrix, 0.005F, 0.005F, 0.995F).color(color.getLeft(), color.getMiddle(), color.getRight(), 1).uv(icon.getU0(), icon.getV1()).uv2(l2, i3).endVertex();
        vb.vertex(matrix, 0.995F, 0.005F, 0.995F).color(color.getLeft(), color.getMiddle(), color.getRight(), 1).uv(icon.getU1(), icon.getV1()).uv2(l2, i3).endVertex();
        vb.vertex(matrix, 0.995F, height, 0.995F).color(color.getLeft(), color.getMiddle(), color.getRight(), 1).uv(icon.getU1(), icon.getV0()).uv2(l2, i3).endVertex();
        vb.vertex(matrix, 0.005F, height, 0.995F).color(color.getLeft(), color.getMiddle(), color.getRight(), 1).uv(icon.getU0(), icon.getV0()).uv2(l2, i3).endVertex();

        // North face
        vb.vertex(matrix, 0.005F, 0.005F, 0.005F).color(color.getLeft(), color.getMiddle(), color.getRight(), 1).uv(icon.getU0(), icon.getV0()).uv2(l2, i3).endVertex();
        vb.vertex(matrix, 0.005F, height, 0.005F).color(color.getLeft(), color.getMiddle(), color.getRight(), 1).uv(icon.getU0(), icon.getV1()).uv2(l2, i3).endVertex();
        vb.vertex(matrix, 0.995F, height, 0.005F).color(color.getLeft(), color.getMiddle(), color.getRight(), 1).uv(icon.getU1(), icon.getV1()).uv2(l2, i3).endVertex();
        vb.vertex(matrix, 0.995F, 0.005F, 0.005F).color(color.getLeft(), color.getMiddle(), color.getRight(), 1).uv(icon.getU1(), icon.getV0()).uv2(l2, i3).endVertex();

        // East face
        vb.vertex(matrix, 0.995F, 0.005F, 0.005F).color(color.getLeft(), color.getMiddle(), color.getRight(), 1).uv(icon.getU0(), icon.getV0()).uv2(l2, i3).endVertex();
        vb.vertex(matrix, 0.995F, height, 0.005F).color(color.getLeft(), color.getMiddle(), color.getRight(), 1).uv(icon.getU0(), icon.getV1()).uv2(l2, i3).endVertex();
        vb.vertex(matrix, 0.995F, height, 0.995F).color(color.getLeft(), color.getMiddle(), color.getRight(), 1).uv(icon.getU1(), icon.getV1()).uv2(l2, i3).endVertex();
        vb.vertex(matrix, 0.995F, 0.005F, 0.995F).color(color.getLeft(), color.getMiddle(), color.getRight(), 1).uv(icon.getU1(), icon.getV0()).uv2(l2, i3).endVertex();

        // West face
        vb.vertex(matrix, 0.005F, 0.005F, 0.995F).color(color.getLeft(), color.getMiddle(), color.getRight(), 1).uv(icon.getU0(), icon.getV0()).uv2(l2, i3).endVertex();
        vb.vertex(matrix, 0.005F, height, 0.995F).color(color.getLeft(), color.getMiddle(), color.getRight(), 1).uv(icon.getU0(), icon.getV1()).uv2(l2, i3).endVertex();
        vb.vertex(matrix, 0.005F, height, 0.005F).color(color.getLeft(), color.getMiddle(), color.getRight(), 1).uv(icon.getU1(), icon.getV1()).uv2(l2, i3).endVertex();
        vb.vertex(matrix, 0.005F, 0.005F, 0.005F).color(color.getLeft(), color.getMiddle(), color.getRight(), 1).uv(icon.getU1(), icon.getV0()).uv2(l2, i3).endVertex();
    }
}
