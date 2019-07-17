package fr.nathanael2611.customplayerdata.command;

import fr.nathanael2611.customplayerdata.core.Database;
import fr.nathanael2611.customplayerdata.core.Databases;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;
import java.util.List;

public class CommandDatabase extends CommandBase {
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
            String playerStr = args[0];
            String actionType = args[1];
            EntityPlayer player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(playerStr);
            if(player == null){
                sender.sendMessage(new TextComponentString("§cCannot resolve player \"" + playerStr + "\" ! Please specify a valid player."));
                return;
            }
            Database playerData = Databases.getPlayerData(player);


            if(actionType.startsWith("get")){
                Object data = null;
                if(actionType.equalsIgnoreCase("getString")){
                    data = playerData.getString(args[2]);
                }
                if(actionType.equalsIgnoreCase("getInteger")){
                    data = playerData.getInteger(args[2]);
                }
                sender.sendMessage(new TextComponentString(args[1].toUpperCase() + ":" + args[2] + " = " + data));
            }else if(actionType.startsWith("set")){
                if(args.length != 4){
                    sender.sendMessage(new TextComponentString("§cCorrect usage: " + args[0] + " " + args[1] + " " + args[2] + "<value>"));
                    return;
                }
                if(actionType.equalsIgnoreCase("setString")){
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

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {

        return super.getTabCompletions(server, sender, args, targetPos);
    }
}
