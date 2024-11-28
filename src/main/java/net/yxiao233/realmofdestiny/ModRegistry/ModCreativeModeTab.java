package net.yxiao233.realmofdestiny.ModRegistry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.yxiao233.realmofdestiny.RealmOfDestiny;

public class ModCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RealmOfDestiny.MODID);
    public static final RegistryObject<CreativeModeTab> ROD_TAB = CREATIVE_MODE_TAB.register("rod_tab", () -> CreativeModeTab.builder()
            .icon(() -> ModItems.CHANGE_STONE.get().getDefaultInstance())
            .displayItems((parameters, output) -> {

                ModItems.ITEMS.getEntries().forEach((reg) -> {
                    output.accept(new ItemStack(reg.get()));
                });
            })
            .title(Component.translatable("itemGroup.realmofdestiny"))
            .build()
    );
}