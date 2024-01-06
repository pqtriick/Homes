package de.pqtriick.homes.commands.admin;

import de.cubbossa.tinytranslations.GlobalMessages;
import de.pqtriick.homes.Homes;
import de.pqtriick.homes.commands.player.AddHome;
import de.pqtriick.homes.files.Messages;
import de.pqtriick.homes.files.Options;
import de.pqtriick.homes.listener.compass.NavigationScheduler;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

/**
 * @author pqtriick_
 * @created 17:07, 31.10.2023
 */

public class ReloadValues implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (player.hasPermission("homes.admin")) {
            if (args.length==0) {
                try {
                    Homes.getInstance().reloadLocales();
                    NavigationScheduler.enabled = Options.optionsconfig.getString("options.particle.enabled");
                    NavigationScheduler.particle = Particle.valueOf(Options.optionsconfig.getString("options.particle.particle"));
                    NavigationScheduler.delay = Options.optionsconfig.getString("options.particle.delay");
                    AddHome.maxhomes = Integer.valueOf(Options.optionsconfig.getString("options.homes.maxsize"));
                    Messages.send(player, Messages.RELOAD_SUCCESS);
                } catch (Exception e) {
                    Messages.send(player, Messages.RELOAD_FAIL.insertString("reason", e.toString()));
                }

            }
        } else {
            Messages.send(player, GlobalMessages.NO_PERM_CMD);
        }
        return false;
    }
}
