package net.yxiao233.realmofdestiny.networking.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;
import net.yxiao233.realmofdestiny.Entities.BaseFluidTankBlockEntity;
import net.yxiao233.realmofdestiny.screen.BaseFluidTankMenu;

import java.util.function.Supplier;

public class FluidSyncS2CPacket extends AbstractModSyncPacket {
    private final FluidStack stack;
    private final BlockPos blockPos;
    public final PacketAction action;

    public FluidSyncS2CPacket(FluidStack stack, BlockPos blockPos, PacketAction action) {
        this.stack = stack;
        this.blockPos = blockPos;
        this.action = action;
    }

    public FluidSyncS2CPacket(FriendlyByteBuf buffer) {
        this.stack = buffer.readFluidStack();
        this.blockPos = buffer.readBlockPos();
        this.action = buffer.readEnum(PacketAction.class);
    }

    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        if(buffer != null){
            buffer.writeFluidStack(stack);
        }
        buffer.writeBlockPos(blockPos);
        buffer.writeEnum(action);
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if(Minecraft.getInstance().level.getBlockEntity(blockPos) instanceof BaseFluidTankBlockEntity entity){
                action(entity);
            }

            if(Minecraft.getInstance().player.containerMenu instanceof BaseFluidTankMenu menu){
                action(menu.blockEntity);
            }
        });
        return true;
    }

    public void action(BaseFluidTankBlockEntity entity){
        if(action == PacketAction.DRAIN){
            entity.drain(stack);
        }else if (action == PacketAction.FILL) {
            entity.fill(stack);
        }else if (action == PacketAction.SET){
            entity.setTank(stack);
        }
    }

    public enum PacketAction{
        SET,
        FILL,
        DRAIN;
    }
}
