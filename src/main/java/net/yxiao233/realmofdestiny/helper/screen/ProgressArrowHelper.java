package net.yxiao233.realmofdestiny.helper.screen;

import net.yxiao233.realmofdestiny.modAbstracts.screen.AbstractVoidMachineScreen;

public class ProgressArrowHelper {
    private final AbstractVoidMachineScreen.Axis axis;
    private final int[] location;

    public ProgressArrowHelper(AbstractVoidMachineScreen.Axis axis, int[] location){
        this.axis = axis;
        this.location = location;
    }

    public ProgressArrowHelper(AbstractVoidMachineScreen.Axis axis, int x, int y){
        this.axis = axis;
        this.location = new int[]{x,y};
    }

    public AbstractVoidMachineScreen.Axis getAxis() {
        return axis;
    }

    public int[] getLocation() {
        return location;
    }
}
