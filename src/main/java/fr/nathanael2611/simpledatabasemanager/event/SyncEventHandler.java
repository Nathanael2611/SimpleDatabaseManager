package fr.nathanael2611.simpledatabasemanager.event;

import fr.nathanael2611.simpledatabasemanager.core.Databases;
import fr.nathanael2611.simpledatabasemanager.core.SyncedDatabases;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/**
 * This class will manage the server -> client sync events
 * And the autosave
 */
public class SyncEventHandler
{

    /**
     * Fired when a player join the game
     * It will sync all the databases via SyncedDatabases class
     */
    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent e)
    {
        if(!e.player.world.isRemote) SyncedDatabases.syncAll();
    }

    @SubscribeEvent
    public void onWorldSave(WorldEvent.Save event)
    {
        Databases.save();
    }

}
