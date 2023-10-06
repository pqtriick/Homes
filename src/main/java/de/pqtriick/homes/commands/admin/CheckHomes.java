package de.pqtriick.homes.commands.admin;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.files.Config;
import de.pqtriick.homes.utils.Skull.SkullBuilder;
import de.pqtriick.homes.utils.Skull.Skulls;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.util.UUID;

/**
 * @author pqtriick_
 * @created 15:43, 06.10.2023
 */

public class CheckHomes implements CommandExecutor {

    public static Inventory homeinv;
    private int invnumber;
    private static File playerdata;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (player.hasPermission("homes.checkhomes")) {
            if (args.length==1) {
                playerdata = new File(Homes.getInstance().getDataFolder().getPath(), getPlayerUUID(args[0]) + ".yml");
                if (playerdata.exists()) {
                    homeinv = Bukkit.createInventory(null, 9*5, "§b" + args[0] + " §3Homes");
                    invnumber = 0;
                    for (String homes : Config.getConfiguration(playerdata).getConfigurationSection("homes").getKeys(false)) {
                        homeinv.setItem(invnumber, SkullBuilder.getCustomSkull(Skulls.HOUSE.getTexture(), "§e" + homes, "§7➥ §aLeftclick to access\n§7➥ §cRightclick to delete"));
                        invnumber++;
                    }
                    player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 2);
                    player.openInventory(homeinv);

                } else {
                    player.sendMessage("§c§lERROR §7| §cNo userdata found for this player.");
                }

            } else {
                player.sendMessage("§3§lUSAGE §7| §3/checkhomes [Player]");
            }
        }

        return false;
    }



    private static UUID getPlayerUUID(String player) {
        Player target = Bukkit.getPlayer(player);
        if (target != null) {
            return target.getUniqueId();
        } else {
            return Bukkit.getOfflinePlayer(player).getUniqueId();
        }

    }


}
