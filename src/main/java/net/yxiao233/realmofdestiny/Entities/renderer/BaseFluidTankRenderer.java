package net.yxiao233.realmofdestiny.Entities.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.yxiao233.realmofdestiny.Entities.BaseFluidTankBlockEntity;
import org.apache.commons.lang3.tuple.Triple;
import org.cyclops.cyclopscore.helper.Helpers;
import org.cyclops.cyclopscore.helper.RenderHelpers;
import org.joml.Matrix4f;

public class BaseFluidTankRenderer implements BlockEntityRenderer<BaseFluidTankBlockEntity> {
    public BaseFluidTankRenderer(BlockEntityRendererProvider.Context context){
    }

    //TODO
    // 更改render逻辑,使其渲染界面贴合流体储罐四周的空白处
    @Override
    public void render(BaseFluidTankBlockEntity entity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int combinedLight, int combinedOverlay) {
        if(entity != null) {
            FluidStack fluid = entity.getFluidStackInTank();
            RenderHelpers.renderFluidContext(fluid, poseStack, () -> {
                float height = (fluid.getAmount() * 0.3125F) / entity.getTankCap() + 0.6875F;
                int brightness = Math.max(combinedLight, fluid.getFluid().getFluidType().getLightLevel(fluid));
                int l2 = brightness >> 0x10 & 0xFFFF;
                int i3 = brightness & 0xFFFF;

                TextureAtlasSprite icon = RenderHelpers.getFluidIcon(fluid, Direction.UP);
                IClientFluidTypeExtensions renderProperties = IClientFluidTypeExtensions.of(fluid.getFluid());
                Triple<Float, Float, Float> color = Helpers.intToRGB(renderProperties.getTintColor(fluid.getFluid().defaultFluidState(), entity.getLevel(), entity.getBlockPos()));

                VertexConsumer vb = multiBufferSource.getBuffer(RenderType.text(icon.atlasLocation()));
                Matrix4f matrix = poseStack.last().pose();
                vb.vertex(matrix, 0.125F, height, 0.125F).color(color.getLeft(), color.getMiddle(), color.getRight(), 1).uv(icon.getU0(), icon.getV1()).uv2(l2, i3).endVertex();
                vb.vertex(matrix, 0.125F, height, 0.875F).color(color.getLeft(), color.getMiddle(), color.getRight(), 1).uv(icon.getU0(), icon.getV0()).uv2(l2, i3).endVertex();
                vb.vertex(matrix, 0.875F, height, 0.875F).color(color.getLeft(), color.getMiddle(), color.getRight(), 1).uv(icon.getU1(), icon.getV0()).uv2(l2, i3).endVertex();
                vb.vertex(matrix, 0.875F, height, 0.125F).color(color.getLeft(), color.getMiddle(), color.getRight(), 1).uv(icon.getU1(), icon.getV1()).uv2(l2, i3).endVertex();
            });
        }
    }
}
