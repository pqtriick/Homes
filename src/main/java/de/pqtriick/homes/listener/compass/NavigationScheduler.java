package de.pqtriick.homes.listener.compass;

import de.pqtriick.homes.Homes;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author pqtriick_
 * @created 13:37, 07.10.2023
 */

public class NavigationScheduler {

    public static HashMap<Player, Location> navigation = new HashMap<>();
    private static double distance;
    private static boolean running;

    public static void startScheduler() {
        if (running) {
            return;
        }
        running = true;
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (navigation.get(all) != null) {
                        distance = all.getLocation().distanceSquared(navigation.get(all));
                        String message = "§6" + Math.round(Math.sqrt(distance)) + "§6m";
                        all.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
                        if (all.getLocation().distanceSquared(navigation.get(all)) <= 25) {
                            navigation.remove(all);
                            all.sendMessage("§3§lHOMES §7| §bYou have reached your home!");
                            all.getInventory().remove(Material.COMPASS);
                        }

                    }
                }
            }
        }.runTaskTimerAsynchronously(Homes.getInstance(), 0, 10 * 1);
    }
}
