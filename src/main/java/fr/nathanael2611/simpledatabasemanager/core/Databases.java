package fr.nathanael2611.simpledatabasemanager.core;

import fr.nathanael2611.simpledatabasemanager.util.SDMHelpers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import fr.nathanael2611.simpledatabasemanager.SimpleDatabaseManager;

import java.util.ArrayList;
import java.util.HashMap;

public class Databases extends WorldSavedData
{

    private static Databases instance;

    /**
     * Save the databases in the world
     */
    public static void save() {
        if (instance != null) {
            // -> SyncedDatabases.syncAll();
            instance.markDirty();
        }
    }

    public Databases(String name) {
        super(name);
    }

    /* All the players assigned databases */
    public static final HashMap<String, Database> PLAYERDATAS = new HashMap<String, Database>();
    /* All the databases */
    public static final HashMap<String, Database> DATABASES = new HashMap<String, Database>();


    /**
     * Get (and create if not exist) a player database
     */
    public static Database getPlayerData(String player){
        if(!PLAYERDATAS.containsKey(player)){
            PLAYERDATAS.put(player, new Database(player, true));
        }
        return PLAYERDATAS.get(player);
    }

    public static Database getPlayerData(EntityPlayer player)
    {
        return getPlayerData(player.getGameProfile().getName());
    }

    /**
     * Get (and create if not exist) a database
     */
    public static Database getDatabase(String dbName){
        if(!DATABASES.containsKey(dbName)){
            DATABASES.put(dbName, new Database(dbName, false));
        }
        return DATABASES.get(dbName);
    }

    public static boolean hasCustomPlayerdata(String playerName)
    {
        return PLAYERDATAS.containsKey(playerName);
    }

    public static boolean hasCustomPlayerdata(EntityPlayer player)
    {
        return hasCustomPlayerdata(player.getGameProfile().getName());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        PLAYERDATAS.clear();
        NBTTagList playerDataList = nbt.getTagList("playerdatas", Constants.NBT.TAG_COMPOUND);

        for (int i = 0; i < playerDataList.tagCount(); i++) {
            Database data = new Database();
            NBTTagCompound compound = playerDataList.getCompoundTagAt(i);
            if(isV1Format(compound)) data.deserializeSDM1DatabaseFormat(compound, true);
            else data.deserializeNBT(compound);
            PLAYERDATAS.put(data.getId(), data);
        }

        DATABASES.clear();
        NBTTagList databaseList = nbt.getTagList("databases", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < databaseList.tagCount(); i++) {
            Database data = new Database();
            NBTTagCompound compound = databaseList.getCompoundTagAt(i);
            if(isV1Format(compound)) data.deserializeSDM1DatabaseFormat(compound, false);
            else data.deserializeNBT(compound);
            DATABASES.put(data.getId(), data);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        NBTTagList playerdataList = new NBTTagList();
        for (Database playerData : PLAYERDATAS.values()) {
            playerdataList.appendTag(playerData.serializeNBT());
        }
        compound.setTag("playerdatas", playerdataList);

        NBTTagList databaseList = new NBTTagList();
        for (Database database : DATABASES.values()) {
            databaseList.appendTag(database.serializeNBT());
        }
        compound.setTag("databases", databaseList);
        return compound;
    }

    public static void onServerStarting(FMLServerStartingEvent event) {
        PLAYERDATAS.clear();
        DATABASES.clear();
        if (!event.getServer().getEntityWorld().isRemote) {
            MapStorage storage = event.getServer().getEntityWorld().getMapStorage();
            {
                Databases data = (Databases) storage.getOrLoadData(Databases.class, SimpleDatabaseManager.MOD_ID);
                if (data == null)
                {
                    data = new Databases(SimpleDatabaseManager.MOD_ID);
                    storage.setData(SimpleDatabaseManager.MOD_ID, data);
                }
                instance = data;
            }
        }
    }

    /**
     * Return as an array, all databases names
     */
    public static String[] getAllDatabasesNames()
    {
        return SDMHelpers.extractAllHashMapEntryNames(DATABASES);
    }

    public static String[] getAllPlayerThatHavePlayerdatas()
    {
        return SDMHelpers.extractAllHashMapEntryNames(PLAYERDATAS);
    }

    /**
     * Check if a database with specific name is existing
     */
    public static boolean containsDatabase(String dbName){
        return DATABASES.containsKey(dbName);
    }

    public static boolean isV1Format(NBTTagCompound compound)
    {
        return compound.hasKey("strings", new NBTTagList().getId());
    }

}
