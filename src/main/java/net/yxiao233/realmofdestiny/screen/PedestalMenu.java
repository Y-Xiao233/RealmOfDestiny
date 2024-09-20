package net.yxiao233.realmofdestiny.screen;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.yxiao233.realmofdestiny.Entities.PedestalBlockEntity;
import net.yxiao233.realmofdestiny.ModRegistry.ModMenuTypes;
import net.yxiao233.realmofdestiny.modAbstracts.screen.AbstractModContainerMenu;

public class PedestalMenu extends AbstractModContainerMenu<PedestalBlockEntity> {
    public PedestalBlockEntity blockEntity;
    public final Level level;
    public Inventory inventory;

    public PedestalMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }
    public PedestalMenu(int pContainerId, Inventory inv, BlockEntity entity) {
        super(ModMenuTypes.PEDESTAL_MENU.get(), pContainerId, (PedestalBlockEntity) entity);
        init(0);
        checkContainerSize(inv, 1);
        this.blockEntity = ((PedestalBlockEntity) entity);
        this.level = inv.player.level();
        this.inventory = inv;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
