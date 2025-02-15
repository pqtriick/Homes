package de.pqtriick.homes.data.configs;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.data.Config;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;

public class PermissionsConfig {

    public static File permissionFile = new File(Homes.getInstance().getDataFolder().getPath(), "permissions.yml");
    public static FileConfiguration permissionsConfig = YamlConfiguration.loadConfiguration(permissionFile);

    public static void init() {
        if (!permissionFile.exists()) {
            Config.createFile(permissionFile);
            permissionsConfig.set("homes.create", "homes.create");
            permissionsConfig.set("homes.use", "homes.use");
            permissionsConfig.set("homes.teleport", "homes.teleport");
            permissionsConfig.set("homes.navigate", "homes.navigate");
            permissionsConfig.set("homes.admin", "homes.admin");
            permissionsConfig.set("homes.permissions.enabled", "FALSE");
            permissionsConfig.set("homes.permissions.default", "40");
            Config.saveFile(permissionsConfig, permissionFile);
        }
    }

    public static boolean hasPermission(Player player, String perm) {
        switch (perm) {
            case "create":
                if (PermissionsConfig.permissionsConfig.getString("homes.create") == null) {
                    return true;
                } else {
                    return player.hasPermission(PermissionsConfig.permissionsConfig.getString("homes.create"));
                }
            case "use":
                if (PermissionsConfig.permissionsConfig.getString("homes.use") == null) {
                    return true;
                } else {
                    return player.hasPermission(PermissionsConfig.permissionsConfig.getString("homes.use"));
                }
            case "teleport":
                if (PermissionsConfig.permissionsConfig.getString("homes.teleport") == null) {
                    return true;
                } else {
                    return player.hasPermission(PermissionsConfig.permissionsConfig.getString("homes.teleport"));
                }
            case "navigate":
                if (PermissionsConfig.permissionsConfig.getString("homes.navigate") == null) {
                    return true;
                } else {
                    return player.hasPermission(PermissionsConfig.permissionsConfig.getString("homes.navigate"));
                }
            case "admin":
                if (PermissionsConfig.permissionsConfig.getString("homes.admin") == null) {
                    return true;
                } else {
                    return player.hasPermission(PermissionsConfig.permissionsConfig.getString("homes.admin"));
                }
        }
        return false;
    }

    public static Integer getHomeAmountForPlayer(Player player) {
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

    public static void addNewPerm(String name, int amount) {
        permissionsConfig.set("homes.permissions." + name, amount);
    }

    public static boolean isEnabled() {
        return permissionsConfig.getString("homes.permissions.enabled").equalsIgnoreCase("TRUE");
    }
}
