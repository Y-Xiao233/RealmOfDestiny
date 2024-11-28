package net.yxiao233.realmofdestiny.modAbstracts.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.yxiao233.realmofdestiny.Entities.ModBaseBlockEntity;
import net.yxiao233.realmofdestiny.modAbstracts.recipe.AbstractVoidMachineRecipe;
import net.yxiao233.realmofdestiny.networking.ModNetWorking;
import net.yxiao233.realmofdestiny.networking.packet.ContainerDataSyncS2CPacket;
import net.yxiao233.realmofdestiny.utils.AdvancedItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Optional;

public abstract class AbstractVoidMachineBlockEntity extends ModBaseBlockEntity implements MenuProvider {
    public ItemStackHandler itemHandler;
    public int progress = 0;
    public int maxProgress = 10;
    public final int size;
    public LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    public ContainerData data;
    public AbstractVoidMachineBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pPos, BlockState pBlockState) {
        super(blockEntityType,pPos, pBlockState);

        size = setItemHandlerSize();
        itemHandler = new ItemStackHandler(size){
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                if(!level.isClientSide()){
                    level.sendBlockUpdated(getBlockPos(),getBlockState(),getBlockState(),3);
                }
            }
        };

        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i){
                    case 0 -> AbstractVoidMachineBlockEntity.this.progress;
                    case 1 -> AbstractVoidMachineBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i){
                    case 0 -> AbstractVoidMachineBlockEntity.this.progress = value;
                    case 1 -> AbstractVoidMachineBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }


    public abstract int setItemHandlerSize();
    public abstract Optional<?> getCurrentRecipe();

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap,side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory",itemHandler.serializeNBT());
        pTag.putInt("void_plant_machine.progress",progress);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.progress = pTag.getInt("void_plant_machine.progress");
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level,this.worldPosition,inventory);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        voidMachineRecipe(level,blockPos,blockState);
    }

    public void setMaxProgress(int time){
        maxProgress = time;
        ModNetWorking.sendToClient(new ContainerDataSyncS2CPacket(progress,maxProgress,this.getBlockPos()));
    }

    public void increaseProgress(){
        progress ++;
        ModNetWorking.sendToClient(new ContainerDataSyncS2CPacket(progress,maxProgress,this.getBlockPos()));
    }

    public void resetProgress(){
        progress = 0;
        ModNetWorking.sendToClient(new ContainerDataSyncS2CPacket(progress,maxProgress,this.getBlockPos()));
    }

    public void voidMachineRecipe(Level level, BlockPos blockPos, BlockState blockState){
        Optional<?> recipe = getCurrentRecipe();

        if(recipe.isEmpty()){
            resetProgress();
            return;
        }

        if(recipe.get() instanceof AbstractVoidMachineRecipe voidMachineRecipe && canInsertItem(voidMachineRecipe)){
            setMaxProgress(voidMachineRecipe.getTime());
            increaseProgress();
            setChanged(level,blockPos,blockState);

            if(progress >= maxProgress){
                resetProgress();
                craftItem(voidMachineRecipe);
            }

        }else{
            resetProgress();
        }
    }


    //TODO 修改输出物品逻辑
    private boolean canInsertItem(AbstractVoidMachineRecipe recipe) {
        int time = 0;
        ArrayList<AdvancedItemStack> itemStacks = recipe.getOutputs();
        for (int i = 1; i < itemHandler.getSlots(); i++) {
            for (int i1 = 0; i1 < itemStacks.size(); i1++) {
                if(itemHandler.getStackInSlot(i).is(itemStacks.get(i1).getItemStack().getItem())){
                    itemStacks.set(i1,AdvancedItemStack.EMPTY);
                    time ++;
                }
            }
        }


        return /*time == itemStacks.size()*/true;
    }

    public void craftItem(AbstractVoidMachineRecipe recipe){
        itemHandler.extractItem(0,recipe.getInput().getItemStack().getCount(),false);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.realmofdestiny.void_plant_machine");
    }

    @Nullable
    @Override
    public abstract AbstractContainerMenu createMenu(int i, Inventory inventory, Player player);
}
