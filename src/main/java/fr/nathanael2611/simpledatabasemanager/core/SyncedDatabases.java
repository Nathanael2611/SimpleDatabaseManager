package fr.nathanael2611.simpledatabasemanager.core;

import fr.nathanael2611.simpledatabasemanager.network.PacketHandler;
import fr.nathanael2611.simpledatabasemanager.network.PacketSendDatabaseToClient;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.ArrayList;

/**
 * The databases synchronization system.
 * Used for add databases to auto sync program.
 * Used for syncall auto-synced databases for all players.
 * Also used to send some databases to specifics players.
 *
 * @author Nathanael2611
 */
public class SyncedDatabases {

    /**
     * Databases present in the ArrayList will be automatically synced for all players
     */
    public static final ArrayList<String> AUTOMATICS_SYNCED_DATABASES = new ArrayList<>();

    /**
     * Just add a database to the auto-synced ArrayList
     */
    public static void addAutoSyncedDB(String dbName){
        if(!AUTOMATICS_SYNCED_DATABASES.contains(dbName)) AUTOMATICS_SYNCED_DATABASES.add(dbName);
    }

    /**
     * Just remove a database to the auto-synced ArrayList
     */
    public static void removeAutoSyncedDB(String dbName){
        if(AUTOMATICS_SYNCED_DATABASES.contains(dbName)) AUTOMATICS_SYNCED_DATABASES.remove(dbName);
    }

    /**
     * Used for sync all databases in synced-databases for all players on the server
     */
    public static void syncAll(){
        for(EntityPlayerMP playerMP : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()){
            for(String str : Databases.DATABASES.keySet()){
                if(AUTOMATICS_SYNCED_DATABASES.contains(str)){
                    PacketHandler.INSTANCE.sendTo(
                            new PacketSendDatabaseToClient(Databases.getDatabase(str)),
                            playerMP
                    );
                }
            }
        }
    }

    /**
     * Used for send a database to a specific player-list
     */
    public static void sendDatabaseToPlayerList(Database db, EntityPlayerMP[] playerMPs){
        for(EntityPlayerMP playerMP : playerMPs){
            PacketHandler.INSTANCE.sendTo(
                    new PacketSendDatabaseToClient(Databases.getDatabase(db.getId())),
                    playerMP
            );
        }
    }

    /**
     * Used for send a database to a specific player
     */
    public static void sendDatabaseToPlayer(Database db, EntityPlayerMP playerMP){
        sendDatabaseToPlayerList(db, new EntityPlayerMP[]{ playerMP });
    }
}