package fr.nathanael2611.simpledatabasemanager.core;

import fr.nathanael2611.simpledatabasemanager.util.Helpers;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashMap;

public class DatabaseReadOnly implements INBTSerializable<NBTTagCompound>
{

    public static final int LAST_DB_VERSION = 2;

    protected final HashMap<String, StoredData> DATA = new HashMap<>();

    protected String id;
    protected boolean isPlayerData;
    protected int dbVersion;

    /**
     * A simple constructor used for deserialize database
     */
    public DatabaseReadOnly()
    {
    }

    public DatabaseReadOnly(String id, boolean isPlayerData)
    {
        this.id = id;
        this.isPlayerData = isPlayerData;
        this.dbVersion = LAST_DB_VERSION;
    }

    public String getId() {
        return id;
    }

    public boolean isPlayerData() {
        return isPlayerData;
    }

    public boolean contains(String key)
    {
        return DATA.containsKey(key);
    }

    private boolean isType(String key, Class type)
    {
        if(contains(key))
        {
            return DATA.get(key).getType() == type;
        }
        return false;
    }

    public boolean isString(String key)
    {
        return isType(key, String.class);
    }

    public boolean isInteger(String key)
    {
        return isType(key, Integer.class);
    }

    public boolean isDouble(String key)
    {
        return isType(key, Double.class);
    }

    public boolean isFloat(String key)
    {
        return isType(key, Float.class);
    }

    public StoredData get(String key)
    {
        return DATA.getOrDefault(key, new StoredData(null));
    }

    public String getString(String key)
    {
        return get(key).asString();
    }

    public int getInteger(String key)
    {
        return get(key).asInteger();
    }

    public double getDouble(String key)
    {
        return get(key).asDouble();
    }

    public float getFloat(String key)
    {
        return get(key).asFloat();
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound compound = new NBTTagCompound();

        compound.setString("id", this.id == null ? "" : this.id);
        compound.setShort("isPlayerData", (short) (isPlayerData ? 1 : 0));
        compound.setInteger("dbVersion", this.dbVersion);

        NBTTagList dataList = new NBTTagList();
        for(String str : DATA.keySet())
        {
            dataList.appendTag(new SavedData(str, DATA.get(str)).serializeNBT());
        }
        compound.setTag("data", dataList);

        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        this.id = nbt.getString("id");
        this.isPlayerData = nbt.getShort("isPlayerData") == 1;

        NBTTagList dataList = nbt.getTagList("data", Constants.NBT.TAG_COMPOUND);
        for(NBTBase compound : dataList)
        {
            if(compound instanceof NBTTagCompound)
            {
                SavedData data = new SavedData();
                data.deserializeNBT((NBTTagCompound) compound);
                DATA.put(data.key, data.value);
            }
        }
    }

    public void deserializeSDM1DatabaseFormat(NBTTagCompound nbt, boolean isPlayerData)
    {

        if(nbt.getInteger("dbVersion") > 1)
        {
            return;
        }

        System.out.println(String.format("Converting database '%s' from storage format 1 to storage format 2...", nbt.getString("id")));

        /*
         * Read the strings
         */
        NBTTagList stringList = nbt.getTagList("strings", Constants.NBT.TAG_COMPOUND);
        for(NBTBase compound : stringList){
            SDM1SavedData string = new SDM1SavedData(String.class);
            string.deserializeNBT((NBTTagCompound) compound);
            DATA.put(string.key, new StoredData(string.value));
        }

        /*
         * Read the integers
         */
        NBTTagList integerList = nbt.getTagList("integers", Constants.NBT.TAG_COMPOUND);
        for(NBTBase compound : integerList){
            SDM1SavedData integer = new SDM1SavedData(Integer.class);
            integer.deserializeNBT((NBTTagCompound) compound);
            DATA.put(integer.key, new StoredData(integer.value));
        }

        /*
         * Read the doubles
         */
        NBTTagList doubleList = nbt.getTagList("doubles", Constants.NBT.TAG_COMPOUND);
        for(NBTBase compound : doubleList){
            SDM1SavedData doubleValue = new SDM1SavedData(Double.class);
            doubleValue.deserializeNBT((NBTTagCompound) compound);
            DATA.put(doubleValue.key, new StoredData(doubleValue.value));
        }

        /*
         * Read the floats
         */
        NBTTagList floatList = nbt.getTagList("floats", Constants.NBT.TAG_COMPOUND);
        for(NBTBase compound : floatList)
        {
            SDM1SavedData floatValue = new SDM1SavedData(Float.class);
            floatValue.deserializeNBT((NBTTagCompound) compound);
            DATA.put(floatValue.key, new StoredData(floatValue.value));
        }

        this.id = nbt.getString("id");
        this.isPlayerData = isPlayerData;
        this.dbVersion = LAST_DB_VERSION;

    }

    public String[] getAllEntryNames()
    {
        return Helpers.extractAllHashMapEntryNames(DATA);
    }

}
