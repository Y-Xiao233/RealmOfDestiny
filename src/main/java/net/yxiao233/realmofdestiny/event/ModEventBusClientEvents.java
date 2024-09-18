package net.yxiao233.realmofdestiny.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderItemInFrameEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.yxiao233.realmofdestiny.Entities.renderer.BaseFluidTankRenderer;
import net.yxiao233.realmofdestiny.Entities.renderer.PedestalRenderer;
import net.yxiao233.realmofdestiny.Items.BaseFluidTankItem;
import net.yxiao233.realmofdestiny.ModRegistry.ModBlockEntities;
import net.yxiao233.realmofdestiny.ModRegistry.ModItems;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import org.cyclops.cyclopscore.helper.RenderHelpers;
import org.joml.Matrix4f;

@Mod.EventBusSubscriber(modid = RealmOfDestiny.MODID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerBlockEntityRenderer(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(ModBlockEntities.PEDESTAL_BE.get(), PedestalRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.BASE_FLUID_TANK_BE.get(), BaseFluidTankRenderer::new);
    }
}
