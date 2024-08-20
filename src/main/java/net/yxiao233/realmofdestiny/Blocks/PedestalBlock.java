package net.yxiao233.realmofdestiny.Blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.yxiao233.realmofdestiny.Entities.PedestalBlockEntity;
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
        VoxelShape base = Block.box(4,0,4,12,2,12);
        VoxelShape substrate = Block.box(6,2,6,10,9,10);

        VoxelShape up = Block.box(5,9,5,11,11,11);
        VoxelShape glass = Block.box(6,10,6,10,14,10);

        VoxelShape base_substrate = Shapes.join(base,substrate, BooleanOp.OR);
        VoxelShape up_glass = Shapes.join(up,glass,BooleanOp.OR);
        return Shapes.join(base_substrate,up_glass,BooleanOp.OR);
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
        ItemStack mainHand = player.getMainHandItem();
        int maxStack = mainHand.getMaxStackSize();
        int putCount = mainHand.getCount() + handlerCount <= maxStack ? mainHand.getCount() + handlerCount : maxStack - handlerCount;

        blockEntity.itemHandler.setStackInSlot(0,new ItemStack(mainHand.getItem(),putCount));
        if(putCount != mainHand.getCount()){
            player.setItemInHand(hand,new ItemStack(mainHand.getItem(),mainHand.getCount() - putCount));
        }else{
            player.setItemInHand(hand,Items.AIR.getDefaultInstance());
        }
    }
}
