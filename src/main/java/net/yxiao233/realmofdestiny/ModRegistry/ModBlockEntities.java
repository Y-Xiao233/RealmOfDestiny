package net.yxiao233.realmofdestiny.ModRegistry;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yxiao233.realmofdestiny.Entities.BaseFluidTankBlockEntity;
import net.yxiao233.realmofdestiny.Entities.CreativeEnergyMatrixBlockEntity;
import net.yxiao233.realmofdestiny.Entities.GemPolishingStationBlockEntity;
import net.yxiao233.realmofdestiny.Entities.PedestalBlockEntity;
import net.yxiao233.realmofdestiny.RealmOfDestiny;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, RealmOfDestiny.MODID);

    public static final RegistryObject<BlockEntityType<GemPolishingStationBlockEntity>> GEM_POLISHING_STATION_BE = BLOCK_ENTITIES.register("gem_polishing_station", () ->
            BlockEntityType.Builder.of(GemPolishingStationBlockEntity::new, ModBlocks.GEM_POLISHING_STATION.get())
                    .build(null)
    );

    public static final RegistryObject<BlockEntityType<PedestalBlockEntity>> PEDESTAL_BE = BLOCK_ENTITIES.register("pedestal", () ->
            BlockEntityType.Builder.of(PedestalBlockEntity::new,ModBlocks.PEDESTAL.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<CreativeEnergyMatrixBlockEntity>> CREATIVE_ENERGY_MATRIX_BE = BLOCK_ENTITIES.register("creative_energy_matrix", () ->
            BlockEntityType.Builder.of(CreativeEnergyMatrixBlockEntity::new,ModBlocks.CREATIVE_ENERGY_MATRIX.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<BaseFluidTankBlockEntity>> BASE_FLUID_TANK_BE = BLOCK_ENTITIES.register("base_fluid_tank", () ->
            BlockEntityType.Builder.of(BaseFluidTankBlockEntity::new,ModBlocks.BASE_FLUID_TANK.get())
                    .build(null));
}
