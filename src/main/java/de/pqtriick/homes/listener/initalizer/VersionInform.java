package de.pqtriick.homes.listener.initalizer;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.files.Messages;
import de.pqtriick.homes.utils.Update.VersionCheck;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author pqtriick_
 * @created 17:25, 15.10.2023
 */

public class VersionInform implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        if (p.hasPermission("homes.admin")) {
            if (Homes.hasUpdate) {
                Messages.send(p, Messages.UPDATE_AVAILABLE);
            }
        }
    }
}
