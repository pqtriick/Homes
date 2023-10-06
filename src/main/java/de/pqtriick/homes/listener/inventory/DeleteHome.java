package de.pqtriick.homes.listener.inventory;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.files.Config;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.File;
import java.util.HashMap;

/**
 * @author pqtriick_
 * @created 17:25, 06.10.2023
 */

public class DeleteHome implements Listener {

    public static HashMap<Player, String> homedeletion = new HashMap<>();
    private static File playerdata;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        playerdata = new File(Homes.getInstance().getDataFolder().getPath(), p.getUniqueId() + ".yml");

        if (event.getClickedInventory().equals(InventoryClickListener.homedelete)) {
            event.setCancelled(true);
            if (event.getSlot() == 2) {
                String path = "homes." + homedeletion.get(p);
                if (Config.getConfiguration(playerdata).get(path) != null) {
                    Config.getConfiguration(playerdata).set(path, null);
                    Config.saveFile(Config.getConfiguration(playerdata), playerdata);
                } else {
                    System.out.println("NOT EXISTING");
                }
                homedeletion.remove(p);
                p.sendMessage("§3§lHOMES §7| §aSucessfully deleted home.");
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                p.closeInventory();
            } else if (event.getSlot() == 6) {
                p.closeInventory();
                homedeletion.remove(p);
                p.sendMessage("§3§lHOMES §7| §aSucessfully cancelled action.");
            }
        }
    }
}
