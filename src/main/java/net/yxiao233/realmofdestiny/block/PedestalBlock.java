package net.yxiao233.realmofdestiny.block;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import net.yxiao233.realmofdestiny.block.entity.PedestalBlockEntity;
import net.yxiao233.realmofdestiny.item.custom.AddonItem;
import net.yxiao233.realmofdestiny.registry.ModBlockEntities;
import net.yxiao233.realmofdestiny.registry.ModItems;
import net.yxiao233.realmofdestiny.util.BlockBoxHelper;
import net.yxiao233.realmofdestiny.util.KeyDownUtil;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;


public class PedestalBlock extends BaseEntityBlock {
    public PedestalBlock(Properties pProperties) {
        super(pProperties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter level, BlockPos blockPos, CollisionContext context) {
        return new BlockBoxHelper("pedestal").getVoxelShapes();
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PedestalBlockEntity(blockPos,blockState);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState newState, boolean movedByPiston) {
        if(blockState.getBlock() != newState.getBlock()){
            PedestalBlockEntity entity = (PedestalBlockEntity) level.getBlockEntity(blockPos);
            if(entity != null){
                entity.drops();
            }
        }
        super.onRemove(blockState, level, blockPos, newState, movedByPiston);
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(!level.isClientSide()){
            BlockPos blockPos = hitResult.getBlockPos();


            BlockEntity entity = level.getBlockEntity(blockPos);
            PedestalBlockEntity blockEntity = entity instanceof PedestalBlockEntity ? (PedestalBlockEntity) entity : null;

            ItemStack itemStack = blockEntity.itemHandler.getStackInSlot(0);
            int count = itemStack.getCount();
            Item item = itemStack.getItem();
            int maxStack = player.getMainHandItem().getMaxStackSize();

            if(KeyDownUtil.isCtrlDown()){
                openMenu(blockPos,player,(PedestalBlockEntity) level.getBlockEntity(blockPos));
            }

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


    public boolean openMenu(BlockPos blockPos, Player player, PedestalBlockEntity entity){
        if(entity != null){
            NetworkHooks.openScreen(((ServerPlayer) player),entity,blockPos);
            return true;
        }else{
            throw new IllegalStateException("Our Container provider is missing");
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
