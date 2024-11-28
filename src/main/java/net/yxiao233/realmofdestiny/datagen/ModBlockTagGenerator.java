package net.yxiao233.realmofdestiny.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.yxiao233.realmofdestiny.ModRegistry.ModBlocks;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, RealmOfDestiny.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.PEDESTAL.get())
                .add(ModBlocks.CREATIVE_ENERGY_MATRIX.get())
                .add(ModBlocks.BASE_FLUID_TANK.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.PEDESTAL.get())
                .add(ModBlocks.CREATIVE_ENERGY_MATRIX.get())
                .add(ModBlocks.BASE_FLUID_TANK.get());

        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.BOLT_PLANKS.get())
                .add(ModBlocks.BOLT_LOG.get())
                .add(ModBlocks.STRIPPED_BOLT_LOG.get());

        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.BOLT_LOG.get())
                .add(ModBlocks.STRIPPED_BOLT_LOG.get());

        this.tag(BlockTags.PLANKS)
                .add(ModBlocks.BOLT_PLANKS.get());

    }
}
