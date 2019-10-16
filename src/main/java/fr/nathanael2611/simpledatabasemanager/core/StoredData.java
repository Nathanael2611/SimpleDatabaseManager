package fr.nathanael2611.simpledatabasemanager.core;

import net.minecraft.nbt.*;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * This class will be used to manipulate data in databases.
 * It is store in Database with NBTBase.
 * And by the way, it can be serialized and deserialize to/from an NBTTagCompound
 */
public class StoredData implements INBTSerializable<NBTBase>
{

    private Object value;
    private Class type;

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
        this.type = data == null ? Object.class : data.getClass();
    }

    /* Get the data as String */
    public String asString()
    {
        if(this.type == null || this.value == null) return null;
        if(this.type == String.class) return (String) this.value;
        return null;
    }

    /* Get the data as Integer */
    public int asInteger()
    {
        if(this.type == null || this.value == null) return 0;
        if(this.type == Integer.class) return (int) this.value;
        return 0;
    }

    /* Get the data as Double */
    public double asDouble()
    {
        if(this.type == null || this.value == null) return 0d;
        if(this.type == Double.class) return (double) this.value;
        return 0;
    }

    /* Get the data as Float */
    public float asFloat()
    {
        if(this.type == null || this.value == null) return 0f;
        if(this.type == Float.class) return (float) this.value;
        return 0;
    }

    /* Get the data as an unknown object type */
    public Object asUnknownObject()
    {
        return this.value;
    }

    @Override
    public NBTBase serializeNBT() {
        if(type == String.class) return new NBTTagString((String) this.value);
        if(type == Integer.class) return new NBTTagInt((int) this.value);
        if(type == Double.class) return new NBTTagDouble((double) this.value);
        if(type == Float.class) return new NBTTagFloat((float) this.value);
        return null;
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        if(nbt instanceof NBTTagString) this.value = ((NBTTagString) nbt).getString();
        else if (nbt instanceof NBTTagInt) this.value = ((NBTTagInt) nbt).getInt();
        else if (nbt instanceof NBTTagDouble) this.value = ((NBTTagDouble) nbt).getDouble();
        else if (nbt instanceof NBTTagFloat) this.value = ((NBTTagFloat) nbt).getFloat();
        this.type = this.value.getClass();
    }

    /* Return the data type as Java Class */
    public Class getType() {
        return type;
    }
}
