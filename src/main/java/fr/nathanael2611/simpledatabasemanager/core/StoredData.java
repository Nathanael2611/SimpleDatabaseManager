package fr.nathanael2611.simpledatabasemanager.core;

import fr.nathanael2611.simpledatabasemanager.util.NBTUtils;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.nbt.*;
import net.minecraft.util.math.BlockPos;
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
    public StoredData() {}

    /**
     * Constructor
     */
    public StoredData(Object data)
    {
        this.value = data;
    }

    /* Get the data as String */
    public String asString() {
        return this.value instanceof String ? (String)this.value : null;
    }


    /* Get the data as Integer */
    public int asInteger() {
        return this.value instanceof Integer ? (Integer)this.value : 0;
    }

    /* Get the data as Double */

    public double asDouble() {
        return this.value instanceof Double ? (Double)this.value : 0.0D;
    }


    /* Get the data as Float */
    public float asFloat() {
        return this.value instanceof Float ? (Float)this.value : 0.0F;
    }


    /* Get the data as HashMap */
    public < K, V > HashMap<K, V> asHashMap() {
        return this.value instanceof Map ? (HashMap< K, V >) this.value : (HashMap< K, V >) Collections.EMPTY_LIST;
    }


    /* Get the data as ArrayList */
    public < V > ArrayList<V> asArrayList() {
        return this.value instanceof ArrayList ? (ArrayList< V >) this.value : (ArrayList< V >) Collections.EMPTY_LIST;
    }

    /* Get the data as BlockPos */
    public BlockPos asBlockPos()
    {
        return this.value instanceof BlockPos ? (BlockPos)this.value : null;
    }

    /* Get the data as Enum */
    public Enum asEnum()
    {
        return this.value instanceof Enum ? (Enum)this.value : null;
    }

    /* Get the data as an object type */
    public Object asObject()
    {
        return this.value;
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
