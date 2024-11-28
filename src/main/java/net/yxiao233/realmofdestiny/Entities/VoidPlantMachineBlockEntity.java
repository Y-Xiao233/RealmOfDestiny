package net.yxiao233.realmofdestiny.Entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.yxiao233.realmofdestiny.ModRegistry.ModBlockEntities;
import net.yxiao233.realmofdestiny.modAbstracts.entity.AbstractVoidMachineBlockEntity;
import net.yxiao233.realmofdestiny.recipes.VoidPlantRecipe;
import net.yxiao233.realmofdestiny.screen.VoidPlantMachineMenu;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class VoidPlantMachineBlockEntity extends AbstractVoidMachineBlockEntity {
    public VoidPlantMachineBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.VOID_PLANT_MACHINE_BE.get(), pPos, pBlockState);
    }

    @Override
    public int setItemHandlerSize() {
        return 2;
    }

    @Override
    public Optional<?> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        inventory.setItem(0,this.itemHandler.getStackInSlot(0));

        return level.getRecipeManager().getRecipeFor(VoidPlantRecipe.Type.INSTANCE,inventory,level);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new VoidPlantMachineMenu(i,inventory,this,data);
    }
}
