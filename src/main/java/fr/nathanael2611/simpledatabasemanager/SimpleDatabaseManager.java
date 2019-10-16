package fr.nathanael2611.simpledatabasemanager;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import fr.nathanael2611.simpledatabasemanager.command.CommandCustomPlayerdata;
import fr.nathanael2611.simpledatabasemanager.command.CommandDatabase;
import fr.nathanael2611.simpledatabasemanager.core.Databases;
import fr.nathanael2611.simpledatabasemanager.event.SyncEventHandler;
import fr.nathanael2611.simpledatabasemanager.network.PacketHandler;

@Mod(modid = SimpleDatabaseManager.MOD_ID)
public class SimpleDatabaseManager
{

    public static final String MOD_ID = "sdm";
    public static final String MOD_NAME = "SimpleDatabaseManager";

    @Mod.Instance
    private static SimpleDatabaseManager instance;

    private SimpleNetworkWrapper packetChannel;

    public SimpleNetworkWrapper getPacketChannel() {
        return this.packetChannel;
    }

    @Mod.EventHandler
    public void preInitialization(FMLPreInitializationEvent e){
        this.packetChannel = NetworkRegistry.INSTANCE.newSimpleChannel("sdm");
        PacketHandler.init();
        MinecraftForge.EVENT_BUS.register(new SyncEventHandler());
    }

    public static SimpleDatabaseManager getInstance() {
        return instance;
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent e){
        Databases.onServerStarting(e);
        e.registerServerCommand(new CommandCustomPlayerdata());
        e.registerServerCommand(new CommandDatabase());
    }

}
