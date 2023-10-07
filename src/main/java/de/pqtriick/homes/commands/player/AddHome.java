package de.pqtriick.homes.commands.player;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.files.Config;
import de.pqtriick.homes.files.Messages;
import de.pqtriick.homes.files.homes.ConfigValues;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

/**
 * @author pqtriick_
 * @created 14:04, 07.10.2023
 */

public class AddHome implements CommandExecutor {

    private static File file;
    private static String PREFIX = Messages.msgconfig.getString("messages.prefix");
    private static String HOMEEXISTS = Messages.msgconfig.getString("messages.homeexists");
    private static String ADDSUCESS = Messages.msgconfig.getString("messages.addsucess");
    private static String ADDINFO = Messages.msgconfig.getString("messages.addinfo");
    public static String NOPERM = Messages.msgconfig.getString("messages.nopermission");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        PREFIX = PREFIX.replace("&", "§");
        HOMEEXISTS = HOMEEXISTS.replace("&", "§");
        ADDINFO = ADDINFO.replace("&", "§");
        NOPERM = NOPERM.replace("&", "§");
        Player p = (Player) sender;
        if (args.length==1) {
            if (p.hasPermission("homes.create")) {
                file = new File(Homes.getInstance().getDataFolder().getPath(), p.getUniqueId() + ".yml");
                if (Config.userfileExists(file)) {
                    if (Config.getConfiguration(file).get("homes." + args[0]) != null) {
                        p.sendMessage(PREFIX + HOMEEXISTS);
                    } else {
                        ConfigValues.saveLocation(args[0], p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), file);
                        ADDSUCESS = PREFIX.replace("&", "§");
                        ADDSUCESS = PREFIX.replace("%homename%", args[0]);
                        p.sendMessage(PREFIX + ADDSUCESS);
                        p.sendMessage(PREFIX + ADDINFO);
                    }
                }
            } else {
                p.sendMessage(NOPERM);
            }
        } else {
            p.sendMessage(PREFIX + "§3/addhome [name]");
        }
        return false;
    }
}
