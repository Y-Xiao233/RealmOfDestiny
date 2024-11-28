package net.yxiao233.realmofdestiny.utils;

import java.util.ArrayList;

public class RecipeList{
    private final AdvancedItemStack input;
    private final ArrayList<AdvancedItemStack> outputs;
    private final int energy;
    private final int time;
    public RecipeList(AdvancedItemStack input, ArrayList<AdvancedItemStack> outputs, int energy, int time){
        this.input = input;
        this.outputs = outputs;
        this.energy = energy;
        this.time = time;
    }

    public AdvancedItemStack getInput() {
        return input;
    }

    public ArrayList<AdvancedItemStack> getOutputs() {
        return outputs;
    }

    public int getEnergy() {
        return energy;
    }

    public int getTime() {
        return time;
    }
}
