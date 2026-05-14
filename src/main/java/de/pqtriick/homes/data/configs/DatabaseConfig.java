package de.pqtriick.homes.data.configs;

import de.pqtriick.homes.Homes;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

@Getter
public class DatabaseConfig {

    private File databaseFile;
    private FileConfiguration databaseConfig;

    public DatabaseConfig() {
        databaseFile = new File(Homes.getInstance().getDataFolder().getPath(), "database.yml");
        if (!databaseFile.exists()) {
            Homes.getInstance().getConfigManager().createFile(databaseFile);
        }
        databaseConfig = YamlConfiguration.loadConfiguration(databaseFile);
        for (DatabaseConfigEnum entry : DatabaseConfigEnum.values()) {
            if (!databaseConfig.contains(entry.getPath())) {
                databaseConfig.set(entry.getPath(), entry.getValue());
            }
        }
        Homes.getInstance().getConfigManager().saveFile(databaseConfig, databaseFile);
    }
}
