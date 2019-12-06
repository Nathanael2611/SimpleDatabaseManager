package fr.nathanael2611.simpledatabasemanager.core;

import fr.nathanael2611.simpledatabasemanager.network.PacketSendClientPlayerData;
import fr.nathanael2611.simpledatabasemanager.util.SDMHelpers;
import net.minecraft.entity.player.EntityPlayerMP;
import fr.nathanael2611.simpledatabasemanager.network.PacketHandler;
import fr.nathanael2611.simpledatabasemanager.network.PacketSendDatabaseToClient;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manage all the databases that will be sync with all clients
 */
public class SyncedDatabases
{

    /* Contains all the synced databases names */
    private static final List<String> SYNCED_DATABASES = new ArrayList<>();

    /**
     * Add a Database to auto-syncing list
     * @param dbName the name of the Database
     */
    public static void add(String dbName)
    {
        if(!SYNCED_DATABASES.contains("")) SYNCED_DATABASES.add(dbName);
    }

    /**
     * Remove a Database from auto-syncing list
     * @param dbName the name of the Database
     */
    public static void remove(String dbName)
    {
        SYNCED_DATABASES.remove(dbName);
    }

    /**
     * Check if a database is contained in auto-syncing list
     * @param db the database name
     */
    public static boolean isAutoSynced(DatabaseReadOnly db)
    {
        return !db.isPlayerData() && SYNCED_DATABASES.contains(db.getId());
    }

    /**
     * Used for send a database to a specific player-list
     */
    public static void sendDatabaseToPlayerList(Database db, EntityPlayerMP[] playerMPs)
    {
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
    public static void sendDatabaseToPlayer(Database db, EntityPlayerMP playerMP)
    {
        sendDatabaseToPlayerList(db, new EntityPlayerMP[]{ playerMP });
    }

    /**
     * Sync all auto-synced databases and player-data with all players
     */
    public static void syncAll()
    {
        /* Send all auto-synced databases to all players */
        Databases.DATABASES.forEach((key, db) -> {
            if(isAutoSynced(db)) SDMHelpers.sendToAll(new PacketSendDatabaseToClient(db));
        });
        /* Send all player-data to his associated player */
        FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers().forEach(entityPlayerMP -> PacketHandler.INSTANCE.sendTo(new PacketSendClientPlayerData(entityPlayerMP), entityPlayerMP));
    }

}
