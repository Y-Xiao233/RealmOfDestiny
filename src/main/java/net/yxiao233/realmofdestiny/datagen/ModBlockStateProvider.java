package net.yxiao233.realmofdestiny.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
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
        saplingBlock(ModBlocks.BOLT_SAPLING);

        logBlock((RotatedPillarBlock) ModBlocks.BOLT_LOG.get());

        axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_BOLT_LOG.get(),blockTexture(ModBlocks.STRIPPED_BOLT_LOG.get()),
                new ResourceLocation(RealmOfDestiny.MODID,"block/stripped_bolt_log_top"));

        blockItem(ModBlocks.BOLT_LOG);
        blockItem(ModBlocks.STRIPPED_BOLT_LOG);
        leavesBlock(ModBlocks.BOLT_LEAVES);
        blockWithItem(ModBlocks.BOLT_PLANKS);
        blockWithItem(ModBlocks.CREATIVE_ENERGY_MATRIX);
        blockWithItem(ModBlocks.WARNING);
    }

    private void leavesBlock(RegistryObject<Block> registryObject){
        simpleBlockWithItem(registryObject.get(),
                models().singleTexture(ForgeRegistries.BLOCKS.getKey(registryObject.get()).getPath(), new ResourceLocation("minecraft:block/leaves"),
                        "all",blockTexture(registryObject.get())).renderType("cutout"));
    }

    private void blockItem(RegistryObject<Block> registryObject){
        simpleBlockItem(registryObject.get(),new ModelFile.UncheckedModelFile(RealmOfDestiny.MODID +
                ":block/" + ForgeRegistries.BLOCKS.getKey(registryObject.get()).getPath()));
    }
    private void blockWithItem(RegistryObject<Block> registryObject){
        simpleBlockWithItem(registryObject.get(),cubeAll(registryObject.get()));
    }

    private void saplingBlock(RegistryObject<Block> blockRegistryObject){
        simpleBlock(blockRegistryObject.get(),
                models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(),blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }
}
