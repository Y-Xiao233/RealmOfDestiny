package net.yxiao233.realmofdestiny.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.block.PedestalBlock;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RealmOfDestiny.MODID);

    public static final RegistryObject<Block> PEDESTAL = BLOCKS.register("pedestal", () ->
            new PedestalBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).sound(SoundType.STONE).noOcclusion()));
}
