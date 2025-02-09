package net.yxiao233.realmofdestiny.block.entity;

import com.hrznstudio.titanium.util.RecipeUtil;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.yxiao233.realmofdestiny.config.machine.PedestalConfig;
import net.yxiao233.realmofdestiny.api.item.custom.AddonItem;
import net.yxiao233.realmofdestiny.recipe.PedestalGeneratorRecipe;
import net.yxiao233.realmofdestiny.registry.ModBlockEntities;
import net.yxiao233.realmofdestiny.registry.ModRecipes;
import net.yxiao233.realmofdestiny.screen.PedestalMenu;
import net.yxiao233.realmofdestiny.util.ItemHandlerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class PedestalBlockEntity extends BlockEntity implements MenuProvider {
    public ItemStackHandler itemHandler = new ItemStackHandler(1){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(),getBlockState(),getBlockState(),3);
            }
        }
    };

    public ItemStackHandler upgradeItemHandler = new ItemStackHandler(4){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(),getBlockState(),getBlockState(),3);
            }
        }
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return stack.getItem() instanceof AddonItem;
        }

        @Override
        public int getSlotLimit(int slot) {
            return 4;
        }
    };

    public EnergyStorage energyStorage = new EnergyStorage(PedestalConfig.maxStoredPower);
    public int progress = 0;
    public int maxProgress = 20;
    private int neededEnergy;
    private BlockPos containerBlockPos;
    public PedestalGeneratorRecipe currentRecipe;
    private LazyOptional<IEnergyStorage> lazyEnergyStorage = LazyOptional.empty();
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    public LazyOptional<IItemHandler> lazyUpgradeItemHandler = LazyOptional.empty();
    public static final int SLOT_ITEM_HANDLER = 0;
    public static final int UPGRADES_ITEM_HANDLER = 1;
    public PedestalBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.PEDESTAL_BE.get(), pPos, pBlockState);
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyItemHandler.cast();
        } else if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyStorage.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if(cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyItemHandler.cast();
        } else if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyStorage.cast();
        }
        return super.getCapability(cap);
    }

    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, int type) {
        if(cap == ForgeCapabilities.ITEM_HANDLER){
            return switch (type){
                case SLOT_ITEM_HANDLER -> lazyItemHandler.cast();
                case UPGRADES_ITEM_HANDLER -> lazyUpgradeItemHandler.cast();
                default -> lazyItemHandler.cast();
            };
        }
        return super.getCapability(cap);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyUpgradeItemHandler = LazyOptional.of(() -> upgradeItemHandler);
        lazyEnergyStorage = LazyOptional.of(() -> energyStorage);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyUpgradeItemHandler.invalidate();
        lazyEnergyStorage.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory",itemHandler.serializeNBT());
        pTag.put("upgrades",upgradeItemHandler.serializeNBT());
        pTag.put("energy",energyStorage.serializeNBT());
        pTag.putInt("progress",progress);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.progress = pTag.getInt("progress");
        energyStorage.deserializeNBT(pTag.get("energy"));
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        upgradeItemHandler.deserializeNBT(pTag.getCompound("upgrades"));
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots()+upgradeItemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        for (int i = 0; i < upgradeItemHandler.getSlots(); i++) {
            inventory.setItem(itemHandler.getSlots()+i,upgradeItemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level,this.worldPosition,inventory);
    }

    public ItemStack getRenderStack(){
        return itemHandler.getStackInSlot(0).isEmpty() ? new ItemStack(Items.AIR) : itemHandler.getStackInSlot(0);
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
        pedestalGeneratorRecipes(level,blockPos);
    }

    private void pedestalGeneratorRecipes(Level level, BlockPos blockPos) {
        checkForRecipe(level);
        checkForContainer(level,blockPos);

        if(currentRecipe != null && containerBlockPos != null){
            progressPrefix();
            this.neededEnergy = currentRecipe.energy;

            if(canIncrease()){
                increaseProgress();

            }

            if(this.progress >= this.maxProgress){
                craftItem(level);
            }
        }else{
            resetProgress();
        }
    }

    public void progressPrefix(){
        this.maxProgress = currentRecipe.processingTime;
        for (int i = 0; i < upgradeItemHandler.getSlots(); i++) {
            if(upgradeItemHandler.getStackInSlot(i).getItem() instanceof AddonItem addonItem){
                if(addonItem.type == AddonItem.Type.SPEED){
                    if(addonItem.value >= 100){
                        this.maxProgress = 0;
                    }else{
                        for (int m = 0; m < upgradeItemHandler.getStackInSlot(i).getCount(); m++) {
                            this.maxProgress = (int) (this.maxProgress * (1 - 0.01 * addonItem.value));
                        }
                    }
                }
            }
        }
    }

    public boolean checkForRecipe(Level level){
        if (!level.isClientSide()) {
            if (this.currentRecipe != null && this.currentRecipe.matches(itemHandler)) {
                return true;
            }

            this.currentRecipe = RecipeUtil.getRecipes(this.level, (RecipeType<PedestalGeneratorRecipe>) ModRecipes.PEDESTAL_TYPE.get()).stream().filter((pedestalRecipe) -> {
                return pedestalRecipe.matches(itemHandler);
            }).findFirst().orElse(null);
            return this.currentRecipe != null;
        }
        return false;
    }

    public boolean checkForContainer(Level level, BlockPos blockPos) {
        if(currentRecipe == null){
            this.containerBlockPos = null;
            return false;
        }
        boolean hasContainerNearby = false;
        BlockPos[] nearbyBlockPosList = {
                blockPos.offset(0,0,-1),
                blockPos.offset(0,0,1),
                blockPos.offset(1,0,0),
                blockPos.offset(-1,0,0),
                blockPos.offset(0,1,0),
                blockPos.offset(0,-1,0)
        };
        for (BlockPos pos : nearbyBlockPosList) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            hasContainerNearby = blockEntity != null && blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent();
            if (hasContainerNearby) {
                if (!isPedestal(blockEntity) && canInsert(blockEntity)) {
                    this.containerBlockPos = pos;
                    break;
                }
            } else {
                this.containerBlockPos = null;
            }
        }
        return this.containerBlockPos != null;
    }

    private boolean isPedestal(BlockEntity entity){
        return entity instanceof PedestalBlockEntity;
    }

    public boolean canInsert(BlockEntity entity){
        AtomicBoolean c = new AtomicBoolean(false);
        entity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(itemHandler -> {
            if(ItemHandlerUtil.canInsertItem(itemHandler,currentRecipe.output)){
                c.set(true);
            }
        });

        return c.get();
    }

    public boolean canIncrease(){
        return this.energyStorage.getEnergyStored() >= this.neededEnergy;
    }

    private void increaseProgress(){
        this.progress ++;
    }

    private void resetProgress(){
        this.progress = 0;
    }

    private void craftItem(Level level){
        Random random = new Random();
        double outputChance = outputChancePrefix();
        level.getBlockEntity(containerBlockPos).getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(itemHandler -> {
            for (int i = 0; i < currentRecipe.output.length; i++) {
                if(random.nextDouble(0,1) < outputChance){
                    ItemHandlerHelper.insertItem(itemHandler,currentRecipe.output[i],false);
                }
            }
        });
        double inputConsumeChance = inputConsumeChancePrefix();
        if(inputConsumeChance != 0 && random.nextDouble(0,1) < inputConsumeChance){
            itemHandler.extractItem(0,1,false);
        }

        energyStorage.extractEnergy(currentRecipe.energy,false);
        resetProgress();
    }

    private double inputConsumeChancePrefix(){
        double chance = currentRecipe.inputConsumeChance;
        for (int i = 0; i < upgradeItemHandler.getSlots(); i++) {
            if(upgradeItemHandler.getStackInSlot(i).getItem() instanceof AddonItem addonItem){
                if(addonItem.type == AddonItem.Type.INPUT_CHANCE){
                    if(addonItem.value >= 100){
                        chance = 0;
                    }else{
                        for (int m = 0; m < upgradeItemHandler.getStackInSlot(i).getCount(); m++) {
                            chance = chance * (1 - 0.01 * addonItem.value);
                        }
                    }
                }
            }
        }
        return chance;
    }

    private double outputChancePrefix(){
        double chance = currentRecipe.outputChance;
        for (int i = 0; i < upgradeItemHandler.getSlots(); i++) {
            if(upgradeItemHandler.getStackInSlot(i).getItem() instanceof AddonItem addonItem){
                if(addonItem.type == AddonItem.Type.OUTPUT_CHANCE){
                    if(addonItem.value >= 100){
                        chance = 1;
                    }else{
                        for (int m = 0; m < upgradeItemHandler.getStackInSlot(i).getCount(); m++) {
                            chance = chance * (1 + 0.01 * addonItem.value);
                        }
                    }
                }
            }
        }
        return chance;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.realmofdestiny.pedestal");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new PedestalMenu(i,inventory,this);
    }
}
