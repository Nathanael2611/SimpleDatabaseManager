package fr.nathanael2611.simpledatabasemanager.command;

import fr.nathanael2611.simpledatabasemanager.core.Database;
import fr.nathanael2611.simpledatabasemanager.core.Databases;
import fr.nathanael2611.simpledatabasemanager.core.StoredData;
import net.minecraft.command.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;
import java.util.List;

public class CommandDatabase extends CommandBase {
    @Override
    public String getName() {
        return "database";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "HAHA";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 4;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        String correctUsage = "/database <database> <set[String/Integer/Double/Float]/get/remove> <key> [<value>]";
        if (args.length > 2) {
            Database db = Databases.getDatabase(args[0]);
            if (args[1].equalsIgnoreCase("get")) {
                if (db.contains(args[2]))
                    sender.sendMessage(new TextComponentString(String.format("§aValue '%s' in database '%s' is '%s'", args[2], args[0], db.get(args[2]).asUnknownObject())));
                else
                    throw new CommandException(String.format("No value named '%s' in database '%s'", args[2], args[0]));
            } else if (args[1].startsWith("set")) {
                String valueType = args[1].substring(3);
                if (args.length > 3) {
                    Object willBeInsert;
                    try {
                        switch (valueType.toLowerCase()) {
                            case "string":
                                willBeInsert = args[3];
                                break;
                            case "integer":
                                willBeInsert = Integer.parseInt(args[3]);
                                break;
                            case "double":
                                willBeInsert = Double.parseDouble(args[3]);
                                break;
                            case "float":
                                willBeInsert = Float.parseFloat(args[3]);
                                break;
                            default:
                                throw new WrongUsageException(correctUsage);
                        }
                    } catch (NumberFormatException ex) {
                        throw new NumberInvalidException("commands.generic.num.invalid", args[3]);
                    }
                    db.set(args[2], new StoredData(willBeInsert));
                    String s1 = Character.toString(valueType.charAt(0)).toUpperCase();
                    sender.sendMessage(new TextComponentString(
                            String.format("§a%s '%s' was successful set to '%s' in database '%s'", s1 + valueType.toLowerCase().toLowerCase().substring(1), args[2], args[3], args[0])
                    ));
                } else throw new WrongUsageException(correctUsage);
            } else if (args[1].equalsIgnoreCase("remove")) {
                db.remove(args[2]);
                sender.sendMessage(new TextComponentString(String.format("§aValue '%s' was successful removed from database '%s'", args[2], args[0])));
            }
        } else throw new WrongUsageException(correctUsage);
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, Databases.getAllDatabasesNames());
        }
        if (args.length == 2) {
            return getListOfStringsMatchingLastWord(
                    args,
                    ArrayUtils.addAll(EnumDatabaseActions.getActionsNames())
            );
        }
        if (args.length == 3) {
            if (Databases.containsDatabase(args[0])) {

                Database db = Databases.getDatabase(args[0]);
                return getListOfStringsMatchingLastWord(
                        args,
                        db.getAllEntryNames()
                );
            }

        }
        return super.getTabCompletions(server, sender, args, targetPos);
    }
}
