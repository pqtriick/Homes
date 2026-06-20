package de.pqtriick.homes.commands.player;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.data.configs.MessageEnum;
import de.pqtriick.homes.data.configs.PermissionsConfigEnum;
import de.pqtriick.homes.data.homes.HomeObject;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;

public class RenameCommand implements CommandExecutor {

    public static HashMap<Player, String> selectedHomeMap = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NonNull @NotNull String[] args) {
        Player player = (Player) sender;
        if (!selectedHomeMap.containsKey(player)) return false;
        if (!Homes.getInstance().getPermissionConfig().hasPermission(player, PermissionsConfigEnum.PERM_HOME_USE)) {
            player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.NO_PERMISSION.getPath()));
            return false;
        }
        if (args.length == 0) {
            player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.RENAME_WRONG_INPUT.getPath()));
            player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.USAGE_RENAMEHOME.getPath()));
            return false;
        }
        String name = args[0];
        if (name.equalsIgnoreCase("cancel")) {
            selectedHomeMap.remove(player);
            player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.RENAME_CANCELLED.getPath()));
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
            return false;
        }
        Homes.getInstance().getHomeManager().renameHome(player, selectedHomeMap.get(player), name);
        player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.RENAME_SUCCESS.getPath()));
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
        return false;
    }
}
