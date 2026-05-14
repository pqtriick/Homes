package de.pqtriick.homes;

import de.pqtriick.homes.commands.admin.AddHomePerm;
import de.pqtriick.homes.commands.admin.ReloadMessages;
import de.pqtriick.homes.commands.admin.ReloadPerms;
import de.pqtriick.homes.commands.player.AddHomeCommand;
import de.pqtriick.homes.commands.player.HomeCommand;
import de.pqtriick.homes.commands.player.RenameCommand;
import de.pqtriick.homes.data.HomeManager;
import de.pqtriick.homes.data.configs.DatabaseConfigEnum;
import de.pqtriick.homes.data.configs.MessageConfig;
import de.pqtriick.homes.data.configs.MessageEnum;
import de.pqtriick.homes.data.configs.OptionsConfig;
import de.pqtriick.homes.data.configs.PermissionsConfig;
import de.pqtriick.homes.database.SQL;
import de.pqtriick.homes.data.Config;
import de.pqtriick.homes.data.configs.DatabaseConfig;
import de.pqtriick.homes.database.SQLMethods;
import de.pqtriick.homes.listener.compass.NavigationScheduler;
import de.pqtriick.homes.listener.initalizer.PlayerJoinInitalizer;
import de.pqtriick.homes.listener.inventory.ActionInventoryClick;
import de.pqtriick.homes.listener.inventory.DeleteInventoryClick;
import de.pqtriick.homes.listener.inventory.HomeInventoryClick;
import de.pqtriick.homes.utils.Update.VersionCheck;
import de.pqtriick.homes.utils.bstats.Metrics;
import lombok.Getter;

import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Homes extends JavaPlugin {

    @Getter
    private static Homes instance;
    private boolean hasUpdate;
    private int bstatsid = 20215;
    private Component PREFIX;

    private SQL sql;
    private Config configManager;
    private DatabaseConfig dbConfig;
    private OptionsConfig optionsConfig;
    @Setter
    private PermissionsConfig permissionConfig;
    private MessageConfig messageConfig;
    private SQLMethods sqlMethods;
    private HomeManager homeManager;
    private NavigationScheduler navigationScheduler;


    @Override
    public void onEnable() {
        instance = this;
        initFiles();
        initDB();
        homeManager = new HomeManager();
        initCmd();
        initListener();
        new Metrics(this, bstatsid);
        checkUpdate();

    }

    @Override
    public void onDisable() {
        if (sql != null) sql.close();
    }

    public boolean checkUpdate() {
        new VersionCheck(this, 112984).getVersion(version -> {
            hasUpdate = this.getDescription().getVersion().equals(version);
        });
        return hasUpdate;
    }

    private void initFiles() {
        configManager = new Config();
        dbConfig = new DatabaseConfig();
        optionsConfig = new OptionsConfig();
        permissionConfig = new PermissionsConfig();
        messageConfig = new MessageConfig();
        PREFIX = messageConfig.getMSG(MessageEnum.PREFIX.getPath());
    }

    private void initCmd() {
        this.getCommand("addhome").setExecutor(new AddHomeCommand());
        this.getCommand("homes").setExecutor(new HomeCommand());
        this.getCommand("rename").setExecutor(new RenameCommand());
        this.getCommand("addperm").setExecutor(new AddHomePerm());
        this.getCommand("reloadmessages").setExecutor(new ReloadMessages());
        this.getCommand("reloadperms").setExecutor(new ReloadPerms());
    }

    private void initListener() {
        navigationScheduler = new NavigationScheduler();
        Bukkit.getPluginManager().registerEvents(new PlayerJoinInitalizer(), this);
        Bukkit.getPluginManager().registerEvents(new HomeInventoryClick(), this);
        Bukkit.getPluginManager().registerEvents(new DeleteInventoryClick(), this);
        Bukkit.getPluginManager().registerEvents(new ActionInventoryClick(), this);
    }

    private void initDB() {
        if (configManager.getConfiguration(dbConfig.getDatabaseFile()).getString(DatabaseConfigEnum.DB_ENABLED.getPath()).equalsIgnoreCase("true")) {
            sql = new SQL(configManager.getConfiguration(dbConfig.getDatabaseFile()).getString(DatabaseConfigEnum.DB_HOST.getPath()),
                    configManager.getConfiguration(dbConfig.getDatabaseFile()).getString(DatabaseConfigEnum.DB_DB.getPath()),
                    configManager.getConfiguration(dbConfig.getDatabaseFile()).getString(DatabaseConfigEnum.DB_USER.getPath()),
                    configManager.getConfiguration(dbConfig.getDatabaseFile()).getString(DatabaseConfigEnum.DB_PASS.getPath()),
                    "true");
            sqlMethods = new SQLMethods();
        }
    }
}
