package de.pqtriick.homes.files;

import de.pqtriick.homes.Homes;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Permissions {

    public static File permissionsfile = new File(Homes.getInstance().getDataFolder().getPath(), "permissions.yml");
    public static FileConfiguration permissionsConfig = YamlConfiguration.loadConfiguration(permissionsfile);

    public static void initPermsFile() {
        if (!permissionsfile.exists()) {
            Config.createFile(permissionsfile);
            permissionsConfig.set("homes.create", "homes.create");
            permissionsConfig.set("homes.use", "homes.use");
            permissionsConfig.set("homes.teleport", "homes.teleport");
            permissionsConfig.set("homes.navigate", "homes.navigate");
            permissionsConfig.set("homes.admin", "homes.admin");
            Config.saveFile(permissionsConfig, permissionsfile);
        }
    }
}
