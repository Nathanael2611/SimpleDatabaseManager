package fr.nathanael2611.simpledatabasemanager.core;

import fr.nathanael2611.simpledatabasemanager.network.PacketHandler;
import fr.nathanael2611.simpledatabasemanager.network.PacketSendClientPlayerData;
import fr.nathanael2611.simpledatabasemanager.util.Helpers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.commons.lang3.ArrayUtils;

import java.util.HashMap;
import java.util.UUID;

/**
 * The Database class.
 * Can be serialized to nbt.
 * Can be deserialized from nbt.
 *
 * @author Nathanael2611
 */
public class Database extends DatabaseReadOnly{

    public Database(){}
    public Database(String id){
        this.id = id;
    }

    /**
     *  String stuff
     */
    public void setString(String key, String value){
        STRINGS.put(key, value);
        save();
    }
    public void removeString(String key) {
        STRINGS.remove(key);
        save();
    }


    /**
     *  Integer stuff
     */

    public void setInteger(String key, int value){
        INTEGERS.put(key, value);
        save();
    }
    public void removeInteger(String key) {
        INTEGERS.remove(key);
        save();
    }

    /**
     *  Double stuff
     */

    public void setDouble(String key, double value){
        DOUBLES.put(key, value);
        save();
    }
    public void removeDouble(String key) {
        DOUBLES.remove(key);
        save();
    }

    /**
     *  Float stuff
     */

    public void setFloat(String key, float value){
        FLOATS.put(key, value);
        save();
    }
    public void removeFloat(String key) {
        FLOATS.remove(key);
        save();
    }

    /**
     *  Boolean stuff
     */

    public void setBoolean(String key, boolean value){
        BOOLEANS.put(key, value);
        save();
    }
    public void removeBoolean(String key) {
        BOOLEANS.remove(key);
        save();
    }

    /**
     * Convert database to read-only one
     */
    public DatabaseReadOnly toReadOnly(){
        DatabaseReadOnly readOnly = new DatabaseReadOnly();
        readOnly.deserializeNBT(this.serializeNBT());
        return readOnly;
    }

}
