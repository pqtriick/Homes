package de.pqtriick.homes;

import de.cubbossa.tinytranslations.Message;
import de.cubbossa.tinytranslations.TinyTranslations;
import de.cubbossa.tinytranslations.Translator;
import de.cubbossa.tinytranslations.persistent.YamlMessageStorage;
import de.cubbossa.tinytranslations.persistent.YamlStyleStorage;
import de.pqtriick.homes.commands.admin.CheckHomes;
import de.pqtriick.homes.commands.admin.ReloadValues;
import de.pqtriick.homes.commands.player.AddHome;
import de.pqtriick.homes.commands.player.Homecommand;
import de.pqtriick.homes.files.Config;
import de.pqtriick.homes.files.Messages;
import de.pqtriick.homes.files.Options;
import de.pqtriick.homes.listener.compass.NavigationScheduler;
import de.pqtriick.homes.listener.initalizer.JoinConfig;
import de.pqtriick.homes.listener.initalizer.VersionInform;
import de.pqtriick.homes.listener.inventory.*;
import de.pqtriick.homes.utils.Update.VersionCheck;
import de.pqtriick.homes.utils.bstats.Metrics;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.Bukkit;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Locale;

public final class Homes extends JavaPlugin {

    public static Homes instance;
    public static boolean hasUpdate;
    private static int bstatsid = 20215;

    private BukkitAudiences audiences;
    private Translator translator;

    @Override
    public void onEnable() {
        instance = this;
        Config.createDir();
        Options.initOptionsFile();

        audiences = BukkitAudiences.create(this);
        TinyTranslations.enable(new File(getDataFolder(), "/../"));
        translator = TinyTranslations.application("Homes");
        translator.addMessages(TinyTranslations.messageFieldsFromClass(Messages.class));
        translator.setMessageStorage(new YamlMessageStorage(new File(getDataFolder(), "/lang/")));
        translator.setStyleStorage(new YamlStyleStorage(new File(getDataFolder(), "/lang/styles.yml")));
        reloadLocales();

        Bukkit.getPluginManager().registerEvents(new JoinConfig(), this);
        Bukkit.getPluginManager().registerEvents(new MainInventoryClick(), this);
        Bukkit.getPluginManager().registerEvents(new DeleteInventory(), this);
        Bukkit.getPluginManager().registerEvents(new ActionInventory(), this);
        Bukkit.getPluginManager().registerEvents(new VersionInform(), this);
        Bukkit.getPluginManager().registerEvents(new AdminInventory(), this);
        Bukkit.getPluginManager().registerEvents(new MultipleSiteInventory(), this);

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

    public void reloadLocales() {
        translator.saveLocale(Locale.ENGLISH);
        translator.loadStyles();
        translator.loadLocales();
    }

    public void sendMessage(CommandSender sender, ComponentLike component) {
        Audience audience = audiences.sender(sender);
        if (component instanceof Message) {
            component = translator.process((Message) component, audience);
        }
        audience.sendMessage(component);
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
