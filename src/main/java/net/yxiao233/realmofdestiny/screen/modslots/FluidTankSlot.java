package net.yxiao233.realmofdestiny.screen.modslots;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

public class FluidTankSlot extends Slot {
    private IFluidHandler fluidHandler;
    private static Container emptyInventory = new SimpleContainer(0);

    public FluidTankSlot(IFluidHandler fluidHandler, int index, int xPosition, int yPosition) {
        super(emptyInventory, index, xPosition, yPosition);
        this.fluidHandler = fluidHandler;
    }

    @Override
    public boolean mayPlace(ItemStack pStack) {
        return false;
    }

    public boolean mayPlace(@NotNull FluidStack stack) {
        return false;
//        return stack.isEmpty() ? false : this.fluidHandler.isFluidValid(this.index, stack);
    }


    public @NotNull FluidStack getFluid() {
        return this.getFluidHandler().getFluidInTank(this.index);
    }

    public void set(@NotNull FluidStack stack) {
        this.getFluidHandler().fill(stack, IFluidHandler.FluidAction.SIMULATE);
        this.setChanged();
    }

    public void initialize(FluidStack stack) {
        this.getFluidHandler().fill(stack, IFluidHandler.FluidAction.SIMULATE);
        this.setChanged();
    }

    public void onQuickCraft(@NotNull FluidStack oldStackIn, @NotNull FluidStack newStackIn) {
    }

    public int getMaxStackSize() {
        return this.fluidHandler.getTankCapacity(this.index);
    }

    public int getMaxFluidAmount() {
        return fluidHandler.getTankCapacity(this.index);
    }

    public boolean mayPickup(Player playerIn) {
        return false;
//        return !this.getFluidHandler().drain(this.index, IFluidHandler.FluidAction.SIMULATE).isEmpty();
    }

    public IFluidHandler getFluidHandler() {
        return this.fluidHandler;
    }
}
