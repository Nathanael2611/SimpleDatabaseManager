package fr.nathanael2611.customplayerdata.core.saveddatas;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class DataString implements INBTSerializable<NBTTagCompound> {

    public String key;
    public String value;

    public DataString(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public DataString(){}

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("key", key);
        compound.setString("value", value);
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        key = nbt.getString("key");
        value = nbt.getString("value");
    }
}
