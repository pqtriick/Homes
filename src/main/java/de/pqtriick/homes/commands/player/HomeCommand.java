package de.pqtriick.homes.commands.player;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.data.configs.MessageEnum;
import de.pqtriick.homes.data.configs.PermissionsConfigEnum;
import de.pqtriick.homes.data.homes.HomeObject;
import de.pqtriick.homes.inventory.HomeSiteInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class HomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NonNull @NotNull String[] args) {
        Player player = (Player) sender;
        if (!Homes.getInstance().getPermissionConfig().hasPermission(player, PermissionsConfigEnum.PERM_HOME_USE)) {
            player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.NO_PERMISSION.getPath()));
            return false;
        }
        if (Homes.getInstance().getHomeManager().getHomeAmount(player) == 0 || Homes.getInstance().getHomeManager().getHomes(player).isEmpty()) {
            player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.HOMES_NO_HOMES_1.getPath()));
            player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.HOMES_NO_HOMES_2.getPath()));
            return false;
        }
        List<HomeObject> homes = Homes.getInstance().getHomeManager().getHomes(player);
        System.out.println(homes.getFirst());
        HomeSiteInventory.openFirstSiteInventory(player, homes);
        return false;
    }
}
