package fr.nathanael2611.simpledatabasemanager.event;

import fr.nathanael2611.simpledatabasemanager.SimpleDatabaseManager;
import fr.nathanael2611.simpledatabasemanager.network.PacketHandler;
import fr.nathanael2611.simpledatabasemanager.network.PacketSendClientPlayerData;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber(modid = SimpleDatabaseManager.MOD_ID)
public class SyncEventHandler {

    @SubscribeEvent
    public static void onJoin(PlayerEvent.PlayerLoggedInEvent e){

        if(!e.player.world.isRemote){

            PacketHandler.getNetwork().sendTo(
                    new PacketSendClientPlayerData(e.player),
                    FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(e.player.getName())
            );

        }

    }

}
