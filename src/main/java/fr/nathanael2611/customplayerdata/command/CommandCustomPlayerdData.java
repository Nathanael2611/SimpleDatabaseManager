package fr.nathanael2611.customplayerdata.command;

import fr.nathanael2611.customplayerdata.core.PlayerData;
import fr.nathanael2611.customplayerdata.core.PlayerDatas;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class CommandCustomPlayerdData extends CommandBase {
    @Override
    public String getName() {
        return "customplayerdata";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if(args.length > 2){
            EntityPlayer player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(args[1]);
            if(player == null){
                sender.sendMessage(new TextComponentString("§cCannot resolve player " + args[1] + " ! Please specify a valid player."));
                return;
            }
            PlayerData playerData = PlayerDatas.getPlayerData(player);
            if(args[0].startsWith("get")){
                Object data = null;
                if(args[0].equalsIgnoreCase("getString")){
                    data = playerData.getString(args[2]);
                }
                if(args[0].equalsIgnoreCase("getInteger")){
                    data = playerData.getInteger(args[2]);
                }
                sender.sendMessage(new TextComponentString(args[1].toUpperCase() + ":" + args[2] + " = " + data));
            }else if(args[0].startsWith("set")){
                if(args.length != 4){
                    sender.sendMessage(new TextComponentString("§cCorrect usage: " + args[0] + " " + args[1] + " " + args[2] + "<value>"));
                    return;
                }
                if(args[0].equalsIgnoreCase("setString")){
                    playerData.setString(args[2], args[3]);
                }
                sender.sendMessage(new TextComponentString("The player's " + args[0].substring(3) + "was set to " + args[3]));
            }
        }

    }

    @Override
    public int getRequiredPermissionLevel() {
        return 4;
    }
}
