package de.pqtriick.homes.commands.player;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.data.Config;
import de.pqtriick.homes.data.ConfigurationManager;
import de.pqtriick.homes.data.configs.MessageConfig;
import de.pqtriick.homes.data.configs.OptionsConfig;
import de.pqtriick.homes.data.configs.PermissionsConfig;
import de.pqtriick.homes.utils.ItemBuilder;
import de.pqtriick.homes.utils.enums.MessageEnum;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class HomeCommand implements CommandExecutor {

    public static Inventory inventory;
    public static HashMap<Player, Inventory> secondSiteInv = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        if (!PermissionsConfig.hasPermission(player, "use")) return false;
        List<String> homes = null;
        if ((ConfigurationManager.getHomes(player).isEmpty()|| ConfigurationManager.getHomeAmount(player) == 0) || ConfigurationManager.getHomesSQL(player).isEmpty() || ConfigurationManager.getHomeAmountSQL(player) == 0) {
            player.sendMessage(MessageConfig.getMSG(MessageEnum.PREFIX.getPath()).append(MessageConfig.getMSG(MessageEnum.HOMES_NO_HOMES_1.getPath())));
            player.sendMessage(MessageConfig.getMSG(MessageEnum.PREFIX.getPath()).append(MessageConfig.getMSG(MessageEnum.HOMES_NO_HOMES_2.getPath())));
        } else {
            if (!ConfigurationManager.isSQLEnabled()) {
                homes = ConfigurationManager.getHomes(player);
            } else {
                homes = ConfigurationManager.getHomesSQL(player);
            }
            if (!homes.isEmpty()) {
                Component message = MessageConfig.getMSG(MessageEnum.HOMES_GUI_TITLE.getPath());
                message = message.replaceText(TextReplacementConfig.builder().matchLiteral("%homes%").replacement(Component.text(homes.size())).build());
                message = message.replaceText(TextReplacementConfig.builder().matchLiteral("%maxhomes%").replacement(Component.text(ConfigurationManager.getMaxHomes(player))).build());
                inventory = Bukkit.createInventory(null, 5 * 9, message);
                Inventory secondInventory = Bukkit.createInventory(null, 5 * 9, message);
                for (int i = 0; i < homes.size(); i++) {
                    if (i < 43) {
                        inventory.setItem(i, new ItemBuilder(Material.getMaterial(OptionsConfig.optionsConfig.getString("options.homes.block").toUpperCase())).name(Component.text(homes.get(i))).lore(
                                List.of(MessageConfig.getMSG(MessageEnum.HOMES_GUI_ACCESS.getPath()), MessageConfig.getMSG(MessageEnum.HOMES_GUI_DELETE.getPath()))).build());
                    } else if (i == 44) {
                        inventory.setItem(44, new ItemBuilder(Material.LIME_STAINED_GLASS).name(MessageConfig.getMSG(MessageEnum.HOMES_GUI_NEXT_SITE.getPath())).build());
                    } else {
                        secondInventory.setItem(i - 44, new ItemBuilder(Material.getMaterial(OptionsConfig.optionsConfig.getString("options.homes.block").toUpperCase())).name(Component.text(homes.get(i))).build());
                    }
                    if (!secondInventory.isEmpty()) secondSiteInv.put(player, secondInventory);
                }
                player.openInventory(inventory);
                player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 0);
            }
        }
        return false;
    }
}
