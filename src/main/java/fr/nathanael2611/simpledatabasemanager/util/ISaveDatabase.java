package fr.nathanael2611.simpledatabasemanager.util;

import net.minecraft.nbt.NBTTagCompound;

public interface ISaveDatabase {
    /*
      WARNING : create empty constructor for not crash
     */
    void writeToNBT(NBTTagCompound nbt);

    void readFromNBT(NBTTagCompound nbt);
}
