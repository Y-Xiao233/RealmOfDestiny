package net.yxiao233.realmofdestiny.Blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.yxiao233.realmofdestiny.Entities.CreativeEnergyMatrixBlockEntity;
import net.yxiao233.realmofdestiny.ModRegistry.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class CreativeEnergyMatrixBlock extends BaseEntityBlock {
    public CreativeEnergyMatrixBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CreativeEnergyMatrixBlockEntity(blockPos,blockState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
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
