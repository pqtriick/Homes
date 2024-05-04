package de.pqtriick.homes.commands.player;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.files.Config;
import de.pqtriick.homes.files.Messages;
import de.pqtriick.homes.files.Options;
import de.pqtriick.homes.files.Permissions;
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
    private static String ADDSUCESS;
    private static String ADDINFO = Messages.msgconfig.getString("messages.addinfo");
    public static String NOPERM = Messages.msgconfig.getString("messages.nopermission");
    public static int maxhomes = Integer.parseInt(Options.optionsconfig.getString("options.homes.maxsize"));
    private static int homeamount = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        PREFIX = PREFIX.replace("&", "§");
        HOMEEXISTS = HOMEEXISTS.replace("&", "§");
        ADDINFO = ADDINFO.replace("&", "§");
        NOPERM = NOPERM.replace("&", "§");
        Player p = (Player) sender;
        if (args.length==1) {
            homeamount = 0;
            if (Permissions.permissionsConfig.getString("homes.create") == null || p.hasPermission(Permissions.permissionsConfig.getString("homes.create"))) {
                file = new File(Homes.getInstance().getDataFolder().getPath(), p.getUniqueId() + ".yml");
                if (Config.userfileExists(file)) {
                    if (Config.getConfiguration(file).get("homes." + args[0]) != null) {
                        p.sendMessage(PREFIX + HOMEEXISTS);
                    } else {
                        if (Config.getConfiguration(file).getConfigurationSection("homes") != null) {
                            for (String homes : Config.getConfiguration(file).getConfigurationSection("homes").getKeys(false)) {
                                homeamount++;
                            }
                        }
                        if (homeamount < maxhomes) {
                            ConfigValues.saveLocation(args[0], p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), p.getWorld(), file);
                            ADDSUCESS = Messages.msgconfig.getString("messages.addsucess");
                            ADDSUCESS = ADDSUCESS.replace("&", "§");
                            ADDSUCESS = ADDSUCESS.replace("%homename%", args[0]);
                            p.sendMessage(PREFIX + ADDSUCESS);
                            p.sendMessage(PREFIX + ADDINFO);
                        } else {
                            p.sendMessage(PREFIX + "§cYou have reached the maximum amount of homes.");
                        }
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
