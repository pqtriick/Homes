package de.pqtriick.homes.listener.inventory;

import de.pqtriick.homes.commands.player.HomeCommand;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;


public class HomeInventoryClick implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null || event.getCurrentItem() == null) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTopInventory().equals(HomeCommand.inventory)) {
            event.setCancelled(true);
            if (event.getClick() == ClickType.RIGHT) {
                DeleteInventoryClick.deleteMap.put(player, PlainTextComponentSerializer.plainText().serialize(event.getCurrentItem().getItemMeta().displayName()));
                DeleteInventoryClick.openDeleteInventory(player);
            } else if (event.getClick() == ClickType.LEFT) {
                ActionInventoryClick.currentSelection.put(player, PlainTextComponentSerializer.plainText().serialize(event.getCurrentItem().getItemMeta().displayName()));
                ActionInventoryClick.openActionInventory(player);
            }
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0);
        }
    }
}
