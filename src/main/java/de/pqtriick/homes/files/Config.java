package de.pqtriick.homes.files;

import de.pqtriick.homes.Homes;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * @author pqtriick_
 * @created 19:57, 04.10.2023
 */

public class Config {

    public static File directory = new File(Homes.getInstance().getDataFolder().getPath());

    public static void createDir() {
        if (!directory.exists()) {
            directory.mkdir();
        }

    }

    public static void createFile(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exe) {
                exe.printStackTrace();
            }
        }

    }

    public static boolean userfileExists(File file) {
        return file.exists();
    }

    public static FileConfiguration getConfiguration(File file) {
        return YamlConfiguration.loadConfiguration(file);
    }

    public static void saveFile(FileConfiguration configuration, File file) {
        try {
            configuration.save(file);
        } catch (IOException exe) {
            exe.printStackTrace();
        }

    }

    public static void setDefaults(FileConfiguration config, File file, String path, String value) {
        config.options().copyDefaults(true);
        config.addDefault(path, value);
        saveFile(config, file);
    }
}
