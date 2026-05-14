package de.pqtriick.homes.data;

import de.pqtriick.homes.Homes;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    public final File directory;

    public Config() {
        directory = new File(Homes.getInstance().getDataFolder().getPath());
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    public void createFile(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exe) {
                exe.printStackTrace();
            }
        }
    }

    public void set(FileConfiguration config, File file, String path, String value) {
        config.set(path,value);
        saveFile(config, file);
    }

    public boolean userfileExists(File file) {
        return file.exists();
    }

    public FileConfiguration getConfiguration(File file) {
        return YamlConfiguration.loadConfiguration(file);
    }

    public void saveFile(FileConfiguration configuration, File file) {
        try {
            configuration.save(file);
        } catch (IOException exe) {
            exe.printStackTrace();
        }
    }

    public void setDefaults(FileConfiguration config, File file, String path, String value) {
        config.options().copyDefaults(true);
        config.addDefault(path, value);
        saveFile(config, file);
    }
}
