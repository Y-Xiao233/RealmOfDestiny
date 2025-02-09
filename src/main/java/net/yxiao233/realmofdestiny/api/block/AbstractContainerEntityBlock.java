package net.yxiao233.realmofdestiny.api.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.yxiao233.realmofdestiny.api.block.entity.AbstractContainerBlockEntity;

public abstract class AbstractContainerEntityBlock<T extends AbstractContainerBlockEntity> extends AbstractEntityBlock{
    public AbstractContainerEntityBlock(Properties pProperties) {
        super(pProperties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState newState, boolean movedByPiston) {
        if(blockState.getBlock() != newState.getBlock()){
            AbstractContainerBlockEntity entity = (AbstractContainerBlockEntity) level.getBlockEntity(blockPos);
            if(entity != null){
                entity.drops();
            }
        }
        super.onRemove(blockState, level, blockPos, newState, movedByPiston);
    }
}
