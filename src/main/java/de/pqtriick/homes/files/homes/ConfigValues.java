package de.pqtriick.homes.files.homes;

import de.pqtriick.homes.files.Config;
import org.bukkit.Location;

import java.io.File;

/**
 * @author pqtriick_
 * @created 20:18, 04.10.2023
 */

public class ConfigValues {

    public static void saveLocation(String name, double x, double y, double z, File file) {
        if (Config.userfileExists(file)) {
            if (Config.getConfiguration(file).get("homes." + name) != null) {
                System.out.println("Already exists");
            } else {
                Config.setDefaults(Config.getConfiguration(file), file, "homes." + name + ".X", String.valueOf(x));
                Config.setDefaults(Config.getConfiguration(file), file, "homes." + name + ".Y", String.valueOf(y));
                Config.setDefaults(Config.getConfiguration(file), file, "homes." + name + ".Z", String.valueOf(z));
                Config.saveFile(Config.getConfiguration(file), file);
            }
        }
    }


    /*public static Location(String name, File file) {
        return null
    }*/
}
