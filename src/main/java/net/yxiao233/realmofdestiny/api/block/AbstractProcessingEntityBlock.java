package net.yxiao233.realmofdestiny.api.block;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.yxiao233.realmofdestiny.api.block.entity.AbstractProcessingBlockEntity;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractProcessingEntityBlock<T extends AbstractProcessingBlockEntity> extends AbstractContainerEntityBlock<T>{
    public AbstractProcessingEntityBlock(Properties pProperties) {
        super(pProperties);
    }

    public abstract BlockEntityType<T> getBlockEntityType();

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()){
            return null;
        }

        return createTickerHelper(pBlockEntityType, getBlockEntityType(),
                (level, blockPos, blockState, blockEntity) -> blockEntity.tick(level,blockPos,blockState));
    }
}
