package de.pqtriick.homes.listener.inventory;

import de.cubbossa.tinytranslations.GlobalMessages;
import de.pqtriick.homes.Homes;
import de.pqtriick.homes.files.Config;
import de.pqtriick.homes.files.Messages;
import de.pqtriick.homes.listener.compass.NavigationScheduler;
import de.pqtriick.homes.utils.ItemBuilder;
import org.bukkit.*;
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
    private World world;
    public static Location newloc;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        Player p = (Player) event.getWhoClicked();
        playerdata = new File(Homes.getInstance().getDataFolder().getPath(), p.getUniqueId() + ".yml");
        if (event.getClickedInventory().equals(MainInventoryClick.homeactions)) {
            if (event.getSlot() == 2) {
                if (p.hasPermission("homes.teleport")) {
                    String path = "homes." + MainInventoryClick.homeselection.get(p);
                    x = Double.parseDouble(Objects.requireNonNull(Config.getConfiguration(playerdata).getString(path + ".X")));
                    y = Double.parseDouble(Objects.requireNonNull(Config.getConfiguration(playerdata).getString(path + ".Y")));
                    z = Double.parseDouble(Objects.requireNonNull(Config.getConfiguration(playerdata).getString(path + ".Z")));
                    world = Bukkit.getWorld(Config.getConfiguration(playerdata).getString(path + ".world"));
                    newloc = new Location(world, x, y, z);
                    p.teleport(newloc);
                    Messages.send(p, Messages.TELEPORT);
                    p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 2);
                    p.closeInventory();
                    MainInventoryClick.homeselection.remove(p);
                } else {
                    Messages.send(p, GlobalMessages.NO_PERM_CMD);
                }
            } else if(event.getSlot() == 6){
                if (p.hasPermission("homes.navigate")) {
                    String path = "homes." + MainInventoryClick.homeselection.get(p);
                    x = Double.parseDouble(Objects.requireNonNull(Config.getConfiguration(playerdata).getString(path + ".X")));
                    y = Double.parseDouble(Objects.requireNonNull(Config.getConfiguration(playerdata).getString(path + ".Y")));
                    z = Double.parseDouble(Objects.requireNonNull(Config.getConfiguration(playerdata).getString(path + ".Z")));
                    world = Bukkit.getWorld(Objects.requireNonNull(Config.getConfiguration(playerdata).getString(path + ".world")));
                    if (p.getWorld() == world) {
                        newloc = new Location(world, x, y, z);
                        p.closeInventory();
                        NavigationScheduler.navigation.put(p, newloc);
                    } else {
                        p.closeInventory();
                        Messages.send(p, Messages.NAVIGATE_SAME_WORLD);
                    }
                } else {
                    Messages.send(p, GlobalMessages.NO_PERM_CMD);
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
}