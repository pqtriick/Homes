package de.pqtriick.homes.listener.inventory;

import de.pqtriick.homes.commands.admin.CheckHomes;
import de.pqtriick.homes.commands.player.Homecommand;
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

/**
 * @author pqtriick_
 * @created 17:23, 06.10.2023
 */

public class InventoryClickListener implements Listener {

    public static Inventory homedelete;

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (p.getOpenInventory().getTitle().contains("Homes")) {
            event.setCancelled(true);
        }
        if (event.getClickedInventory().getItem(event.getSlot()) != null) {
            if (event.getClick() == ClickType.RIGHT) {
                DeleteHome.selection.put(p, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                homedelete= Bukkit.createInventory(null, 9, "§cDelete Home");
                for (int i = 0; i <= 8; i++) {
                    homedelete.setItem(i, new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE).setName("").build());
                }
                homedelete.setItem(2, SkullBuilder.getCustomSkull(Skulls.GREEN.getTexture(), "§aDelete Home", "§7➥ §eClick to delete"));
                homedelete.setItem(6, SkullBuilder.getCustomSkull(Skulls.RED.getTexture(), "§cCancel", "§7➥ §eClick to cancel"));
                p.openInventory(homedelete);
                System.out.println(DeleteHome.selection.get(p));
            }

        }
    }

    @EventHandler
    public void onInventoryQuit(InventoryCloseEvent event) {
        Player p = (Player) event.getPlayer();
        if (event.getInventory().equals(homedelete)) {
            if (DeleteHome.selection.containsKey(p)) {
                DeleteHome.selection.remove(p);
            }

        }
    }


}
