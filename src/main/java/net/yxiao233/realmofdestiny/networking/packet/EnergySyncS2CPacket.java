package net.yxiao233.realmofdestiny.networking.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.yxiao233.realmofdestiny.Entities.BaseFluidTankBlockEntity;

import java.util.function.Supplier;

public class EnergySyncS2CPacket extends AbstractModSyncPacket {
    private final int energy;
    private final BlockPos blockPos;

    public EnergySyncS2CPacket(int energy, BlockPos blockPos) {
        this.energy = energy;
        this.blockPos = blockPos;
    }

    public EnergySyncS2CPacket(FriendlyByteBuf buffer) {
        this.energy = buffer.readInt();
        this.blockPos = buffer.readBlockPos();
    }


    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeInt(energy);
        buffer.writeBlockPos(blockPos);
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
           if(Minecraft.getInstance().level.getBlockEntity(blockPos) instanceof BaseFluidTankBlockEntity entity){
               entity.setChanged();
           }
        });
        return true;
    }
}
