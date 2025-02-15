package de.pqtriick.homes.commands.player;

import de.pqtriick.homes.data.ConfigurationManager;
import de.pqtriick.homes.data.configs.MessageConfig;
import de.pqtriick.homes.data.configs.PermissionsConfig;
import de.pqtriick.homes.data.homes.HomeObject;
import de.pqtriick.homes.database.SQLMethods;
import de.pqtriick.homes.utils.enums.MessageEnum;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AddHomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        if (!PermissionsConfig.hasPermission(player, "create")) return false;
        if (PermissionsConfig.isEnabled()) {
            PermissionsConfig.getHomeAmountForPlayer(player);
        }
        if (ConfigurationManager.hasSpace(player)) {
            HomeObject home = new HomeObject(strings[0], player.getX(), player.getY(), player.getZ(), player.getWorld());
            if (!ConfigurationManager.isSQLEnabled()) {
                ConfigurationManager.saveHome(player, home);
                ConfigurationManager.setHomeAmount(player, ConfigurationManager.getHomeAmount(player) + 1);
            } else {
                SQLMethods.addHome(player, home.getName(), home.getX(), home.getY(), home.getZ(), home.getWorld().getName());
                ConfigurationManager.setHomeAmountSQL(player, ConfigurationManager.getHomeAmountSQL(player) + 1);
            }
        } else {
            player.sendMessage(MessageConfig.getMSG(MessageEnum.PREFIX.getPath()).append(MessageConfig.getMSG(MessageEnum.HOMES_NO_SPACE.getPath())));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1,1);
        }
        return false;
    }
}
