package net.yxiao233.realmofdestiny.recipes.container;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class EmptyContainer implements Container {
    private final Player player;
    private BlockPos blockPos;

    public EmptyContainer(Player player) {
        this.player = player;
        if(player != null){
            HitResult hitResult = player.pick(5.0D,player.getEyeHeight(),false);
            if(hitResult.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                blockPos = blockHitResult.getBlockPos();
            }
        }
    }

    public Player getPlayer() {
        return player;
    }
    public BlockPos getBlockPos(){
        return blockPos;
    }

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public ItemStack getItem(int i) {
        return null;
    }

    @Override
    public ItemStack removeItem(int i, int i1) {
        return null;
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        return null;
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {

    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }

    @Override
    public void clearContent() {

    }
}
