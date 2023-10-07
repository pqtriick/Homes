package de.pqtriick.homes.files;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.commands.player.Homecommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * @author pqtriick_
 * @created 16:11, 07.10.2023
 */

public class Messages {

    private static File msgfile = new File(Homes.getInstance().getDataFolder().getPath(), "messages.yml");
    public static FileConfiguration msgconfig = YamlConfiguration.loadConfiguration(msgfile);

    public static void initMessageFile() {
        if(!msgfile.exists()) {
            Config.createFile(msgfile);
            msgconfig.set("messages.prefix", "&3&lHOMES &7| ");
            msgconfig.set("messages.nopermission", "&cYou don't have permission for that.");
            msgconfig.set("messages.homeexists", "&cThis home already exists in your list of homes.");
            msgconfig.set("messages.addsucess", "&aSucessfully added &e%homename% &ato your homes.");
            msgconfig.set("messages.addinfo", "&eYou can access all of your homes with /homes");
            msgconfig.set("messages.leftclick", "&7➥ &aLeftclick to access");
            msgconfig.set("messages.rightclick", "&7➥ &cRightclick to delete");
            msgconfig.set("messages.reachedhome", "&bYou have reached your home.");
            msgconfig.set("messages.teleport", "&aSucessfully teleported to home!");
            msgconfig.set("messages.compassdesc", "&7➥ &6Currently navigating to&7: §e%homename%");
            msgconfig.set("messages.actioncancel", "&aSucessfully cancelled action.");
            msgconfig.set("messages.homedeletion", "&aSucessfully deleted home.");
            msgconfig.set("messages.guidelhome", "&aDelete home");
            msgconfig.set("messages.guidelhomelore", "&7➥ &aClick to delete");
            msgconfig.set("messages.guicancelaction", "&cCancel action");
            msgconfig.set("messages.guicancelactionlore", "&7➥ &cClick to cancel");
            msgconfig.set("messages.guitphome", "&dTeleport to home");
            msgconfig.set("messages.guitphomelore", "&7➥ &dClick to teleport");
            msgconfig.set("messages.guinavigation", "&6Navigate to home");
            msgconfig.set("messages.guinavigationlore", "&7➥ &6Click to navigate");
            Config.saveFile(msgconfig, msgfile);




        }

    }
}
