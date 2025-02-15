package de.pqtriick.homes.data;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.data.configs.DatabaseConfig;
import de.pqtriick.homes.data.configs.MessageConfig;
import de.pqtriick.homes.data.configs.OptionsConfig;
import de.pqtriick.homes.data.configs.PermissionsConfig;
import de.pqtriick.homes.data.homes.HomeObject;
import de.pqtriick.homes.database.SQLMethods;
import de.pqtriick.homes.utils.enums.MessageEnum;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationManager {


    public static void initFiles() {

    }

    public static void saveHome(Player player, HomeObject home) {
        if (!playerDataExists(player)) {
            createUserData(player);
        }
        File file = getPlayerFile(player);
        if (Config.getConfiguration(file).get("homes." + home.getName()) != null) {
            player.sendMessage(Component.text("A home with this name already exists!!"));
        } else {
            Config.set(Config.getConfiguration(file), file, "homes." + home.getName() + ".X", String.valueOf(home.getX()));
            Config.set(Config.getConfiguration(file), file, "homes." + home.getName() + ".Y", String.valueOf(home.getY()));
            Config.set(Config.getConfiguration(file), file, "homes." + home.getName() + ".Z", String.valueOf(home.getZ()));
            Config.set(Config.getConfiguration(file), file, "homes." + home.getName() + ".world", home.getWorld().getName());

            player.sendMessage(MessageConfig.getMSG(MessageEnum.PREFIX.getPath()).append(MessageConfig.getMSG(MessageEnum.HOME_SAVED_SUCCESS_1.getPath())));
            player.sendMessage(MessageConfig.getMSG(MessageEnum.PREFIX.getPath()).append(MessageConfig.getMSG(MessageEnum.HOME_SAVED_SUCCESS_2.getPath())));
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
        }
    }

    //Returns only names of homes
    public static List<String> getHomes(Player player) {
        List<String> homes = new ArrayList<>();
        if (!playerDataExists(player) || Config.getConfiguration(getPlayerFile(player)).getConfigurationSection("homes") == null) {
            return null;
        } else {
            for (String home : Config.getConfiguration(getPlayerFile(player)).getConfigurationSection("homes").getKeys(false)) {
                homes.add(home);
            }
        }
        return homes;
    }

    public static void renameHome(Player player, String oldName, String newName) {
        HomeObject object = getHomeByString(player, oldName);
        deleteHome(player, oldName);
        object.setName(newName);
        saveHome(player, object);
    }

    public static void renameHomeSQL(Player player, String oldName, String newName) {
        HomeObject object = getHomeByStringSQL(player, oldName);
        deleteHomeSQL(player, oldName);
        object.setName(newName);
        saveHomeSQL(player, object);
    }

    public static int getHomeAmount(Player player) {
        return Integer.parseInt(Config.getConfiguration(getPlayerFile(player)).getString("homeamount"));
    }

    public static void setHomeAmount(Player player, int amount) {
        Config.set(Config.getConfiguration(getPlayerFile(player)), getPlayerFile(player), "homeamount", String.valueOf(amount));
    }

    private static File getPlayerFile(Player player) {
        return new File(Homes.getInstance().getDataFolder().getPath(), player.getUniqueId() + ".yml");
    }

    public static boolean playerDataExists(Player player) {
        return Config.userfileExists(new File(Homes.getInstance().getDataFolder().getPath(), player.getUniqueId() + ".yml"));
    }

    public static boolean hasSpace(Player player) {
        int maxspace;
        int amount;
        if (isSQLEnabled()) {
            amount = getHomeAmountSQL(player);
        } else {
            amount = getHomeAmount(player);
        }
        if (PermissionsConfig.isEnabled()) {
            maxspace = PermissionsConfig.getHomeAmountForPlayer(player);
        } else {
            maxspace = getMaxHomes(player);
        }
        return amount < maxspace;
    }

    public static int getMaxHomes(Player player) {
        if (PermissionsConfig.isEnabled()) {
            return PermissionsConfig.getHomeAmountForPlayer(player);
        } else {
            return Integer.parseInt(OptionsConfig.optionsConfig.getString("options.homes.maxhomes"));
        }
    }

    private static void createUserData(Player player) {
        File file = getPlayerFile(player);
        Config.createFile(file);
        Config.setDefaults(Config.getConfiguration(file), file, "homeamount", "0");
    }

    public static boolean isSQLEnabled() {
        return Config.getConfiguration(DatabaseConfig.databaseFile).getString("database.enabled").equalsIgnoreCase("true");
    }

    public static List<String> getHomesSQL(Player player) {
        return SQLMethods.getHomes(player);
    }

    public static void setHomeAmountSQL(Player player, int amount) {
        SQLMethods.setHomeAmount(player, amount);

    }

    public static Integer getHomeAmountSQL(Player player) {
        return SQLMethods.getHomeAmount(player);
    }

    public static void deleteHome(Player player, String name) {
        File file = getPlayerFile(player);
        Config.set(Config.getConfiguration(file), file, "homes." + name, null);
    }

    public static void deleteHomeSQL(Player player, String name) {
        SQLMethods.deleteHome(player, name);
    }

    public static HomeObject getHomeByString(Player player, String name) {
        File file = getPlayerFile(player);
        String path = "homes." + name;
        return new HomeObject(name, Double.parseDouble(Config.getConfiguration(file).getString(path + ".X")),
                Double.parseDouble(Config.getConfiguration(file).getString(path + ".Y")),
                Double.parseDouble(Config.getConfiguration(file).getString(path + ".Z")),
                Bukkit.getWorld(Config.getConfiguration(file).getString(path + ".world")));
    }

    public static HomeObject getHomeByStringSQL(Player player, String name) {
        return SQLMethods.getHomeByName(player, name);
    }

    public static void saveHomeSQL(Player player, HomeObject obj) {
        SQLMethods.addHome(player, obj.getName(), obj.getX(), obj.getY(), obj.getZ(), obj.getWorld().getName());
    }
}
