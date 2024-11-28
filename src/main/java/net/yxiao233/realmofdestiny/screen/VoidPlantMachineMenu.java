package net.yxiao233.realmofdestiny.screen;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.yxiao233.realmofdestiny.Entities.VoidPlantMachineBlockEntity;
import net.yxiao233.realmofdestiny.ModRegistry.ModMenuTypes;
import net.yxiao233.realmofdestiny.modAbstracts.screen.AbstractVoidMachineMenu;

public class VoidPlantMachineMenu extends AbstractVoidMachineMenu<VoidPlantMachineBlockEntity> {
    public final Level level;
    public VoidPlantMachineMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()),new SimpleContainerData(2));
    }

    public VoidPlantMachineMenu(int pContainerId, Inventory inv, BlockEntity entity,ContainerData data) {
        super(ModMenuTypes.VOID_PLANT_MACHINE_MENU.get(), pContainerId, (VoidPlantMachineBlockEntity) entity,data);
        init(2,inv);

        this.level = inv.player.level();
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
