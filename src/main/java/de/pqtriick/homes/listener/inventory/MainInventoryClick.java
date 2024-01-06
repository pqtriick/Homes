package de.pqtriick.homes.listener.inventory;

import de.cubbossa.tinytranslations.MessageFormat;
import de.pqtriick.homes.files.Messages;
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
import org.bukkit.inventory.ItemStack;


import java.util.HashMap;

import static de.pqtriick.homes.files.Messages.*;

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
        if (event.getClickedInventory() == null) return;
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
                    try {
                        homedelete.setItem(2, SkullBuilder.getCustomSkull(Skulls.GREEN.getTexture(), GUI_DELHOME, GUI_DELHOMELORE));
                        homedelete.setItem(6, SkullBuilder.getCustomSkull(Skulls.RED.getTexture(), GUI_CANCELACTION, GUI_CANCELACTIONLORE));
                    } catch (Exception exe) {
                        homedelete.setItem(2, new ItemBuilder(Material.LIME_CONCRETE)
                                .setName(GUI_DELHOME).setLore(GUI_DELHOMELORE).build());
                        homedelete.setItem(6, new ItemBuilder(Material.RED_CONCRETE)
                                .setName(GUI_CANCELACTION).setLore(GUI_CANCELACTIONLORE).build());
                    }
                    p.openInventory(homedelete);
                    homeselection.put(p, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                }

            } else if (event.getClick() == ClickType.LEFT) {
                if (p.getOpenInventory().getTitle().equals("§3§lHomes")) {
                    homeactions = Bukkit.createInventory(null, 9, "§bHome actions");
                    for (int i = 0; i <= 8; i++) {
                        homeactions.setItem(i, new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE).setName("").build());
                    }
                    try {
                        homeactions.setItem(2, SkullBuilder.getCustomSkull(Skulls.TELEPORT.getTexture(), GUI_TPHOME, GUI_TPHOMELORE));
                        homeactions.setItem(6, SkullBuilder.getCustomSkull(Skulls.NAVIGATION.getTexture(), GUI_NAVIGATION, GUI_NAVIGATIONLORE));
                    } catch (Exception exe) {
                        homeactions.setItem(2, new ItemBuilder(Material.ENDER_EYE).setName(GUI_TPHOME).setLore(GUI_TPHOMELORE).build());
                        homeactions.setItem(6, new ItemBuilder(Material.COMPASS).setName(GUI_NAVIGATION).setLore(GUI_NAVIGATIONLORE).build());
                    }
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
