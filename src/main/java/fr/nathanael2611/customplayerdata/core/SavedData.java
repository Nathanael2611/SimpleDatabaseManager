package fr.nathanael2611.customplayerdata.core;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class SavedData<V extends Object> implements INBTSerializable<NBTTagCompound> {

    public String key;
    public Object value;

    public SavedData(String key, V value) {
        this.key = key;
        this.value = value;
    }

    public SavedData() {
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("key", key);

        if (value.equals(String.class)) {
            compound.setString("value", (String) value);
        } else if (value.equals(Integer.class) || value.equals(int.class)) {
            compound.setInteger("value", (Integer) value);
        } else if (value.equals(Double.class) || value.equals(double.class)) {
            compound.setDouble("value", (Double) value);
        } else if (value.equals(Float.class) || value.equals(float.class)) {
            compound.setFloat("value", (Float) value);
        } else if (value.equals(Boolean.class)) {
            compound.setBoolean("value", (Boolean) value);
        }
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        key = nbt.getString("key");
        System.out.println(" ouf quoi :/");

        if (value.equals(String.class)) {
            this.value = nbt.getString("value");
        }
        if (value.equals(Double.class) || value.equals(double.class)) {
            this.value = nbt.getInteger("value");
        }
        if (value.equals(Double.class) || value.equals(double.class)) {
            this.value = nbt.getDouble("value");
        }
        if (value.equals(Float.class) || value.equals(float.class)) {
            this.value = nbt.getFloat("value");
        }
        if (value.equals(Boolean.class)) {
            this.value = nbt.getBoolean("value");
        }
    }

}