package de.pqtriick.homes.commands.player;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.files.Config;
import de.pqtriick.homes.files.Messages;
import de.pqtriick.homes.files.Permissions;
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
import java.util.Objects;

import static de.pqtriick.homes.listener.inventory.MultipleSiteInventory.homesiteinv;

/**
 * @author pqtriick_
 * @created 15:02, 06.10.2023
 */

public class Homecommand implements CommandExecutor {

    private static File playerstorage;
    public static Inventory homeinv = Bukkit.createInventory(null, 9*5, "§3§lHomes");
    private int invnumber;
    private static String LEFTCLICK = Messages.msgconfig.getString("messages.leftclick");
    private static String RIGHTCLICK = Messages.msgconfig.getString("messages.rightclick");
    private static String NOPERM = Messages.msgconfig.getString("messages.nopermission");
    private static String PREFIX = Messages.msgconfig.getString("messages.prefix");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        LEFTCLICK = LEFTCLICK.replace("&", "§");
        RIGHTCLICK = RIGHTCLICK.replace("&", "§");
        NOPERM = NOPERM.replace("&", "§");
        PREFIX = PREFIX.replace("&", "§");
        Player player = (Player) sender;
        playerstorage = new File(Homes.getInstance().getDataFolder().getPath(), player.getUniqueId() + ".yml");
        if (Permissions.permissionsConfig.getString("homes.use") == null || player.hasPermission(Permissions.permissionsConfig.getString("homes.use"))) {
            invnumber = 0;
            if (Config.getConfiguration(playerstorage).getConfigurationSection("homes") != null) {
                for (String homes : Config.getConfiguration(playerstorage).getConfigurationSection("homes").getKeys(false)) {
                    if (invnumber < 89) {
                        if (invnumber == 44) {
                            homeinv.setItem(44, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setName("§eSite 2").setLore("§7§l>>>").build());
                            invnumber++;
                        } else if (invnumber <=43) {
                            try {
                                homeinv.setItem(invnumber, SkullBuilder.getCustomSkull(Skulls.HOUSE.getTexture(), "§e" + homes, LEFTCLICK, RIGHTCLICK));
                            } catch (Exception exe) {
                                homeinv.setItem(invnumber, new ItemBuilder(Material.CHEST).setName("§e" + homes).setLore(LEFTCLICK).setLore(RIGHTCLICK).build());
                            }
                            invnumber++;
                        } else {
                            if (invnumber >= 44) {
                                if (invnumber == 81) {
                                    invnumber++;
                                } else {
                                    try {
                                        homesiteinv.setItem(invnumber-45, SkullBuilder.getCustomSkull(Skulls.HOUSE.getTexture(), "§e" + homes, LEFTCLICK, RIGHTCLICK));
                                    } catch (Exception exe) {
                                        homesiteinv.setItem(invnumber-45, new ItemBuilder(Material.CHEST).setName("§e" + homes).setLore(LEFTCLICK).setLore(RIGHTCLICK).build());
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
                player.sendMessage(PREFIX + "§cYou don't have any homes!");
            }

        } else {
            player.sendMessage(NOPERM);

        }
        return false;
    }
}
