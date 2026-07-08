package de.pqtriick.homes.listener.inventory;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.commands.player.RenameCommand;
import de.pqtriick.homes.data.configs.HomeGUIConfigEnum;
import de.pqtriick.homes.data.configs.MessageEnum;
import de.pqtriick.homes.data.configs.OptionsConfig;
import de.pqtriick.homes.data.configs.OptionsConfigEnum;
import de.pqtriick.homes.data.configs.PermissionsConfigEnum;
import de.pqtriick.homes.data.homes.HomeObject;
import de.pqtriick.homes.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class ActionInventoryClick implements Listener {

    public static HashMap<Player, String> currentSelection = new HashMap<>();
    private static HashMap<Player, BukkitTask> teleportDelayMap = new HashMap<>();
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

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (Homes.getInstance().getOptionsConfig().getOptionsConfig().get(OptionsConfigEnum.OPTIONS_TPDELAY_CANCELONMOVE.getPath()).equals("false")) return;
        Player player = event.getPlayer();
        if (!teleportDelayMap.containsKey(player)) return;
        teleportDelayMap.get(player).cancel();
        teleportDelayMap.remove(player);
        player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.TELPORT_ACTIONBAR_CANCEL.getPath()));
    }

    public static void openActionInventory(Player player) {
        actionInv = Bukkit.createInventory(null, 1*9, Homes.getInstance().getMessageConfig().getMSG(MessageEnum.ACTION_GUI_TITLE.getPath()));
        setGuiItem(actionInv, 0, Homes.getInstance().getGuiConfig().getHomeGUIConfig().getString(HomeGUIConfigEnum.SLOT_1.getPath()).toUpperCase(), Component.text(""));
        setGuiItem(actionInv, 1, Homes.getInstance().getGuiConfig().getHomeGUIConfig().getString(HomeGUIConfigEnum.SLOT_2.getPath()).toUpperCase(), Homes.getInstance().getMessageConfig().getMSG(MessageEnum.ACTION_GUI_TELEPORT.getPath()));
        setGuiItem(actionInv, 2, Homes.getInstance().getGuiConfig().getHomeGUIConfig().getString(HomeGUIConfigEnum.SLOT_3.getPath()).toUpperCase(), Component.text(""));
        setGuiItem(actionInv, 3, Homes.getInstance().getGuiConfig().getHomeGUIConfig().getString(HomeGUIConfigEnum.SLOT_4.getPath()).toUpperCase(), Component.text(""));
        setGuiItem(actionInv, 4, Homes.getInstance().getGuiConfig().getHomeGUIConfig().getString(HomeGUIConfigEnum.SLOT_5.getPath()).toUpperCase(), Homes.getInstance().getMessageConfig().getMSG(MessageEnum.ACTION_GUI_RENAME.getPath()));
        setGuiItem(actionInv, 5, Homes.getInstance().getGuiConfig().getHomeGUIConfig().getString(HomeGUIConfigEnum.SLOT_6.getPath()).toUpperCase(), Component.text(""));
        setGuiItem(actionInv, 6, Homes.getInstance().getGuiConfig().getHomeGUIConfig().getString(HomeGUIConfigEnum.SLOT_7.getPath()).toUpperCase(), Component.text(""));
        setGuiItem(actionInv, 7, Homes.getInstance().getGuiConfig().getHomeGUIConfig().getString(HomeGUIConfigEnum.SLOT_8.getPath()).toUpperCase(), Homes.getInstance().getMessageConfig().getMSG(MessageEnum.ACTION_GUI_NAVIGATE.getPath()));
        setGuiItem(actionInv, 8, Homes.getInstance().getGuiConfig().getHomeGUIConfig().getString(HomeGUIConfigEnum.SLOT_9.getPath()).toUpperCase(), Component.text(""));
        player.openInventory(actionInv);
    }

    private static void teleport(Player player) {
        HomeObject home;
        if (!Homes.getInstance().getPermissionConfig().hasPermission(player, PermissionsConfigEnum.PERM_HOME_TELEPORT)) {
            player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.PREFIX.getPath()).append(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.NO_PERMISSION.getPath())));
            return;
        }
        home = Homes.getInstance().getHomeManager().getHomeByString(player, currentSelection.get(player));
        player.closeInventory();
        if (Homes.getInstance().getOptionsConfig().getOptionsConfig().get(OptionsConfigEnum.OPTIONS_TPDELAY_ENABLED.getPath()).equals("true")) {
            try {
                int delayInSec = Integer.parseInt(Homes.getInstance().getOptionsConfig().getOptionsConfig().getString(OptionsConfigEnum.OPTIONS_TPDELAY_DELAY.getPath()));
                if (teleportDelayMap.containsKey(player)) {
                    teleportDelayMap.get(player).cancel();
                }
                BukkitTask tpRunnable = new BukkitRunnable() {
                    int times = delayInSec;
                    @Override
                    public void run() {
                        if (times == 0) {
                            player.teleport(new Location(home.getWorld(), home.getX(), home.getY(), home.getZ()));
                            player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.PREFIX.getPath()).append(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.ACTION_GUI_TELEPORT_SUCCESS.getPath())));
                            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 2);
                            currentSelection.remove(player);
                            this.cancel();
                            teleportDelayMap.remove(player);
                        } else {
                            Component message = Homes.getInstance().getMessageConfig().getMSG(MessageEnum.TELEPORT_ACTIONBAR.getPath());
                            message = message.replaceText(TextReplacementConfig.builder().matchLiteral("%seconds%").replacement(Component.text(times)).build());
                            player.sendActionBar(message);
                            times--;
                        }
                    }
                }.runTaskTimer(Homes.getInstance(), 0, 20L);
                teleportDelayMap.put(player, tpRunnable);
                return;
            } catch (Exception e) {
                System.out.println("{HOMES ERROR} Variable delay for teleport is not an Integer!");
            }
        }
        player.teleport(new Location(home.getWorld(), home.getX(), home.getY(), home.getZ()));
        player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.PREFIX.getPath()).append(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.ACTION_GUI_TELEPORT_SUCCESS.getPath())));
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 2);
        currentSelection.remove(player);
    }

    private static void rename(Player player) {
        RenameCommand.selectedHomeMap.put(player, currentSelection.get(player));
        player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.PREFIX.getPath()).append(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.ACTION_GUI_RENAME_TEXT_1.getPath())));
        player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.PREFIX.getPath()).append(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.ACTION_GUI_RENAME_TEXT_2.getPath())));
        player.closeInventory();
    }

    private static void navigate(Player player) {
        HomeObject obj;
        if (!Homes.getInstance().getPermissionConfig().hasPermission(player, PermissionsConfigEnum.PERM_HOME_NAVIGATE)) {
            player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.PREFIX.getPath()).append(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.NO_PERMISSION.getPath())));
            return;
        }
        obj = Homes.getInstance().getHomeManager().getHomeByString(player, currentSelection.get(player));
        Homes.getInstance().getNavigationScheduler().activeNavigation.put(player, new Location(obj.getWorld(), obj.getX(), obj.getY(), obj.getZ()));
        player.closeInventory();
    }

    private static void setGuiItem(Inventory inv, int slot, String materialName, Component name) {
        Material material = Material.matchMaterial(materialName);
        if (material == null || material == Material.AIR) {
            inv.setItem(slot, null);
            return;
        }
        inv.setItem(slot, new ItemBuilder(material).name(name).build());
    }
}