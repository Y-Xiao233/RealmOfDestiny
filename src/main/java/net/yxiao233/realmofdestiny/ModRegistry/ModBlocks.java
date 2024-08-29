package net.yxiao233.realmofdestiny.ModRegistry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yxiao233.realmofdestiny.Blocks.GemPolishingStationBlock;
import net.yxiao233.realmofdestiny.Blocks.ModFlammableRotatedPillarBlock;
import net.yxiao233.realmofdestiny.Blocks.PedestalBlock;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.worldgen.tree.BoltTreeGrower;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RealmOfDestiny.MODID);
    public static final RegistryObject<Block> GEM_POLISHING_STATION = BLOCKS.register("gem_polishing_station", () ->
            new GemPolishingStationBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> PEDESTAL = BLOCKS.register("pedestal", () ->
            new PedestalBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).sound(SoundType.STONE).noOcclusion()));

    public static final RegistryObject<Block> BOLT_LOG = BLOCKS.register("bolt_log", () ->
            new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));

    public static final RegistryObject<Block> STRIPPED_BOLT_LOG = BLOCKS.register("stripped_bolt_log", () ->
            new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)));

    public static final RegistryObject<Block> BOLT_LEAVES = BLOCKS.register("bolt_leaves", () ->
            new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));

    public static final RegistryObject<Block> BOLT_SAPLING = BLOCKS.register("bolt_sapling", () ->
            new SaplingBlock(new BoltTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));

    private static <T extends Block> RegistryObject<T> registryBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name,block);
        registryBlockItem(name,toReturn);
        return toReturn;
    }
    public static <T extends Block> RegistryObject<Item> registryBlockItem(String name, RegistryObject<T> block){
        return ModItems.ITEMS.register(name,() ->
                new BlockItem(ModBlocks.GEM_POLISHING_STATION.get(),new Item.Properties()));
    }
}
