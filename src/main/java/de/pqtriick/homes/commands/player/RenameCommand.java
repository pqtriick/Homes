package de.pqtriick.homes.commands.player;

import de.pqtriick.homes.data.ConfigurationManager;
import de.pqtriick.homes.data.configs.MessageConfig;
import de.pqtriick.homes.data.configs.PermissionsConfig;
import de.pqtriick.homes.utils.enums.MessageEnum;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class RenameCommand implements CommandExecutor {

    public static HashMap<Player, String> currentHome = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        if (!PermissionsConfig.hasPermission(player, "use")) return false;
        if (currentHome.get(player) != null) {
            if (strings.length != 1) {
                player.sendMessage(MessageConfig.getMSG(MessageEnum.PREFIX.getPath()).append(MessageConfig.getMSG(MessageEnum.RENAME_WRONG_INPUT.getPath())));
            } else {
                String newName = strings[0];
                if (newName.equalsIgnoreCase("cancel")) {
                    currentHome.remove(player);
                    player.sendMessage(MessageConfig.getMSG(MessageEnum.PREFIX.getPath()).append(MessageConfig.getMSG(MessageEnum.RENAME_CANCELLED.getPath())));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                } else if (!ConfigurationManager.isSQLEnabled()) {
                    ConfigurationManager.renameHome(player, currentHome.get(player), newName);
                } else {
                    ConfigurationManager.renameHomeSQL(player, currentHome.get(player), newName);
                }
                player.sendMessage(MessageConfig.getMSG(MessageEnum.PREFIX.getPath()).append(MessageConfig.getMSG(MessageEnum.RENAME_SUCCESS.getPath())));
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
            }
        }
        return false;
    }
}
