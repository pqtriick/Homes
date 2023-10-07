package de.pqtriick.homes.commands.player;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.files.Config;
import de.pqtriick.homes.files.Messages;
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

/**
 * @author pqtriick_
 * @created 15:02, 06.10.2023
 */

public class Homecommand implements CommandExecutor {

    private static File playerstorage;
    public static Inventory homeinv;
    private int invnumber;
    private static String LEFTCLICK = Messages.msgconfig.getString("messages.leftclick");
    private static String RIGHTCLICK = Messages.msgconfig.getString("messages.rightclick");
    private static String NOPERM = Messages.msgconfig.getString("messages.nopermission");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        LEFTCLICK = LEFTCLICK.replace("&", "§");
        RIGHTCLICK = RIGHTCLICK.replace("&", "§");
        NOPERM = NOPERM.replace("&", "§");
        Player player = (Player) sender;
        homeinv = Bukkit.createInventory(null, 9*5, "§3§lHomes");
        playerstorage = new File(Homes.getInstance().getDataFolder().getPath(), player.getUniqueId() + ".yml");
        if (player.hasPermission("homes.use")) {
            invnumber = 0;
            for (String homes : Config.getConfiguration(playerstorage).getConfigurationSection("homes").getKeys(false)) {
                homeinv.setItem(invnumber, SkullBuilder.getCustomSkull(Skulls.HOUSE.getTexture(), "§e" + homes, LEFTCLICK,RIGHTCLICK));
                invnumber++;
            }
            player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 2);
            player.openInventory(homeinv);
        } else {
            player.sendMessage(NOPERM);

        }
        return false;
    }
}
