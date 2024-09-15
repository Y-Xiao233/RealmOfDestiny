package net.yxiao233.realmofdestiny.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.yxiao233.realmofdestiny.ModRegistry.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags) {
        super(pOutput, pLookupProvider, pBlockTags);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ItemTags.LOGS_THAT_BURN)
                .add(ModItems.BOLT_LOG_ITEM.get())
                .add(ModItems.STRIPPED_BOLT_LOG_ITEM.get());

        this.tag(ItemTags.PLANKS)
                .add(ModItems.BOLT_PLANKS_ITEM.get());
    }
}
