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

/**
 * @author pqtriick_
 * @created 12:19, 07.10.2023
 */

public class DeleteInventory implements Listener {

    private static File playerdata;
    private static String PREFIX = Messages.msgconfig.getString("messages.prefix");
    private static String ACTIONCANCEL = Messages.msgconfig.getString("messages.actioncancel");
    private static String HOMEDELETE = Messages.msgconfig.getString("messages.homedeletion");

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        playerdata = new File(Homes.getInstance().getDataFolder().getPath(), p.getUniqueId() + ".yml");
        PREFIX = PREFIX.replace("&", "§");
        ACTIONCANCEL = ACTIONCANCEL.replace("&", "§");
        HOMEDELETE = HOMEDELETE.replace("&", "§");

        if (event.getClickedInventory().equals(MainInventoryClick.homedelete)) {
            event.setCancelled(true);
            if (event.getSlot() == 2) {
                String path = "homes." + MainInventoryClick.homeselection.get(p);
                Config.set(Config.getConfiguration(playerdata), playerdata, path, null);
                MainInventoryClick.homeselection.remove(p);
                p.sendMessage(PREFIX +  HOMEDELETE);
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                p.closeInventory();
            } else if (event.getSlot() == 6) {
                p.closeInventory();
                MainInventoryClick.homeselection.remove(p);
                p.sendMessage(PREFIX + ACTIONCANCEL);
            }
        }

    }
}
