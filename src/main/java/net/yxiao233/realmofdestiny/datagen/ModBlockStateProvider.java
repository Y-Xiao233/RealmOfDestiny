package net.yxiao233.realmofdestiny.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.yxiao233.realmofdestiny.ModRegistry.ModBlocks;
import net.yxiao233.realmofdestiny.RealmOfDestiny;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, RealmOfDestiny.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.GEM_POLISHING_STATION);
    }

    private void blockWithItem(RegistryObject<Block> registryObject){
        simpleBlockWithItem(registryObject.get(),cubeAll(registryObject.get()));
    }
}
