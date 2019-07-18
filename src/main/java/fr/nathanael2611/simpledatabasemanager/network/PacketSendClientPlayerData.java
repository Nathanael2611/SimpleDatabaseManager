package fr.nathanael2611.simpledatabasemanager.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketSendClientPlayerData implements IMessage {

    private NBTTagCompound compound;

    public PacketSendClientPlayerData() {
    }

    public PacketSendClientPlayerData(EntityPlayer player) {
        /*NBTTagCompound compound = Databases.getPlayerData(player).serializeNBT();
        this.compound = compound;*/
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        //compound = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        //ByteBufUtils.writeTag(buf, compound);
    }

    public static class Message implements IMessageHandler<PacketSendClientPlayerData, IMessage> {
        @SideOnly(Side.CLIENT)
        @Override
        public IMessage onMessage(PacketSendClientPlayerData message, MessageContext ctx) {
            /*Minecraft.getMinecraft().addScheduledTask(() -> {
                DatabaseReadOnly playerData = new DatabaseReadOnly();
                playerData.deserializeNBT(message.compound);
                ClientDatabases.updatePersonalPlayerData(playerData);
                return;
            });*/
            return null;
        }
    }
}