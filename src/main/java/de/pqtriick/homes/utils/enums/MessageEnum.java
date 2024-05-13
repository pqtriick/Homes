package de.pqtriick.homes.utils.enums;

import de.pqtriick.homes.files.Config;
import de.pqtriick.homes.files.Messages;

public enum MessageEnum {

    PREFIX("messages.prefix"),
    NOPERM("messages.nopermission"),
    HOME_EXISTS("messages.homeexists"),
    ADD_SUCCESS("messages.addsucess"),
    ADD_INFO("messages.addinfo"),
    LEFT_CLICK("messages.leftclick"),
    RIGHT_CLICK("messages.rightclick"),
    HOME_REACHED("messages.reachedhome"),
    HOME_TELEPORT("messages.teleport"),
    COMPASS_DESC("messages.compassdesc"),
    ACTION_CANCEL("messages.actioncancel"),
    HOME_DELETE("messages.homedeletion"),
    HOME_DELETE_GUI("messages.guidelhome"),
    HOME_DELETE_LORE_GUI("messages.guidelhomelore"),
    HOME_CANCEL_ACTION_GUI("messages.guicancelaction"),
    HOME_CANCEL_ACTION_LORE_GUI("messages.guicancelactionlore"),
    HOME_TELEPORT_GUI("messages.guitphome"),
    HOME_TELEPORT_LORE_GUI("messages.guitphomelore"),
    HOME_NAVIGATION_GUI("messages.guinavigation"),
    HOME_NAVIGATION_LORE_GUI("messages.guinavigationlore");


    final String path;

    MessageEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public static String replaceString(String path, String oldArg, String newArg) {
        String s = Config.getConfiguration(Messages.msgfile).getString(path);
        if (oldArg == null && newArg == null) {
            s = s.replace("&", "§");
        } else {
            s = s.replace("&", "§");
            s = s.replace(oldArg, newArg);
        }
        return s;

    }
}
