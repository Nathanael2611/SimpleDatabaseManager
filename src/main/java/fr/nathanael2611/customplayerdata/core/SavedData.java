package fr.nathanael2611.customplayerdata.core;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class SavedData<V> implements INBTSerializable<NBTTagCompound> {

    public String key;
    public V value;

    public SavedData(String key, V value) {
        this.key = key;
        this.value = value;
    }

    public SavedData(){}

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("key", key);

        if(value instanceof String)
            compound.setString("value", (String)value);
        if(value instanceof Integer)
            compound.setInteger("value", (Integer)value);
        if(value instanceof Double)
            compound.setDouble("value", (Double)value);
        if(value instanceof Float)
            compound.setFloat("value", (Float)value);
        if(value instanceof Boolean)
            compound.setBoolean("value", (Boolean)value);


        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        key = nbt.getString("key");
        System.out.println(" ouf quoi :/");

        if(value.getClass() == String.class)
            this.value = nbt.getString("value");
        if(value.getClass() == Integer.class)
            this.value = nbt.getInteger("value");
        if(value.getClass() == Double.class)
            this.value = nbt.getDouble("value");
        if(value.getClass() ==  Float.class)
            this.value = nbt.getFloat("value");
        if(value.getClass() == Boolean.class)
            this.value = nbt.getBoolean("value");V.
    }

}
