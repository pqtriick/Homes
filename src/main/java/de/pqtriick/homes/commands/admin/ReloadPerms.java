package de.pqtriick.homes.commands.admin;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.data.configs.MessageEnum;
import de.pqtriick.homes.data.configs.PermissionsConfig;
import de.pqtriick.homes.data.configs.PermissionsConfigEnum;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class ReloadPerms implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NonNull @NotNull String[] args) {
        Player player = (Player) sender;
        if (!Homes.getInstance().getPermissionConfig().hasPermission(player, PermissionsConfigEnum.PERM_HOME_ADMIN)) {
            player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.NO_PERMISSION.getPath()));
            return false;
        }
        try {
            Homes.getInstance().setPermissionConfig(new PermissionsConfig());
            player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.RELOAD_PERMS_SUCCESS.getPath()));
        } catch (Exception e) {
            player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.RELOAD_PERMS_ERROR.getPath()));
        }
        return false;
    }
}
