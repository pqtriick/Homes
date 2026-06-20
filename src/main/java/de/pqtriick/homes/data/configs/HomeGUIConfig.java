package de.pqtriick.homes.data.configs;

import de.pqtriick.homes.Homes;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

@Getter
public class HomeGUIConfig {

    private File homeGuiFile;
    private FileConfiguration homeGUIConfig;

    public HomeGUIConfig() {
        homeGuiFile = new File(Homes.getInstance().getDataFolder().getPath(), "homegui.yml");
        if (!homeGuiFile.exists()) {
            Homes.getInstance().getConfigManager().createFile(homeGuiFile);
        }
        homeGUIConfig = YamlConfiguration.loadConfiguration(homeGuiFile);
        for (HomeGUIConfigEnum entry : HomeGUIConfigEnum.values()) {
            if (!homeGUIConfig.contains(entry.getPath())) {
                homeGUIConfig.set(entry.getPath(), entry.getMaterial().toString());
            }
        }
        Homes.getInstance().getConfigManager().saveFile(homeGUIConfig, homeGuiFile);
    }
}

