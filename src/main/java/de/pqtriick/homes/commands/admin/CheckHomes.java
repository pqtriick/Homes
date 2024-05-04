package de.pqtriick.homes.commands.admin;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.files.Config;
import de.pqtriick.homes.files.Messages;
import de.pqtriick.homes.files.Permissions;
import de.pqtriick.homes.utils.ItemBuilder;
import de.pqtriick.homes.utils.Skull.SkullBuilder;
import de.pqtriick.homes.utils.Skull.Skulls;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author pqtriick_
 * @created 19:59, 19.10.2023
 */

public class CheckHomes implements CommandExecutor {

    private static File playerstorage;
    public static Inventory checkhomes;
    private int invnumber;
    private static String NOPERM = Messages.msgconfig.getString("messages.nopermission");
    private static String PREFIX = Messages.msgconfig.getString("messages.prefix");
    public static HashMap<Player, UUID> invName = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        NOPERM = NOPERM.replace("&", "§");
        PREFIX = PREFIX.replace("&", "§");
        Player player = (Player) sender;
        if (Permissions.permissionsConfig.getString("homes.admin") == null || player.hasPermission(Permissions.permissionsConfig.getString("homes.admin"))) {
            if (args.length == 1) {
                playerstorage = new File(Homes.getInstance().getDataFolder().getPath(), getPlayer(args[0]) + ".yml");
                if (!Config.userfileExists(playerstorage)) {
                    player.sendMessage(PREFIX + "§cThis user is not known by the plugin.");
                    return true;
                }
                if (Config.getConfiguration(playerstorage).get("homes") == null) {
                    player.sendMessage(PREFIX + "§cThis user doesn't have any homes.");
                    return true;
                }
                checkhomes = Bukkit.createInventory(null, 9 * 5, "§b" + args[0] + " §3Homes");
                invnumber = 0;
                for (String homes : Config.getConfiguration(playerstorage).getConfigurationSection("homes").getKeys(false)) {
                    try {
                        checkhomes.setItem(invnumber, SkullBuilder.getCustomSkull(Skulls.HOUSE.getTexture(), "§e" + homes, "§7➥ §aLeftclick to teleport", "§7➥ §cRightclick to insta-delete"));
                    } catch (Exception exe) {
                        checkhomes.setItem(invnumber, new ItemBuilder(Material.CHEST).setName("§e" + homes).setLore("§7➥ §aLeftclick to teleport", "§7➥ §cRightclick to insta-delete").build());
                    }
                    invnumber++;
                }
                player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 2);
                player.openInventory(checkhomes);
                invName.put(player, getPlayer(args[0]));
            } else {
                player.sendMessage(PREFIX + "§7/checkhome [Player]");

            }
        } else {
            player.sendMessage(NOPERM);
        }
        return false;
    }


    public UUID getPlayer(String playername) {
        Player p = Bukkit.getPlayer(playername);
        if (p != null) {
            return p.getUniqueId();
        } else {
            OfflinePlayer player = Bukkit.getOfflinePlayer(playername);
            return player.getUniqueId();
        }
    }
}


