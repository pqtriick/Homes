package de.pqtriick.homes.data;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.data.configs.DatabaseConfigEnum;
import de.pqtriick.homes.data.configs.MessageEnum;
import de.pqtriick.homes.data.configs.OptionsConfigEnum;
import de.pqtriick.homes.data.homes.HomeObject;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HomeManager {

    public boolean isSQLEnabled() {
        return Homes.getInstance().getDbConfig().getDatabaseConfig().getString(DatabaseConfigEnum.DB_ENABLED.getPath()).equalsIgnoreCase("true");
    }

    public void saveHome(Player player, HomeObject object) {
        if (isSQLEnabled()) {
            Homes.getInstance().getSqlMethods().addHome(player.getUniqueId(), object.getName(), object.getX(), object.getY(), object.getZ(), object.getWorld().getName());
        } else {
            File file = getPlayerFile(player);
            FileConfiguration configuration = Homes.getInstance().getConfigManager().getConfiguration(file);
            if (configuration.get("homes." + object.getName()) != null) {
                player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.HOME_ALREADY_EXISTS.getPath()));
                return;
            }
            configuration.set("homes." + object.getName() + ".X", String.valueOf(object.getX()));
            configuration.set("homes." + object.getName() + ".Y", String.valueOf(object.getY()));
            configuration.set("homes." + object.getName() + ".Z", String.valueOf(object.getZ()));
            configuration.set("homes." + object.getName() + ".world", object.getWorld().getName());
            Homes.getInstance().getConfigManager().saveFile(configuration, Homes.getInstance().getHomeManager().getPlayerFile(player));
            Homes.getInstance().getHomeManager().setHomeAmount(player, Homes.getInstance().getHomeManager().getHomeAmount(player)+1);
            player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.HOME_SAVED_SUCCESS_1.getPath()));
            player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.HOME_SAVED_SUCCESS_2.getPath()));
        }
    }

    public void setHomeAmount(Player player, int amount) {
        if (isSQLEnabled()) {
            Homes.getInstance().getSqlMethods().setHomeAmount(player.getUniqueId(), amount);
        } else {
            Homes.getInstance().getConfigManager().set(Homes.getInstance().getConfigManager().getConfiguration(getPlayerFile(player)),
                    getPlayerFile(player), "homeamount", String.valueOf(amount));
            FileConfiguration configuration = Homes.getInstance().getConfigManager().getConfiguration(Homes.getInstance().getHomeManager().getPlayerFile(player));
            Homes.getInstance().getConfigManager().saveFile(configuration, Homes.getInstance().getHomeManager().getPlayerFile(player));
        }
    }

    public int getHomeAmount(Player player) {
        if (isSQLEnabled()) {
            return Homes.getInstance().getSqlMethods().getHomeAmount(player.getUniqueId()).join();
        } else {
            return Integer.parseInt(Homes.getInstance().getConfigManager().getConfiguration(getPlayerFile(player)).getString("homeamount"));
        }
    }

    public int getMaxHomes(Player player) {
        if (Homes.getInstance().getPermissionConfig().isEnabled()) {
            return Homes.getInstance().getPermissionConfig().getHomeAmountForPlayer(player);
        }
        return Integer.parseInt(Homes.getInstance().getOptionsConfig().getOptionsConfig().getString(OptionsConfigEnum.OPTIONS_HOME_MAXHOMES.getPath()));
    }

    public void deleteHome(Player player, String name) {
        if (isSQLEnabled()) {
            Homes.getInstance().getSqlMethods().deleteHome(player.getUniqueId(), name);
        } else {
            File file = getPlayerFile(player);
            FileConfiguration configuration = Homes.getInstance().getConfigManager().getConfiguration(file);
            Homes.getInstance().getConfigManager().set(configuration, file, "homes." + name, null);
            Homes.getInstance().getConfigManager().set(configuration, file, "homeamount", String.valueOf(getHomeAmount(player)-1));
            Homes.getInstance().getConfigManager().saveFile(configuration, Homes.getInstance().getHomeManager().getPlayerFile(player));
        }
    }

    public HomeObject getHomeByString(Player player, String name) {
        if (isSQLEnabled()) {
            return Homes.getInstance().getSqlMethods().getHomeByName(player.getUniqueId(), name).join();
        }
        String path = "homes." + name;
        FileConfiguration configuration = Homes.getInstance().getConfigManager().getConfiguration(getPlayerFile(player));
        return new HomeObject(name, Double.parseDouble(configuration.getString(path + ".X")),
                Double.parseDouble(configuration.getString(path + ".Y")),
                Double.parseDouble(configuration.getString(path + ".Z")),
                Bukkit.getWorld(configuration.getString(path + ".world")));
    }

    public List<HomeObject> getHomes(Player player) {
        if (isSQLEnabled()) {
            return Homes.getInstance().getSqlMethods().getHomes(player.getUniqueId()).join();
        }
        List<HomeObject> homes = new ArrayList<>();
        FileConfiguration configuration = Homes.getInstance().getConfigManager().getConfiguration(getPlayerFile(player));
        if (configuration.getConfigurationSection("homes") == null) return homes;
        for (String homeName : configuration.getConfigurationSection("homes").getKeys(false)) {
            double x = configuration.getDouble("homes." + homeName + ".X");
            double y = configuration.getDouble("homes." + homeName + ".Y");
            double z = configuration.getDouble("homes." + homeName + ".Z");
            String worldName = configuration.getString("homes." + homeName + ".world");
            World world = Bukkit.getWorld(worldName);
            HomeObject object = new HomeObject(homeName, x, y, z, world);
            homes.add(object);
        }
        return homes;
    }

    public void renameHome(Player player, String oldName, String newName) {
        HomeObject object = getHomeByString(player, oldName);
        if (isSQLEnabled()) {
            Homes.getInstance().getSqlMethods().deleteHome(player.getUniqueId(), oldName);
            object.setName(newName);
            Homes.getInstance().getSqlMethods().addHome(player.getUniqueId(), object.getName(), object.getX(), object.getY(), object.getZ(), object.getWorld().getName());
        } else {
            deleteHome(player, oldName);
            object.setName(newName);
            saveHome(player, object);
        }
    }

    public boolean hasSpace(Player player) {
        int maxspace = getMaxHomes(player);
        int amount = getHomeAmount(player);
        System.out.println(maxspace);
        System.out.println(amount);
        if (Homes.getInstance().getPermissionConfig().isEnabled()) {
            maxspace = Homes.getInstance().getPermissionConfig().getHomeAmountForPlayer(player);
        }
        if (isSQLEnabled()) {
            amount = Homes.getInstance().getSqlMethods().getHomeAmount(player.getUniqueId()).join();
        }
        System.out.println(maxspace);
        System.out.println(amount);
        return amount < maxspace;

    }

    public File getPlayerFile(Player player) {
        return new File(Homes.getInstance().getDataFolder().getPath(), player.getUniqueId() + ".yml");
    }

}
