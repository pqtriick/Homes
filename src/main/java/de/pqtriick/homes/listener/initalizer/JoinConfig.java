package de.pqtriick.homes.listener.initalizer;

import de.pqtriick.homes.files.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

/**
 * @author pqtriick_
 * @created 19:58, 04.10.2023
 */

public class JoinConfig implements Listener {
    private static File playerstorage;



    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        playerstorage = new File(Config.directory, player.getUniqueId() + ".yml");
        if (!Config.userfileExists(playerstorage))  {
            Config.createFile(playerstorage);
            Config.setDefaults(Config.getConfiguration(playerstorage), playerstorage, "homes", "");
        }
    }


}
