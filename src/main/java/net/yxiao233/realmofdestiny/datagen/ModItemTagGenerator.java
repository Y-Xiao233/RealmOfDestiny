package net.yxiao233.realmofdestiny.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.yxiao233.realmofdestiny.api.item.custom.AddonItem;
import net.yxiao233.realmofdestiny.registry.ModItems;
import net.yxiao233.realmofdestiny.registry.ModTags;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags) {
        super(pOutput, pLookupProvider, pBlockTags);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        List<Item> pedestal_upgrade_augment = new ArrayList<>();
        ModItems.ITEMS.getEntries().forEach(reg ->{
            if(reg.get() instanceof AddonItem){
                pedestal_upgrade_augment.add(reg.get());
            }
        });

        pedestal_upgrade_augment.forEach(item -> {
            this.tag(ModTags.Items.PEDESTAL_UPGRADE_AUGMENT).add(item);
        });
    }
}
