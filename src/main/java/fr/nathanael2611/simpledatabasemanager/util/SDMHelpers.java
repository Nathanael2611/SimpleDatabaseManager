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

public class SDMHelpers
{

    /**
     * Just extract all entry-names from a HashMap<String, T>
     */
    public static <T>  String[] extractAllHashMapEntryNames(HashMap<String, T> map) {
        String[] strings = new String[map.size()];
        int i = 0;
        for(String str : map.keySet())
        {
            strings[i] = str;
            i++;
        }
        return strings;
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
        FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers().forEach(e -> PacketHandler.INSTANCE.sendTo(message, e));
    }

    public static void sendToSpecifics(IMessage message, List<EntityPlayerMP> list)
    {
        list.forEach(e ->
        {
            if(e != null) PacketHandler.INSTANCE.sendTo(message, e);
        });
    }

    public static void sendTo(IMessage message, EntityPlayerMP playerMP)
    {
        PacketHandler.INSTANCE.sendTo(message, playerMP);
    }
}
