package net.yxiao233.realmofdestiny.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class AbstractModSyncPacket {
    public AbstractModSyncPacket() {

    }

    public abstract void toBytes(FriendlyByteBuf buffer);
    public abstract boolean handle(Supplier<NetworkEvent.Context> supplier);
}
