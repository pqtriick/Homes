package de.pqtriick.homes.listener.compass;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.files.Messages;
import de.pqtriick.homes.files.Options;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
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
    private static String REACHEDHOME = Messages.msgconfig.getString("messages.reachedhome");
    private static String PREFIX = Messages.msgconfig.getString("messages.prefix");
    public static String enabled = Options.optionsconfig.getString("options.particle.enabled");
    public static Particle particle = Particle.valueOf(Options.optionsconfig.getString("options.particle.particle"));
    public static String delay = Options.optionsconfig.getString("options.particle.delay");


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
                        if (enabled.equalsIgnoreCase("TRUE")) {
                            circle(navigation.get(all), all, particle);
                        }
                        if (all.getLocation().distanceSquared(navigation.get(all)) <= 2) {
                            navigation.remove(all);
                            REACHEDHOME = REACHEDHOME.replace("&", "§");
                            PREFIX = PREFIX.replace("&", "§");
                            all.sendMessage(PREFIX + REACHEDHOME);
                            all.getInventory().remove(Material.COMPASS);
                        }

                    }
                }
            }
        }.runTaskTimerAsynchronously(Homes.getInstance(), 0, Integer.parseInt(delay));
    }

    private static void circle(Location loc, Player player, Particle particle) {
        Location spawner = loc;
        for (double i=0; i< Math.PI*2; i+=0.1) {
            double x = Math.cos(i);
            double z = Math.sin(i);
            player.spawnParticle(particle, loc.getX()+x, spawner.getY(), spawner.getZ()+z, 0, 0, 0, 0);

        }

    }


}
