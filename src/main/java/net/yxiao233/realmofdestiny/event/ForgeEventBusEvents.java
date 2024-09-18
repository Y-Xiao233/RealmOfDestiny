package net.yxiao233.realmofdestiny.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderItemInFrameEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.yxiao233.realmofdestiny.ModRegistry.ModItems;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import org.joml.Matrix4f;

@Mod.EventBusSubscriber(modid = RealmOfDestiny.MODID,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventBusEvents {
    @SubscribeEvent
    public static void renderItem(RenderItemInFrameEvent event){
        ItemStack stack = event.getItemStack();

        if(stack.is(ModItems.BASE_FLUID_TANK_ITEM.get())){
            if(stack.hasTag()){
                String location = stack.getTag().getString("FluidName");
                String path = location.substring(0,location.indexOf(':'));
                String name = location.substring(location.indexOf(':'));
                ResourceLocation texture = new ResourceLocation(path + "textures/blocks/" + name + ".png");
                render(texture,event.getPoseStack());
            }
        }
    }

    public static void render(ResourceLocation texture, PoseStack poseStack){
        RenderSystem.setShaderTexture(0,texture);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        drawFluid(poseStack);
    }

    public static void drawFluid(PoseStack poseStack){
        int width = 16;
        int height = 16;

        poseStack.pushPose();
        {
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder buffer = tesselator.getBuilder();
            Matrix4f matrix = poseStack.last().pose();

            buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            buffer.vertex(matrix, 0, height, 0).uv(0, 1).endVertex();
            buffer.vertex(matrix, width, height, 0).uv(1, 1).endVertex();
            buffer.vertex(matrix, width, 0, 0).uv(1, 0).endVertex();
            buffer.vertex(matrix, 0, 0, 0).uv(0, 0).endVertex();
            tesselator.end();
        }
        poseStack.popPose();
    }
}
