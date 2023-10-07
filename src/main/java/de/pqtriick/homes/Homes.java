package de.pqtriick.homes;

import de.pqtriick.homes.commands.admin.CheckHomes;
import de.pqtriick.homes.commands.player.AddHome;
import de.pqtriick.homes.commands.player.Homecommand;
import de.pqtriick.homes.files.Config;
import de.pqtriick.homes.listener.compass.NavigationScheduler;
import de.pqtriick.homes.listener.initalizer.JoinConfig;
import de.pqtriick.homes.listener.inventory.ActionInventory;
import de.pqtriick.homes.listener.inventory.DeleteInventory;
import de.pqtriick.homes.listener.inventory.MainInventoryClick;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Homes extends JavaPlugin {

    public static Homes instance;

    @Override
    public void onEnable() {
        instance = this;
        Config.createDir();

        Bukkit.getPluginManager().registerEvents(new JoinConfig(), this);
        Bukkit.getPluginManager().registerEvents(new MainInventoryClick(), this);
        Bukkit.getPluginManager().registerEvents(new DeleteInventory(), this);
        Bukkit.getPluginManager().registerEvents(new ActionInventory(), this);

        this.getCommand("addhome").setExecutor(new AddHome());
        this.getCommand("homes").setExecutor(new Homecommand());
        this.getCommand("checkhomes").setExecutor(new CheckHomes());
        NavigationScheduler.startScheduler();



    }

    @Override
    public void onDisable() {

    }

    public static Homes getInstance() {
        return instance;
    }
}
