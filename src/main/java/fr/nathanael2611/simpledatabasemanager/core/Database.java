package fr.nathanael2611.simpledatabasemanager.core;

import fr.nathanael2611.simpledatabasemanager.network.PacketSendData;
import fr.nathanael2611.simpledatabasemanager.util.SDMHelpers;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Database extends DatabaseReadOnly
{
    /**
     * Store the data-map that is contained in database after (and by the way, before) a change.
     */
    private ConcurrentHashMap<String, StoredData> beforeMap = null;

    /**
     * Constructor
     */
    public Database()
    {
    }

    /**
     * Constructor
     * @param id the database-id/name
     * @param isPlayerData true if the database that we are creating is a player-data
     */
    public Database(String id, boolean isPlayerData)
    {
        super(id, isPlayerData);
    }

    /* The database helper instance */
    private DatabaseHelper helper = new DatabaseHelper(this);

    /**
     * Return the DatabaseHelper
     */
    public DatabaseHelper getHelper(){
        return helper;
    }

    /**
     * Set an entry in the database
     */
    public void set(String key, StoredData data, boolean saveAndSync)
    {
        this.DATA.put(key, data);
        if(saveAndSync)
        {
            //Databases.save();
            this.sync();
        }
    }

    /**
     * Set an entry in the database
     */
    public void set(String key, StoredData data)
    {
        set(key, data, true);
    }

    /**
     * Set a string-entry in the database
     */
    public void setString(String key, String value)
    {
        set(key, new StoredData(value));
    }

    /**
     * Set a integer-entry in the database
     */
    public void setInteger(String key, int value)
    {
        set(key, new StoredData(value));
    }

    /**
     * Set a double-entry in the database
     */
    public void setDouble(String key, double value)
    {
        set(key, new StoredData(value));
    }

    /**
     * Set a float-entry in the database
     */
    public void setFloat(String key, float value)
    {
        this.set(key, new StoredData(value));
    }

    /**
     * Set a hashmap and arraylist in the database
     */
    public void setHashMapOrArrayList(String key, Object value)
    {
        this.set(key, new StoredData(value));
    }

    /**
     * Remove an entry from the database
     */
    public void remove(String key, boolean saveAndSync)
    {
        this.DATA.remove(key);
        if(saveAndSync)
        {
            //Databases.save();
            this.sync();
        }
    }

    /**
     * Remove an entry from the database
     */
    public void remove(String key)
    {
        this.remove(key, true);
    }

    /**
     * Sync the database to concerned players
     */
    private void sync()
    {
        if(isPlayerData || SyncedDatabases.isAutoSynced(this))
        {
            if(beforeMap == null) beforeMap = new ConcurrentHashMap<>();
            checkAndSend();
            beforeMap = new ConcurrentHashMap<>(DATA);
        }
    }
    /**
     * Convert database to read-only one
     */
    public DatabaseReadOnly toReadOnly()
    {
        DatabaseReadOnly readOnly = new DatabaseReadOnly();
        readOnly.deserializeNBT(this.serializeNBT());
        return readOnly;
    }

    /**
     * Just check the database-content before the modification and send updates to concerned players
     */
    private void checkAndSend()
    {
        final EntityPlayerMP[] playerMP = new EntityPlayerMP[1];
        if(isPlayerData) FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers().forEach(p -> { if(p.getName().equalsIgnoreCase(getId())) playerMP[0] = p; });
        new HashMap<>(beforeMap).forEach((key, storedData) -> {
            if(!DATA.containsKey(key))
            {
                if(isPlayerData && playerMP[0] != null) SDMHelpers.sendTo(new PacketSendData(PacketSendData.PLAYER_DATA, "remove", key, new SavedData(key, storedData)), playerMP[0]);
                else SDMHelpers.sendToAll(new PacketSendData(getId(), "remove", key, new SavedData(key, storedData)));
            }
            else if (get(key) != null)
            {
                if (get(key) != storedData)
                {
                    if (isPlayerData && playerMP[0] != null) SDMHelpers.sendTo(new PacketSendData(PacketSendData.PLAYER_DATA, "set", key, new SavedData(key, get(key))), playerMP[0]);
                    else SDMHelpers.sendToAll(new PacketSendData(getId(), "set", key, new SavedData(key, get(key))));
                }
            }
        });
        DATA.forEach((key, storedData) ->
        {
            if(!beforeMap.containsKey(key))
            {
                if (isPlayerData && playerMP[0] != null) SDMHelpers.sendTo(new PacketSendData(PacketSendData.PLAYER_DATA, "set", key, new SavedData(key, storedData)), playerMP[0]);
                else SDMHelpers.sendToAll(new PacketSendData(getId(), "set", key, new SavedData(key, storedData)));
            }
        });
    }
}
