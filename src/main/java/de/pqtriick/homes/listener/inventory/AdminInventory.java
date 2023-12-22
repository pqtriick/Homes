package de.pqtriick.homes.listener.inventory;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.commands.admin.CheckHomes;
import de.pqtriick.homes.files.Config;
import de.pqtriick.homes.files.Messages;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

/**
 * @author pqtriick_
 * @created 20:19, 19.10.2023
 */

public class AdminInventory implements Listener {

    private static File playerdata;
    private double x;
    private double y;
    private double z;
    private World world;
    public static Location newloc;
    private static String TELEPORT = Messages.msgconfig.getString("messages.teleport");
    private static String PREFIX = Messages.msgconfig.getString("messages.prefix");
    private static String HOMEDELETE = Messages.msgconfig.getString("messages.homedeletion");

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        TELEPORT = TELEPORT.replace("&", "§");
        PREFIX = PREFIX.replace("&", "§");
        HOMEDELETE = HOMEDELETE.replace("&", "§");
        Player player = (Player) event.getWhoClicked();

        playerdata = new File(Homes.getInstance().getDataFolder().getPath(), CheckHomes.invName.get(player) + ".yml");
        if (event.getClickedInventory().equals(CheckHomes.checkhomes)) {
            event.setCancelled(true);
            if (event.getCurrentItem() != null) {
                if (event.getClick().equals(ClickType.LEFT)) {
                    String path = "homes." + ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
                    System.out.println(path);
                    System.out.println(playerdata);

                    x = Double.parseDouble(Objects.requireNonNull(Config.getConfiguration(playerdata).getString(path + ".X")));
                    y = Double.parseDouble(Objects.requireNonNull(Config.getConfiguration(playerdata).getString(path + ".Y")));
                    z = Double.parseDouble(Objects.requireNonNull(Config.getConfiguration(playerdata).getString(path + ".Z")));
                    world = Bukkit.getWorld(Objects.requireNonNull(Config.getConfiguration(playerdata).getString(path + ".world")));
                    newloc = new Location(world, x, y, z);
                    player.teleport(newloc);
                    player.sendMessage(PREFIX + TELEPORT);
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 2);
                    player.closeInventory();

                } else if (event.getClick().equals(ClickType.RIGHT)) {
                    String path = "homes." + ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
                    Config.set(Config.getConfiguration(playerdata), playerdata, path, null);
                    player.sendMessage(PREFIX + HOMEDELETE);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                    player.closeInventory();


                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        CheckHomes.invName.remove(event.getPlayer());
    }




    public UUID getPlayer(String playername) {
        Player p = Bukkit.getPlayer(playername);
        if (p != null) {
            return p.getUniqueId();
        } else {
            OfflinePlayer player = Bukkit.getOfflinePlayer(playername);
            return player.getUniqueId();
        }
    }
}
