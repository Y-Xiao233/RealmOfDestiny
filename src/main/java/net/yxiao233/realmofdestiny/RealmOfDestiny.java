package net.yxiao233.realmofdestiny;

import com.hrznstudio.titanium.module.ModuleController;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.yxiao233.realmofdestiny.provider.ModRecipeProvider;
import net.yxiao233.realmofdestiny.provider.ModSerializableProvider;
import net.yxiao233.realmofdestiny.registry.*;
import net.yxiao233.realmofdestiny.screen.PedestalScreen;

@Mod(RealmOfDestiny.MODID)
public class RealmOfDestiny extends ModuleController {
    public static final String MODID = "realmofdestiny";

    public RealmOfDestiny()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::commonSetup);


        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModMenuTypes.MENUS.register(modEventBus);
        ModCreativeModeTab.CREATIVE_MODE_TAB.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
    }


    @SuppressWarnings("deprecation")
    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.PEDESTAL.get(), RenderType.cutout());
        });
    }
    @Override
    protected void initModules() {
        new ModRecipes().generateFeatures(getRegistries());
    }

    @Override
    public void addDataProvider(GatherDataEvent event) {
        event.getGenerator().addProvider(event.includeServer(), new ModRecipeProvider(event.getGenerator()));
        event.getGenerator().addProvider(event.includeServer(), new ModSerializableProvider(event.getGenerator()));
    }


    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.PEDESTAL_MENU.get(), PedestalScreen::new);
        }
    }
}
