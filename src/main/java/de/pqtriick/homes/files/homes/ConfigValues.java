package de.pqtriick.homes.files.homes;

import de.pqtriick.homes.files.Config;
import org.bukkit.Location;

import java.io.File;

/**
 * @author pqtriick_
 * @created 20:18, 04.10.2023
 */

public class ConfigValues {

    private static int amt;

    public static void saveLocation(String name, double x, double y, double z, File file) {
        if (Config.userfileExists(file)) {
            if (Config.getConfiguration(file).get("homes." + name) != null) {
                System.out.println("Already exists");
            } else {
                Config.set(Config.getConfiguration(file), file, "homes." + name + ".X", String.valueOf(x));
                Config.set(Config.getConfiguration(file), file, "homes." + name + ".Y", String.valueOf(y));
                Config.set(Config.getConfiguration(file), file, "homes." + name + ".Z", String.valueOf(z));
            }
        }
    }


    public static int getHomeAmount(File file) {
        if (Config.userfileExists(file)) {
            amt = (int) Config.getConfiguration(file).get("amount");
        }
        return amt;
    }

    public static void addHomeAmount(File file, int amount) {
        if (Config.userfileExists(file)) {
            Config.setDefaults(Config.getConfiguration(file), file, "amount", Integer.toString(amount));
            Config.saveFile(Config.getConfiguration(file), file);
        }
    }
}
