package de.pqtriick.homes.listener.inventory;

import de.pqtriick.homes.utils.ItemBuilder;
import de.pqtriick.homes.utils.Skull.SkullBuilder;
import de.pqtriick.homes.utils.Skull.Skulls;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

/**
 * @author pqtriick_
 * @created 12:09, 07.10.2023
 */

public class MainInventoryClick implements Listener {

    public static HashMap<Player, String> homeselection = new HashMap<>();
    public static Inventory homedelete;
    public static Inventory homeactions;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (event.getInventory().equals(homedelete)) {
            event.setCancelled(true);
        }
        if (event.getClickedInventory().getItem(event.getSlot()) != null) {
            if (event.getClick() == ClickType.RIGHT) {
                if (p.getOpenInventory().getTitle().equals("§3§lHomes")) {
                    homedelete = Bukkit.createInventory(null, 9, "§cDelete homes");
                    for (int i = 0; i <= 8; i++) {
                        homedelete.setItem(i, new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE).setName("").build());
                    }
                    homedelete.setItem(2, SkullBuilder.getCustomSkull(Skulls.GREEN.getTexture(), "§aDelete home", "§7➥ §aClick to delete"));
                    homedelete.setItem(6, SkullBuilder.getCustomSkull(Skulls.RED.getTexture(), "§cCancel action", "§7➥ §cClick to cancel"));
                    p.openInventory(homedelete);
                    homeselection.put(p, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                }

            } else if (event.getClick() == ClickType.LEFT) {
                if (p.getOpenInventory().getTitle().equals("§3§lHomes")) {
                    homeactions = Bukkit.createInventory(null, 9, "§bHome actions");
                    for (int i = 0; i <= 8; i++) {
                        homeactions.setItem(i, new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE).setName("").build());
                    }
                    homeactions.setItem(2, SkullBuilder.getCustomSkull(Skulls.TELEPORT.getTexture(), "§dTeleport to home", "§7➥ §dClick to teleport"));
                    homeactions.setItem(6, SkullBuilder.getCustomSkull(Skulls.NAVIGATION.getTexture(), "§6Navigate to home", "§7➥ §6Click to navigate"));
                    p.openInventory(homeactions);
                    homeselection.put(p, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                }
            }
        }
    }



    @EventHandler
    public void canceller(InventoryClickEvent event) {
        if (event.getWhoClicked().getOpenInventory().getTitle().equals("§3§lHomes")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent event) {
        Player p = (Player) event.getPlayer();
        homeselection.remove(p);
    }
}
