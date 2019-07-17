package fr.nathanael2611.simpledatabasemanager.core;

import fr.nathanael2611.simpledatabasemanager.util.Helpers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.util.HashMap;
import java.util.UUID;

public class Databases extends WorldSavedData {
    private static Databases INSTANCE;

    public static final HashMap<UUID, Database> PLAYERDATAS = new HashMap<UUID, Database>();
    public static final HashMap<String, Database> DATABASES = new HashMap<String, Database>();

    public static void save() {
        if (INSTANCE != null) {
            INSTANCE.markDirty();
        }
    }

    public Databases(String name) {
        super(name);
    }

    public static Database getPlayerData(EntityPlayer player){
        UUID playerUUID = player.getGameProfile().getId();
        if(!PLAYERDATAS.containsKey(playerUUID)){
            PLAYERDATAS.put(playerUUID, new Database(playerUUID.toString()));
        }
        return PLAYERDATAS.get(playerUUID);
    }

    public static Database getDatabase(String dbName){
        if(!DATABASES.containsKey(dbName)){
            DATABASES.put(dbName, new Database(dbName));
        }
        return DATABASES.get(dbName);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        PLAYERDATAS.clear();
        NBTTagList playerDataList = nbt.getTagList("playerdatas", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < playerDataList.tagCount(); i++) {
            Database data = new Database();
            data.deserializeNBT(playerDataList.getCompoundTagAt(i));
            PLAYERDATAS.put(UUID.fromString(data.getId()), data);
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
            Databases data = (Databases) storage.getOrLoadData(Databases.class, "simpledatabasemanager");
            if (data == null) {
                data = new Databases("simpledatabasemanager");
                storage.setData("simpledatabasemanager", data);
            }
            INSTANCE = data;
        }
    }

    public static String[] getAllDatabasesNames(){
        return Helpers.extractAllHashMapEntryNames(DATABASES);
    }
}
