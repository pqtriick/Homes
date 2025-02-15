package de.pqtriick.homes.listener.inventory;

import de.pqtriick.homes.data.ConfigurationManager;
import de.pqtriick.homes.data.configs.MessageConfig;
import de.pqtriick.homes.utils.ItemBuilder;
import de.pqtriick.homes.utils.enums.MessageEnum;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class DeleteInventoryClick implements Listener {

    public static HashMap<Player, String> deleteMap = new HashMap<>();
    private static Inventory confirmDeletion;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null || event.getCurrentItem() == null) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTopInventory().equals(confirmDeletion)) {
            event.setCancelled(true);
            if (event.getSlot() == 1) {
                deleteHome(player);
            } else if (event.getSlot() == 7) {
                deleteMap.remove(player);
                player.sendMessage(MessageConfig.getMSG(MessageEnum.PREFIX.getPath()).append(MessageConfig.getMSG(MessageEnum.HOMES_GUI_DELETE_CANCEL_SUCCESS.getPath())));
                player.closeInventory();
            }
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0);
        }
    }

    public static void openDeleteInventory(Player player) {
        confirmDeletion = Bukkit.createInventory(null, 1*9, MessageConfig.getMSG(MessageEnum.HOMES_GUI_DELETE_TITLE.getPath()));
        for (int i = 0; i < 8; i++) {
            confirmDeletion.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name(Component.text("§7")).build());
        }
        confirmDeletion.setItem(1, new ItemBuilder(Material.GREEN_CONCRETE).name(MessageConfig.getMSG(MessageEnum.HOMES_GUI_DELETE_CONFIRM.getPath())).build());
        confirmDeletion.setItem(7, new ItemBuilder(Material.RED_CONCRETE).name(MessageConfig.getMSG(MessageEnum.HOMES_GUI_DELETE_CANCEL.getPath())).build());
        player.openInventory(confirmDeletion);
    }

    private static void deleteHome(Player player) {
        if (!ConfigurationManager.isSQLEnabled()) {
            ConfigurationManager.deleteHome(player, deleteMap.get(player));
        } else {
            ConfigurationManager.deleteHomeSQL(player, deleteMap.get(player));
        }
        player.sendMessage(MessageConfig.getMSG(MessageEnum.PREFIX.getPath()).append(MessageConfig.getMSG(MessageEnum.HOMES_GUI_DELETE_HOME_SUCCESS.getPath())));
        player.closeInventory();
        deleteMap.remove(player);
    }
}
