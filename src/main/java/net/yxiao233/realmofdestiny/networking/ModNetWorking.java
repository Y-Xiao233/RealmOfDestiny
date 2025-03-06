package net.yxiao233.realmofdestiny.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.yxiao233.realmofdestiny.RealmOfDestiny;

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

        net.messageBuilder(ProcessingEntityBlockWithMenuKeyDownSyncC2SPacket.class,id(),NetworkDirection.PLAY_TO_SERVER)
                .decoder(ProcessingEntityBlockWithMenuKeyDownSyncC2SPacket::new)
                .encoder(ProcessingEntityBlockWithMenuKeyDownSyncC2SPacket::toBytes)
                .consumerMainThread(ProcessingEntityBlockWithMenuKeyDownSyncC2SPacket::handle)
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
