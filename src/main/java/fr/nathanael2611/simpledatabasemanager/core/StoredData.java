package fr.nathanael2611.simpledatabasemanager.core;

import fr.nathanael2611.simpledatabasemanager.util.NBTUtils;
import net.minecraft.nbt.*;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.*;

/**
 * This class will be used to manipulate data in databases.
 * It is store in Database with NBTBase.
 * And by the way, it can be serialized and deserialize to/from an NBTTagCompound
 */
public class StoredData implements INBTSerializable<NBTBase>
{

    private Object value;

    /**
     * Used for deserialization
     */
    public StoredData()
    {
    }

    /**
     * Constructor
     */
    public StoredData(Object data)
    {
        this.value = data;
    }

    /* Get the data as String */
    public String asString()
    {
        if(this.value == null) return null;
        if(this.value instanceof String) return (String) this.value;
        return null;
    }

    /* Get the data as Integer */
    public int asInteger()
    {
        if(this.value == null) return 0;
        if(this.value instanceof Integer) return (Integer) this.value;
        return 0;
    }

    /* Get the data as Double */
    public double asDouble()
    {
        if(this.value == null) return 0d;
        if(this.value instanceof Double) return (Double) this.value;
        return 0;
    }

    /* Get the data as Float */
    public float asFloat()
    {
        if(this.value == null) return 0f;
        if(this.value instanceof Float) return (Float) this.value;
        return 0;
    }

    /* Get the data as HashMap */
    public Object asHashMap()
    {
        if(this.value == null) return Collections.EMPTY_LIST;
        if(this.value instanceof Map) return this.value;
        return Collections.EMPTY_LIST;
    }

    /* Get the data as ArrayList */
    public Object asArrayList()
    {
        if(this.value == null) return Collections.EMPTY_LIST;
        if(this.value instanceof ArrayList) return this.value;
        return Collections.EMPTY_LIST;
    }

    /* Get the data as an unknown object type */
    public Object asUnknownObject()
    {
        return this.value;
    }

    @Override
    public NBTBase serializeNBT() {
        return NBTUtils.serializeNBT(value);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        this.value = NBTUtils.deserializeNBT(nbt);
    }

    /* Return the data type as Java Class */
    public Class getType() {
        return value.getClass();
    }
}
