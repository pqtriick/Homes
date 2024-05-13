package de.pqtriick.homes.listener.inventory;

import de.pqtriick.homes.files.Messages;
import de.pqtriick.homes.listener.chat.RenameChat;
import de.pqtriick.homes.utils.ItemBuilder;
import de.pqtriick.homes.utils.Skull.SkullBuilder;
import de.pqtriick.homes.utils.Skull.Skulls;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


import java.util.HashMap;

import static de.pqtriick.homes.files.Messages.ACTION_CANCEL_GUI;
import static de.pqtriick.homes.files.Messages.ACTION_CANCEL_LORE_GUI;
import static de.pqtriick.homes.files.Messages.HOME_DELETE_GUI;
import static de.pqtriick.homes.files.Messages.HOME_DELETE_LORE_GUI;
import static de.pqtriick.homes.files.Messages.HOME_NAVIGATION;
import static de.pqtriick.homes.files.Messages.HOME_NAVIGATION_LORE;
import static de.pqtriick.homes.files.Messages.HOME_TELEPORT_GUI;
import static de.pqtriick.homes.files.Messages.HOME_TELEPORT_LORE_GUI;

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
                        homedelete.setItem(2, SkullBuilder.getCustomSkull(Skulls.GREEN.getTexture(), HOME_DELETE_GUI, HOME_DELETE_LORE_GUI));
                        homedelete.setItem(6, SkullBuilder.getCustomSkull(Skulls.RED.getTexture(), ACTION_CANCEL_GUI, ACTION_CANCEL_LORE_GUI));
                    } catch (Exception exe) {
                        homedelete.setItem(2, new ItemBuilder(Material.LIME_CONCRETE).setName(HOME_DELETE_GUI).setLore(HOME_DELETE_LORE_GUI).build());
                        homedelete.setItem(6, new ItemBuilder(Material.RED_CONCRETE).setName(ACTION_CANCEL_GUI).setLore(ACTION_CANCEL_LORE_GUI).build());
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
                        homeactions.setItem(2, SkullBuilder.getCustomSkull(Skulls.TELEPORT.getTexture(), HOME_TELEPORT_GUI, HOME_TELEPORT_LORE_GUI));
                        homeactions.setItem(6, SkullBuilder.getCustomSkull(Skulls.NAVIGATION.getTexture(), HOME_NAVIGATION, HOME_NAVIGATION_LORE));
                    } catch (Exception exe) {
                        homeactions.setItem(2, new ItemBuilder(Material.ENDER_EYE).setName(HOME_TELEPORT_GUI).setLore(HOME_TELEPORT_LORE_GUI).build());
                        homeactions.setItem(6, new ItemBuilder(Material.COMPASS).setName(HOME_NAVIGATION).setLore(HOME_NAVIGATION_LORE).build());
                    }
                    p.openInventory(homeactions);
                    homeselection.put(p, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                }
            } else if (event.getClick() == ClickType.MIDDLE) {
                if (p.getOpenInventory().getTitle().equals("§3§lHomes")) {
                    ItemStack item = event.getCurrentItem();
                    RenameChat.sendRenameMSG(p, item);
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
