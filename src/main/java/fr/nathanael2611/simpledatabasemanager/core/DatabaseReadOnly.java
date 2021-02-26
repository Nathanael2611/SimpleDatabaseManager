package fr.nathanael2611.simpledatabasemanager.core;

import fr.nathanael2611.simpledatabasemanager.util.SDMHelpers;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashMap;

/**
 * A Database read-only.
 * Only read methods.
 */
public class DatabaseReadOnly implements INBTSerializable<NBTTagCompound>
{

    /* The last database storage format version */
    public static final int LAST_DB_VERSION = 2;

    /* All the stored-data contained in database */
    protected final HashMap<String, StoredData> DATA = new HashMap<>();

    /* The Database ID */
    protected String id;
    /* Define if the database is a player-data or not */
    protected boolean isPlayerData;
    /* The database's format-version */
    protected int dbVersion;

    /**
     * A simple constructor used for deserialize database
     */
    public DatabaseReadOnly()
    {
    }

    /**
     * Constructor
     * @param id the Database id
     * @param isPlayerData if the database is a player-data
     */
    public DatabaseReadOnly(String id, boolean isPlayerData)
    {
        this.id = id;
        this.isPlayerData = isPlayerData;
        this.dbVersion = LAST_DB_VERSION;
    }

    /**
     * ID Getter
     * @return the database ID
     */
    public String getId()
    {
        return id;
    }

    /**
     * isPlayerData Getter
     * @return if the database is a player-data
     */
    public boolean isPlayerData()
    {
        return isPlayerData;
    }

    /**
     * Used for check if the database contains a specific value-key
     * @param key the researched key
     * @return if the database contains the specified key
     */
    public boolean contains(String key)
    {
        return DATA.containsKey(key);
    }

    /**
     * A simple method used to verify if a key is assigned to a specific class type
     * @param key the researched value-key
     * @param type the tested type
     * @return if the value assigned to the key is a specified type
     */
    private boolean isType(String key, Class type)
    {
        if(this.contains(key))
        {
            return this.DATA.get(key).getType() == type;
        }
        return false;
    }

    /**
     * Check if a specified key is assigned to a String
     * @param key the researched key
     * @return if the value assigned to the key is a String
     */
    public boolean isString(String key)
    {
        return this.isType(key, String.class);
    }

    /**
     * Check if a specified key is assigned to an Integer
     * @param key the researched key
     * @return if the value assigned to the key is an Integer
     */
    public boolean isInteger(String key)
    {
        return this.isType(key, Integer.class);
    }

    /**
     * Check if a specified key is assigned to a Double
     * @param key the researched key
     * @return if the value assigned to the key is a Double
     */
    public boolean isDouble(String key)
    {
        return this.isType(key, Double.class);
    }

    /**
     * Check if a specified key is assigned to a Float
     * @param key the researched key
     * @return if the value assigned to the key is a Float
     */
    public boolean isFloat(String key)
    {
        return this.isType(key, Float.class);
    }

    /**
     * Used for get a stored-data in the Database with his assigned key
     * @param key the researched key
     * @return the stored-data assigned to the specified key
     */
    public StoredData get(String key)
    {
        return this.DATA.getOrDefault(key, new StoredData(null));
    }

    /**
     * Used for directly get a stored-data as String
     * @param key the researched key
     * @return the stored-data as String
     */
    public String getString(String key)
    {
        return this.get(key).asString();
    }

    /**
     * Used for directly get a stored-data as Integer
     * @param key the researched key
     * @return the stored-data as Integer
     */
    public int getInteger(String key)
    {
        return this.get(key).asInteger();
    }

    /**
     * Used for directly get a stored-data as Double
     * @param key the researched key
     * @return the stored-data as Double
     */
    public double getDouble(String key)
    {
        return this.get(key).asDouble();
    }

    /**
     * Used for directly get a stored-data as Float
     * @param key the researched key
     * @return the stored-data as Float
     */
    public float getFloat(String key)
    {
        return this.get(key).asFloat();
    }

    /**
     * Used for directly get a stored-data as Hashmap
     * @param key the researched key
     * @return the stored-data as HashMap
     */
    public Object getHashMap(String key)
    {
        return this.get(key).asHashMap();
    }

    /**
     * Used for directly get a stored-data as ArrayList
     * @param key the researched key
     * @return the stored-data as ArrayList
     */
    public Object getArrayList(String key)
    {
        return this.get(key).asArrayList();
    }

    /**
     * Used for serialize the database to NBTTagCompound
     * @return the serialized database as NBTTagCompound
     */
    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound compound = new NBTTagCompound();

        compound.setString("id", this.id == null ? "" : this.id);
        compound.setShort("isPlayerData", (short) (isPlayerData ? 1 : 0));
        compound.setInteger("dbVersion", 2);

        NBTTagList dataList = new NBTTagList();
        for(String str : this.DATA.keySet())
        {
            dataList.appendTag(new SavedData(str, DATA.get(str)).serializeNBT());
        }
        compound.setTag("data", dataList);

        return compound;
    }

    /**
     * Used for deserialize an NBTTagCompound to the Database
     * @param nbt the specified NBTTagCompound
     */
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
                this.DATA.put(data.key, data.value);
            }
        }
    }

    /**
     * Used for deserialize an old NBTTagCompound database format to the fresh new 2.0 storage-format
     * @param nbt the old NBTTagCompound
     * @param isPlayerData define if the NBTTagCompound that we will deserialize is a player-data
     */
    void deserializeSDM1DatabaseFormat(NBTTagCompound nbt, boolean isPlayerData)
    {

        if(nbt.hasKey("data", new NBTTagList().getId()))
        {
            this.deserializeNBT(nbt);
            return;
        }

        System.out.println(String.format("Converting database '%s' from storage format 1 to storage format 2...", nbt.getString("id")));

        /*
         * Read the strings
         */
        NBTTagList stringList = nbt.getTagList("strings", Constants.NBT.TAG_COMPOUND);
        for(NBTBase compound : stringList)
        {
            SDM1SavedData string = new SDM1SavedData(String.class);
            string.deserializeNBT((NBTTagCompound) compound);
            this.DATA.put(string.key, new StoredData(string.value));
        }

        /*
         * Read the integers
         */
        NBTTagList integerList = nbt.getTagList("integers", Constants.NBT.TAG_COMPOUND);
        for(NBTBase compound : integerList)
        {
            SDM1SavedData integer = new SDM1SavedData(Integer.class);
            integer.deserializeNBT((NBTTagCompound) compound);
            this.DATA.put(integer.key, new StoredData(integer.value));
        }

        /*
         * Read the doubles
         */
        NBTTagList doubleList = nbt.getTagList("doubles", Constants.NBT.TAG_COMPOUND);
        for(NBTBase compound : doubleList)
        {
            SDM1SavedData doubleValue = new SDM1SavedData(Double.class);
            doubleValue.deserializeNBT((NBTTagCompound) compound);
            this.DATA.put(doubleValue.key, new StoredData(doubleValue.value));
        }

        /*
         * Read the floats
         */
        NBTTagList floatList = nbt.getTagList("floats", Constants.NBT.TAG_COMPOUND);
        for(NBTBase compound : floatList)
        {
            SDM1SavedData floatValue = new SDM1SavedData(Float.class);
            floatValue.deserializeNBT((NBTTagCompound) compound);
            this.DATA.put(floatValue.key, new StoredData(floatValue.value));
        }

        this.id = nbt.getString("id");
        this.isPlayerData = isPlayerData;
        this.dbVersion = LAST_DB_VERSION;
    }

    /**
     * Get all the database entries
     * @return the database entries as String array
     */
    public String[] getAllEntryNames()
    {
        return SDMHelpers.extractAllHashMapEntryNames(DATA);
    }

}
