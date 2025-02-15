package de.pqtriick.homes.data.configs;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.data.Config;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class OptionsConfig {

    public static File optionsFile = new File(Homes.getInstance().getDataFolder().getPath(), "options.yml");
    public static FileConfiguration optionsConfig = YamlConfiguration.loadConfiguration(optionsFile);

    public static void init() {
        if (!optionsFile.exists()) {
            Config.createFile(optionsFile);
            optionsConfig.set("options.info", "All Available Particles: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Particle.html");
            optionsConfig.set("options.particle.enabled", "true");
            optionsConfig.set("options.particle.particle", "FLAME");
            optionsConfig.set("options.particle.spawnDelay", "10");
            optionsConfig.set("options.navigation.spacing", "0.5");
            optionsConfig.set("options.navigation.length", "5");
            optionsConfig.set("options.navigation.particle", "SOUL_FIRE_FLAME");
            optionsConfig.set("options.homes.maxhomes", "99");
            optionsConfig.set("options.homes.block", "CYAN_BED");
            Config.saveFile(optionsConfig, optionsFile);
        }
    }
}
