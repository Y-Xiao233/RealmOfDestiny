package net.yxiao233.realmofdestiny.Entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.yxiao233.realmofdestiny.modUtils.ChanceList;
import net.yxiao233.realmofdestiny.ModRegistry.ModBlockEntities;
import net.yxiao233.realmofdestiny.ModRegistry.ModBlocks;
import net.yxiao233.realmofdestiny.helper.recipe.KeyToItemStackHelper;
import net.yxiao233.realmofdestiny.recipes.PedestalGeneratorRecipe;
import net.yxiao233.realmofdestiny.recipes.PedestalLightingRecipe;
import net.yxiao233.realmofdestiny.screen.BaseFluidTankMenu;
import net.yxiao233.realmofdestiny.screen.PedestalMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class PedestalBlockEntity extends ModBaseBlockEntity implements MenuProvider {
    private boolean pressed = false;
    private int structureId = -1;
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
    private int generatorProgress = 0;
    private int lightingProgress = 0;
    private int generatorMaxProgress = 20;
    private int lightingMaxProgress = 20;
    private BlockPos containerBlockPos;
    private LazyOptional<IEnergyStorage> lazyEnergyStorage = LazyOptional.empty();
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    public PedestalBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.PEDESTAL_BE.get(), pPos, pBlockState);

        this.data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i){
                    case 0 -> PedestalBlockEntity.this.generatorProgress;
                    case 1 -> PedestalBlockEntity.this.generatorMaxProgress;
                    case 2 -> PedestalBlockEntity.this.lightingProgress;
                    case 3 -> PedestalBlockEntity.this.lightingMaxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i){
                    case 0 -> PedestalBlockEntity.this.generatorProgress = value;
                    case 1 -> PedestalBlockEntity.this.generatorMaxProgress = value;
                    case 2 -> PedestalBlockEntity.this.lightingProgress = value;
                    case 3 -> PedestalBlockEntity.this.lightingMaxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 4;
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
        pTag.putInt("pedestal_generator.progress",generatorProgress);
        pTag.putInt("pedestal_lighting.progress",lightingProgress);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.generatorProgress = pTag.getInt("pedestal_generator.progress");
        this.lightingProgress = pTag.getInt("pedestal_lighting.progress");
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
        pedestalGeneratorRecipes(blockPos,blockState);

        pedestalLightingRecipes(blockPos,blockState);
    }

    //Pedestal Generator
    private void pedestalGeneratorRecipes(BlockPos blockPos, BlockState blockState){
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
            Optional<PedestalGeneratorRecipe> recipe = getGeneratorRecipe();
            if(hasGeneratorRecipe(recipe,blockPos) && hasGeneratorEnoughEnergy(recipe)){
                this.generatorMaxProgress = recipe.get().getTime();
                increasrGeneratorProgress();
                setChanged(level,blockPos,blockState);

                if(hasGeneratorProgressFinished()){
                    craftItem(recipe);
                    resetGeneratorProgress();
                }
            }else{
                resetGeneratorProgress();
            }
        }
    }
    private boolean hasGeneratorRecipe(Optional<PedestalGeneratorRecipe> recipe,BlockPos pedestalBlockPos) {
        if(!recipe.isEmpty()){
            if(recipe.get().getKeyItemStack() == KeyToItemStackHelper.EMPTY){
                return true;
            }else{
                return checkBlock(recipe.get().getKeyItemStack(),recipe.get().getPatternsList(),pedestalBlockPos);
            }
        }
        return false;
    }

    private boolean hasGeneratorEnoughEnergy(Optional<PedestalGeneratorRecipe> recipe){
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
        BlockEntity containerEntity = level.getBlockEntity(this.containerBlockPos);

        containerEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent((inventory -> {
            if(inventory instanceof IItemHandler){
                ItemStack itemStack = chanceList.getChanceItemStack();
                ItemHandlerHelper.insertItem(inventory,itemStack,false);
                energyStorage.extractEnergy(recipe.get().getNeededEnergy(),false);
            }
        }));
    }
    private void increasrGeneratorProgress() {
        this.generatorProgress ++;
    }
    private boolean hasGeneratorProgressFinished() {
        return this.generatorProgress >= this.generatorMaxProgress;
    }

    private void resetGeneratorProgress() {
        this.generatorProgress = 0;
    }

    private Optional<PedestalGeneratorRecipe> getGeneratorRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        inventory.setItem(0,this.itemHandler.getStackInSlot(0));

        return this.level.getRecipeManager().getRecipeFor(PedestalGeneratorRecipe.Type.INSTANCE, inventory, level);
    }

    //Pedestal Lighting
    private void pedestalLightingRecipes(BlockPos blockPos, BlockState blockState){
        Optional<PedestalLightingRecipe> recipe = getLightingRecipe();
        if(hasLightingRecipe(recipe,blockPos) && hasLightingEnoughEnergy(recipe)){
            this.lightingMaxProgress = recipe.get().getTime();
            increasrLightingProgress();
            addParticle(blockPos);
            setChanged(level,blockPos,blockState);

            if(hasLightingProgressFinished()){
                craftItem(recipe,blockPos);
                resetLightingProgress();
            }
        }else{
            resetLightingProgress();
        }
    }

    private boolean hasLightingRecipe(Optional<PedestalLightingRecipe> recipe, BlockPos blockPos){
        if(!recipe.isEmpty()){
            for (int i = 1; i < level.getHeight() - blockPos.getY(); i++) {
                BlockPos pos = blockPos.offset(0,i,0);
                BlockState state = level.getBlockState(pos);
                if(state.is(ModBlocks.BOLT_LEAVES.get())){
                    return true;
                }else if(state.is(Blocks.AIR)){
                    continue;
                }else{
                    return false;
                }
            }
        }
        return false;
    }

    private boolean hasLightingEnoughEnergy(Optional<PedestalLightingRecipe> recipe){
        return energyStorage.getEnergyStored() >= recipe.get().getEnergy();
    }

    private void increasrLightingProgress(){
        this.lightingProgress ++;
    }

    private boolean hasLightingProgressFinished(){
        return this.lightingProgress >= this.lightingMaxProgress;
    }

    private void  resetLightingProgress(){
        this.lightingProgress = 0;
    }

    private void addParticle(BlockPos blockPos){
        ServerLevel serverLevel = (ServerLevel) level;
        serverLevel.sendParticles(ParticleTypes.AMBIENT_ENTITY_EFFECT,blockPos.getX() + 0.5,blockPos.getY() + 0.5,blockPos.getZ() + 0.5,4,0,0,0,0.05);
    }

    private void craftItem(Optional<PedestalLightingRecipe> recipe, BlockPos blockPos){
        LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,level);
        lightningBolt.setPos(blockPos.getX() + 0.5,blockPos.getY() + 0.5,blockPos.getZ() + 0.5);


        int count =  itemHandler.getStackInSlot(0).getCount();
        ItemStack itemStack = new ItemStack(recipe.get().getOutput().getItem(),count);

        level.addFreshEntity(lightningBolt);

        itemHandler.extractItem(0,count,false);
        energyStorage.extractEnergy(recipe.get().getEnergy(),false);

        Random random = new Random();
        if(random.nextDouble(0,1) <= recipe.get().getChance()){
            itemHandler.insertItem(0,itemStack,false);
        }
    }

    private Optional<PedestalLightingRecipe> getLightingRecipe(){
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        inventory.setItem(0,this.itemHandler.getStackInSlot(0));

        return this.level.getRecipeManager().getRecipeFor(PedestalLightingRecipe.Type.INSTANCE, inventory, level);
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

    public void setPressed(boolean hasPressed){
        this.pressed = hasPressed;
    }

    public boolean isPressed(){
        return this.pressed;
    }

    public int getStructureId(){
        return this.structureId;
    }

    public void increaseStructureId(){
        this.structureId ++;
    }

    public void setStructureId(int structureId){
        this.structureId = structureId;
    }

    public List<PedestalGeneratorRecipe> getPedestalGeneratorRecipeList(){
        return level.getRecipeManager().getAllRecipesFor(PedestalGeneratorRecipe.Type.INSTANCE);
    }
}