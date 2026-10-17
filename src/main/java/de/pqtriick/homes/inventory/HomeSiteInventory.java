package de.pqtriick.homes.inventory;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.data.configs.HomeGUIConfigEnum;
import de.pqtriick.homes.data.configs.MessageEnum;
import de.pqtriick.homes.data.homes.HomeObject;
import de.pqtriick.homes.utils.InventoryBuilder;
import de.pqtriick.homes.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class HomeSiteInventory {

    public static void openFirstSiteInventory(Player player, List<HomeObject> homes) {
        Component message = Homes.getInstance().getMessageConfig().getMSG(MessageEnum.HOMES_GUI_TITLE.getPath());
        message = message.replaceText(TextReplacementConfig.builder().matchLiteral("%homes%").replacement(Component.text(homes.size())).build());
        message = message.replaceText(TextReplacementConfig.builder().matchLiteral("%maxhomes%").replacement(Component.text(Homes.getInstance().getHomeManager().getMaxHomes(player))).build());
        InventoryBuilder inventory = new InventoryBuilder().create(Homes.getInstance(), message, 5*9);
        for (int i = 0; i < homes.size(); i++) {
            int finalI = i;
            if (i < 45) {
                inventory.item(i, new ItemBuilder(Material.getMaterial(Homes.getInstance().getOptionsConfig().getOptionsConfig().getString("options.homes.block").toUpperCase())).name(Component.text(homes.get(i).getName())).lore(
                        List.of(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.HOMES_GUI_ACCESS.getPath()), Homes.getInstance().getMessageConfig().getMSG(MessageEnum.HOMES_GUI_DELETE.getPath()))).build(), ((player1, event) -> {
                        if (event.getClick() == ClickType.RIGHT) {
                            openDeleteInventory(player, homes.get(finalI));
                        } else if (event.getClick() == ClickType.LEFT) {
                            openActionInventory(player, homes.get(finalI));
                        }
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0);
                        }));
            } else {
                Component finalMessage = message;
                inventory.item(44, new ItemBuilder(Material.LIME_STAINED_GLASS).name(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.HOMES_GUI_NEXT_SITE.getPath())).build(), ((player1, event) -> {
                    List<HomeObject> leftAmount = new ArrayList<>(homes.subList(44, homes.size()));
                    openSecondSiteInventory(player, leftAmount, finalMessage);
                }));
            }
        }
        inventory.open(player);
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 0);
    }

    private static void openSecondSiteInventory(Player player, List<HomeObject> homes, Component title) {
        InventoryBuilder inventory = new InventoryBuilder().create(Homes.getInstance(), title, 5*9);
        for (int i = 0; i < homes.size(); i++) {
            int finalI = i;
            inventory.item(i, new ItemBuilder(Material.getMaterial(Homes.getInstance().getOptionsConfig().getOptionsConfig().getString("options.homes.block").toUpperCase())).name(Component.text(homes.get(i).getName())).lore(
                    List.of(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.HOMES_GUI_ACCESS.getPath()), Homes.getInstance().getMessageConfig().getMSG(MessageEnum.HOMES_GUI_DELETE.getPath()))).build(), ((player1, event) -> {
                    if (event.getClick() == ClickType.RIGHT) {
                        openDeleteInventory(player, homes.get(finalI));
                    } else if (event.getClick() == ClickType.LEFT) {
                        openActionInventory(player, homes.get(finalI));
                    }
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0);
                }));
        }
        inventory.open(player);
    }

    private static void openActionInventory(Player player, HomeObject home) {
        System.out.println(home);
        Component title = Homes.getInstance().getMessageConfig().getMSG(MessageEnum.ACTION_GUI_TITLE.getPath());
        InventoryBuilder inventory = new InventoryBuilder().create(Homes.getInstance(), title, 9);
        inventory.item(0, getActionInvItem(Homes.getInstance().getGuiConfig().getHomeGUIConfig().getString(HomeGUIConfigEnum.SLOT_1.getPath()).toUpperCase(), Component.text("")));
        inventory.item(1, getActionInvItem(Homes.getInstance().getGuiConfig().getHomeGUIConfig().getString(HomeGUIConfigEnum.SLOT_2.getPath()).toUpperCase(), Homes.getInstance().getMessageConfig().getMSG(MessageEnum.ACTION_GUI_TELEPORT.getPath())), ((player1, event) -> {
            if (event.getClick() == ClickType.LEFT) {
                //Item fliegt sehr wahrscheinlich ins spieler inv wenn man shift rechtsklick machts beim close, damit verhindern wir es
                HomeActionInvHandler.teleport(player, home);
            }
        }));
        inventory.item(2, getActionInvItem(Homes.getInstance().getGuiConfig().getHomeGUIConfig().getString(HomeGUIConfigEnum.SLOT_3.getPath()).toUpperCase(), Component.text("")));
        inventory.item(3, getActionInvItem(Homes.getInstance().getGuiConfig().getHomeGUIConfig().getString(HomeGUIConfigEnum.SLOT_4.getPath()).toUpperCase(), Component.text("")));
        inventory.item(4, getActionInvItem(Homes.getInstance().getGuiConfig().getHomeGUIConfig().getString(HomeGUIConfigEnum.SLOT_5.getPath()).toUpperCase(), Homes.getInstance().getMessageConfig().getMSG(MessageEnum.ACTION_GUI_RENAME.getPath())), ((player1, event) -> {
            if (event.getClick() == ClickType.LEFT) {
                HomeActionInvHandler.rename(player, home);
            }
        }));
        inventory.item(5, getActionInvItem(Homes.getInstance().getGuiConfig().getHomeGUIConfig().getString(HomeGUIConfigEnum.SLOT_6.getPath()).toUpperCase(), Component.text("")));
        inventory.item(6, getActionInvItem(Homes.getInstance().getGuiConfig().getHomeGUIConfig().getString(HomeGUIConfigEnum.SLOT_7.getPath()).toUpperCase(), Component.text("")));
        inventory.item(7, getActionInvItem(Homes.getInstance().getGuiConfig().getHomeGUIConfig().getString(HomeGUIConfigEnum.SLOT_8.getPath()).toUpperCase(), Homes.getInstance().getMessageConfig().getMSG(MessageEnum.ACTION_GUI_NAVIGATE.getPath())), ((player1, event) -> {
            if (event.getClick() == ClickType.LEFT) {
                HomeActionInvHandler.navigate(player, home);
            }
        }));
        inventory.item(8, getActionInvItem(Homes.getInstance().getGuiConfig().getHomeGUIConfig().getString(HomeGUIConfigEnum.SLOT_9.getPath()).toUpperCase(), Component.text("")));
        inventory.open(player);
    }

    private static void openDeleteInventory(Player player, HomeObject home) {
        Component title = Homes.getInstance().getMessageConfig().getMSG(MessageEnum.HOMES_GUI_DELETE_TITLE.getPath());
        InventoryBuilder inventory = new InventoryBuilder().create(Homes.getInstance(), title, 9);
        for (int i = 0; i < inventory.getInventory().getSize(); i++) {
            inventory.item(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name(Component.text("")).build());
        }
        inventory.item(1, new ItemBuilder(Material.GREEN_CONCRETE).name(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.HOMES_GUI_DELETE_CONFIRM.getPath())).build(), ((player1, event) -> {
            if (event.getClick() == ClickType.LEFT) {
                Homes.getInstance().getHomeManager().deleteHome(player, home.getName());
                player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.PREFIX.getPath()).append(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.HOMES_GUI_DELETE_HOME_SUCCESS.getPath())));
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0);
            }
        }));
        inventory.item(7, new ItemBuilder(Material.RED_CONCRETE).name(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.HOMES_GUI_DELETE_CANCEL.getPath())).build(), ((player1, event) -> {
            if (event.getClick() == ClickType.LEFT) {
                player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.PREFIX.getPath()).append(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.HOMES_GUI_DELETE_CANCEL_SUCCESS.getPath())));
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0);
            }
        }));
        inventory.open(player);
    }

    private static ItemStack getActionInvItem(String materialName, Component name) {
        Material material = Material.matchMaterial(materialName);
        if (material == null || material == Material.AIR) {
            return null;
        }
        return new ItemBuilder(material).name(name).build();
    }
}
