package de.pqtriick.homes.listener.chat;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.files.Config;
import de.pqtriick.homes.files.Messages;
import de.pqtriick.homes.files.homes.ConfigValues;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class RenameChat implements Listener{

    private static File playerstorage;
    private static HashMap<Player, String> oldNameMap = new HashMap<>();
    private static ArrayList<Player> inputAwaiting = new ArrayList<>();
    private static String PREFIX = Messages.msgconfig.getString("messages.prefix");

    public static void sendRenameMSG(Player player, ItemStack item) {
        PREFIX = PREFIX.replace("&", "§");
        player.closeInventory();
        player.sendMessage(PREFIX + " §bWrite the new name for your home. Type §ecancel §bto cancel input!");
        inputAwaiting.add(player);
        oldNameMap.put(player, ChatColor.stripColor(item.getItemMeta().getDisplayName()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (inputAwaiting.contains(event.getPlayer())) {
            inputAwaiting.remove(event.getPlayer());
            oldNameMap.remove(event.getPlayer());
        }
    }

    @EventHandler
    public void onClick(AsyncPlayerChatEvent event) {
        Player player = (Player) event.getPlayer();
        if (inputAwaiting.contains(player)) {
            event.setCancelled(true);
            if (event.getMessage().equalsIgnoreCase("cancel")) {
                inputAwaiting.remove(player);
                oldNameMap.remove(player);
                player.sendMessage(PREFIX + " §cCancelled home renaming,");
            } else {
                String newName = event.getMessage();
                String oldName = oldNameMap.get(player);
                playerstorage = new File(Homes.getInstance().getDataFolder().getPath(), player.getUniqueId() + ".yml");
                double x = Config.getConfiguration(playerstorage).getConfigurationSection("homes." + oldName).getDouble("X");
                double y = Config.getConfiguration(playerstorage).getConfigurationSection("homes." + oldName).getDouble("Y");
                double z = Config.getConfiguration(playerstorage).getConfigurationSection("homes." + oldName).getDouble("Z");
                String world = Config.getConfiguration(playerstorage).getConfigurationSection("homes." + oldName).getString("world");
                Config.set(Config.getConfiguration(playerstorage), playerstorage, "homes." + oldName, null);
                ConfigValues.saveLocation(newName, x, y, z, Bukkit.getWorld(world), playerstorage);
                Config.saveFile(Config.getConfiguration(playerstorage), playerstorage);
                player.sendMessage(PREFIX + " §aSucessfully renamed home!");
                inputAwaiting.remove(event.getPlayer());
                oldNameMap.remove(event.getPlayer());
            }
        }
    }
}
