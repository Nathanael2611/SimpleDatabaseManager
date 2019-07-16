package fr.nathanael2611.customplayerdata.core;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashMap;
import java.util.UUID;

public class PlayerData implements INBTSerializable<NBTTagCompound> {

    private final HashMap<String, String>  STRINGS  = new HashMap<>();
    private final HashMap<String, Integer> INTEGERS = new HashMap<>();
    private final HashMap<String, Double>  DOUBLES  = new HashMap<>();
    private final HashMap<String, Float>   FLOATS   = new HashMap<>();
    private final HashMap<String, Boolean> BOOLEANS = new HashMap<>();

    private UUID uuid;

    public PlayerData(){}

    public PlayerData(UUID uuid){
        this.uuid = uuid;
    }

    /**
     *  String stuff
     */
    public String getString(String key){
        if(STRINGS.containsKey(key)){
            for(String str : STRINGS.values()){
                System.out.println(str);
            }
            return STRINGS.get(key);
        }
        return "<undefined string>";
    }
    public void setString(String key, String value){
        STRINGS.put(key, value);
        PlayerDatas.save();
    }


    /**
     *  Integer stuff
     */
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

    /**
     *  Double stuff
     */
    public double getDouble(String key){
        if(DOUBLES.containsKey(key)){
            return DOUBLES.get(key);
        }
        return 0;
    }
    public void setDouble(String key, double value){
        DOUBLES.put(key, value);
        PlayerDatas.save();
    }

    /**
     *  Float stuff
     */
    public float getFloat(String key){
        if(FLOATS.containsKey(key)){
            return FLOATS.get(key);
        }
        return 0f;
    }
    public void setFloat(String key, float value){
        FLOATS.put(key, value);
        PlayerDatas.save();
    }

    /**
     *  Boolean stuff
     */
    public boolean getBoolean(String key){
        if(BOOLEANS.containsKey(key)){
            return BOOLEANS.get(key);
        }
        return false;
    }
    public void setBoolean(String key, boolean value){
        BOOLEANS.put(key, value);
        PlayerDatas.save();
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();

        /**
         * Saving the strings
         */
        NBTTagList stringList = new NBTTagList();
        for(String str : STRINGS.keySet()){
            stringList.appendTag(
                    new SavedData<String>(
                            str,
                            STRINGS.get(str)
                    ).serializeNBT()
            );
        }
        compound.setTag("strings", stringList);

        /**
         * Saving the integers
         */
        NBTTagList integerList = new NBTTagList();
        for(String str : INTEGERS.keySet()){
            integerList.appendTag(
                    new SavedData<Integer>(
                            str,
                            INTEGERS.get(str)
                    ).serializeNBT()
            );
        }
        compound.setTag("integers", integerList);

        /**
         * Saving the doubles
         */
        NBTTagList doubleList = new NBTTagList();
        for(String str : DOUBLES.keySet()){
            doubleList.appendTag(
                    new SavedData<Double>(
                            str,
                            DOUBLES.get(str)
                    ).serializeNBT()
            );
        }
        compound.setTag("doubles", doubleList);

        /**
         * Saving the floats
         */
        NBTTagList floatList = new NBTTagList();
        for(String str : FLOATS.keySet()){
            floatList.appendTag(
                    new SavedData<Float>(
                            str,
                            FLOATS.get(str)
                    ).serializeNBT()
            );
        }
        compound.setTag("floats", floatList);

        /**
         * Saving the player-uuid
         */
        compound.setUniqueId("uuid", uuid);

        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {

        /**
         * Read the strings
         */
        NBTTagList stringList = nbt.getTagList("strings", Constants.NBT.TAG_COMPOUND);
        for(NBTBase compound : stringList){
            SavedData<String> string = new SavedData<>();
            string.deserializeNBT((NBTTagCompound) compound);
            STRINGS.put(
                    string.key, string.value
            );
        }

        /**
         * Read the integers
         */
        NBTTagList integerList = nbt.getTagList("integers", Constants.NBT.TAG_COMPOUND);
        for(NBTBase compound : integerList){
            SavedData<Integer> integer = new SavedData<>();
            integer.deserializeNBT((NBTTagCompound) compound);
            INTEGERS.put(
                    integer.key, integer.value
            );
        }

        /**
         * Read the doubles
         */
        NBTTagList doubleList = nbt.getTagList("doubles", Constants.NBT.TAG_COMPOUND);
        for(NBTBase compound : doubleList){
            SavedData<Double> doubleValue = new SavedData<>();
            doubleValue.deserializeNBT((NBTTagCompound) compound);
            DOUBLES.put(
                    doubleValue.key, doubleValue.value
            );
        }

        /**
         * Read the floats
         */
        NBTTagList floatList = nbt.getTagList("floats", Constants.NBT.TAG_COMPOUND);
        for(NBTBase compound : floatList){
            SavedData<Float> floatValue = new SavedData<>();
            floatValue.deserializeNBT((NBTTagCompound) compound);
            FLOATS.put(
                    floatValue.key, floatValue.value
            );
        }

        /**
         * Read the player-uuid
         */
        this.uuid = nbt.getUniqueId("uuid");

    }

    public UUID getUUID() {
        return uuid;
    }
}
