package fr.nathanael2611.simpledatabasemanager.event;

import fr.nathanael2611.simpledatabasemanager.network.PacketHandler;
import fr.nathanael2611.simpledatabasemanager.network.PacketSendClientPlayerData;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/**
 * This class will contain all syncing events
 *
 * @author Nathanael2611
 */
@Mod.EventBusSubscriber
public class SyncEventHandler {
    /**
     * Just syncing the player-data from server, to client.
     */
    @SubscribeEvent
    public static void onJoin(PlayerEvent.PlayerLoggedInEvent e) {
        if (!e.player.world.isRemote) {
            PacketHandler.INSTANCE.sendTo(
                    new PacketSendClientPlayerData(e.player),
                    FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(e.player.getName())
            );
        }
    }
}
