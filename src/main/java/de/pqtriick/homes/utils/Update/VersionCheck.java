package de.pqtriick.homes.utils.Update;

import de.pqtriick.homes.Homes;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * @author pqtriick_
 * @created 17:08, 15.10.2023
 */

public class VersionCheck {

    private final JavaPlugin plugin;
    private final int resourceID;

    public VersionCheck(JavaPlugin plugin, int resourceID) {
        this.plugin = plugin;
        this.resourceID = resourceID;
    }

    public void getVersion(Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(Homes.getInstance(), () -> {
            try {
                InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceID).openStream();
                Scanner s = new Scanner(inputStream);
                if (s.hasNext()) {
                    consumer.accept(s.next());
                }
            } catch (IOException e) {
                Homes.getInstance().getLogger().info("Unable to check for update. " + e.getMessage());
            }
        });
    }
}
