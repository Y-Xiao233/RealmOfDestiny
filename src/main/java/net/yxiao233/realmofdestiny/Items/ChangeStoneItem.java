package net.yxiao233.realmofdestiny.Items;


import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.yxiao233.realmofdestiny.ModRegistry.ModItems;
import net.yxiao233.realmofdestiny.recipes.ChangeStoneRecipe;
import net.yxiao233.realmofdestiny.recipes.GemPolishingRecipe;
import net.yxiao233.realmofdestiny.recipes.container.EmptyContainer;

import java.util.*;

public class ChangeStoneItem extends Item {
    public int i;

    public ChangeStoneItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(pLevel.isClientSide()){
            return super.use(pLevel, pPlayer, pUsedHand);
        }

        Optional<ChangeStoneRecipe> recipe = getCurrentRecipe(pPlayer,pLevel);
        if(!recipe.isEmpty()){
            craft(pLevel,pPlayer,recipe);
            return InteractionResultHolder.success(pPlayer.getMainHandItem());
        }else{
            return super.use(pLevel, pPlayer, pUsedHand);
        }
    }
    public void craft(Level level, Player player, Optional<ChangeStoneRecipe> recipe){
        this.i = 0;
        BlockPos blockPos = getTarget(player);
        ItemStack checkBlockItemStack = recipe.get().getCheckBlockItem();

        NonNullList<Ingredient> ingredients = recipe.get().getIngredients();
        ArrayList<Double> chanceList = recipe.get().getChanceList();
        ChanceList list = initChanceList(ingredients,chanceList);

        replaceBlock(level,blockPos,player,list);
        replaceNearbyBlock(level,blockPos,player,checkBlockItemStack,list);
    }
    public boolean replaceBlock(Level level, BlockPos blockPos, Player player, ChanceList list){
        BlockState blockState = getChanceBlockState(list);
        if(blockState == Blocks.AIR.defaultBlockState() && this.i == 0){
            this.i = 1;
            player.sendSystemMessage(Component.translatable("recipe.realmofdestiny.changestone.failed"));
        }
        return level.setBlock(blockPos,blockState,3);
    }
    public void replaceNearbyBlock(Level level, BlockPos blockPos, Player player, ItemStack checkBlockItemStack, ChanceList list){
        BlockPos[] nearbyBlockPosList = {
                blockPos.offset(0,0,-1),
                blockPos.offset(0,0,1),
                blockPos.offset(1,0,0),
                blockPos.offset(-1,0,0),
                blockPos.offset(0,1,0),
                blockPos.offset(0,-1,0)
        };
        for(BlockPos pos : nearbyBlockPosList){
            Block checkBlock = Block.byItem(checkBlockItemStack.getItem());
            if(level.getBlockState(pos).getBlock() == checkBlock){
                replaceBlock(level,pos,player,list);
                replaceNearbyBlock(level,pos,player,checkBlockItemStack,list);
            }
        }
    }
    public BlockPos getTarget(Player player){
        HitResult hitResult = player.pick(5.0D,player.getEyeHeight(),false);
        BlockHitResult blockHitResult = null;
        if(hitResult.getType() == HitResult.Type.BLOCK){
            blockHitResult = (BlockHitResult) hitResult;
        }

        return blockHitResult.getBlockPos();
    }
    private Optional<ChangeStoneRecipe> getCurrentRecipe(Player player, Level level) {
        EmptyContainer emptyContainer = new EmptyContainer(player);
        return level.getRecipeManager().getRecipeFor(ChangeStoneRecipe.Type.INSTANCE, emptyContainer, level);
    }
    public BlockState getChanceBlockState(ChanceList list){
        Random random = new Random();
        double chance = random.nextDouble(0,1);
        double totalChance = 0;
        BlockState blockState = null;
        for (int i = 0; i < list.getChanceList().size(); i++) {
            double currentChance = list.getChanceList().get(i);
            totalChance += currentChance;
            if(chance <= totalChance){
                blockState = list.getBlockStateList().get(i);
                break;
            }
        }
        return blockState == null ? Blocks.AIR.defaultBlockState() : blockState;
    }
    public ChanceList initChanceList(NonNullList<Ingredient> ingredients, ArrayList<Double> chanceList){
        ChanceList list = new ChanceList();
        for (int i = 0; i < ingredients.size(); i++) {
            BlockState blockState1 = Block.byItem(ingredients.get(i).getItems()[0].getItem()).defaultBlockState();
            double chance1 = chanceList.get(i);
            list.add(blockState1,chance1);
        }
        return list;
    }
}
