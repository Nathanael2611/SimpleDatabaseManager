package fr.nathanael2611.simpledatabasemanager.command;

import fr.nathanael2611.simpledatabasemanager.core.Database;
import fr.nathanael2611.simpledatabasemanager.core.Databases;
import fr.nathanael2611.simpledatabasemanager.core.SyncedDatabases;
import fr.nathanael2611.simpledatabasemanager.util.Helpers;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

/**
 * This class contain the database-managing command
 *
 * @author Nathanael2611
 */
public class CommandDatabase extends CommandBase {
    @Override
    public String getName() {
        return "database";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        TextComponentString correctUsage = new TextComponentString(
                "§cCorrect usage: /database <database> <<set/get/remove><String/Integer/Double/Float/Boolean>> <key> [<value>] OR /database <database> enableAutoSynced <true/false> OR /database <database> sendDatabase <player>"
        );
        if (args.length > 2) {
            String dbName = args[0];
            String actionType = args[1];
            Database database = Databases.getDatabase(dbName);

            List<String> commandPlus = Arrays.asList(new String[]{"sendDatabase", "enableAutoSynced"});

            if (!Arrays.asList(Database.COMMAND_ALL_ACTIONS).contains(actionType) && !commandPlus.contains(actionType)) {
                sender.sendMessage(
                        correctUsage
                );
                return;
            }

            if (actionType.startsWith("get")) {
                Object data = null;
                if (actionType.equalsIgnoreCase("getString")) {
                    data = database.getString(args[2]);
                }
                if (actionType.equalsIgnoreCase("getInteger")) {
                    data = database.getInteger(args[2]);
                }
                if (actionType.equalsIgnoreCase("getDouble")) {
                    data = database.getDouble(args[2]);
                }
                if (actionType.equalsIgnoreCase("getFloat")) {
                    data = database.getFloat(args[2]);
                }
                if (actionType.equalsIgnoreCase("getBoolean")) {
                    data = database.getBoolean(args[2]);
                }
                sender.sendMessage(new TextComponentString("§2" + actionType.substring(3) + " §a'" + args[2] + "'§2 in database §a'" + dbName + "'§2 is §a'" + data + "'§2."));
            } else if (actionType.startsWith("set")) {
                if (args.length < 4) {
                    sender.sendMessage(new TextComponentString("§cCorrect usage: " + args[0] + " " + args[1] + " " + args[2] + "<value>"));
                    return;
                }
                StringBuilder builder = null;
                if (actionType.equalsIgnoreCase("setString")) {
                    builder = new StringBuilder();
                    for (int i = 3; i < args.length; i++) {
                        builder.append(args[i]).append(" ");
                    }
                    database.setString(args[2], builder.toString().substring(0, builder.toString().length() - 1));
                } else if (actionType.equalsIgnoreCase("setBoolean")) {
                    if (args[3].equalsIgnoreCase("true") || args[3].equalsIgnoreCase("false")) {
                        database.setBoolean(args[2], Boolean.valueOf(args[3]));
                    } else {
                        sender.sendMessage(new TextComponentString("§cInvalid Boolean '" + args[3] + "'. A boolean is 'true' or 'false'."));
                        return;
                    }
                } else if (Helpers.isNumeric(args[3])) {
                    if (actionType.equalsIgnoreCase("setInteger")) {
                        database.setInteger(args[2], Integer.parseInt(args[3]));
                    }
                    if (actionType.equalsIgnoreCase("setDouble")) {
                        database.setDouble(args[2], Double.parseDouble(args[3]));
                    }
                    if (actionType.equalsIgnoreCase("setFloat")) {
                        database.setFloat(args[2], Float.parseFloat(args[3]));
                    }
                } else {
                    sender.sendMessage(
                            new TextComponentString("§c'" + args[3] + "' is not a valid " + actionType.substring(3).toLowerCase() + ".")
                    );
                    return;
                }

                if (builder == null) {
                    builder = new StringBuilder();
                    builder.append(args[3]);
                }
                sender.sendMessage(new TextComponentString("§2" + actionType.substring(3) + " §a'" + args[2] + "' §2in database §a'" + dbName + "' §2was set to §a'" + builder.toString() + "'§2."));
            }else if(actionType.startsWith("remove")){
                if(actionType.equalsIgnoreCase("removeString")){
                    database.removeString(args[2]);
                }
                if(actionType.equalsIgnoreCase("removeInteger")){
                    database.removeInteger(args[2]);
                }
                if(actionType.equalsIgnoreCase("removeDouble")){
                    database.removeDouble(args[2]);
                }
                if(actionType.equalsIgnoreCase("removeFloat")){
                    database.removeFloat(args[2]);
                }
                if(actionType.equalsIgnoreCase("removeBoolean")){
                    database.removeBoolean(args[2]);
                }

                sender.sendMessage(new TextComponentString("§2" + actionType.substring(6) + " §a'" + args[2] + "'§2 was successfully removed from database §a'" + dbName + "'§2."));
            }else if(actionType.equalsIgnoreCase("enableAutoSynced")){
                if (args[2].equalsIgnoreCase("true")) {
                    SyncedDatabases.addAutoSyncedDB(dbName);
                    sender.sendMessage(
                            new TextComponentString(
                                    "§2The database §a'" + dbName + "'§2 was successfully added to all-player database-sync program."
                            )
                    );
                } else if(args[3].equalsIgnoreCase("false")){
                    SyncedDatabases.removeAutoSyncedDB(dbName);
                    sender.sendMessage(
                            new TextComponentString(
                                    "§2The database §a'" + dbName + "'§2 was successfully removed from all-player database-sync program."
                            )
                    );
                }else {
                    sender.sendMessage(new TextComponentString("§cInvalid Boolean '" + args[3] + "'. A boolean is 'true' or 'false'."));
                    return;
                }
            }else if(actionType.equalsIgnoreCase("sendDatabase")){
                EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(args[2]);
                if(player != null){
                    SyncedDatabases.sendDatabaseToPlayer(Databases.getDatabase(dbName), player);
                    sender.sendMessage(new TextComponentString("§2The database §a'" + dbName + "'§2 was successfully sent to §a'" + args[2] + "'§2."));

                }else{
                    sender.sendMessage(new TextComponentString("§cCannot resolve player \"" + args[2] + "\" ! Please specify a valid player."));
                    return;
                }
            }
        } else {
            sender.sendMessage(correctUsage);
        }

    }

    @Override
    public int getRequiredPermissionLevel() {
        return 4;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, Databases.getAllDatabasesNames());
        }
        if (args.length == 2) {
            String[] commandPlus = new String[]{"sendDatabase", "enableAutoSynced"};
            return getListOfStringsMatchingLastWord(
                    args,
                    ArrayUtils.addAll(Database.COMMAND_ALL_ACTIONS, commandPlus)
            );
        }
        if (args.length == 3) {
            if(args[1].equalsIgnoreCase("sendDatabase")){
                return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
            }
            if(args[1].equalsIgnoreCase("enableAutoSynced")){
                return getListOfStringsMatchingLastWord(args, new String[]{"true", "false"});
            }
            if (Databases.containsDatabase(args[0])) {

                Database db = Databases.getDatabase(args[0]);
                if (args[1].contains("String")) {
                    return getListOfStringsMatchingLastWord(
                            args,
                            db.getAllStringEntry()
                    );
                }
                if (args[1].contains("Integer")) {
                    return getListOfStringsMatchingLastWord(
                            args,
                            db.getAllIntegerEntry()
                    );
                }
                if (args[1].contains("Double")) {
                    return getListOfStringsMatchingLastWord(
                            args,
                            db.getAllDoubleEntry()
                    );
                }
                if (args[1].contains("Float")) {
                    return getListOfStringsMatchingLastWord(
                            args,
                            db.getAllFloatEntry()
                    );
                }
                if (args[1].contains("Boolean")) {
                    return getListOfStringsMatchingLastWord(
                            args,
                            db.getAllBooleanEntry()
                    );
                }

            }

        }
        return super.getTabCompletions(server, sender, args, targetPos);
    }
}
