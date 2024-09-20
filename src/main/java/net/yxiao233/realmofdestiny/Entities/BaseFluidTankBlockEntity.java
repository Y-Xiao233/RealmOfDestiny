package net.yxiao233.realmofdestiny.Entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.yxiao233.realmofdestiny.ModRegistry.ModBlockEntities;
import net.yxiao233.realmofdestiny.networking.ModNetWorking;
import net.yxiao233.realmofdestiny.networking.packet.FluidSyncS2CPacket;
import net.yxiao233.realmofdestiny.screen.BaseFluidTankMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BaseFluidTankBlockEntity extends BlockEntity implements MenuProvider {
    public FluidTank tank = new FluidTank(10000){
        @Override
        protected void onContentsChanged() {
            setChanged();
        }

        @Override
        public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
            return isBEFluidValid(stack);
        }
    };
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    public BaseFluidTankBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.BASE_FLUID_TANK_BE.get(), pPos, pBlockState);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction facing) {
        if(capability == ForgeCapabilities.FLUID_HANDLER){
            return lazyFluidHandler.cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void onLoad() {
        lazyFluidHandler = LazyOptional.of(() -> tank);
        super.onLoad();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tank.writeToNBT(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        tank.readFromNBT(tag);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.realmofdestiny.base_fluid_tank");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new BaseFluidTankMenu(i,inventory,this);
    }

    public FluidStack getFluidStackInTank(){
        return tank.getFluid();
    }

    public int getTankCap(){
        return tank.getTankCapacity(0);
    }

    public boolean isBEFluidValid(@NotNull FluidStack stack){
        boolean b1 = stack.getFluid().isSame(getFluidStackInTank().getFluid()) && stack.getAmount() <= getTankCap() - getFluidStackInTank().getAmount();
        boolean b2 = getFluidStackInTank().isEmpty() && stack.getAmount() <= getTankCap();
        return b1 || b2;
    }

    public void drain(FluidStack stack){
        tank.drain(stack, IFluidHandler.FluidAction.EXECUTE);
    }

    public void fill(FluidStack stack){
        tank.fill(stack, IFluidHandler.FluidAction.EXECUTE);
    }

    public void setTank(FluidStack stack){
        tank.setFluid(stack);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState){
        BlockEntity entity = level.getBlockEntity(blockPos);
        if(entity instanceof BaseFluidTankBlockEntity baseFluidTankBlockEntity){
            FluidStack stack = baseFluidTankBlockEntity.getFluidStackInTank();
            ModNetWorking.sendToClient(new FluidSyncS2CPacket(stack,blockPos,FluidSyncS2CPacket.PacketAction.SET));
        }
    }

    public void drops(){
        CompoundTag tag = new CompoundTag();
        tank.writeToNBT(tag);
        ItemStack stack = new ItemStack(this.getBlockState().getBlock().asItem());

        if(!tag.getString("FluidName").equals("minecraft:empty")){
            stack.setTag(tag);
        }

        ItemEntity itemEntity = new ItemEntity(level, getBlockPos().getX() + 0.5, getBlockPos().getY() + 1,getBlockPos().getZ() + 0.5,stack);
        level.addFreshEntity(itemEntity);
    }
}
