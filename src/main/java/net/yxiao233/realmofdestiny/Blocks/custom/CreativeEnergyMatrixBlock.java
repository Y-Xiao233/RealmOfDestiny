package net.yxiao233.realmofdestiny.Blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.yxiao233.realmofdestiny.Blocks.modAbstractBlock.AbstractModBaseEntityBlock;
import net.yxiao233.realmofdestiny.Entities.CreativeEnergyMatrixBlockEntity;
import net.yxiao233.realmofdestiny.ModRegistry.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class CreativeEnergyMatrixBlock extends AbstractModBaseEntityBlock<CreativeEnergyMatrixBlockEntity> {
    public CreativeEnergyMatrixBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public CreativeEnergyMatrixBlockEntity setBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CreativeEnergyMatrixBlockEntity(blockPos,blockState);
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()){
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.CREATIVE_ENERGY_MATRIX_BE.get(),
                (level, blockPos, blockState, blockEntity) -> blockEntity.tick(level,blockPos,blockState));
    }
}
