package de.pqtriick.homes.commands.player;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.data.configs.MessageEnum;
import de.pqtriick.homes.data.configs.PermissionsConfigEnum;
import de.pqtriick.homes.data.homes.HomeObject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class AddHomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NonNull @NotNull String[] args) {
        Player player = (Player) sender;
        if (!Homes.getInstance().getPermissionConfig().hasPermission(player, PermissionsConfigEnum.PERM_HOME_CREATE)) {
            player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.NO_PERMISSION.getPath()));
            return false;
        }
        if (args.length == 0) {
            player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.USAGE_ADDHOME.getPath()));
            return false;
        }
        if (!Homes.getInstance().getHomeManager().hasSpace(player)) {
            player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.HOMES_NO_SPACE.getPath()));
            return false;
        }
        HomeObject object = new HomeObject(args[0], player.getX(), player.getY(), player.getZ(), player.getWorld());
        Homes.getInstance().getHomeManager().saveHome(player, object);
        return false;
    }
}
