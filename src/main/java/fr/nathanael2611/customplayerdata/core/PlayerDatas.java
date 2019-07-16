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

public class PlayerDatas extends WorldSavedData {
    private static PlayerDatas INSTANCE;

    public static final HashMap<UUID, PlayerData> PLAYERDATAS = new HashMap<UUID, PlayerData>();

    public static void save() {
        if (INSTANCE != null) {
            INSTANCE.markDirty();
        }
    }

    public PlayerDatas(String name) {
        super(name);
    }

    public static PlayerData getPlayerData(EntityPlayer player){
        UUID playerUUID = player.getGameProfile().getId();
        if(!PLAYERDATAS.containsKey(playerUUID)){
            PLAYERDATAS.put(playerUUID, new PlayerData(playerUUID));
        }
        return PLAYERDATAS.get(playerUUID);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        PLAYERDATAS.clear();
        NBTTagList dataList = nbt.getTagList("playerdatas", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < dataList.tagCount(); i++) {
            PlayerData data = new PlayerData();
            data.deserializeNBT(dataList.getCompoundTagAt(i));
            PLAYERDATAS.put(data.getUUID(), data);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagList dataList = new NBTTagList();
        for (PlayerData playerData : PLAYERDATAS.values()) {
            dataList.appendTag(playerData.serializeNBT());
        }
        compound.setTag("playerdatas", dataList);
        return compound;
    }

    public static void onServerStarting(FMLServerStartingEvent event) {
        if (!event.getServer().getEntityWorld().isRemote) {
            MapStorage storage = event.getServer().getEntityWorld().getMapStorage();
            PlayerDatas data = (PlayerDatas) storage.getOrLoadData(PlayerDatas.class, "customplayerdata");
            if (data == null) {
                data = new PlayerDatas("customplayerdata");
                storage.setData("customplayerdata", data);
            }
            INSTANCE = data;
        }
    }
}
