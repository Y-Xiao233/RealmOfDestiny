package net.yxiao233.realmofdestiny.Blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.yxiao233.realmofdestiny.modAbstracts.block.AbstractModFluidTankEntityBlock;
import net.yxiao233.realmofdestiny.Entities.BaseFluidTankBlockEntity;
import net.yxiao233.realmofdestiny.ModRegistry.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class BaseFluidTankBlock extends AbstractModFluidTankEntityBlock<BaseFluidTankBlockEntity> {
    public BaseFluidTankBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BaseFluidTankBlockEntity setBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BaseFluidTankBlockEntity(blockPos,blockState);
    }

    @Override
    public boolean isFluidValid(BaseFluidTankBlockEntity entity, FluidStack stack) {
        return entity.isBEFluidValid(stack);
    }

    @Override
    public void drain(BaseFluidTankBlockEntity entity, FluidStack stack) {
        entity.drain(stack);
    }

    @Override
    public void fill(BaseFluidTankBlockEntity entity, FluidStack stack) {
        entity.fill(stack);
    }

    @Override
    public int getTankCap(BaseFluidTankBlockEntity entity) {
        return entity.getTankCap();
    }

    @Override
    public FluidStack getFluidStackInTank(BaseFluidTankBlockEntity entity) {
        return entity.getFluidStackInTank();
    }

    @Override
    public void setTank(BaseFluidTankBlockEntity entity, FluidStack stack) {
        entity.setTank(stack);
    }

    @Override
    public void setChanged(BaseFluidTankBlockEntity entity) {
        entity.setChanged();
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()){
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.BASE_FLUID_TANK_BE.get(),
                (level, blockPos, blockState, blockEntity) -> blockEntity.tick(level,blockPos,blockState));
    }


    @Override
    public void setDrops(Level level, BlockPos blockPos) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if(blockEntity instanceof BaseFluidTankBlockEntity entity){
            entity.drops();
        }
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if(blockEntity instanceof BaseFluidTankBlockEntity entity){
            if(pStack.hasTag()){
                entity.onLoad();
                CompoundTag tag = pStack.getTag();
                entity.tank.readFromNBT(tag);
                entity.load(tag);
            }
        }
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
    }
}
