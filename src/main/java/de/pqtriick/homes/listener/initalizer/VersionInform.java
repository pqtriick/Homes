package de.pqtriick.homes.listener.initalizer;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.files.Messages;
import de.pqtriick.homes.files.Permissions;
import de.pqtriick.homes.utils.Update.VersionCheck;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author pqtriick_
 * @created 17:25, 15.10.2023
 */

public class VersionInform implements Listener {

    private static String PREFIX = Messages.msgconfig.getString("messages.prefix");

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        PREFIX = PREFIX.replace("&", "§");
        Player p = event.getPlayer();
        if (p.hasPermission(Objects.requireNonNull(Permissions.permissionsConfig.getString("homes.admin")))) {
            if (Homes.hasUpdate) {
                p.sendMessage(PREFIX + "§aThere is a new Update available for §3Homes §a!");
                p.sendMessage(PREFIX + "§ehttps://www.spigotmc.org/resources/homes.112984/");
            }
        }
    }

}
