package net.yxiao233.realmofdestiny.ModRegistry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.yxiao233.realmofdestiny.RealmOfDestiny;

public class ModCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RealmOfDestiny.MODID);
    public static final RegistryObject<CreativeModeTab> ROD_TAB = CREATIVE_MODE_TAB.register("rod_tab", () -> CreativeModeTab.builder()
            .icon(() -> ModItems.CHANGE_STONE.get().getDefaultInstance())
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(ModItems.CHANGE_STONE.get());
                output.accept(ModItems.GEM_POLISHING_STATION_ITEM.get());
                output.accept(ModItems.PEDESTAL_ITEM.get());
                output.accept(ModItems.BOLT_LOG_ITEM.get());
                output.accept(ModItems.BOLT_LEAVES_ITEM.get());
                output.accept(ModItems.BOLT_SAPLING_ITEM.get());
                output.accept(ModItems.STRIPPED_BOLT_LOG_ITEM.get());
                output.accept(ModItems.BOLT_PLANKS_ITEM.get());
                output.accept(ModItems.CREATIVE_ENERGY_MATRIX_ITEM.get());
                output.accept(ModItems.BASE_FLUID_TANK_ITEM.get());
            })
            .title(Component.translatable("itemGroup.realmofdestiny"))
            .build()
    );
}