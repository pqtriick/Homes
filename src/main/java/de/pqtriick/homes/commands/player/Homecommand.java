package de.pqtriick.homes.commands.player;

import de.cubbossa.tinytranslations.GlobalMessages;
import de.pqtriick.homes.Homes;
import de.pqtriick.homes.files.Config;
import de.pqtriick.homes.files.Messages;
import de.pqtriick.homes.utils.ItemBuilder;
import de.pqtriick.homes.utils.Skull.SkullBuilder;
import de.pqtriick.homes.utils.Skull.Skulls;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.util.Arrays;

import static de.pqtriick.homes.files.Messages.*;
import static de.pqtriick.homes.listener.inventory.MultipleSiteInventory.homesiteinv;

/**
 * @author pqtriick_
 * @created 15:02, 06.10.2023
 */

public class Homecommand implements CommandExecutor {

    private static File playerstorage;
    public static Inventory homeinv;
    private int invnumber;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        homeinv = Bukkit.createInventory(null, 9*5, "§3§lHomes");
        playerstorage = new File(Homes.getInstance().getDataFolder().getPath(), player.getUniqueId() + ".yml");
        if (player.hasPermission("homes.use")) {
            invnumber = 0;
            if (Config.getConfiguration(playerstorage).getConfigurationSection("homes") != null) {
                for (String homes : Config.getConfiguration(playerstorage).getConfigurationSection("homes").getKeys(false)) {
                    if (invnumber < 89) {
                        if (invnumber == 44) {
                            homeinv.setItem(44, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setName("§eSite 2").setLore("§7§l>>>").build());
                            invnumber++;
                        } else if (invnumber <=43) {
                            try {
                                homeinv.setItem(invnumber, SkullBuilder.getCustomSkull(Skulls.HOUSE.getTexture(), HOME_NAME.insertString("home", homes), HOME_LORE));
                            } catch (Exception exe) {
                                homeinv.setItem(invnumber, new ItemBuilder(Material.CHEST).setName(HOME_NAME.insertString("home", homes)).setLore(HOME_LORE).build());
                            }
                            invnumber++;
                        } else {
                            if (invnumber >= 44) {
                                if (invnumber == 81) {
                                    invnumber++;
                                } else {
                                    try {
                                        homesiteinv.setItem(invnumber-45, SkullBuilder.getCustomSkull(Skulls.HOUSE.getTexture(), HOME_NAME.insertString("home", homes), HOME_LORE));
                                    } catch (Exception exe) {
                                        homesiteinv.setItem(invnumber-45, new ItemBuilder(Material.CHEST)
                                                .setName(HOME_NAME.insertString("home", homes)).setLore(HOME_LORE).build());
                                    }
                                    invnumber++;
                                }
                                if (!homesiteinv.contains(Material.RED_STAINED_GLASS_PANE) && homesiteinv.getItem(81-45) == null) {
                                    homesiteinv.setItem(81-45, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName("§eSite 1").setLore("§7§l<<<").build());
                                }
                            }
                        }
                    }
                }
                player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 2);
                player.openInventory(homeinv);
            } else {
                send(player, NO_HOMES);
            }
        } else {
            send(player, GlobalMessages.NO_PERM_CMD);
        }
        return false;
    }
}
