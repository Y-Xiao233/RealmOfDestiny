package net.yxiao233.realmofdestiny.ModRegistry;

import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yxiao233.realmofdestiny.Blocks.*;
import net.yxiao233.realmofdestiny.Blocks.custom.BaseFluidTankBlock;
import net.yxiao233.realmofdestiny.Blocks.custom.CreativeEnergyMatrixBlock;
import net.yxiao233.realmofdestiny.Blocks.custom.PedestalBlock;
import net.yxiao233.realmofdestiny.Blocks.custom.VoidPlantMachineBlock;
import net.yxiao233.realmofdestiny.Entities.VoidPlantMachineBlockEntity;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.worldgen.tree.BoltTreeGrower;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RealmOfDestiny.MODID);
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

    public static final RegistryObject<Block> BOLT_PLANKS = BLOCKS.register("bolt_planks", () ->
            new ModFlammableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));

    public static final RegistryObject<Block> CREATIVE_ENERGY_MATRIX = BLOCKS.register("creative_energy_matrix", () ->
            new CreativeEnergyMatrixBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> BASE_FLUID_TANK = BLOCKS.register("base_fluid_tank", () ->
            new BaseFluidTankBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).sound(SoundType.METAL).noLootTable()));

    public static final RegistryObject<Block> WARNING = BLOCKS.register("warning", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noLootTable()));

    public static final RegistryObject<Block> VOID_PLANT_MACHINE = BLOCKS.register("void_plant_machine", () ->
        new VoidPlantMachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

}
