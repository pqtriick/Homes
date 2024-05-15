package de.pqtriick.homes.commands.admin;

import de.pqtriick.homes.commands.player.AddHome;
import de.pqtriick.homes.files.Messages;
import de.pqtriick.homes.files.Options;
import de.pqtriick.homes.files.Permissions;
import de.pqtriick.homes.listener.compass.NavigationScheduler;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

import static de.pqtriick.homes.files.Messages.NOPERM;
import static de.pqtriick.homes.files.Messages.PREFIX;

/**
 * @author pqtriick_
 * @created 17:07, 31.10.2023
 */

public class ReloadValues implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        if (Permissions.permissionsConfig.getString("homes.admin") == null || player.hasPermission(Permissions.permissionsConfig.getString("homes.admin"))) {
            if (args.length==0) {
                try {
                    NavigationScheduler.enabled = Options.optionsconfig.getString("options.particle.enabled");
                    NavigationScheduler.particle = Particle.valueOf(Options.optionsconfig.getString("options.particle.particle"));
                    NavigationScheduler.delay = Options.optionsconfig.getString("options.particle.delay");
                    player.sendMessage(PREFIX + "§aSucessfully reloaded values!");
                } catch (Exception e) {
                    player.sendMessage(PREFIX + "§cCouldn't reload values.");
                    player.sendMessage("§c" + e);
                }

            }
        } else {
            player.sendMessage(NOPERM);
        }
        return false;
    }
}
