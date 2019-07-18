package fr.nathanael2611.simpledatabasemanager.network;

import fr.nathanael2611.simpledatabasemanager.SimpleDatabaseManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

    public static SimpleNetworkWrapper network;

    public static void initPackets() {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(SimpleDatabaseManager.MOD_ID.toUpperCase());
        network.registerMessage(
                PacketSendClientPlayerData.Message.class,
                PacketSendClientPlayerData.class,
                1,
                Side.CLIENT
        );
        network.registerMessage(
                PacketSendDatabaseToClient.Message.class,
                PacketSendDatabaseToClient.class,
                2,
                Side.CLIENT
        );
    }

}
