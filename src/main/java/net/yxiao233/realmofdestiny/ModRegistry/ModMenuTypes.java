package net.yxiao233.realmofdestiny.ModRegistry;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.screen.BaseFluidTankMenu;
import net.yxiao233.realmofdestiny.screen.PedestalMenu;
import net.yxiao233.realmofdestiny.screen.VoidPlantMachineMenu;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, RealmOfDestiny.MODID);

    public static final RegistryObject<MenuType<BaseFluidTankMenu>> BASE_FLUID_TANK_MENU =
            registerMenuType("base_fluid_tank_menu", BaseFluidTankMenu::new);

    public static final RegistryObject<MenuType<PedestalMenu>> PEDESTAL_MENU =
            registerMenuType("pedestal_menu",PedestalMenu::new);

    public static final RegistryObject<MenuType<VoidPlantMachineMenu>> VOID_PLANT_MACHINE_MENU =
            registerMenuType("void_plant_machine_menu",VoidPlantMachineMenu::new);


    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }
}