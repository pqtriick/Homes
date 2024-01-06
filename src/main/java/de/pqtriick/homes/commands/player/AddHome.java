package de.pqtriick.homes.commands.player;

import de.cubbossa.tinytranslations.GlobalMessages;
import de.pqtriick.homes.Homes;
import de.pqtriick.homes.files.Config;
import de.pqtriick.homes.files.Messages;
import de.pqtriick.homes.files.Options;
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
    public static int maxhomes = Integer.parseInt(Options.optionsconfig.getString("options.homes.maxsize"));
    private static int homeamount = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length==1) {
            homeamount = 0;
            if (p.hasPermission("homes.create")) {
                file = new File(Homes.getInstance().getDataFolder().getPath(), p.getUniqueId() + ".yml");
                if (Config.userfileExists(file)) {
                    if (Config.getConfiguration(file).get("homes." + args[0]) != null) {
                        Messages.send(p, Messages.HOMEEXISTS);
                    } else {
                        if (Config.getConfiguration(file).getConfigurationSection("homes") != null) {
                            for (String homes : Config.getConfiguration(file).getConfigurationSection("homes").getKeys(false)) {
                                homeamount++;
                            }
                        }
                        if (homeamount < maxhomes) {
                            ConfigValues.saveLocation(args[0], p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), p.getWorld(), file);
                            Messages.send(p, Messages.ADDSUCESS.insertString("homename", args[0]));
                            Messages.send(p, Messages.ADDINFO);
                        } else {
                            Messages.send(p, Messages.MAX_HOMES);
                        }
                    }
                }
            } else {
                Messages.send(p, GlobalMessages.NO_PERM_CMD);
            }
        } else {
            Messages.send(p, Messages.SYNTAX_ADDHOME);
        }
        return false;
    }
}
