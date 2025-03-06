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
import net.yxiao233.realmofdestiny.networking.ModNetWorking;
import net.yxiao233.realmofdestiny.networking.ProcessingEntityBlockWithMenuKeyDownSyncC2SPacket;
import net.yxiao233.realmofdestiny.util.KeyDownUtil;

public abstract class AbstractProcessingEntityBlockWithMenu<T extends AbstractProcessingBlockEntityWithMenu> extends AbstractProcessingEntityBlock<T>{
    public static boolean isShiftDown;
    public static boolean isCtrlDown;
    public static boolean isAltDown;
    public AbstractProcessingEntityBlockWithMenu(Properties pProperties) {
        super(pProperties);
    }
    public abstract boolean openMenuLogic(Level level, Player player);

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(level.isClientSide()){
            isShiftDown = KeyDownUtil.isShiftDown();
            isCtrlDown = KeyDownUtil.isCtrlDown();
            isAltDown = KeyDownUtil.isAltDown();
            ModNetWorking.sendToServer(new ProcessingEntityBlockWithMenuKeyDownSyncC2SPacket(isShiftDown,isCtrlDown,isAltDown));
        }

        if(!level.isClientSide()){
            BlockPos blockPos = hitResult.getBlockPos();
            if(openMenuLogic(level,player)){
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
