package net.yxiao233.realmofdestiny.Blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.yxiao233.realmofdestiny.Entities.PedestalBlockEntity;
import net.yxiao233.realmofdestiny.ModRegistry.ModBlockEntities;
import net.yxiao233.realmofdestiny.helper.blockBox.BlockBoxHelper;
import org.jetbrains.annotations.Nullable;

public class PedestalBlock extends BaseEntityBlock {
    public PedestalBlock(Properties pProperties) {

        super(pProperties);
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PedestalBlockEntity(blockPos,blockState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return new BlockBoxHelper("pedestal").getVoxelShapes();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if(pState.getBlock() != pNewState.getBlock()){
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if(blockEntity instanceof PedestalBlockEntity){
                ((PedestalBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(!level.isClientSide()){
            BlockPos blockPos = hitResult.getBlockPos();

            BlockEntity entity = level.getBlockEntity(blockPos);
            PedestalBlockEntity blockEntity = entity instanceof PedestalBlockEntity ? (PedestalBlockEntity) entity : null;

            ItemStack itemStack =  blockEntity.itemHandler.getStackInSlot(0);
            int count = itemStack.getCount();
            Item item = itemStack.getItem();
            int maxStack = player.getMainHandItem().getMaxStackSize();

            if(player.getMainHandItem().isEmpty() && player.isShiftKeyDown()){
                ItemStack get = blockEntity.itemHandler.getStackInSlot(0);
                blockEntity.itemHandler.extractItem(0,count,false);
                player.setItemInHand(hand,get);
            }else if(item == Items.AIR){
                put(blockEntity,player,count,hand);
            }else if(player.getMainHandItem().is(item) && count < maxStack){
                put(blockEntity,player,count,hand);
            }else{
                return super.use(state, level, pos, player, hand, hitResult);
            }
            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, pos, player, hand, hitResult);
    }

    public void put(PedestalBlockEntity blockEntity, Player player,int handlerCount,InteractionHand hand){
        ItemStack handItemStack = hand == InteractionHand.MAIN_HAND ? player.getMainHandItem() : player.getOffhandItem();
        int maxStack = handItemStack.getMaxStackSize();
        int putCount = handItemStack.getCount() + handlerCount <= maxStack ? handItemStack.getCount() + handlerCount : maxStack - handlerCount;

        blockEntity.itemHandler.setStackInSlot(0,new ItemStack(handItemStack.getItem(),putCount));
        if(putCount != handItemStack.getCount()){
            player.setItemInHand(hand,new ItemStack(handItemStack.getItem(),handItemStack.getCount() - putCount));
        }else{
            player.setItemInHand(hand,Items.AIR.getDefaultInstance());
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()){
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.PEDESTAL_BE.get(),
                (level, blockPos, blockState, blockEntity) -> blockEntity.tick(level,blockPos,blockState));
    }
}
