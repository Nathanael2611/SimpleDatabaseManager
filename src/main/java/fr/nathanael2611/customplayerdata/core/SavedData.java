package fr.nathanael2611.customplayerdata.core;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class SavedData implements INBTSerializable<NBTTagCompound> {

    public String key;
    public Object value;

    public SavedData(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public SavedData() {
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("key", key);

        if(value instanceof String){
            compound.setString("value", (String) value);
        } else if (value instanceof Integer) {
            compound.setInteger("value", (Integer) value);
        } else if (value instanceof Double) {
            compound.setDouble("value", (Double) value);
        } else if (value instanceof Float) {
            compound.setFloat("value", (Float) value);
        } else if (value instanceof Boolean) {
            compound.setBoolean("value", (Boolean) value);
        }
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        key = nbt.getString("key");
        System.out.println(" ouf quoi :/");

        if (value instanceof String) {
            this.value = nbt.getString("value");
        }
        if (value instanceof Integer) {
            this.value = nbt.getInteger("value");
        }
        if (value instanceof Double) {
            this.value = nbt.getDouble("value");
        }
        if (value instanceof Float) {
            this.value = nbt.getFloat("value");
        }
        if (value instanceof Boolean) {
            this.value = nbt.getBoolean("value");
        }
    }

}