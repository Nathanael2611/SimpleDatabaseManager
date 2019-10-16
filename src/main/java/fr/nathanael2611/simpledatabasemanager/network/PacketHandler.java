package fr.nathanael2611.simpledatabasemanager.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import fr.nathanael2611.simpledatabasemanager.SimpleDatabaseManager;

/**
 * Used for initialize the networkwrapper and to register packets.
 *
 * @author Protoxy22
 */
public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE;
    private static int nextId = 0;

    /**
     * Initialize the packets
     */
    public static void init() {
        registerMessage(PacketSendClientPlayerData.Handler.class, PacketSendClientPlayerData.class, Side.CLIENT);
        registerMessage(PacketSendDatabaseToClient.Handler.class, PacketSendDatabaseToClient.class, Side.CLIENT);
        registerMessage(PacketSendData.Handler.class, PacketSendData.class, Side.CLIENT);
    }

    private static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side) {
        INSTANCE.registerMessage(messageHandler, requestMessageType, nextId++, side);

    }

    static {
        INSTANCE = SimpleDatabaseManager.getInstance().getPacketChannel();
    }
}