package net.yxiao233.realmofdestiny.ModRegistry;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.yxiao233.realmofdestiny.RealmOfDestiny;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?,?>> BOLT_KEY = registryKey("bolt");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?,?>> context){
        registry(context,BOLT_KEY,Feature.TREE,new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.BOLT_LOG.get()),
                new StraightTrunkPlacer(5,4,3),
                BlockStateProvider.simple(ModBlocks.BOLT_LEAVES.get()),
                new BlobFoliagePlacer(ConstantInt.of(3),ConstantInt.of(2),3),
                new TwoLayersFeatureSize(1,0,2)).build()
        );
    }

    public static ResourceKey<ConfiguredFeature<?,?>> registryKey(String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE,new ResourceLocation(RealmOfDestiny.MODID,name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void registry(BootstapContext<ConfiguredFeature<?,?>> context,
    ResourceKey<ConfiguredFeature<?,?>> key, F feature, FC configuration){
        context.register(key,new ConfiguredFeature<>(feature,configuration));
    }
}
