package fr.nathanael2611.simpledatabasemanager.network;

import fr.nathanael2611.simpledatabasemanager.client.ClientDatabases;
import fr.nathanael2611.simpledatabasemanager.core.Database;
import fr.nathanael2611.simpledatabasemanager.core.DatabaseReadOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketSendDatabaseToClient implements IMessage {

    private DatabaseReadOnly db;

    public PacketSendDatabaseToClient() {
    }

    public PacketSendDatabaseToClient(Database db) {
        NBTTagCompound compound = db.serializeNBT();
        db = new DatabaseReadOnly();
        db.deserializeNBT(compound);
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        db = new DatabaseReadOnly();
        db.deserializeNBT(ByteBufUtils.readTag(buf));

    }

    @Override
    public void toBytes(ByteBuf buf) {

        ByteBufUtils.writeTag(buf, db.serializeNBT());

    }

    public static class Message implements IMessageHandler<PacketSendDatabaseToClient, IMessage> {
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(final PacketSendDatabaseToClient message, final MessageContext ctx) {
            return null;
        }
    }
}