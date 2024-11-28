package net.yxiao233.realmofdestiny.ModRegistry;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yxiao233.realmofdestiny.Items.*;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.foods.ModFoods;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RealmOfDestiny.MODID);
    //Item
    public static final RegistryObject<Item> CHANGE_STONE = ITEMS.register("change_stone", () ->
            new ChangeStoneItem(new Item.Properties()));

    public static final RegistryObject<Item> STRUCTURE_VIEWER = ITEMS.register("structure_viewer", () ->
            new StructureViewerItem(new Item.Properties()));

    public static final RegistryObject<Item> PAPER_PILE = ITEMS.register("paper_pile", () ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> RIPE_APPLE = ITEMS.register("ripe_apple", () ->
            new Item(new Item.Properties().food(ModFoods.RIPE_APPLE)));

    //BlockItem
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

    public static final RegistryObject<Item> BASE_FLUID_TANK_ITEM = ITEMS.register("base_fluid_tank", () ->
            new BaseFluidTankItem(ModBlocks.BASE_FLUID_TANK.get(),new Item.Properties()));

    public static final RegistryObject<Item> VOID_PLANT_MACHINE_ITEM = ITEMS.register("void_plant_machine", () ->
            new BlockItem(ModBlocks.VOID_PLANT_MACHINE.get(),new Item.Properties()));
}
