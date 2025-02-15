package de.pqtriick.homes.listener.inventory;

import de.pqtriick.homes.commands.player.RenameCommand;
import de.pqtriick.homes.data.ConfigurationManager;
import de.pqtriick.homes.data.configs.MessageConfig;
import de.pqtriick.homes.data.configs.PermissionsConfig;
import de.pqtriick.homes.data.homes.HomeObject;
import de.pqtriick.homes.listener.compass.NavigationScheduler;
import de.pqtriick.homes.utils.ItemBuilder;
import de.pqtriick.homes.utils.enums.MessageEnum;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class ActionInventoryClick implements Listener {

    public static HashMap<Player, String> currentSelection = new HashMap<>();
    private static Inventory actionInv;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null || event.getCurrentItem() == null) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTopInventory().equals(actionInv)) {
            event.setCancelled(true);
            switch (event.getSlot()) {
                case 1:
                    teleport(player);
                    break;
                case 4:
                    rename(player);
                    break;
                case 7:
                    navigate(player);
                    break;
            }
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0);
        }

    }

    public static void openActionInventory(Player player) {
        actionInv = Bukkit.createInventory(null, 1*9, MessageConfig.getMSG(MessageEnum.ACTION_GUI_TITLE.getPath()));
        for (int i = 0; i < 8; i++) {
            actionInv.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name(Component.text("")).build());
        }
        actionInv.setItem(1, new ItemBuilder(Material.ENDER_EYE).name(MessageConfig.getMSG(MessageEnum.ACTION_GUI_TELEPORT.getPath())).build());
        actionInv.setItem(4, new ItemBuilder(Material.NAME_TAG).name(MessageConfig.getMSG(MessageEnum.ACTION_GUI_RENAME.getPath())).build());
        actionInv.setItem(7, new ItemBuilder(Material.RECOVERY_COMPASS).name(MessageConfig.getMSG(MessageEnum.ACTION_GUI_NAVIGATE.getPath())).build());
        player.openInventory(actionInv);
    }

    private static void teleport(Player player) {
        HomeObject home;
        if (!PermissionsConfig.hasPermission(player, "teleport")) return;
        if (!ConfigurationManager.isSQLEnabled()) {
            home = ConfigurationManager.getHomeByString(player, currentSelection.get(player));
        } else {
            home = ConfigurationManager.getHomeByStringSQL(player, currentSelection.get(player));
        }
        player.closeInventory();
        player.teleport(new Location(home.getWorld(), home.getX(), home.getY(), home.getZ()));
        player.sendMessage(MessageConfig.getMSG(MessageEnum.PREFIX.getPath()).append(MessageConfig.getMSG(MessageEnum.ACTION_GUI_TELEPORT_SUCCESS.getPath())));
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 2);
        currentSelection.remove(player);
    }

    private static void rename(Player player) {
        RenameCommand.currentHome.put(player, currentSelection.get(player));
        player.sendMessage(MessageConfig.getMSG(MessageEnum.PREFIX.getPath()).append(MessageConfig.getMSG(MessageEnum.ACTION_GUI_RENAME_TEXT_1.getPath())));
        player.sendMessage(MessageConfig.getMSG(MessageEnum.PREFIX.getPath()).append(MessageConfig.getMSG(MessageEnum.ACTION_GUI_RENAME_TEXT_2.getPath())));
        player.closeInventory();
    }

    private static void navigate(Player player) {
        HomeObject obj;
        if (!PermissionsConfig.hasPermission(player, "navigate")) return;
        if (ConfigurationManager.isSQLEnabled()) {
            obj = ConfigurationManager.getHomeByStringSQL(player, currentSelection.get(player));
        } else {
            obj = ConfigurationManager.getHomeByString(player, currentSelection.get(player));
        }
        NavigationScheduler.activeNavigation.put(player, new Location(obj.getWorld(), obj.getX(), obj.getY(), obj.getZ()));
        player.closeInventory();
    }
}
