package net.yxiao233.realmofdestiny.Entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import net.yxiao233.realmofdestiny.Items.ChanceList;
import net.yxiao233.realmofdestiny.ModRegistry.ModBlockEntities;
import net.yxiao233.realmofdestiny.helper.recipe.KeyToItemStackHelper;
import net.yxiao233.realmofdestiny.recipes.PedestalGeneratorRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PedestalBlockEntity extends BlockEntity {
    public ItemStackHandler itemHandler = new ItemStackHandler(1){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(),getBlockState(),getBlockState(),3);
            }
        }
    };
    public EnergyStorage energyStorage = new EnergyStorage(100000);


    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 20;
    private BlockPos containerBlockPos;
    private LazyOptional<IEnergyStorage> lazyEnergyStorage = LazyOptional.empty();
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    public PedestalBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.PEDESTAL_BE.get(), pPos, pBlockState);

        this.data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i){
                    case 0 -> PedestalBlockEntity.this.progress;
                    case 1 -> PedestalBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i){
                    case 0 -> PedestalBlockEntity.this.progress = value;
                    case 1 -> PedestalBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
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
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyStorage = LazyOptional.of(() -> energyStorage);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyStorage.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory",itemHandler.serializeNBT());
        pTag.put("energy",energyStorage.serializeNBT());
        pTag.putInt("pedestal.progress",progress);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.progress = pTag.getInt("pedestal.progress");
        energyStorage.deserializeNBT(pTag.get("energy"));
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
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
        boolean hasContainerNearby = false;
        BlockPos[] nearbyBlockPosList = {
                blockPos.offset(0,0,-1),
                blockPos.offset(0,0,1),
                blockPos.offset(1,0,0),
                blockPos.offset(-1,0,0),
                blockPos.offset(0,1,0),
                blockPos.offset(0,-1,0)
        };
        for (int i = 0; i < nearbyBlockPosList.length; i++) {
            BlockEntity blockEntity = level.getBlockEntity(nearbyBlockPosList[i]);
            hasContainerNearby = blockEntity != null && blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent();
            if(hasContainerNearby){
                this.containerBlockPos = nearbyBlockPosList[i];
                break;
            }
        }

        if(hasContainerNearby){
            Optional<PedestalGeneratorRecipe> recipe = getCurrentRecipe();
            if(hasRecipe(recipe,blockPos) && hasEnoughEnergy(recipe)){
                this.maxProgress = recipe.get().getTime();
                increasrCraftingProgress();
                setChanged(level,blockPos,blockState);

                if(hasProgressFinished()){
                    craftItem(recipe);
                    resetProgress();
                }
            }else{
                resetProgress();
            }
        }
    }

    private boolean hasRecipe(Optional<PedestalGeneratorRecipe> recipe,BlockPos pedestalBlockPos) {
        if(!recipe.isEmpty()){
            return checkBlock(recipe.get().getKeyItemStack(),recipe.get().getPatternsList(),pedestalBlockPos);
        }
        return false;
    }

    private boolean hasEnoughEnergy(Optional<PedestalGeneratorRecipe> recipe){
        return energyStorage.getEnergyStored() >= recipe.get().getNeededEnergy();
    }
    private boolean checkBlock(KeyToItemStackHelper helper, char[][][] patternsList, BlockPos blockPos){
        int[] pbp = findPedestal(patternsList);
        int right = 0;
        BlockPos rightBlockPos = blockPos.offset(pbp[0],pbp[1],pbp[2]);
        for (int y = 0; y < patternsList.length; y++) {
            int offsetY = -y;
            for (int x = 0; x < patternsList[y].length; x++) {
                int offsetX = -x;
                for (int z = 0; z < patternsList[y][x].length; z++) {
                    int offsetZ = -z;
                    BlockPos newPos = rightBlockPos.offset(offsetX,offsetY,offsetZ);
                    if(increaseRight(helper,newPos,patternsList[y][x][z])){
                        right ++;
                    }
                }
            }
        }
        return patternsList.length * patternsList[0].length * patternsList[0][0].length == right;
    }

    private boolean increaseRight(KeyToItemStackHelper helper, BlockPos newPos, char c){
        String key = String.valueOf(c);
        if(key.equals(" ") || key.equals("P")){
            return true;
        }else{
            ItemStack recipeBlock = helper.getCurrentItemStack(key);
            Item worldBlock = level.getBlockState(newPos).getBlock().asItem();
            return recipeBlock.is(worldBlock);
        }
    }
    private int[] findPedestal(char[][][] patternsList) {
        for (int y = 0; y < patternsList.length; y++) {
            for (int x = 0; x < patternsList[y].length; x++) {
                for (int z = 0; z < patternsList[y][x].length; z++) {
                    if(String.valueOf(patternsList[y][x][z]).equals("P")){
                        return new int[]{x,y,z};
                    }
                }
            }
        }
        return new int[]{0,0,0};
    }

    private void craftItem(Optional<PedestalGeneratorRecipe> recipe) {
        ChanceList chanceList = new ChanceList(recipe.get().getIngredients(),recipe.get().getChanceList(),recipe.get().getCountList());
        BlockEntity containerEntity =  level.getBlockEntity(this.containerBlockPos);

        containerEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent((inventory -> {
            if(inventory instanceof IItemHandler){
                ItemStack itemStack = chanceList.getChanceItemStack();
                ItemHandlerHelper.insertItem(inventory,itemStack,false);
                energyStorage.extractEnergy(recipe.get().getNeededEnergy(),false);
            }
        }));
    }
    private void increasrCraftingProgress() {
        this.progress ++;
    }
    private boolean hasProgressFinished() {
        return this.progress >= this.maxProgress;
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private Optional<PedestalGeneratorRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        inventory.setItem(0,this.itemHandler.getStackInSlot(0));

        return this.level.getRecipeManager().getRecipeFor(PedestalGeneratorRecipe.Type.INSTANCE, inventory, level);
    }
}
