package de.pqtriick.homes.commands.admin;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.data.configs.MessageEnum;
import de.pqtriick.homes.data.configs.PermissionsConfigEnum;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class AddHomePerm implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NonNull @NotNull String[] args) {
        Player player = (Player) sender;
        if (!Homes.getInstance().getPermissionConfig().hasPermission(player, PermissionsConfigEnum.PERM_HOME_ADMIN)) {
            player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.NO_PERMISSION.getPath()));
            return false;
        }
        if (args.length == 0) {
            player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.ADD_HOME_USAGE.getPath()));
            return false;
        }
        try {
            Homes.getInstance().getPermissionConfig().addNewPerm(args[0], Integer.parseInt(args[1]));
        } catch (Exception e) {
            player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.ADD_HOME_WRONG_INPUT.getPath()));
            player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.ADD_HOME_USAGE.getPath()));
        }
        return false;
    }
}
