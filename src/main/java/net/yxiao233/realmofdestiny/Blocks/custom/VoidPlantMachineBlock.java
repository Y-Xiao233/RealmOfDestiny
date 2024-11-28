package net.yxiao233.realmofdestiny.Blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import net.yxiao233.realmofdestiny.Entities.VoidPlantMachineBlockEntity;
import net.yxiao233.realmofdestiny.ModRegistry.ModBlockEntities;
import net.yxiao233.realmofdestiny.modAbstracts.block.AbstractVoidMachineEntityBlock;

public class VoidPlantMachineBlock extends AbstractVoidMachineEntityBlock<VoidPlantMachineBlockEntity> {
    public VoidPlantMachineBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoidPlantMachineBlockEntity setBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new VoidPlantMachineBlockEntity(blockPos,blockState);
    }

    @Override
    public BlockEntityType getBlockEntityType() {
        return ModBlockEntities.VOID_PLANT_MACHINE_BE.get();
    }

    @Override
    public boolean openMenu(BlockPos blockPos, Player player, BlockEntity entity) {
        if(entity != null){
            NetworkHooks.openScreen(((ServerPlayer) player), (MenuProvider) entity,blockPos);
            return true;
        }else{
            throw new IllegalStateException("Our Container provider is missing");
        }
    }

    @Override
    public void setDrops(Level level, BlockPos blockPos) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if(blockEntity instanceof VoidPlantMachineBlockEntity entity){
            entity.drops();
        }
    }
}
