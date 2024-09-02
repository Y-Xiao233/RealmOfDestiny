package net.yxiao233.realmofdestiny.Entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.yxiao233.realmofdestiny.ModRegistry.ModBlockEntities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CreativeEnergyMatrixBlockEntity extends BlockEntity {
    private final EnergyStorage energyStorage = new EnergyStorage(energyStored);
    private LazyOptional<IEnergyStorage> lazyEnergyStorage = LazyOptional.empty();
    private static final int energyStored = 2147483647;

    public CreativeEnergyMatrixBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CREATIVE_ENERGY_MATRIX_BE.get(), pPos, pBlockState);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyStorage.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        lazyEnergyStorage = LazyOptional.of(() -> energyStorage);
        super.onLoad();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyEnergyStorage.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("energy", energyStorage.serializeNBT());
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        energyStorage.deserializeNBT(pTag.get("energy"));
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        energyStorage.receiveEnergy(energyStored,false);

        activeOutput(blockPos);
        setChanged(level, blockPos, blockState);
    }
    private void activeOutput(BlockPos blockPos) {
        for (Direction direction : Direction.values()) {
            BlockPos nearbyPos = blockPos.offset(direction.getNormal());
            BlockEntity entity = level.getBlockEntity(nearbyPos);

            if (entity != null && entity.getCapability(ForgeCapabilities.ENERGY).isPresent()) {
                extractEnergyTo(entity);
            }
        }
    }

    private void extractEnergyTo(BlockEntity entity) {
        entity.getCapability(ForgeCapabilities.ENERGY).ifPresent((energy -> {
            energy.receiveEnergy(energy.getMaxEnergyStored() - energy.getEnergyStored(), false);
        }));
    }
}
