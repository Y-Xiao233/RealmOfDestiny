package net.yxiao233.realmofdestiny.networking.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.yxiao233.realmofdestiny.modAbstracts.entity.AbstractVoidMachineBlockEntity;
import net.yxiao233.realmofdestiny.modAbstracts.packet.AbstractModSyncPacket;

import java.util.function.Supplier;

public class ContainerDataSyncS2CPacket extends AbstractModSyncPacket {
    private final int progress;
    private final int maxProgress;
    private final BlockPos blockPos;
    public ContainerDataSyncS2CPacket(int progress, int maxProgress, BlockPos blockPos){
        this.progress = progress;
        this.maxProgress = maxProgress;
        this.blockPos = blockPos;
    }

    public ContainerDataSyncS2CPacket(FriendlyByteBuf buffer) {
        this.progress = buffer.readInt();
        this.maxProgress = buffer.readInt();
        this.blockPos = buffer.readBlockPos();
    }
    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeInt(progress);
        buffer.writeInt(maxProgress);
        buffer.writeBlockPos(blockPos);
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if(Minecraft.getInstance().level.getBlockEntity(blockPos) instanceof AbstractVoidMachineBlockEntity entity){
                entity.progress = progress;
                entity.maxProgress = maxProgress;
                entity.setChanged();
            }
        });
        return true;
    }
}
