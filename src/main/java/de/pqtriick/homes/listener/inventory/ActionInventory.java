package de.pqtriick.homes.listener.inventory;

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
    private ItemStack navigator;
    private static String TELEPORT = Messages.msgconfig.getString("messages.teleport");
    private static String PREFIX = Messages.msgconfig.getString("messages.prefix");
    private static String COMPASSDESC = Messages.msgconfig.getString("messages.compassdesc");
    private static String NOPERM = Messages.msgconfig.getString("messages.nopermission");

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        playerdata = new File(Homes.getInstance().getDataFolder().getPath(), p.getUniqueId() + ".yml");
        TELEPORT = TELEPORT.replace("&", "§");
        PREFIX = PREFIX.replace("&", "§");
        NOPERM = NOPERM.replace("&", "§");
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
                    p.sendMessage(PREFIX + TELEPORT);
                    p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 2);
                    p.closeInventory();
                    MainInventoryClick.homeselection.remove(p);
                } else {
                    p.sendMessage(NOPERM);
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
                        COMPASSDESC = COMPASSDESC.replace("&", "§");
                        COMPASSDESC = COMPASSDESC.replace("%homename%", MainInventoryClick.homeselection.get(p));
                        navigator = new ItemBuilder(Material.COMPASS).setName("§cNavigator").setLore(COMPASSDESC).build();
                        p.getInventory().addItem(navigator);
                        p.closeInventory();
                        p.setCompassTarget(newloc);
                        NavigationScheduler.navigation.put(p, newloc);
                    } else {
                        p.closeInventory();
                        p.sendMessage(PREFIX + " §cYou have to be in the same world, to navigate to the location!");
                    }
                } else {
                    p.sendMessage(NOPERM);
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