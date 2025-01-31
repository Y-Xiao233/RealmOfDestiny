package net.yxiao233.realmofdestiny.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.block.renderer.PedestalRenderer;
import net.yxiao233.realmofdestiny.registry.ModBlockEntities;

@Mod.EventBusSubscriber(modid = RealmOfDestiny.MODID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerBlockEntityRenderer(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(ModBlockEntities.PEDESTAL_BE.get(),PedestalRenderer::new);
    }
}
