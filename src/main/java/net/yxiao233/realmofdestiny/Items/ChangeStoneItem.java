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
    public int failed;
    public int total;

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
        this.failed = 0;
        this.total = 0;
        BlockPos blockPos = getTarget(player);
        ItemStack checkBlockItemStack = recipe.get().getCheckBlockItem();

        ChanceList list = new ChanceList(recipe.get().getIngredients(),recipe.get().getChanceList());

        replaceBlock(level,blockPos,player,list);
        replaceNearbyBlock(level,blockPos,player,checkBlockItemStack,list);

        if(this.failed == this.total){
            player.sendSystemMessage(Component.translatable("recipe.realmofdestiny.changestone.failed"));
        }
    }
    public boolean replaceBlock(Level level, BlockPos blockPos, Player player, ChanceList list){
        BlockState blockState = list.getChanceBlockState();
        if(blockState.isAir()){
            this.failed ++;
        }
        this.total ++;
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
}
