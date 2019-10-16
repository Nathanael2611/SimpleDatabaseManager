package fr.nathanael2611.simpledatabasemanager.util;

import fr.nathanael2611.simpledatabasemanager.core.DatabaseReadOnly;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import fr.nathanael2611.simpledatabasemanager.core.Database;
import fr.nathanael2611.simpledatabasemanager.network.PacketHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Helpers
{
    /**
     * Just return true if string is numeric.
     * Just return false if string is not numeric.
     */
    public static boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Just extract all entry-names from a HashMap<String, T>
     */
    public static <T>  String[] extractAllHashMapEntryNames(HashMap<String, T> map) {
        String[] strings = new String[map.size()];
        int i = 0;
        for(String str : map.keySet()) {
            strings[i] = str;
            i++;
        }
        return strings;
    }

    /**
     * Create an entry
     */
    public static <V> Map.Entry createEntry(String str, V obj){
        return new Map.Entry() {
            @Override
            public Object getKey() {
                return str;
            }

            @Override
            public V getValue() {
                return obj;
            }

            @Override
            public Object setValue(Object value) {
                return null;
            }
        };
    }

    public static Database toDatabase(DatabaseReadOnly databaseReadOnly)
    {
        NBTTagCompound compound = databaseReadOnly.serializeNBT();
        Database database = new Database();
        database.deserializeNBT(compound);
        return database;
    }

    public static void sendToAll(IMessage message)
    {
        FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers().forEach(e -> {
            PacketHandler.INSTANCE.sendTo(message, e);
        });
    }

    public static void sendToSpecifics(IMessage message, List<EntityPlayerMP> list)
    {
        list.forEach(e -> {
            if(e != null)
                PacketHandler.INSTANCE.sendTo(message, e);
        });
    }

    public static void sendTo(IMessage message, EntityPlayerMP playerMP)
    {
        if(playerMP == null) System.out.println("bon... On ne vas pas passer par 4 chemins, le player est nulL..");
        PacketHandler.INSTANCE.sendTo(message, playerMP);
    }
}
