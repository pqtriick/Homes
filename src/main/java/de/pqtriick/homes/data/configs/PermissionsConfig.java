package de.pqtriick.homes.data.configs;

import de.pqtriick.homes.Homes;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;

public class PermissionsConfig {

    private File permissionFile;
    private FileConfiguration permissionsConfig;

    public PermissionsConfig() {
        permissionFile = new File(Homes.getInstance().getDataFolder().getPath(), "permissions.yml");
        if (!permissionFile.exists()) {
            Homes.getInstance().getConfigManager().createFile(permissionFile);
        }
        permissionsConfig = YamlConfiguration.loadConfiguration(permissionFile);
        for (PermissionsConfigEnum entry : PermissionsConfigEnum.values()) {
            if (!permissionsConfig.contains(entry.getPath())) {
                permissionsConfig.set(entry.getPath(), entry.getValue());
            }
        }
        Homes.getInstance().getConfigManager().saveFile(permissionsConfig, permissionFile);
    }


    public boolean hasPermission(Player player, PermissionsConfigEnum perm) {
        if (permissionsConfig.getString(perm.getPath()) == null) return true;
        return player.hasPermission(permissionsConfig.getString(perm.getPath()));
    }

    public Integer getHomeAmountForPlayer(Player player) {
        HashMap<String, Integer> permMap = new HashMap<>();
        for (String rank : permissionsConfig.getConfigurationSection("homes.permissions").getKeys(true)) {
            if (!rank.equalsIgnoreCase("enabled")) {
                permMap.put("homes.permissions." + rank, Integer.parseInt(permissionsConfig.getString("homes.permissions." + rank)));
            }
        }
        int x = 0;
        for (String perm : permMap.keySet()) {
            if (player.hasPermission(perm)) {
                x = permMap.get(perm);
            }
        }
        if (x == 0) return permMap.get("homes.permissions.default");
        return x;
    }

    public void addNewPerm(String name, int amount) {
        permissionsConfig.set("homes.permissions." + name, amount);
        Homes.getInstance().getConfigManager().saveFile(permissionsConfig, permissionFile);
    }

    public boolean isEnabled() {
        return permissionsConfig.getString(PermissionsConfigEnum.PERM_HOME_PERMENABLED.getPath()).equalsIgnoreCase("TRUE");
    }
}
