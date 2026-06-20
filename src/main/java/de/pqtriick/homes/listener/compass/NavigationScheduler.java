package de.pqtriick.homes.listener.compass;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.data.configs.MessageEnum;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class NavigationScheduler {

    public HashMap<Player, Location> activeNavigation = new HashMap<>();
    private Component message;

    public NavigationScheduler() {
        if (Homes.getInstance().getOptionsConfig().getOptionsConfig().getString("options.particle.enabled").equalsIgnoreCase("FALSE")) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : activeNavigation.keySet()) {
                    double distance = player.getLocation().distanceSquared(activeNavigation.get(player));
                    message = Homes.getInstance().getMessageConfig().getMSG(MessageEnum.NAVIGATION_DISTANCE.getPath());
                    message = message.replaceText(TextReplacementConfig.builder().matchLiteral("<>").replacement(Component.text(Math.round(Math.sqrt(distance)))).build());
                    player.sendActionBar(message);
                    circle(activeNavigation.get(player), player, Particle.valueOf(Homes.getInstance().getOptionsConfig().getOptionsConfig().getString("options.particle.particle")));
                    drawLine(activeNavigation.get(player), player, Particle.valueOf(Homes.getInstance().getOptionsConfig().getOptionsConfig().getString("options.navigation.particle")));
                    if (player.getLocation().distanceSquared(activeNavigation.get(player)) <= 2) {
                        activeNavigation.remove(player);
                        player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.PREFIX.getPath()).append(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.NAVIGATION_REACHED.getPath())));
                    }
                }
            }
        }.runTaskTimerAsynchronously(Homes.getInstance(), 0, Integer.parseInt(Homes.getInstance().getOptionsConfig().getOptionsConfig().getString("options.particle.spawnDelay")));
    }

    private void circle(Location location, Player player, Particle particle) {
        for (double i = 0; i < Math.PI*2; i+=0.1) {
            double x = Math.cos(i);
            double z = Math.sin(i);
            player.spawnParticle(particle, location.getX()+x, location.getY(), location.getZ()+z, 0, 0, 0, 0);
        }
    }

    private void drawLine(Location location, Player player, Particle particle) {
        Location playerloc = player.getLocation();
        Vector projection = location.toVector().subtract(player.getLocation().toVector());
        for (double i = 0.5; i < Integer.parseInt(Homes.getInstance().getOptionsConfig().getOptionsConfig().getString("options.navigation.length")); i += Double.parseDouble(Homes.getInstance().getOptionsConfig().getOptionsConfig().getString("options.navigation.spacing"))) {
            projection.multiply(i);
            playerloc.add(projection);
            player.spawnParticle(particle, playerloc.getX(), playerloc.getY()+1, playerloc.getZ(), 0, 0, 0, 0);
            playerloc.subtract(projection);
            projection.normalize();
        }
    }
}