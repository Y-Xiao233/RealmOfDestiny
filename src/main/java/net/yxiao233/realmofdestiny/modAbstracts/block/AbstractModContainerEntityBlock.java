package net.yxiao233.realmofdestiny.modAbstracts.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.yxiao233.realmofdestiny.modAbstracts.block.AbstractModBaseEntityBlock;

public abstract class AbstractModContainerEntityBlock<T extends BlockEntity> extends AbstractModBaseEntityBlock<T> {
    protected AbstractModContainerEntityBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public abstract T setBlockEntity(BlockPos blockPos, BlockState blockState);

    public abstract void setDrops(Level level, BlockPos blockPos);

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if(pState.getBlock() != pNewState.getBlock()){
            setDrops(pLevel,pPos);
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }
}
