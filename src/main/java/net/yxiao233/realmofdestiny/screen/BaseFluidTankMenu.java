package net.yxiao233.realmofdestiny.screen;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;
import net.yxiao233.realmofdestiny.Entities.BaseFluidTankBlockEntity;
import net.yxiao233.realmofdestiny.ModRegistry.ModMenuTypes;
import net.yxiao233.realmofdestiny.networking.ModNetWorking;
import net.yxiao233.realmofdestiny.networking.packet.FluidSyncS2CPacket;

public class BaseFluidTankMenu extends AbstractModContainerMenu<BaseFluidTankBlockEntity> {
    public BaseFluidTankBlockEntity blockEntity;
    public final Level level;
    public Inventory inventory;

    public BaseFluidTankMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public BaseFluidTankMenu(int pContainerId, Inventory inv, BlockEntity entity) {
        super(ModMenuTypes.BASE_FLUID_TANK_MENU.get(), pContainerId, (BaseFluidTankBlockEntity) entity);
        init(0);
        checkContainerSize(inv, 1);
        this.blockEntity = ((BaseFluidTankBlockEntity) entity);
        this.level = inv.player.level();
        this.inventory = inv;

        FluidStack stack = blockEntity.getFluidStackInTank();
        int amount = stack.getAmount();
        FluidStack newStack = new FluidStack(stack,amount);
        ModNetWorking.sendToClient(new FluidSyncS2CPacket(newStack,blockEntity.getBlockPos(), FluidSyncS2CPacket.FluidCs2PacketAction.SET));

        addPlayerInventory(inv);
        addPlayerHotbar(inv);
    }

    @Override
    public boolean stillValid(Player player) {
        return getStillValid(level,player);
    }

    public FluidStack getFluidStackInTank(){
        return blockEntity.getFluidStackInTank();
    }
}
