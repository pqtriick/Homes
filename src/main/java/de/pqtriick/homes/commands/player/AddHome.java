package de.pqtriick.homes.commands.player;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.files.Config;
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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length==1) {
            if (p.hasPermission("homes.create")) {
                file = new File(Homes.getInstance().getDataFolder().getPath(), p.getUniqueId() + ".yml");
                if (Config.userfileExists(file)) {
                    if (Config.getConfiguration(file).get("homes." + args[0]) != null) {
                        p.sendMessage("§3§lHOMES §7| §cThis home already exists in your list fo homes.");
                    } else {
                        ConfigValues.saveLocation(args[0], p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), file);
                    }
                }
            }
        } else {
            p.sendMessage("§3§lHOMES §7| §3/addhome [name]");
        }
        return false;
    }
}
