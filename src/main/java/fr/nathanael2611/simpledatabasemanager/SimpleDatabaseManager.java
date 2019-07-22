package fr.nathanael2611.simpledatabasemanager;

import fr.nathanael2611.simpledatabasemanager.command.CommandCustomPlayerdData;
import fr.nathanael2611.simpledatabasemanager.command.CommandDatabase;
import fr.nathanael2611.simpledatabasemanager.core.Databases;
import fr.nathanael2611.simpledatabasemanager.network.PacketHandler;
import fr.nathanael2611.simpledatabasemanager.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

/**
 * The mod main-class
 *
 * @author Nathanael2611
 */
@Mod(modid = "customplayerdata")
public class SimpleDatabaseManager {

    public static final String MOD_ID = "sdm";
    public static final String MOD_NAME = "SimpleDatabaseManager";

    @SidedProxy(
            serverSide = "fr.nathanael2611.simpledatabasemanager.proxy.ServerProxy",
            clientSide = "fr.nathanael2611.simpledatabasemanager.proxy.ClientProxy"
    )
    private static CommonProxy proxy;

    @Mod.Instance
    private static SimpleDatabaseManager instance;

    private SimpleNetworkWrapper packetChannel;

    public SimpleNetworkWrapper getPacketChannel() {
        return this.packetChannel;
    }

    @Mod.EventHandler
    public void preInitialization(FMLPreInitializationEvent e){
        this.packetChannel = NetworkRegistry.INSTANCE.newSimpleChannel("sdm");
        getProxy().preInitialization(e.getSuggestedConfigurationFile());
        PacketHandler.init();
    }

    @Mod.EventHandler
    public void initialization(FMLInitializationEvent e){
        getProxy().initialization();
    }

    public static CommonProxy getProxy() {
        return proxy;
    }

    public static SimpleDatabaseManager getInstance() {
        return instance;
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent e){
        Databases.onServerStarting(e);
        e.registerServerCommand(new CommandCustomPlayerdData());
        e.registerServerCommand(new CommandDatabase());
    }
}
