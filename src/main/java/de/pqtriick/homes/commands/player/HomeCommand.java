package de.pqtriick.homes.commands.player;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.data.configs.MessageEnum;
import de.pqtriick.homes.data.configs.PermissionsConfigEnum;
import de.pqtriick.homes.data.homes.HomeObject;
import de.pqtriick.homes.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.List;

public class HomeCommand implements CommandExecutor {

    public static HashMap<Player, Inventory> firstSiteInv = new HashMap<>();
    public static HashMap<Player, Inventory> secondSiteInv = new HashMap<>();

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
        Component message = Homes.getInstance().getMessageConfig().getMSG(MessageEnum.HOMES_GUI_TITLE.getPath());
        message = message.replaceText(TextReplacementConfig.builder().matchLiteral("%homes%").replacement(Component.text(homes.size())).build());
        message = message.replaceText(TextReplacementConfig.builder().matchLiteral("%maxhomes%").replacement(Component.text(Homes.getInstance().getHomeManager().getMaxHomes(player))).build());
        Inventory inventory = Bukkit.createInventory(null, 5 * 9, message);
        Inventory secondInventory = Bukkit.createInventory(null, 5 * 9, message);
        for (int i = 0; i < homes.size(); i++) {
            if (i < 44) {
                inventory.setItem(i, new ItemBuilder(Material.getMaterial(Homes.getInstance().getOptionsConfig().getOptionsConfig().getString("options.homes.block").toUpperCase())).name(Component.text(homes.get(i).getName())).lore(
                        List.of(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.HOMES_GUI_ACCESS.getPath()), Homes.getInstance().getMessageConfig().getMSG(MessageEnum.HOMES_GUI_DELETE.getPath()))).build());
            } else if (i == 44) {
                inventory.setItem(44, new ItemBuilder(Material.LIME_STAINED_GLASS).name(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.HOMES_GUI_NEXT_SITE.getPath())).build());
            } else {
                secondInventory.setItem(i-45, new ItemBuilder(Material.getMaterial(Homes.getInstance().getOptionsConfig().getOptionsConfig().getString("options.homes.block").toUpperCase())).name(Component.text(homes.get(i).getName())).lore(
                        List.of(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.HOMES_GUI_ACCESS.getPath()), Homes.getInstance().getMessageConfig().getMSG(MessageEnum.HOMES_GUI_DELETE.getPath()))).build());
            }
        }
        secondSiteInv.put(player, inventory);
        firstSiteInv.put(player, inventory);
        player.openInventory(inventory);
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 0);
        return false;
    }
}
