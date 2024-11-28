package net.yxiao233.realmofdestiny;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.yxiao233.realmofdestiny.ModRegistry.*;
import net.yxiao233.realmofdestiny.networking.ModNetWorking;
import net.yxiao233.realmofdestiny.screen.BaseFluidTankScreen;
import net.yxiao233.realmofdestiny.screen.PedestalScreen;
import net.yxiao233.realmofdestiny.screen.VoidPlantMachineScreen;

@Mod(RealmOfDestiny.MODID)
public class RealmOfDestiny
{
    public static final String MODID = "realmofdestiny";

    public RealmOfDestiny()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        modEventBus.addListener(this::commonSetup);

        ModItems.ITEMS.register(modEventBus);
        ModCreativeModeTab.CREATIVE_MODE_TAB.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModMenuTypes.MENUS.register(modEventBus);
        ModRecipes.SERIALIZERS.register(modEventBus);
        ModNetWorking.register();
    }


    @SuppressWarnings("deprecation")
    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.PEDESTAL.get(),RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.BASE_FLUID_TANK.get(),RenderType.cutout());
        });
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.BASE_FLUID_TANK_MENU.get(), BaseFluidTankScreen::new);
            MenuScreens.register(ModMenuTypes.PEDESTAL_MENU.get(), PedestalScreen::new);
            MenuScreens.register(ModMenuTypes.VOID_PLANT_MACHINE_MENU.get(), VoidPlantMachineScreen::new);
        }
    }
}
