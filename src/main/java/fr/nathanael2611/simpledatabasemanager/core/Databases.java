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

    public static final HashMap<String, Database> PLAYERDATAS = new HashMap<String, Database>();
    public static final HashMap<String, Database> DATABASES = new HashMap<String, Database>();

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

    public static Database getPlayerData(EntityPlayer player){
        String playerName = player.getGameProfile().getName();
        if(!PLAYERDATAS.containsKey(playerName)){
            PLAYERDATAS.put(playerName, new Database(playerName));
        }
        return PLAYERDATAS.get(playerName);
    }

    public static Database getDatabase(String dbName){
        if(!DATABASES.containsKey(dbName)){
            DATABASES.put(dbName, new Database(dbName));
        }
        return DATABASES.get(dbName);
    }

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

    public static String[] getAllDatabasesNames(){
        return Helpers.extractAllHashMapEntryNames(DATABASES);
    }
}
