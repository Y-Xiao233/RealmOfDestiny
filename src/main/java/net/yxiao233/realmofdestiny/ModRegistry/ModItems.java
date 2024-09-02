package net.yxiao233.realmofdestiny.ModRegistry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yxiao233.realmofdestiny.Items.ChangeStoneItem;
import net.yxiao233.realmofdestiny.Items.CreativeEnergyMatrixItem;
import net.yxiao233.realmofdestiny.Items.PedestalItem;
import net.yxiao233.realmofdestiny.RealmOfDestiny;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RealmOfDestiny.MODID);
    //Item
    public static final RegistryObject<Item> CHANGE_STONE = ITEMS.register("change_stone",
            () -> new ChangeStoneItem(new Item.Properties()));

    //BlockItem
    public static final RegistryObject<Item> GEM_POLISHING_STATION_ITEM = ITEMS.register("gem_polishing_station",() ->
            new BlockItem(ModBlocks.GEM_POLISHING_STATION.get(),new Item.Properties()));
    public static final RegistryObject<Item> PEDESTAL_ITEM = ITEMS.register("pedestal", () ->
            new PedestalItem(ModBlocks.PEDESTAL.get(),new Item.Properties()));

    public static final RegistryObject<Item> BOLT_LOG_ITEM = ITEMS.register("bolt_log", () ->
            new BlockItem(ModBlocks.BOLT_LOG.get(),new Item.Properties()));

    public static final RegistryObject<Item> STRIPPED_BOLT_LOG_ITEM = ITEMS.register("stripped_bolt_log", () ->
            new BlockItem(ModBlocks.STRIPPED_BOLT_LOG.get(),new Item.Properties()));

    public static final RegistryObject<Item> BOLT_LEAVES_ITEM = ITEMS.register("bolt_leaves", () ->
            new BlockItem(ModBlocks.BOLT_LEAVES.get(),new Item.Properties()));

    public static final RegistryObject<Item> BOLT_SAPLING_ITEM = ITEMS.register("bolt_sapling", () ->
            new BlockItem(ModBlocks.BOLT_SAPLING.get(),new Item.Properties()));

    public static final RegistryObject<Item> BOLT_PLANKS_ITEM = ITEMS.register("bolt_planks", () ->
            new BlockItem(ModBlocks.BOLT_PLANKS.get(),new Item.Properties()));

    public static final RegistryObject<Item> CREATIVE_ENERGY_MATRIX_ITEM = ITEMS.register("creative_energy_matrix", () ->
            new CreativeEnergyMatrixItem(ModBlocks.CREATIVE_ENERGY_MATRIX.get(),new Item.Properties()));
}
