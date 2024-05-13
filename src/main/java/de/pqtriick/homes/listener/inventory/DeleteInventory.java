package de.pqtriick.homes.listener.inventory;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.files.Config;
import de.pqtriick.homes.files.Messages;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.File;

import static de.pqtriick.homes.files.Messages.ACTION_CANCEL;
import static de.pqtriick.homes.files.Messages.HOME_DELETE;
import static de.pqtriick.homes.files.Messages.PREFIX;

/**
 * @author pqtriick_
 * @created 12:19, 07.10.2023
 */

public class DeleteInventory implements Listener {

    private static File playerdata;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        Player p = (Player) event.getWhoClicked();
        playerdata = new File(Homes.getInstance().getDataFolder().getPath(), p.getUniqueId() + ".yml");
        if (event.getClickedInventory().equals(MainInventoryClick.homedelete)) {
            event.setCancelled(true);
            if (event.getSlot() == 2) {
                String path = "homes." + MainInventoryClick.homeselection.get(p);
                Config.set(Config.getConfiguration(playerdata), playerdata, path, null);
                MainInventoryClick.homeselection.remove(p);
                p.sendMessage(PREFIX +  HOME_DELETE);
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                p.closeInventory();
            } else if (event.getSlot() == 6) {
                p.closeInventory();
                MainInventoryClick.homeselection.remove(p);
                p.sendMessage(PREFIX + ACTION_CANCEL);
            }
        }

    }
}
