package net.yxiao233.realmofdestiny.ModRegistry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yxiao233.realmofdestiny.Blocks.GemPolishingStationBlock;
import net.yxiao233.realmofdestiny.RealmOfDestiny;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RealmOfDestiny.MODID);
    public static final RegistryObject<Block> GEM_POLISHING_STATION = BLOCKS.register("gem_polishing_station", () ->
            new GemPolishingStationBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noCollission()));
}
