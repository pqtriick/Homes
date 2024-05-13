package de.pqtriick.homes.files;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.commands.admin.ReloadMessages;
import de.pqtriick.homes.commands.player.Homecommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * @author pqtriick_
 * @created 16:11, 07.10.2023
 */

public class Messages {

    public static File msgfile = new File(Homes.getInstance().getDataFolder().getPath(), "messages.yml");
    public static FileConfiguration msgconfig = YamlConfiguration.loadConfiguration(msgfile);

    public static String PREFIX = "";
    public static String NOPERM = "";
    public static String HOME_EXISTS = "";
    public static String ADD_SUCCESS = "";
    public static String ADD_INFO = "";
    public static String LEFT_CLICK = "";
    public static String RIGHT_CLICK = "";
    public static String HOME_REACHED = "";
    public static String HOME_TELEPORT = "";
    public static String COMPASS_DESC = "";
    public static String ACTION_CANCEL = "";
    public static String HOME_DELETE = "";
    public static String HOME_DELETE_GUI = "";
    public static String HOME_DELETE_LORE_GUI = "";
    public static String ACTION_CANCEL_GUI = "";
    public static String ACTION_CANCEL_LORE_GUI = "";
    public static String HOME_TELEPORT_GUI = "";
    public static String HOME_TELEPORT_LORE_GUI = "";
    public static String HOME_NAVIGATION = "";
    public static String HOME_NAVIGATION_LORE = "";
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
        ReloadMessages.reloadMessages();
    }
}
