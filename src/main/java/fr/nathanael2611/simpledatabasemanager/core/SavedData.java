package fr.nathanael2611.simpledatabasemanager.core;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class SavedData implements INBTSerializable<NBTTagCompound>
{

    public String key;
    public StoredData value;

    /**
     * Used for deserialization
     */
    public SavedData()
    {
    }

    public SavedData(String key, StoredData value)
    {
        this.key = key;
        this.value = value;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("key", key);
        compound.setTag("value", value.serializeNBT());
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        this.key = nbt.getString("key");
        StoredData data = new StoredData();
        data.deserializeNBT(nbt.getTag("value"));
        this.value = data;
    }
}
