package de.pqtriick.homes;

import de.pqtriick.homes.commands.testin;
import de.pqtriick.homes.files.Config;
import de.pqtriick.homes.listener.initalizer.JoinConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Homes extends JavaPlugin {

    public static Homes instance;

    @Override
    public void onEnable() {
        instance = this;
        Config.createDir();

        Bukkit.getPluginManager().registerEvents(new JoinConfig(), this);
        this.getCommand("testin").setExecutor(new testin());
        //Particle Navigation
        //Jump to home
        //Delete home
        //add home



    }

    @Override
    public void onDisable() {

    }

    public static Homes getInstance() {
        return instance;
    }
}
