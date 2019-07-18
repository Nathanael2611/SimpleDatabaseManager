package fr.nathanael2611.simpledatabasemanager.network;

import fr.nathanael2611.simpledatabasemanager.client.ClientDatabases;
import fr.nathanael2611.simpledatabasemanager.core.Database;
import fr.nathanael2611.simpledatabasemanager.core.DatabaseReadOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Used to send a database to a specific player
 *
 * @author Nathanael2611
 */
public class PacketSendDatabaseToClient implements IMessage {

    private DatabaseReadOnly db;

    public PacketSendDatabaseToClient() {
    }

    public PacketSendDatabaseToClient(Database db) {
        this.db = db.toReadOnly();
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

    public static class Handler implements IMessageHandler<PacketSendDatabaseToClient, IMessage> {
        @Override
        public IMessage onMessage(PacketSendDatabaseToClient message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                ClientDatabases.updateClientDB(message.db);
                return;
            });
            return null;
        }
    }
}