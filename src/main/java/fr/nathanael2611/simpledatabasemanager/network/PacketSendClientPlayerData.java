package fr.nathanael2611.simpledatabasemanager.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import fr.nathanael2611.simpledatabasemanager.client.ClientDatabases;
import fr.nathanael2611.simpledatabasemanager.core.DatabaseReadOnly;
import fr.nathanael2611.simpledatabasemanager.core.Databases;

/**
 * Used for send his player data to a player
 *
 * @author Nathanael2611
 */
public class PacketSendClientPlayerData implements IMessage
{

    private DatabaseReadOnly playerData;

    /**
     * Empty constructor
     */
    public PacketSendClientPlayerData()
    {
    }

    /**
     * Constructor
     * @param player the player to send the player-data
     */
    public PacketSendClientPlayerData(EntityPlayer player)
    {
        NBTTagCompound compound = Databases.getPlayerData(player).serializeNBT();
        playerData = new DatabaseReadOnly();
        playerData.deserializeNBT(compound);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        playerData = new DatabaseReadOnly();
        playerData.deserializeNBT(ByteBufUtils.readTag(buf));
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeTag(buf, playerData.serializeNBT());
    }

    public static class Handler implements IMessageHandler<PacketSendClientPlayerData, IMessage>
    {
        @Override
        public IMessage onMessage(PacketSendClientPlayerData message, MessageContext ctx)
        {
            ClientDatabases.updatePersonalPlayerData(message.playerData);
            return null;
        }
    }
}