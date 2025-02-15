package de.pqtriick.homes.commands.admin;

import de.pqtriick.homes.data.configs.MessageConfig;
import de.pqtriick.homes.data.configs.PermissionsConfig;
import de.pqtriick.homes.utils.enums.MessageEnum;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AddHomePerm implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        if (!PermissionsConfig.hasPermission(player, "admin")) return false;
        if (strings.length == 0) {
            player.sendMessage(MessageConfig.getMSG(MessageEnum.ADD_HOME_USAGE.getPath()));
        } else {
            try {
                PermissionsConfig.addNewPerm(strings[0], Integer.parseInt(strings[1]));
            } catch (Exception e) {
                player.sendMessage(MessageConfig.getMSG(MessageEnum.PREFIX.getPath()).append(MessageConfig.getMSG(MessageEnum.ADD_HOME_WRONG_INPUT.getPath())));
                player.sendMessage(MessageConfig.getMSG(MessageEnum.PREFIX.getPath()).append(MessageConfig.getMSG(MessageEnum.ADD_HOME_USAGE.getPath())));
            }
        }
        return false;
    }
}
