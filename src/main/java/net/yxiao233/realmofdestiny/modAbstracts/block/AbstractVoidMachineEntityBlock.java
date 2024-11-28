package net.yxiao233.realmofdestiny.modAbstracts.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.yxiao233.realmofdestiny.Entities.ModBaseBlockEntity;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractVoidMachineEntityBlock<T extends ModBaseBlockEntity> extends AbstractModContainerEntityBlock<T> {
    public AbstractVoidMachineEntityBlock(Properties pProperties) {
        super(pProperties);
    }

    public abstract BlockEntityType<T> getBlockEntityType();
    public abstract boolean openMenu(BlockPos blockPos, Player player, BlockEntity entity);
    public abstract void setDrops(Level level, BlockPos blockPos);

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

        return createTickerHelper(pBlockEntityType, getBlockEntityType(),
                (level, blockPos, blockState, blockEntity) -> blockEntity.tick(level,blockPos,blockState));
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(!pLevel.isClientSide()){
            if(openMenu(pPos,pPlayer,pLevel.getBlockEntity(pPos))){
                return InteractionResult.SUCCESS;
            }else{
                return InteractionResult.FAIL;
            }
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if(pState.getBlock() != pNewState.getBlock()){
            setDrops(pLevel,pPos);
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }
}
