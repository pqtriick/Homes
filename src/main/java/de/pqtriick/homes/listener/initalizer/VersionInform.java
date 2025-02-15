package de.pqtriick.homes.listener.initalizer;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.data.configs.MessageConfig;
import de.pqtriick.homes.data.configs.PermissionsConfig;
import de.pqtriick.homes.utils.enums.MessageEnum;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class VersionInform implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission(PermissionsConfig.permissionsConfig.getString("homes.admin"))) {
            if (Homes.hasUpdate) {
                player.sendMessage(MessageConfig.getMSG(MessageEnum.PREFIX.getPath()).append(Component.text("§bThere is a new Update Available for Homes!")));
            }
        }
    }
}
