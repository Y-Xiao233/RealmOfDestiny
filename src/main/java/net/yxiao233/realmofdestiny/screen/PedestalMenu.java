package net.yxiao233.realmofdestiny.screen;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import net.yxiao233.realmofdestiny.block.entity.PedestalBlockEntity;
import net.yxiao233.realmofdestiny.registry.ModMenuTypes;

public class PedestalMenu extends AbstractModContainerMenu<PedestalBlockEntity> {
    public PedestalBlockEntity blockEntity;
    public final Level level;
    public Inventory inventory;

    public PedestalMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }
    public PedestalMenu(int pContainerId, Inventory inv, BlockEntity entity) {
        super(ModMenuTypes.PEDESTAL_MENU.get(), pContainerId, (PedestalBlockEntity) entity);
        init(4);
        checkContainerSize(inv, 1);
        this.blockEntity = ((PedestalBlockEntity) entity);
        this.level = inv.player.level();
        this.inventory = inv;

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER,PedestalBlockEntity.UPGRADES_ITEM_HANDLER).ifPresent(iItemHandler -> {
            int x = 52;
            int y = 30;
            for (int i = 0; i < iItemHandler.getSlots(); i++) {
                this.addSlot(new SlotItemHandler(iItemHandler,i,x,y));
                x += 20;
            }
        });

        addPlayerInventory(inv);
        addPlayerHotbar(inv);
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
