package de.pqtriick.homes.listener.inventory;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.commands.player.Homecommand;
import de.pqtriick.homes.files.Config;
import de.pqtriick.homes.files.Messages;
import de.pqtriick.homes.utils.ItemBuilder;
import de.pqtriick.homes.utils.Skull.SkullBuilder;
import de.pqtriick.homes.utils.Skull.Skulls;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.io.File;

/**
 * @author pqtriick_
 * @created 17:58, 05.01.2024
 */

public class MultipleSiteInventory implements Listener {

    public static Inventory homesiteinv = Bukkit.createInventory(null, 9*5, "§3§lHomes");

    @EventHandler
    public void onSiteSwitch(InventoryClickEvent event) {

        if (event.getClickedInventory() == null) return;
        if (event.getCurrentItem() == null) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory().equals(Homecommand.homeinv) || event.getClickedInventory().equals(homesiteinv)) {
            event.setCancelled(true);
            if (event.getCurrentItem().getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                player.closeInventory();
                player.openInventory(homesiteinv);
                player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 2);
            } else if (event.getCurrentItem().getType().equals(Material.RED_STAINED_GLASS_PANE)) {
                player.closeInventory();
                player.performCommand("homes");
            }
        }
    }

}
