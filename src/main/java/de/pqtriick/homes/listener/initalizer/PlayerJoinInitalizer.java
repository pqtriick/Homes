package de.pqtriick.homes.listener.initalizer;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.data.configs.MessageEnum;
import de.pqtriick.homes.data.configs.PermissionsConfigEnum;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinInitalizer implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (Homes.getInstance().getPermissionConfig().hasPermission(player, PermissionsConfigEnum.PERM_HOME_ADMIN)) {
            if (Homes.getInstance().isHasUpdate()) {
                player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.PREFIX.getPath()).append(Component.text("§bThere is a new Update Available for Homes!")));
            }
        }
        if (!Homes.getInstance().getConfigManager().userfileExists(Homes.getInstance().getHomeManager().getPlayerFile(player))) {
            Homes.getInstance().getConfigManager().createFile(Homes.getInstance().getHomeManager().getPlayerFile(player));
            FileConfiguration configuration = Homes.getInstance().getConfigManager().getConfiguration(Homes.getInstance().getHomeManager().getPlayerFile(player));
            configuration.set("homeamount", 0);
            Homes.getInstance().getConfigManager().saveFile(configuration, Homes.getInstance().getHomeManager().getPlayerFile(player));
        }
    }
}