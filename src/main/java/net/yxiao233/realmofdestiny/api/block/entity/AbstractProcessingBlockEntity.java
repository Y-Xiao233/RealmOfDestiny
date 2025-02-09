package net.yxiao233.realmofdestiny.api.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractProcessingBlockEntity extends AbstractContainerBlockEntity implements IProcessingBlockEntity {
    public AbstractProcessingBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }
}
