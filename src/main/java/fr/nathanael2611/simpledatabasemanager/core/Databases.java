package fr.nathanael2611.simpledatabasemanager.core;

import fr.nathanael2611.simpledatabasemanager.SimpleDatabaseManager;
import fr.nathanael2611.simpledatabasemanager.network.PacketHandler;
import fr.nathanael2611.simpledatabasemanager.network.PacketSendClientPlayerData;
import fr.nathanael2611.simpledatabasemanager.util.Helpers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.util.HashMap;
import java.util.UUID;

/**
 * The databases and playerdatas storage and managing class
 *
 * @author Nathanael2611
 */
public class Databases extends WorldSavedData {
    private static Databases instance;

    /* All the players assigned databases */
    public static final HashMap<String, Database> PLAYERDATAS = new HashMap<String, Database>();
    /* All the databases */
    public static final HashMap<String, Database> DATABASES = new HashMap<String, Database>();

    /**
     * Save the databases in the world
     */
    public static void save() {
        SyncedDatabases.syncAll();
        for(EntityPlayerMP entityPlayerMP : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()){
            PacketHandler.INSTANCE.sendTo(
                    new PacketSendClientPlayerData(entityPlayerMP), entityPlayerMP
            );
        }
        if (instance != null) {
            instance.markDirty();
        }
    }

    public Databases(String name) {
        super(name);
    }

    /**
     * Get (and create if not exist) a player database
     */
    public static Database getPlayerData(EntityPlayer player){
        String playerName = player.getGameProfile().getName();
        if(!PLAYERDATAS.containsKey(playerName)){
            PLAYERDATAS.put(playerName, new Database(playerName));
        }
        return PLAYERDATAS.get(playerName);
    }

    /**
     * Get (and create if not exist) a database
     */
    public static Database getDatabase(String dbName){
        if(!DATABASES.containsKey(dbName)){
            DATABASES.put(dbName, new Database(dbName));
        }
        return DATABASES.get(dbName);
    }

    /**
     * Just remove a database from the HashMap
     */
    public static void removeDatabase(String dbName){
        DATABASES.remove(dbName);
        save();
    }

    /**
     * Check if a database with specific name is existing
     */
    public static boolean containsDatabase(String dbName){
        return DATABASES.containsKey(dbName);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        PLAYERDATAS.clear();
        NBTTagList playerDataList = nbt.getTagList("playerdatas", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < playerDataList.tagCount(); i++) {
            Database data = new Database();
            data.deserializeNBT(playerDataList.getCompoundTagAt(i));
            PLAYERDATAS.put(data.getId(), data);
        }

        DATABASES.clear();
        NBTTagList databaseList = nbt.getTagList("databases", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < databaseList.tagCount(); i++) {
            Database data = new Database();
            data.deserializeNBT(databaseList.getCompoundTagAt(i));
            DATABASES.put(data.getId(), data);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
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
            Databases data = (Databases) storage.getOrLoadData(Databases.class, SimpleDatabaseManager.MOD_ID);
            if (data == null) {
                data = new Databases(SimpleDatabaseManager.MOD_ID);
                storage.setData(SimpleDatabaseManager.MOD_ID, data);
            }
            instance = data;
        }
    }

    /**
     * Return as an array, all databases names
     */
    public static String[] getAllDatabasesNames(){
        return Helpers.extractAllHashMapEntryNames(DATABASES);
    }
}
