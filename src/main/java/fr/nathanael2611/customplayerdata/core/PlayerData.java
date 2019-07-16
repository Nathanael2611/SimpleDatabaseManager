package fr.nathanael2611.customplayerdata.core;

import fr.nathanael2611.customplayerdata.core.saveddatas.DataInteger;
import fr.nathanael2611.customplayerdata.core.saveddatas.DataString;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashMap;
import java.util.UUID;

public class PlayerData implements INBTSerializable<NBTTagCompound> {

    private final HashMap<String, String> STRINGS   = new HashMap<>();
    private final HashMap<String, Integer> INTEGERS = new HashMap<>();

    private UUID uuid;

    public PlayerData(){}

    public PlayerData(UUID uuid){
        this.uuid = uuid;
    }

    public String getString(String key){
        if(STRINGS.containsKey(key)){
            return STRINGS.get(key);
        }
        return "<undefined string>";
    }

    public void setString(String key, String value){
        STRINGS.put(key, value);
        PlayerDatas.save();
    }



    public int getInteger(String key){
        if(INTEGERS.containsKey(key)){
            return INTEGERS.get(key);
        }
        return 0;
    }

    public void setInteger(String key, int value){
        INTEGERS.put(key, value);
        PlayerDatas.save();
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();

        // Saving the strings
        NBTTagList stringList = new NBTTagList();
        for(String str : STRINGS.keySet()){
            stringList.appendTag(
                    new DataString(
                            str,
                            STRINGS.get(str)
                    ).serializeNBT()
            );
        }
        compound.setTag("strings", stringList);

        // Saving the integers
        NBTTagList integerList = new NBTTagList();
        for(String str : INTEGERS.keySet()){
            stringList.appendTag(
                    new DataInteger(
                            str,
                            INTEGERS.get(str)
                    ).serializeNBT()
            );
        }
        compound.setTag("integers", integerList);

        compound.setUniqueId("uuid", uuid);

        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {

        //Read the strings
        NBTTagList stringList = nbt.getTagList("strings", Constants.NBT.TAG_COMPOUND);
        for(NBTBase compound : stringList){
            DataString string = new DataString();
            string.deserializeNBT((NBTTagCompound) compound);
            STRINGS.put(
                    string.key, string.value
            );
        }

        //Read the integers
        NBTTagList integerList = nbt.getTagList("integers", Constants.NBT.TAG_COMPOUND);
        for(NBTBase compound : integerList){
            DataInteger integer = new DataInteger();
            integer.deserializeNBT((NBTTagCompound) compound);
            INTEGERS.put(
                    integer.key, integer.value
            );
        }

        this.uuid = nbt.getUniqueId("uuid");

    }

    public UUID getUUID() {
        return uuid;
    }
}
