package fr.nathanael2611.simpledatabasemanager.client;

import fr.nathanael2611.simpledatabasemanager.core.Database;
import fr.nathanael2611.simpledatabasemanager.core.DatabaseReadOnly;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientDatabases {

    private static Database personalPlayerData = new Database(Minecraft.getMinecraft().player.getGameProfile().getId().toString());

    public static Database getPersonalPlayerData() {
        return personalPlayerData;
    }

    public static void updatePersonalPlayerData(Database database){

        personalPlayerData = database;
        DatabaseReadOnly databaseReadOnly = new DatabaseReadOnly().setBoolean("", false);
        databaseReadOnly.setBoole
    }

}
