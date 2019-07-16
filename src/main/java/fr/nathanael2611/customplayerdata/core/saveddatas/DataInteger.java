package fr.nathanael2611.customplayerdata.core.saveddatas;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class DataInteger implements INBTSerializable<NBTTagCompound> {

    public String key;
    public int value;

    public DataInteger(String key, int value) {
        this.key = key;
        this.value = value;
    }

    public DataInteger(){}

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("key", key);
        compound.setInteger("value", value);
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        key = nbt.getString("key");
        value = nbt.getInteger("value");
    }
}
