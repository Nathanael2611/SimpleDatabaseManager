package fr.nathanael2611.simpledatabasemanager.network;

import fr.nathanael2611.simpledatabasemanager.client.ClientDatabases;
import fr.nathanael2611.simpledatabasemanager.core.Database;
import fr.nathanael2611.simpledatabasemanager.core.Databases;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSendClientPlayerData implements IMessage {

    private Database playerData;

    public PacketSendClientPlayerData() {
    }

    public PacketSendClientPlayerData(EntityPlayer player){
        playerData = Databases.getPlayerData(player);
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        playerData = new Database();
        playerData.deserializeNBT(ByteBufUtils.readTag(buf));

    }

    @Override
    public void toBytes(ByteBuf buf) {

        ByteBufUtils.writeTag(buf, playerData.serializeNBT());

    }

    public static class Handler implements IMessageHandler<PacketSendClientPlayerData, IMessage> {


        @Override
        public IMessage onMessage(PacketSendClientPlayerData message, MessageContext ctx) {

            ClientDatabases.updatePersonalPlayerData(message.playerData);

            return null;
        }
    }
}
