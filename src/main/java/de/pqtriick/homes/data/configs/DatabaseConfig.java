package de.pqtriick.homes.data.configs;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.data.Config;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class DatabaseConfig {

    public static File databaseFile = new File(Homes.getInstance().getDataFolder().getPath(), "database.yml");
    public static FileConfiguration databaseConfig = YamlConfiguration.loadConfiguration(databaseFile);

    public static void init() {

        if (!databaseFile.exists()) {
            Config.createFile(databaseFile);
            databaseConfig.set("database.enabled", "false");
            databaseConfig.set("database.host", "localhost");
            databaseConfig.set("database.database", "homes");
            databaseConfig.set("database.user", "admin");
            databaseConfig.set("database.password", "123");
            Config.saveFile(databaseConfig, databaseFile);
        }
    }
}
