package de.pqtriick.homes.listener.inventory;

import de.pqtriick.homes.commands.player.HomeCommand;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SecondSiteInventory implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getClickedInventory() == null) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTopInventory().equals(HomeCommand.inventory)) {
            event.setCancelled(true);
            if (event.getCurrentItem().equals(Material.LIME_STAINED_GLASS_PANE)) {
                player.openInventory(HomeCommand.secondSiteInv.get(player));
            }
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0);
        }
    }
}
