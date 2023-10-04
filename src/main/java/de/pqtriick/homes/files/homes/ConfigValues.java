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
            if (Config.getConfiguration(file).get("homes." + name) == null) {
                Config.getConfiguration(file).set("homes", name);
                Config.getConfiguration(file).set("homes." + name + ".X", x);
                Config.getConfiguration(file).set("homes." + name + ".Y", y);
                Config.getConfiguration(file).set("homes." + name + ".Z", z);
            }
        }
    }
    

    /*public static Location(String name, File file) {
        return null
    }*/
}
