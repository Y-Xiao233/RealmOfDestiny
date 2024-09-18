package net.yxiao233.realmofdestiny.Blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import net.yxiao233.realmofdestiny.Blocks.modAbstractBlock.AbstractModContainerEntityBlock;
import net.yxiao233.realmofdestiny.Entities.GemPolishingStationBlockEntity;
import net.yxiao233.realmofdestiny.ModRegistry.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class GemPolishingStationBlock extends AbstractModContainerEntityBlock<GemPolishingStationBlockEntity> {
    public static final VoxelShape SHAPE = Block.box(0,0,0,16,16,16);
    public GemPolishingStationBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public GemPolishingStationBlockEntity setBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new GemPolishingStationBlockEntity(blockPos,blockState);
    }

    @Override
    public void setDrops(Level level, BlockPos blockPos) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if(blockEntity instanceof GemPolishingStationBlockEntity entity){
            entity.drops();
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }


    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(!pLevel.isClientSide()){
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof GemPolishingStationBlockEntity){
                NetworkHooks.openScreen(((ServerPlayer) pPlayer),(GemPolishingStationBlockEntity)entity,pPos);
                return InteractionResult.SUCCESS;
            }else{
                throw new IllegalStateException("Our Container provider is missing");
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()){
            return null;
        }

        return createTickerHelper(pBlockEntityType,ModBlockEntities.GEM_POLISHING_STATION_BE.get(),
                (level, blockPos, blockState, blockEntity) -> blockEntity.tick(level,blockPos,blockState));
    }
}
