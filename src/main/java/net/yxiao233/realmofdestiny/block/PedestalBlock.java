package net.yxiao233.realmofdestiny.block;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.yxiao233.realmofdestiny.api.block.AbstractProcessingEntityBlockWithMenu;
import net.yxiao233.realmofdestiny.block.entity.PedestalBlockEntity;
import net.yxiao233.realmofdestiny.api.item.custom.AddonItem;
import net.yxiao233.realmofdestiny.registry.ModBlockEntities;
import net.yxiao233.realmofdestiny.util.BlockBoxHelper;
import net.yxiao233.realmofdestiny.util.KeyDownUtil;
import org.jetbrains.annotations.Nullable;

public class PedestalBlock extends AbstractProcessingEntityBlockWithMenu<PedestalBlockEntity> {
    public PedestalBlock(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public BlockEntityType<PedestalBlockEntity> getBlockEntityType() {
        return ModBlockEntities.PEDESTAL_BE.get();
    }
    @Override
    public boolean openMenuLogic() {
        return KeyDownUtil.isCtrlDown();
    }
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter level, BlockPos blockPos, CollisionContext context) {
        return new BlockBoxHelper("pedestal").getVoxelShapes();
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PedestalBlockEntity(blockPos,blockState);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(!level.isClientSide()){
            super.use(state, level, pos, player, hand, hitResult);
            return extractAndInsertLogic(state,level,pos,player,hand,hitResult);
        }
        return super.use(state, level, pos, player, hand, hitResult);
    }


    public InteractionResult extractAndInsertLogic(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult){

        BlockPos blockPos = hitResult.getBlockPos();
        BlockEntity entity = level.getBlockEntity(blockPos);
        PedestalBlockEntity blockEntity = entity instanceof PedestalBlockEntity ? (PedestalBlockEntity) entity : null;

        ItemStack itemStack = blockEntity.itemHandler.getStackInSlot(0);
        int count = itemStack.getCount();
        Item item = itemStack.getItem();
        int maxStack = player.getMainHandItem().getMaxStackSize();

        if(player.getMainHandItem().getItem() instanceof AddonItem && player.isShiftKeyDown()){
            if(hand == InteractionHand.MAIN_HAND){
                player.sendSystemMessage(Component.translatable("addon_item.realmofdestiny.hold_ctrl").withStyle(ChatFormatting.GRAY));
            }
        }

        if(player.getMainHandItem().isEmpty() && player.isShiftKeyDown()){
            ItemStack get = blockEntity.itemHandler.getStackInSlot(0);
            blockEntity.itemHandler.extractItem(0,count,false);
            player.setItemInHand(hand,get);
        }else if(item == Items.AIR && !player.getMainHandItem().isEmpty()){
            put(blockEntity,player,count,hand);
        }else if(player.getMainHandItem().is(item) && count < maxStack){
            put(blockEntity,player,count,hand);
        }else{
            return super.use(state, level, pos, player, hand, hitResult);
        }
        return InteractionResult.SUCCESS;
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
}
