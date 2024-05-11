package de.pqtriick.homes;


import de.pqtriick.homes.commands.admin.CheckHomes;
import de.pqtriick.homes.commands.admin.ReloadValues;
import de.pqtriick.homes.commands.player.AddHome;
import de.pqtriick.homes.commands.player.Homecommand;
import de.pqtriick.homes.files.Config;
import de.pqtriick.homes.files.Messages;
import de.pqtriick.homes.files.Options;
import de.pqtriick.homes.files.Permissions;
import de.pqtriick.homes.listener.chat.RenameChat;
import de.pqtriick.homes.listener.compass.NavigationScheduler;
import de.pqtriick.homes.listener.initalizer.JoinConfig;
import de.pqtriick.homes.listener.initalizer.VersionInform;
import de.pqtriick.homes.listener.inventory.*;
import de.pqtriick.homes.utils.Update.VersionCheck;
import de.pqtriick.homes.utils.bstats.Metrics;
import org.bukkit.Bukkit;

import org.bukkit.plugin.java.JavaPlugin;

public final class Homes extends JavaPlugin {

    public static Homes instance;
    public static boolean hasUpdate;
    private static int bstatsid = 20215;

    @Override
    public void onEnable() {
        instance = this;
        Config.createDir();
        Messages.initMessageFile();
        Options.initOptionsFile();
        Permissions.initPermsFile();

        Bukkit.getPluginManager().registerEvents(new JoinConfig(), this);
        Bukkit.getPluginManager().registerEvents(new MainInventoryClick(), this);
        Bukkit.getPluginManager().registerEvents(new DeleteInventory(), this);
        Bukkit.getPluginManager().registerEvents(new ActionInventory(), this);
        Bukkit.getPluginManager().registerEvents(new VersionInform(), this);
        Bukkit.getPluginManager().registerEvents(new AdminInventory(), this);
        Bukkit.getPluginManager().registerEvents(new MultipleSiteInventory(), this);
        Bukkit.getPluginManager().registerEvents(new RenameChat(), this);

        this.getCommand("addhome").setExecutor(new AddHome());
        this.getCommand("homes").setExecutor(new Homecommand());
        this.getCommand("checkhomes").setExecutor(new CheckHomes());
        this.getCommand("reloadvalues").setExecutor(new ReloadValues());
        NavigationScheduler.startScheduler();
        checkUpdate();
        Metrics metrics = new Metrics(this, bstatsid);

        //add max home size for individual players
        //add multiple sites for homes




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

}
