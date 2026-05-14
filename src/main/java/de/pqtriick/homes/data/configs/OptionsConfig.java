package de.pqtriick.homes.data.configs;

import de.pqtriick.homes.Homes;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class OptionsConfig {

    private File optionsFile;
    @Getter
    private FileConfiguration optionsConfig;

    public OptionsConfig() {
        optionsFile = new File(Homes.getInstance().getDataFolder().getPath(), "options.yml");
        if (!optionsFile.exists()) {
            Homes.getInstance().getConfigManager().createFile(optionsFile);
        }
        optionsConfig = YamlConfiguration.loadConfiguration(optionsFile);
        for (OptionsConfigEnum entry : OptionsConfigEnum.values()) {
            if (!optionsConfig.contains(entry.getPath())) {
                optionsConfig.set(entry.getPath(), entry.getValue());
            }
        }
        Homes.getInstance().getConfigManager().saveFile(optionsConfig, optionsFile);
    }
}
