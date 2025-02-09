package net.yxiao233.realmofdestiny.api.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.yxiao233.realmofdestiny.api.block.entity.AbstractProcessingBlockEntityWithMenu;

public abstract class AbstractProcessingEntityBlockWithMenu<T extends AbstractProcessingBlockEntityWithMenu> extends AbstractProcessingEntityBlock<T>{
    public AbstractProcessingEntityBlockWithMenu(Properties pProperties) {
        super(pProperties);
    }
    public abstract boolean openMenuLogic();

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(!level.isClientSide()){
            BlockPos blockPos = hitResult.getBlockPos();
            if(openMenuLogic()){
                openMenu(blockPos,player,(T) level.getBlockEntity(blockPos));
            }

            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, pos, player, hand, hitResult);
    }

    public boolean openMenu(BlockPos blockPos, Player player, T entity){
        if(entity != null){
            NetworkHooks.openScreen(((ServerPlayer) player),entity,blockPos);
            return true;
        }else{
            throw new IllegalStateException("Our Container provider is missing");
        }
    }
}
