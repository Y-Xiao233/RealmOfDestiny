package net.yxiao233.realmofdestiny.Blocks.modAbstractBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkHooks;
import net.yxiao233.realmofdestiny.networking.ModNetWorking;
import net.yxiao233.realmofdestiny.networking.packet.FluidSyncS2CPacket;

public abstract class AbstractModFluidTankEntityBlock<T extends BlockEntity> extends AbstractModBaseEntityBlock<T>{
    protected AbstractModFluidTankEntityBlock(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public abstract T setBlockEntity(BlockPos blockPos, BlockState blockState);
    public abstract boolean isFluidValid(T entity, FluidStack stack);
    public abstract void drain(T entity, FluidStack stack);
    public abstract void fill(T entity, FluidStack stack);
    public abstract int getTankCap(T entity);
    public abstract FluidStack getFluidStackInTank(T entity);
    public abstract void setTank(T entity, FluidStack stack);
    public boolean fillFluid(Player player, T entity, InteractionHand hand){
        if(entity != null && player.getMainHandItem().getItem() instanceof BucketItem bucketItem && getFluidStackInTank(entity).getAmount() + 1000 <= getTankCap(entity)){
            FluidStack stack = new FluidStack(bucketItem.getFluid(),1000);
            if(isFluidValid(entity,stack) && !player.getMainHandItem().is(Items.BUCKET)){
                int amount = getFluidStackInTank(entity).getAmount();
                FluidStack newStack = new FluidStack(stack,amount + stack.getAmount());
                setTank(entity,newStack);
                if(!(player.isCreative() || player.isSpectator())){
                    player.setItemInHand(hand,new ItemStack(player.getMainHandItem().getItem(),player.getMainHandItem().getCount() - 1));
                    player.addItem(new ItemStack(Items.BUCKET,1));
                }
                entity.setChanged();
                ModNetWorking.sendToClient(new FluidSyncS2CPacket(stack,entity.getBlockPos(), FluidSyncS2CPacket.FluidCs2PacketAction.SET));
                return true;
            }else {
                return false;
            }
        }else{
            return false;
        }
    }

    public boolean drainFluid(Player player, T entity, InteractionHand hand){
        ItemStack handItemStack = hand == InteractionHand.MAIN_HAND ? player.getMainHandItem() : player.getOffhandItem();
        SoundEvent soundEvent = null;
        if(handItemStack.is(Items.BUCKET) && getFluidStackInTank(entity).getAmount() >= 1000){
            FluidStack fluidStack = getFluidStackInTank(entity);
            soundEvent = fluidStack.getFluid().getFluidType().getSound(fluidStack, SoundActions.BUCKET_EMPTY);
            if(!fluidStack.isEmpty()){
                ItemStack itemStack = new ItemStack(fluidStack.getFluid().getBucket());
                int amount = fluidStack.getAmount();
                FluidStack newStack = new FluidStack(fluidStack,amount - 1000);
                setTank(entity,newStack);
                player.addItem(itemStack);
                ModNetWorking.sendToClient(new FluidSyncS2CPacket(newStack,entity.getBlockPos(), FluidSyncS2CPacket.FluidCs2PacketAction.SET));
            }else{
                return false;
            }
        }else{
            return false;
        }
        if(!(player.isCreative() || player.isSpectator())) {
            player.setItemInHand(hand,new ItemStack(handItemStack.getItem(),handItemStack.getCount() - 1));
        }
        player.getInventory().setChanged();
        player.playSound(soundEvent);
        ServerPlayer serverPlayer = (ServerPlayer)player;

        serverPlayer.connection.send(new ClientboundSoundPacket(Holder.direct(soundEvent), SoundSource.PLAYERS,  player.getX(), player.getY(), player.getZ(),1.0F,1.0F,1));

        int containerId = player.containerMenu.containerId;
        int stateId = player.containerMenu.getStateId();
        for (int i = 0; i < serverPlayer.containerMenu.slots.size(); i++) {
            ItemStack stack = serverPlayer.getInventory().getItem(i);
            serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(containerId,stateId,i, stack));
        }

        entity.setChanged();
        serverPlayer.connection.send(new ClientboundContainerSetContentPacket(containerId,stateId,player.containerMenu.getItems(),Items.AIR.getDefaultInstance()));
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(!pLevel.isClientSide() && pHand == InteractionHand.MAIN_HAND){
            if(allUseEvent(pLevel,pPos,pPlayer,pHand) || pPlayer.getMainHandItem().isEmpty()){
                return InteractionResult.SUCCESS;
            }else{
                return InteractionResult.FAIL;
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    public boolean allUseEvent(Level level, BlockPos blockPos, Player player, InteractionHand hand){
        T entity = (T) level.getBlockEntity(blockPos);
        boolean b1 = false;
        boolean b2 = fillFluid(player,entity,hand);
        boolean b3 = drainFluid(player,entity,hand);
        if(!(b2 || b3)){
            b1 = openMenu(blockPos,player,entity);
        }
        return b1 || b2 || b3;
    }
    public boolean openMenu(BlockPos blockPos, Player player, T entity){
        if(entity != null){
            NetworkHooks.openScreen(((ServerPlayer) player), (MenuProvider) entity,blockPos);
            return true;
        }else{
            throw new IllegalStateException("Our Container provider is missing");
        }
    }

    public abstract void setChanged(T entity);
}
