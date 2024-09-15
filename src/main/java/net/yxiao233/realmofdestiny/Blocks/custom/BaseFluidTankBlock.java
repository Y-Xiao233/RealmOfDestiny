package net.yxiao233.realmofdestiny.Blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidStack;
import net.yxiao233.realmofdestiny.Blocks.modAbstractBlock.AbstractModFluidTankEntityBlock;
import net.yxiao233.realmofdestiny.Entities.BaseFluidTankBlockEntity;
import net.yxiao233.realmofdestiny.helper.blockBox.BlockBoxHelper;
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
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return new BlockBoxHelper("base_fluid_tank").getVoxelShapes();
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
}
