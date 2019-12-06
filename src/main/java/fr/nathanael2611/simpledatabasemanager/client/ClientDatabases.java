package fr.nathanael2611.simpledatabasemanager.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import fr.nathanael2611.simpledatabasemanager.core.DatabaseReadOnly;
import fr.nathanael2611.simpledatabasemanager.util.SDMHelpers;

import java.util.HashMap;

/**
 * This class will contain stuff to get the read-only databases
 * that the server share with the client
 *
 * @author Nathanael2611
 */
@SideOnly(Side.CLIENT)
public class ClientDatabases
{

    /* Contain the personal client's player-data */
    private static DatabaseReadOnly personalPlayerData = new DatabaseReadOnly();

    /**
     * Just get the client player-data
     */
    public static DatabaseReadOnly getPersonalPlayerData()
    {
        return personalPlayerData;
    }

    /* Update the client personal player-data */
    public static void updatePersonalPlayerData(DatabaseReadOnly database)
    {
        personalPlayerData = database;
    }

    /**
     * The databases HashMap
     */
    private static final HashMap<String, DatabaseReadOnly> CLIENT_DATABASES = new HashMap<>();

    /**
     * Used for update a client db
     */
    public static void updateClientDB(DatabaseReadOnly db)
    {
        Minecraft.getMinecraft().addScheduledTask(() -> CLIENT_DATABASES.put(db.getId(), SDMHelpers.toDatabase(db)));
    }

    /**
     * Used for get a read-only db
     */
    public static DatabaseReadOnly getDatabase(String dbName)
    {
        if(!CLIENT_DATABASES.containsKey(dbName)) CLIENT_DATABASES.put(dbName, new DatabaseReadOnly(dbName, false));
        return CLIENT_DATABASES.get(dbName);
    }

}
