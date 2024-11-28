package net.yxiao233.realmofdestiny.modAbstracts.screen;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import net.yxiao233.realmofdestiny.modAbstracts.entity.AbstractVoidMachineBlockEntity;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractVoidMachineMenu<T extends AbstractVoidMachineBlockEntity> extends AbstractContainerMenu {
    public final T blockEntity;
    public final ContainerData data;
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    public int TE_INVENTORY_SLOT_COUNT = 2;  // must be the number of slots you have!
    protected AbstractVoidMachineMenu(@Nullable MenuType<?> pMenuType, int pContainerId, T blockEntity, ContainerData data) {
        super(pMenuType, pContainerId);
        this.blockEntity = blockEntity;
        this.data = data;

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            int x = 90;
            int y = 25;

            this.addSlot(new SlotItemHandler(iItemHandler,0,30,25));
            for (int i = 1; i < iItemHandler.getSlots(); i++) {
                this.addSlot(new SlotItemHandler(iItemHandler, i, x, y));
                x += 20;
                if(i % 3 == 0){
                    y += 20;
                    x = 90;
                }
            }
        });
    }

    public void init(int slotCount, Inventory inventory){
        this.TE_INVENTORY_SLOT_COUNT = slotCount;
        checkContainerSize(inventory, slotCount);
        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
    }
    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        Slot sourceSlot = slots.get(i);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (i < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else if (i < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + i);
            return ItemStack.EMPTY;
        }
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(player, sourceStack);
        return copyOfSourceStack;
    }

    public abstract boolean stillValid(Player player);


    public boolean getStillValid(Level level, Player player){
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, blockEntity.getBlockState().getBlock());
    }

    protected void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    protected void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
    public boolean isCrafting() {
        return blockEntity.progress > 0;
    }

    public int getScaledProgress() {
        int progress = blockEntity.progress;
        int maxProgress = blockEntity.maxProgress;
        int progressArrowSize = 26;

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

}
