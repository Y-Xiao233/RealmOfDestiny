package net.yxiao233.realmofdestiny.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yxiao233.realmofdestiny.RealmOfDestiny;

public class ModCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RealmOfDestiny.MODID);
    public static final RegistryObject<CreativeModeTab> ROD_TAB = CREATIVE_MODE_TAB.register("rod_tab", () -> CreativeModeTab.builder()
            .icon(() -> ModItems.PEDESTAL_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                ModItems.ITEMS.getEntries().forEach(reg ->{
                    output.accept(reg.get());
                });
            })
            .title(Component.translatable("itemGroup.realmofdestiny"))
            .build()
    );
}
