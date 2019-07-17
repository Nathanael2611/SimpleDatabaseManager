package fr.nathanael2611.customplayerdata.core;

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
        DATABASES.clear();

        NBTTagList dataList = nbt.getTagList("playerdatas", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < dataList.tagCount(); i++) {
            Database data = new Database();
            data.deserializeNBT(dataList.getCompoundTagAt(i));
            PLAYERDATAS.put(UUID.fromString(data.getDbName()), data);
        }

        NBTTagList databaseList = nbt.getTagList("databases", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < databaseList.tagCount(); i++) {
            Database data = new Database();
            data.deserializeNBT(databaseList.getCompoundTagAt(i));
            DATABASES.put(data.getDbName(), data);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagList dataList = new NBTTagList();
        for (Database playerData : PLAYERDATAS.values()) {
            dataList.appendTag(playerData.serializeNBT());
        }
        compound.setTag("playerdatas", dataList);

        NBTTagList databasesList = new NBTTagList();
        for (Database playerData : DATABASES.values()) {
            databasesList.appendTag(playerData.serializeNBT());
        }
        compound.setTag("databases", dataList);

        return compound;
    }

    public static void onServerStarting(FMLServerStartingEvent event) {
        if (!event.getServer().getEntityWorld().isRemote) {
            MapStorage storage = event.getServer().getEntityWorld().getMapStorage();
            Databases data = (Databases) storage.getOrLoadData(Databases.class, "customplayerdata");
            if (data == null) {
                data = new Databases("customplayerdata");
                storage.setData("customplayerdata", data);
            }
            INSTANCE = data;
        }
    }
}
