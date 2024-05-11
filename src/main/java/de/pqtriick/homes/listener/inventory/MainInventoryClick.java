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

/**
 * @author pqtriick_
 * @created 12:09, 07.10.2023
 */

public class MainInventoryClick implements Listener {

    public static HashMap<Player, String> homeselection = new HashMap<>();
    public static Inventory homedelete;
    public static Inventory homeactions;
    private static String GUIDELHOME = Messages.msgconfig.getString("messages.guidelhome");
    private static String GUIDELHOMELORE = Messages.msgconfig.getString("messages.guidelhomelore");
    private static String GUICANCELACTION = Messages.msgconfig.getString("messages.guicancelaction");
    private static String GUICANCELACTIONLORE = Messages.msgconfig.getString("messages.guicancelactionlore");
    private static String GUITPHOME = Messages.msgconfig.getString("messages.guitphome");
    private static String GUITPHOMELORE = Messages.msgconfig.getString("messages.guitphomelore");
    private static String GUINAVIGATION = Messages.msgconfig.getString("messages.guinavigation");
    private static String GUINAVIGATIONLORE = Messages.msgconfig.getString("messages.guinavigationlore");

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        GUIDELHOME = GUIDELHOME.replace("&", "§");
        GUIDELHOMELORE = GUIDELHOMELORE.replace("&", "§");
        GUICANCELACTION = GUICANCELACTION.replace("&", "§");
        GUICANCELACTIONLORE = GUICANCELACTIONLORE.replace("&", "§");
        GUITPHOME = GUITPHOME.replace("&", "§");
        GUITPHOMELORE = GUITPHOMELORE.replace("&", "§");
        GUINAVIGATION = GUINAVIGATION.replace("&", "§");
        GUINAVIGATIONLORE = GUINAVIGATIONLORE.replace("&", "§");
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
                        homedelete.setItem(2, SkullBuilder.getCustomSkull(Skulls.GREEN.getTexture(), GUIDELHOME, GUIDELHOMELORE));
                        homedelete.setItem(6, SkullBuilder.getCustomSkull(Skulls.RED.getTexture(), GUICANCELACTION, GUICANCELACTIONLORE));
                    } catch (Exception exe) {
                        homedelete.setItem(2, new ItemBuilder(Material.LIME_CONCRETE).setName(GUIDELHOME).setLore(GUIDELHOMELORE).build());
                        homedelete.setItem(6, new ItemBuilder(Material.RED_CONCRETE).setName(GUICANCELACTION).setLore(GUICANCELACTIONLORE).build());
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
                        homeactions.setItem(2, SkullBuilder.getCustomSkull(Skulls.TELEPORT.getTexture(), GUITPHOME, GUITPHOMELORE));
                        homeactions.setItem(6, SkullBuilder.getCustomSkull(Skulls.NAVIGATION.getTexture(), GUINAVIGATION, GUINAVIGATIONLORE));
                    } catch (Exception exe) {
                        homeactions.setItem(2, new ItemBuilder(Material.ENDER_EYE).setName(GUITPHOME).setLore(GUITPHOMELORE).build());
                        homeactions.setItem(6, new ItemBuilder(Material.COMPASS).setName(GUINAVIGATION).setLore(GUINAVIGATIONLORE).build());
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
