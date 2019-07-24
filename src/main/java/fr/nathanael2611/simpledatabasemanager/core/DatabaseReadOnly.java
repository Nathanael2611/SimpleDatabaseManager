package fr.nathanael2611.simpledatabasemanager.core;

import fr.nathanael2611.simpledatabasemanager.util.Helpers;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashMap;

/**
 * Simply a read-only database.
 *
 * @author Nathanael2611
 */
public class DatabaseReadOnly implements INBTSerializable<NBTTagCompound> {

    protected final HashMap<String, String>  STRINGS         = new HashMap<>();
    protected final HashMap<String, Integer> INTEGERS        = new HashMap<>();
    protected final HashMap<String, Double>  DOUBLES         = new HashMap<>();
    protected final HashMap<String, Float>   FLOATS          = new HashMap<>();
    protected final HashMap<String, Boolean> BOOLEANS        = new HashMap<>();
    protected final HashMap<String, NBTTagCompound> NBT_TAGS = new HashMap<>();

    protected String id;

    public DatabaseReadOnly(){}

    /**
     * Get a string from the database
     */
    public String getString(String key){
        return STRINGS.get(key);
    }

    /**
     * Return true if database contain a specific string key
     */
    public boolean containsString(String key){
        return STRINGS.containsKey(key);
    }

    /**
     * Get an integer from the database
     */
    public int getInteger(String key){
        if(INTEGERS.containsKey(key)){
            return INTEGERS.get(key);
        }
        return 0;
    }

    /**
     * Return true if database contain a specific integer key
     */
    public boolean containsInteger(String key){
        return INTEGERS.containsKey(key);
    }

    /**
     * Get a double from the database
     */
    public double getDouble(String key){
        if(DOUBLES.containsKey(key)){
            return DOUBLES.get(key);
        }
        return 0;
    }

    /**
     * Return true if database contain a specific double key
     */
    public boolean containsDouble(String key){
        return DOUBLES.containsKey(key);
    }

    /**
     * Get a float from the database
     */
    public float getFloat(String key){
        if(FLOATS.containsKey(key)){
            return FLOATS.get(key);
        }
        return 0f;
    }

    /**
     * Return true if database contain a specific float key
     */
    public boolean containsFloat(String key){
        return FLOATS.containsKey(key);
    }

    /**
     * Get a boolean from the database
     */
    public boolean getBoolean(String key){
        if(BOOLEANS.containsKey(key)){
            return BOOLEANS.get(key);
        }
        return false;
    }

    /**
     * Return true if database contain a specific integer key
     */
    public boolean containsBoolean(String key){
        return BOOLEANS.containsKey(key);
    }

    /**
     * Get an nbt-tag from the database
     */
    public NBTTagCompound getTag(String key){
        if(NBT_TAGS.containsKey(key)){
            return NBT_TAGS.get(key);
        }
        return null;
    }

    /**
     * Return true if database contain a specific nbt-tag key
     */
    public boolean containsTag(String key){
        return NBT_TAGS.containsKey(key);
    }

    /**
     * Serialize the database to a NBTagCompound
     */
    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        /*
         * Saving the strings
         */
        NBTTagList stringList = new NBTTagList();
        for(String str : STRINGS.keySet()){
            stringList.appendTag(
                    new SavedData(
                            str,
                            STRINGS.get(str)
                    ).serializeNBT()
            );
        }
        compound.setTag("strings", stringList);

        /*
         * Saving the integers
         */
        NBTTagList integerList = new NBTTagList();
        for(String str : INTEGERS.keySet()){
            integerList.appendTag(new SavedData(str, INTEGERS.get(str)).serializeNBT());
        }
        compound.setTag("integers", integerList);

        /*
         * Saving the doubles
         */
        NBTTagList doubleList = new NBTTagList();
        for(String str : DOUBLES.keySet()){
            doubleList.appendTag(
                    new SavedData(
                            str,
                            DOUBLES.get(str)
                    ).serializeNBT()
            );
        }
        compound.setTag("doubles", doubleList);

        /*
         * Saving the floats
         */
        NBTTagList floatList = new NBTTagList();
        for(String str : FLOATS.keySet()){
            floatList.appendTag(
                    new SavedData(
                            str,
                            FLOATS.get(str)
                    ).serializeNBT()
            );
        }
        compound.setTag("floats", floatList);

        /*
         * Saving the booleans
         */
        NBTTagList booleanList = new NBTTagList();
        for(String str : NBT_TAGS.keySet()){
            booleanList.appendTag(
                    new SavedData(
                            str,
                            NBT_TAGS.get(str)
                    ).serializeNBT()
            );
        }
        compound.setTag("booleans", booleanList);

        /*
         * Saving the booleans
         */
        NBTTagList tagList = new NBTTagList();
        for(String str : NBT_TAGS.keySet()){
            tagList.appendTag(
                    new SavedData(
                            str,
                            NBT_TAGS.get(str)
                    ).serializeNBT()
            );
        }
        compound.setTag("tags", tagList);

        /*
         * Saving the player-id
         */
        compound.setString("id", id);

        return compound;
    }

    /**
     * Deserialize a NBTagCompound to the database
     */
    @Override
    public void deserializeNBT(NBTTagCompound nbt) {

        /*
         * Read the strings
         */
        NBTTagList stringList = nbt.getTagList("strings", Constants.NBT.TAG_COMPOUND);
        for(NBTBase compound : stringList){
            SavedData string = new SavedData(String.class);
            string.deserializeNBT((NBTTagCompound) compound);
            STRINGS.put(string.key, (String)string.value);
        }

        /*
         * Read the integers
         */
        NBTTagList integerList = nbt.getTagList("integers", Constants.NBT.TAG_COMPOUND);
        for(NBTBase compound : integerList){
            SavedData integer = new SavedData(Integer.class);
            integer.deserializeNBT((NBTTagCompound) compound);
            INTEGERS.put(
                    integer.key, (Integer)integer.value
            );
        }

        /*
         * Read the doubles
         */
        NBTTagList doubleList = nbt.getTagList("doubles", Constants.NBT.TAG_COMPOUND);
        for(NBTBase compound : doubleList){
            SavedData doubleValue = new SavedData(Double.class);
            doubleValue.deserializeNBT((NBTTagCompound) compound);
            DOUBLES.put(
                    doubleValue.key, (Double)doubleValue.value
            );
        }

        /*
         * Read the floats
         */
        NBTTagList floatList = nbt.getTagList("floats", Constants.NBT.TAG_COMPOUND);
        for(NBTBase compound : floatList){
            SavedData floatValue = new SavedData(Float.class);
            floatValue.deserializeNBT((NBTTagCompound) compound);
            FLOATS.put(
                    floatValue.key, (Float)floatValue.value
            );
        }

        /*
         * Read the booleans
         */
        NBTTagList booleanList = nbt.getTagList("booleans", Constants.NBT.TAG_COMPOUND);
        for(NBTBase compound : booleanList){
            SavedData booleanValue = new SavedData(Boolean.class);
            booleanValue.deserializeNBT((NBTTagCompound) compound);
            BOOLEANS.put(
                    booleanValue.key, (Boolean) booleanValue.value
            );
        }

        /*
         * Read the booleans
         */
        NBTTagList tagList = nbt.getTagList("tags", Constants.NBT.TAG_COMPOUND);
        for(NBTBase compound : tagList){
            SavedData nbtValue = new SavedData(NBTTagCompound.class);
            nbtValue.deserializeNBT((NBTTagCompound) compound);
            NBT_TAGS.put(
                    nbtValue.key, (NBTTagCompound) nbtValue.value
            );
        }

        /*
         * Read the player-id
         */
        this.id = nbt.getString("id");

    }

    public String getId() {
        return id;
    }

    public String[] getAllStringEntry(){
        return Helpers.extractAllHashMapEntryNames(STRINGS);
    }

    /**
     * Get all the integers keys stored in the database
     */
    public String[] getAllIntegerEntry(){
        return Helpers.extractAllHashMapEntryNames(INTEGERS);
    }

    /**
     * Get all the doubles keys stored in the database
     */
    public String[] getAllDoubleEntry(){
        return Helpers.extractAllHashMapEntryNames(DOUBLES);
    }

    /**
     * Get all the floats keys stored in the database
     */
    public String[] getAllFloatEntry(){
        return Helpers.extractAllHashMapEntryNames(FLOATS);
    }

    /**
     * Get all the booleans keys stored in the database
     */
    public String[] getAllBooleanEntry(){
        return Helpers.extractAllHashMapEntryNames(BOOLEANS);
    }

    /**
     * Save the database in the Databases WorldSavedData
     */
    public void save(){
        Databases.save();
    }
}
