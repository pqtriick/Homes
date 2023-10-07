package de.pqtriick.homes.listener.inventory;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.files.Config;
import de.pqtriick.homes.listener.compass.NavigationScheduler;
import de.pqtriick.homes.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Objects;

/**
 * @author pqtriick_
 * @created 12:39, 07.10.2023
 */

public class ActionInventory implements Listener {

    private static File playerdata;
    private double x;
    private double y;
    private double z;
    public static Location newloc;
    private ItemStack navigator;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        playerdata = new File(Homes.getInstance().getDataFolder().getPath(), p.getUniqueId() + ".yml");
        if (event.getClickedInventory().equals(MainInventoryClick.homeactions)) {
            if (event.getSlot() == 2) {
                if (p.hasPermission("homes.teleport")) {
                    String path = "homes." + MainInventoryClick.homeselection.get(p);
                    x = Double.parseDouble(Objects.requireNonNull(Config.getConfiguration(playerdata).getString(path + ".X")));
                    y = Double.parseDouble(Objects.requireNonNull(Config.getConfiguration(playerdata).getString(path + ".Y")));
                    z = Double.parseDouble(Objects.requireNonNull(Config.getConfiguration(playerdata).getString(path + ".Z")));
                    newloc = new Location(Bukkit.getWorld("world"), x, y, z);
                    p.teleport(newloc);
                    p.sendMessage("§3§lHOMES §7| §aSucessfully teleported to home!");
                    p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 2);
                    p.closeInventory();
                    MainInventoryClick.homeselection.remove(p);
                }
            } else if(event.getSlot() == 6){
                if (p.hasPermission("homes.navigate")) {
                    String path = "homes." + MainInventoryClick.homeselection.get(p);
                    x = Double.parseDouble(Objects.requireNonNull(Config.getConfiguration(playerdata).getString(path + ".X")));
                    y = Double.parseDouble(Objects.requireNonNull(Config.getConfiguration(playerdata).getString(path + ".Y")));
                    z = Double.parseDouble(Objects.requireNonNull(Config.getConfiguration(playerdata).getString(path + ".Z")));
                    newloc = new Location(Bukkit.getWorld("world"), x, y, z);
                    navigator = new ItemBuilder(Material.COMPASS).setName("§cNavigator").setLore("§7➥ §6Currently navigating to§7: §e" + MainInventoryClick.homeselection.get(p)).build();
                    p.getInventory().addItem(navigator);
                    p.closeInventory();
                    p.setCompassTarget(newloc);
                    NavigationScheduler.navigation.put(p, newloc);
                }

            } else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onCompassDrop(PlayerDropItemEvent event) {
        if (event.getItemDrop().getItemStack().equals(Material.COMPASS)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        NavigationScheduler.navigation.remove(event.getPlayer());
        if (event.getPlayer().getInventory().contains(Material.COMPASS)) {
            event.getPlayer().getInventory().remove(Material.COMPASS);
        }
    }
}