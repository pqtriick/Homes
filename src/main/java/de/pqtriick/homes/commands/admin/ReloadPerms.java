package de.pqtriick.homes.commands.admin;

import de.pqtriick.homes.data.configs.PermissionsConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReloadPerms implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        if (!PermissionsConfig.hasPermission(player, "admin")) return false;
        if (strings.length == 0) {
            PermissionsConfig.permissionsConfig = YamlConfiguration.loadConfiguration(PermissionsConfig.permissionFile);
        }
        return false;
    }
}
