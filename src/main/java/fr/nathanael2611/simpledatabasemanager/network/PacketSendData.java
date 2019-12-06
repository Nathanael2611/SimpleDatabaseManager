package fr.nathanael2611.simpledatabasemanager.network;

import fr.nathanael2611.simpledatabasemanager.util.SDMHelpers;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import fr.nathanael2611.simpledatabasemanager.client.ClientDatabases;
import fr.nathanael2611.simpledatabasemanager.core.Database;
import fr.nathanael2611.simpledatabasemanager.core.SavedData;

public class PacketSendData implements IMessage
{

    public static final String PLAYER_DATA = "playerdata";

    private String dbName;
    private String action;
    private String key;
    private SavedData value;

    public PacketSendData()
    {
    }

    public PacketSendData(String dbName, String action, String key, SavedData value)
    {
        this.dbName = dbName;
        this.action = action;
        this.key = key;
        this.value = value;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.dbName = ByteBufUtils.readUTF8String(buf);
        this.action = ByteBufUtils.readUTF8String(buf);
        this.key = ByteBufUtils.readUTF8String(buf);
        SavedData data = new SavedData();
        data.deserializeNBT(ByteBufUtils.readTag(buf));
        this.value = data;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, this.dbName);
        ByteBufUtils.writeUTF8String(buf, this.action);
        ByteBufUtils.writeUTF8String(buf, this.key);
        ByteBufUtils.writeTag(buf, this.value.serializeNBT());
    }

    public static class Handler implements IMessageHandler<PacketSendData, IMessage> {
        @Override
        public IMessage onMessage(PacketSendData message, MessageContext ctx) {
            Database editableDB = message.dbName.equals(PLAYER_DATA) ? SDMHelpers.toDatabase(ClientDatabases.getPersonalPlayerData()) : SDMHelpers.toDatabase(ClientDatabases.getDatabase(message.dbName));
            if(message.value.value != null)
            {
                if(message.action.equals("remove")) editableDB.remove(message.key, false);
                else if (message.action.equals("set")) editableDB.set(message.key, message.value.value, false);
                if(!message.dbName.equals(PLAYER_DATA)) ClientDatabases.updateClientDB(editableDB.toReadOnly());
                else ClientDatabases.updatePersonalPlayerData(editableDB.toReadOnly());
            }
            return null;
        }
    }
}
