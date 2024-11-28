package net.yxiao233.realmofdestiny.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.yxiao233.realmofdestiny.helper.screen.ProgressArrowHelper;
import net.yxiao233.realmofdestiny.modAbstracts.screen.AbstractVoidMachineScreen;

public class VoidPlantMachineScreen extends AbstractVoidMachineScreen<VoidPlantMachineMenu> {
    public VoidPlantMachineScreen(VoidPlantMachineMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }


    //TODO 位置确定精确
    @Override
    public ProgressArrowHelper setProgressArrowLocation() {
        return new ProgressArrowHelper(Axis.X,58,29);
    }

    @Override
    protected void init() {
        super.init();
        setTEXTURE("textures/gui/blank_gui.png");
    }
}
