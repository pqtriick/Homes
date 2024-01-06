package de.pqtriick.homes.commands.admin;

import de.cubbossa.tinytranslations.GlobalMessages;
import de.pqtriick.homes.Homes;
import de.pqtriick.homes.files.Config;
import de.pqtriick.homes.files.Messages;
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
    public static HashMap<Player, UUID> invName = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (player.hasPermission("homes.admin")) {
            if (args.length == 1) {
                playerstorage = new File(Homes.getInstance().getDataFolder().getPath(), getPlayer(args[0]) + ".yml");
                if (!Config.userfileExists(playerstorage)) {
                    Messages.send(player, Messages.UNKNOWN_USER);
                    return true;
                }
                if (Config.getConfiguration(playerstorage).get("homes") == null) {
                    Messages.send(player, Messages.NO_HOMES_OTHER);
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
                Messages.send(player, Messages.SYNTAX_CHECKHOMES);

            }
        } else {
            Messages.send(player, GlobalMessages.NO_PERM_CMD);
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


