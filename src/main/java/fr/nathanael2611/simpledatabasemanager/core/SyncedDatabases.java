package fr.nathanael2611.simpledatabasemanager.core;

import fr.nathanael2611.simpledatabasemanager.network.PacketHandler;
import fr.nathanael2611.simpledatabasemanager.network.PacketSendDatabaseToClient;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.ArrayList;

public class SyncedDatabases {

    public static final ArrayList<String> AUTOMATICS_SYNCED_DATABASES = new ArrayList<>();

    public static void addAutoSyncedDB(String dbName){
        if(!AUTOMATICS_SYNCED_DATABASES.contains(dbName)) AUTOMATICS_SYNCED_DATABASES.add(dbName);
    }

    public static void removeAutoSyncedDB(String dbName){
        if(AUTOMATICS_SYNCED_DATABASES.contains(dbName)) AUTOMATICS_SYNCED_DATABASES.remove(dbName);
    }

    public static void syncAll(){
        for(EntityPlayerMP playerMP : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()){
            for(String str : Databases.DATABASES.keySet()){
                if(AUTOMATICS_SYNCED_DATABASES.contains(str)){
                    PacketHandler.network.sendTo(
                            new PacketSendDatabaseToClient(Databases.getDatabase(str)),
                            playerMP
                    );
                }
            }
        }
    }

    public static void sendDatabaseToPlayerList(Database db, EntityPlayerMP[] playerMPs){
        for(EntityPlayerMP playerMP : playerMPs){
            PacketHandler.network.sendTo(
                    new PacketSendDatabaseToClient(db),
                    playerMP
            );
        }
    }

}
