package net.yxiao233.realmofdestiny.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.yxiao233.realmofdestiny.api.block.AbstractProcessingEntityBlockWithMenu;

import java.util.function.Supplier;

public class ProcessingEntityBlockWithMenuKeyDownSyncC2SPacket {
    private final boolean[] values;
    public ProcessingEntityBlockWithMenuKeyDownSyncC2SPacket(boolean... values) {
        this.values = values;
    }

    public ProcessingEntityBlockWithMenuKeyDownSyncC2SPacket(FriendlyByteBuf buffer) {
        int size = buffer.readInt();

        boolean[] temp = new boolean[size];

        for (int i = 0; i < size; i++) {
            temp[i] = buffer.readBoolean();
        }

        this.values = temp;
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeInt(values.length);

        for (int i = 0; i < values.length; i++) {
            buffer.writeBoolean(values[i]);
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            AbstractProcessingEntityBlockWithMenu.isShiftDown = values[0];
            AbstractProcessingEntityBlockWithMenu.isCtrlDown = values[1];
            AbstractProcessingEntityBlockWithMenu.isAltDown = values[2];
        });
        return true;
    }
}
