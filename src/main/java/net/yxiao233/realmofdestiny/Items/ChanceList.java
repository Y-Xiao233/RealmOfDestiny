package net.yxiao233.realmofdestiny.Items;

import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

public class ChanceList {
    private ArrayList<BlockState> blockStateList = new ArrayList<>();
    private ArrayList<Double> rawChanceList = new ArrayList<>();
    private ArrayList<Double> chanceList = new ArrayList<>();
    private double totalChance;
    public ChanceList(BlockState blockState, double chance){
        add(blockState,chance);
    }
    public ChanceList(){}
    public ArrayList<BlockState> getBlockStateList(){
        return this.blockStateList;
    }
    public ArrayList<Double> getChanceList(){
        return this.chanceList;
    }
    public void add(BlockState blockState, double chance){
        this.totalChance += chance;
        this.blockStateList.add(blockState);
        this.rawChanceList.add(chance);
        this.chanceList.removeAll(this.chanceList);
        for(double rawChance : this.rawChanceList){
            double newChance = rawChance / this.totalChance;
            this.chanceList.add(newChance);
        }
    }
}
