package net.yxiao233.realmofdestiny.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.item.PedestalItem;
import net.yxiao233.realmofdestiny.item.custom.AddonItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RealmOfDestiny.MODID);
    //Item
    public static final RegistryObject<Item> SPEED_AUGMENT_1 = ITEMS.register("speed_augment_1", () ->
            new AddonItem(AddonItem.Type.SPEED,5, Rarity.EPIC));

    public static final RegistryObject<Item> INPUT_AUGMENT_1 = ITEMS.register("input_augment_1", () ->
            new AddonItem(AddonItem.Type.INPUT_CHANCE,5, Rarity.EPIC));

    public static final RegistryObject<Item> OUTPUT_AUGMENT_1 = ITEMS.register("output_augment_1", () ->
            new AddonItem(AddonItem.Type.OUTPUT_CHANCE,5, Rarity.EPIC));

    //BlockItem
    public static final RegistryObject<Item> PEDESTAL_ITEM = ITEMS.register("pedestal", () ->
            new PedestalItem(ModBlocks.PEDESTAL.get(),new Item.Properties()));
}
