package fr.nathanael2611.simpledatabasemanager.core;

import fr.nathanael2611.simpledatabasemanager.network.PacketHandler;
import fr.nathanael2611.simpledatabasemanager.network.PacketSendClientPlayerData;
import fr.nathanael2611.simpledatabasemanager.util.Helpers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.commons.lang3.ArrayUtils;

import java.util.HashMap;
import java.util.UUID;

/**
 * The Database class.
 * Can be serialized to nbt.
 * Can be deserialized from nbt.
 *
 * @author Nathanael2611
 */
public class Database implements INBTSerializable<NBTTagCompound> {
    public static final String[] COMMAND_SET_ACTIONS = new String[]{
            "setString", "setDouble", "setBoolean", "setInteger", "setFloat"
    };
    public static final String[] COMMAND_GET_ACTIONS = new String[]{
            "getString", "getDouble", "getBoolean", "getInteger", "getFloat"
    };
    public static final String[] COMMAND_REMOVE_ACTIONS = new String[]{
            "removeString", "removeDouble", "removeBoolean", "removeInteger", "removeFloat"
    };
    public static final String[] COMMAND_ALL_ACTIONS = ArrayUtils.addAll(ArrayUtils.addAll(COMMAND_GET_ACTIONS, COMMAND_SET_ACTIONS), COMMAND_REMOVE_ACTIONS);
    private final HashMap<String, String>  STRINGS  = new HashMap<>();
    private final HashMap<String, Integer> INTEGERS = new HashMap<>();
    private final HashMap<String, Double>  DOUBLES  = new HashMap<>();
    private final HashMap<String, Float>   FLOATS   = new HashMap<>();
    private final HashMap<String, Boolean> BOOLEANS = new HashMap<>();

    private String id;

    public Database(){}

    public Database(String id){
        this.id = id;
    }

    /**
     *  String stuff
     */
    public String getString(String key){
        return STRINGS.get(key);
    }
    public void setString(String key, String value){
        STRINGS.put(key, value);
        save();
    }
    public void removeString(String key) {
        STRINGS.remove(key);
        save();
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
        save();
    }
    public void removeInteger(String key) {
        INTEGERS.remove(key);
        save();
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
        save();
    }
    public void removeDouble(String key) {
        DOUBLES.remove(key);
        save();
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
        save();
    }
    public void removeFloat(String key) {
        FLOATS.remove(key);
        save();
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
        save();
    }
    public void removeBoolean(String key) {
        BOOLEANS.remove(key);
        save();
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
                    new SavedData(
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
            integerList.appendTag(new SavedData(str, INTEGERS.get(str)).serializeNBT());
        }
        compound.setTag("integers", integerList);

        /**
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

        /**
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

        /**
         * Saving the player-id
         */
        compound.setString("id", id);

        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {

        /**
         * Read the strings
         */
        NBTTagList stringList = nbt.getTagList("strings", Constants.NBT.TAG_COMPOUND);
        for(NBTBase compound : stringList){
            SavedData string = new SavedData(String.class);
            string.deserializeNBT((NBTTagCompound) compound);
            STRINGS.put(string.key, (String)string.value);
        }

        /**
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

        /**
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

        /**
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

        /**
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

    public void save(){
        Databases.save();

    }

    /**
     * Convert database to read-only one
     */
    public DatabaseReadOnly toReadOnly(){
        DatabaseReadOnly readOnly = new DatabaseReadOnly();
        readOnly.deserializeNBT(this.serializeNBT());
        return readOnly;
    }

}
