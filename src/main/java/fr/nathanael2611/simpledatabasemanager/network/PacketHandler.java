package fr.nathanael2611.simpledatabasemanager.network;

import fr.nathanael2611.simpledatabasemanager.SimpleDatabaseManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

    private static SimpleNetworkWrapper network;
    private static int nextId = 1;

    public static void initPackets(){
        network = NetworkRegistry.INSTANCE.newSimpleChannel(SimpleDatabaseManager.MOD_ID.toUpperCase());

        registerMessage(
                PacketSendClientPlayerData.Handler.class,
                PacketSendClientPlayerData.class,
                Side.CLIENT
        );
    }

    private static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(
            Class<? extends IMessageHandler<REQ, REPLY>> messageHandler,
            Class<REQ> requestMessageType,
            Side side
    ) {
        network.registerMessage(messageHandler, requestMessageType, nextId++, side);

    }

    public static SimpleNetworkWrapper getNetwork() {
        return network;
    }
}
