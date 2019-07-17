package fr.nathanael2611.customplayerdata.command;

import fr.nathanael2611.customplayerdata.core.Database;
import fr.nathanael2611.customplayerdata.core.Databases;
import fr.nathanael2611.customplayerdata.util.Helpers;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        TextComponentString correctUsage = new TextComponentString(
                "§cCorrect usage: /customplayerdata <player> <<set/get><String/Integer/Double/Float/Boolean>> <key> [<value>]"
        );
        if(args.length > 2){
            String playerStr = args[0];
            String actionType = args[1];
            EntityPlayer player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(playerStr);
            if(player == null){
                sender.sendMessage(new TextComponentString("§cCannot resolve player \"" + playerStr + "\" ! Please specify a valid player."));
                return;
            }
            Database playerData = Databases.getPlayerData(player);


            if(!Arrays.asList(Database.COMMAND_ALL_ACTIONS).contains(actionType)){
                sender.sendMessage(
                        correctUsage
                );
            }

            if(actionType.startsWith("get")){
                Object data = null;
                if(actionType.equalsIgnoreCase("getString")){
                    data = playerData.getString(args[2]);
                }
                if(actionType.equalsIgnoreCase("getInteger")){
                    data = playerData.getInteger(args[2]);
                }
                if(actionType.equalsIgnoreCase("getDouble")){
                    data = playerData.getDouble(args[2]);
                }
                if(actionType.equalsIgnoreCase("getFloat")){
                    data = playerData.getFloat(args[2]);
                }
                if(actionType.equalsIgnoreCase("getBoolean")){
                    data = playerData.getBoolean(args[2]);
                }

                sender.sendMessage(new TextComponentString(args[1].toUpperCase() + ":" + args[2] + " = " + data));
            }else if(actionType.startsWith("set")){
                if(args.length <4){
                    sender.sendMessage(new TextComponentString("§cCorrect usage: " + args[0] + " " + args[1] + " " + args[2] + "<value>"));
                    return;
                }
                StringBuilder builder = null;
                if(actionType.equalsIgnoreCase("setString")){
                     builder = new StringBuilder();
                    for(int i = 3; i < args.length; i++){
                        builder.append(args[i]).append(" ");
                    }
                    playerData.setString(args[2], builder.toString().substring(0, builder.toString().length()-1));
                }else if(actionType.equalsIgnoreCase("setBoolean")){
                    if(args[3].equalsIgnoreCase("true") || args[3].equalsIgnoreCase("false")){
                        playerData.setBoolean(args[2], Boolean.valueOf(args[3]));
                    }else{
                        sender.sendMessage(new TextComponentString("§cInvalid Boolean '"+args[3]+"'. A boolean is 'true' or 'false'."));
                        return;
                    }
                }else if(Helpers.isNumeric(args[3])){
                    if(actionType.equalsIgnoreCase("setInteger")){
                        playerData.setInteger(args[2], Integer.parseInt(args[3]));
                    }
                    if(actionType.equalsIgnoreCase("setDouble")){
                        playerData.setDouble(args[2], Double.parseDouble(args[3]));
                    }
                    if(actionType.equalsIgnoreCase("setFloat")){
                        playerData.setFloat(args[2], Float.parseFloat(args[3]));
                    }
                }else{
                    sender.sendMessage(
                            new TextComponentString("§c'" + args[3] + "' is not a valid " + actionType.substring(3).toLowerCase() + ".")
                    );
                    return;
                }

                if(builder == null){
                    builder = new StringBuilder();
                    builder.append(args[3]);
                }
                sender.sendMessage(new TextComponentString("§2" + actionType.substring(3) + " §a'" + args[2] + "' §2in player §a'" + playerStr + "' §2was set to §a'" + builder.toString() + "'§2."));
            }
        }else{
            sender.sendMessage(correctUsage);
        }

    }

    @Override
    public int getRequiredPermissionLevel() {
        return 4;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if(args.length == 1)
            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        if(args.length == 2)
            return getListOfStringsMatchingLastWord(
                    args,
                    "getString", "getInteger", "getDouble", "getFloat", "getBoolean",
                    "setString", "setInteger", "setDouble", "setFloat", "setBoolean"
            );
        if(args.length == 3){
            EntityPlayer player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(args[0]);
            if(player != null){
                System.out.println(args[1]);
                if(args[1].contains("String")){
                    System.out.println("pas foufou x)");
                    return getListOfStringsMatchingLastWord(
                            args,
                            Databases.getPlayerData(player).getAllStringEntry()
                    );
                }
                if(args[1].contains("Integer")){
                    return getListOfStringsMatchingLastWord(
                            args,
                            Databases.getPlayerData(player).getAllIntegerEntry()
                    );
                }
                if(args[1].contains("Double")){
                    return getListOfStringsMatchingLastWord(
                            args,
                            Databases.getPlayerData(player).getAllDoubleEntry()
                    );
                }
                if(args[1].contains("Float")){
                    return getListOfStringsMatchingLastWord(
                            args,
                            Databases.getPlayerData(player).getAllFloatEntry()
                    );
                }
                if(args[1].contains("Boolean")){
                    return getListOfStringsMatchingLastWord(
                            args,
                            Databases.getPlayerData(player).getAllBooleanEntry()
                    );
                }

            }

        }
        return super.getTabCompletions(server, sender, args, targetPos);
    }
}
