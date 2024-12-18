package net.yxiao233.realmofdestiny.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.yxiao233.realmofdestiny.RealmOfDestiny;
import net.yxiao233.realmofdestiny.networking.packet.ContainerDataSyncS2CPacket;
import net.yxiao233.realmofdestiny.networking.packet.EnergySyncS2CPacket;
import net.yxiao233.realmofdestiny.networking.packet.FluidSyncS2CPacket;

public class ModNetWorking {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;
    private static int id(){
        return packetId ++;
    }

    public static void register(){
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(RealmOfDestiny.MODID,"networks"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(EnergySyncS2CPacket.class,id(),NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EnergySyncS2CPacket::new)
                .encoder(EnergySyncS2CPacket::toBytes)
                .consumerMainThread(EnergySyncS2CPacket::handle)
                .add();

        net.messageBuilder(FluidSyncS2CPacket.class,id(),NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FluidSyncS2CPacket::new)
                .encoder(FluidSyncS2CPacket::toBytes)
                .consumerMainThread(FluidSyncS2CPacket::handle)
                .add();

        net.messageBuilder(ContainerDataSyncS2CPacket.class,id(),NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ContainerDataSyncS2CPacket::new)
                .encoder(ContainerDataSyncS2CPacket::toBytes)
                .consumerMainThread(ContainerDataSyncS2CPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player),message);
    }

    public static <MSG> void sendToClient(MSG message){
        INSTANCE.send(PacketDistributor.ALL.noArg(),message);
    }
}
