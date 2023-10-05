package de.pqtriick.homes.commands;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.files.homes.ConfigValues;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

/**
 * @author pqtriick_
 * @created 19:19, 05.10.2023
 */

public class testin implements CommandExecutor {

    private File playerfile;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length==0) {
            Player p = (Player) sender;
            playerfile = new File(Homes.getInstance().getDataFolder().getPath(), p.getUniqueId() + ".yml");
            ConfigValues.saveLocation("Home1", 2.0, 5, 10, playerfile);

        }
        return false;
    }
}