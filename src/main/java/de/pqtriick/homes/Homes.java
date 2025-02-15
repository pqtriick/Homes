package de.pqtriick.homes;

import de.pqtriick.homes.commands.admin.AddHomePerm;
import de.pqtriick.homes.commands.admin.ReloadPerms;
import de.pqtriick.homes.commands.admin.ReloadMessages;
import de.pqtriick.homes.commands.player.AddHomeCommand;
import de.pqtriick.homes.commands.player.HomeCommand;
import de.pqtriick.homes.commands.player.RenameCommand;
import de.pqtriick.homes.data.configs.MessageConfig;
import de.pqtriick.homes.data.configs.OptionsConfig;
import de.pqtriick.homes.data.configs.PermissionsConfig;
import de.pqtriick.homes.database.SQL;
import de.pqtriick.homes.data.Config;
import de.pqtriick.homes.data.configs.DatabaseConfig;
import de.pqtriick.homes.database.SQLMethods;
import de.pqtriick.homes.listener.compass.NavigationScheduler;
import de.pqtriick.homes.listener.initalizer.VersionInform;
import de.pqtriick.homes.listener.inventory.ActionInventoryClick;
import de.pqtriick.homes.listener.inventory.DeleteInventoryClick;
import de.pqtriick.homes.listener.inventory.HomeInventoryClick;
import de.pqtriick.homes.listener.inventory.SecondSiteInventory;
import de.pqtriick.homes.utils.Update.VersionCheck;
import de.pqtriick.homes.utils.bstats.Metrics;
import org.bukkit.Bukkit;

import org.bukkit.plugin.java.JavaPlugin;

public final class Homes extends JavaPlugin {

    public static Homes instance;
    public static boolean hasUpdate;
    private static int bstatsid = 20215;
    private static SQL sql;

    @Override
    public void onEnable() {
        instance = this;
        Config.createDir();
        initFiles();

        if (Config.getConfiguration(DatabaseConfig.databaseFile).getString("database.enabled").equalsIgnoreCase("true")) {
            sql = new SQL(Config.getConfiguration(DatabaseConfig.databaseFile).getString("database.host"),
                    Config.getConfiguration(DatabaseConfig.databaseFile).getString("database.database"),
                    Config.getConfiguration(DatabaseConfig.databaseFile).getString("database.user"),
                    Config.getConfiguration(DatabaseConfig.databaseFile).getString("database.password"),
                    "true");
            SQLMethods.init();
        }


        this.getCommand("addhome").setExecutor(new AddHomeCommand());
        this.getCommand("homes").setExecutor(new HomeCommand());
        this.getCommand("rename").setExecutor(new RenameCommand());
        this.getCommand("addperm").setExecutor(new AddHomePerm());
        this.getCommand("reloadmessages").setExecutor(new ReloadMessages());
        this.getCommand("reloadperms").setExecutor(new ReloadPerms());

        Bukkit.getPluginManager().registerEvents(new SecondSiteInventory(), this);
        Bukkit.getPluginManager().registerEvents(new HomeInventoryClick(), this);
        Bukkit.getPluginManager().registerEvents(new DeleteInventoryClick(), this);
        Bukkit.getPluginManager().registerEvents(new ActionInventoryClick(), this);
        Bukkit.getPluginManager().registerEvents(new VersionInform(), this);
        checkUpdate();
        NavigationScheduler.startScheduler();
        Metrics metrics = new Metrics(this, bstatsid);





    }

    @Override
    public void onDisable() {

    }

    public static Homes getInstance() {
        return instance;
    }

    public boolean checkUpdate() {
        new VersionCheck(this, 112984).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                hasUpdate = false;
            } else {
                hasUpdate = true;
            }
        });
        return hasUpdate;
    }

    private void initFiles() {
        DatabaseConfig.init();
        OptionsConfig.init();
        PermissionsConfig.init();
        MessageConfig.init();
    }

    public static SQL getSql() {
        return sql;
    }
}
