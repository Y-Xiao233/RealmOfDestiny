package net.yxiao233.realmofdestiny.datagen.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import net.yxiao233.realmofdestiny.ModRegistry.ModBlocks;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.GEM_POLISHING_STATION.get());
        this.dropSelf(ModBlocks.PEDESTAL.get());
        this.dropSelf(ModBlocks.BOLT_LOG.get());
        this.dropSelf(ModBlocks.BOLT_SAPLING.get());
        this.dropSelf(ModBlocks.STRIPPED_BOLT_LOG.get());
        this.add(ModBlocks.BOLT_LEAVES.get(), block ->
                createLeavesDrops(block,ModBlocks.BOLT_SAPLING.get(),NORMAL_LEAVES_SAPLING_CHANCES));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        //注册方法时后面加上.noLootTable()就不会在这里生成
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
