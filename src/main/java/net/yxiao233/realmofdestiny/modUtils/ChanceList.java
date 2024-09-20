package net.yxiao233.realmofdestiny.modUtils;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Random;

public class ChanceList {
    private ArrayList<BlockState> blockStateList = new ArrayList<>();
    private ArrayList<Double> rawChanceList = new ArrayList<>();
    private ArrayList<Double> chanceList = new ArrayList<>();
    private ArrayList<Integer> countList = new ArrayList<>();
    private double totalChance;
    private Random random = new Random();
    public ChanceList(BlockState blockState, double chance){
        add(blockState,chance);
    }
    public ChanceList(NonNullList<Ingredient> ingredients, ArrayList<Double> chanceList){
        initChanceList(ingredients,chanceList);
    }
    public ChanceList(NonNullList<Ingredient> ingredients, ArrayList<Double> chanceList,int[] countList){
        initChanceList(ingredients,chanceList,countList);
    }
    public ChanceList(){}


    public ArrayList<BlockState> getBlockStateList(){
        return this.blockStateList;
    }
    public ArrayList<Double> getChanceList(){
        return this.chanceList;
    }
    public ArrayList<Integer> getCountList(){
        return this.countList;
    }


    public void add(BlockState blockState, double chance){
        this.totalChance += chance;
        this.blockStateList.add(blockState);
        this.rawChanceList.add(chance);
        this.chanceList.removeAll(this.chanceList);
        if(isOnly()){
            this.chanceList.add(this.rawChanceList.get(0));
            return;
        }
        for(double rawChance : this.rawChanceList){
            double newChance = rawChance / this.totalChance;
            this.chanceList.add(newChance);
        }
    }

    public void add(BlockState blockState, double chance, int count){
        this.totalChance += chance;
        this.blockStateList.add(blockState);
        this.countList.add(count);
        this.rawChanceList.add(chance);
        this.chanceList.removeAll(this.chanceList);
        if(isOnly()){
            this.chanceList.add(this.rawChanceList.get(0));
            return;
        }
        for(double rawChance : this.rawChanceList){
            double newChance = rawChance / this.totalChance;
            this.chanceList.add(newChance);
        }
    }
    public boolean isOnly(){
        return this.rawChanceList.size() == 1;
    }



    public void initChanceList(NonNullList<Ingredient> ingredients, ArrayList<Double> chanceList){
        for (int i = 0; i < ingredients.size(); i++) {
            BlockState blockState1 = Block.byItem(ingredients.get(i).getItems()[0].getItem()).defaultBlockState();
            double chance1 = chanceList.get(i);
            add(blockState1,chance1);
        }
    }
    public void initChanceList(NonNullList<Ingredient> ingredients, ArrayList<Double> chanceList,int[] countList){
        for (int i = 0; i < ingredients.size(); i++) {
            BlockState blockState1 = Block.byItem(ingredients.get(i).getItems()[0].getItem()).defaultBlockState();
            double chance1 = chanceList.get(i);
            add(blockState1,chance1,countList[i]);
        }
    }


    public ItemStack getChanceItemStack(){
        if(this.countList.isEmpty()){
            return Items.AIR.getDefaultInstance();
        }
        double chance = random.nextDouble(0,1);
        double totalChance = 0;
        ItemStack itemStack = null;
        for (int i = 0; i < this.chanceList.size(); i++) {
            double currentChance = this.chanceList.get(i);
            totalChance += currentChance;
            if(chance <= totalChance){
                itemStack = new ItemStack(this.blockStateList.get(i).getBlock().asItem(),this.countList.get(i));
                break;
            }
        }
        return itemStack == null ? Items.AIR.getDefaultInstance() : itemStack;
    }

    public BlockState getChanceBlockState(){
        if(this.blockStateList.isEmpty()){
            return Blocks.AIR.defaultBlockState();
        }
        double chance = random.nextDouble(0,1);
        double totalChance = 0;
        BlockState blockState = null;
        for (int i = 0; i < this.chanceList.size(); i++) {
            double currentChance = this.chanceList.get(i);
            totalChance += currentChance;
            if(chance <= totalChance){
                blockState = this.blockStateList.get(i);
                break;
            }
        }
        return blockState == null ? Blocks.AIR.defaultBlockState() : blockState;
    }
}
