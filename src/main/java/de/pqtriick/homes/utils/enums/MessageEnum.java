package de.pqtriick.homes.utils.enums;

import de.pqtriick.homes.data.configs.MessageConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.HashMap;

public enum MessageEnum {

    PREFIX("messages.prefix", "<mm><gradient:#41D1FF:#55FDB2>Homes</gradient> <gray> | <reset>"),
    ACTION_GUI_TITLE("messages.action_gui_title", "§bSelect an action"),
    ACTION_GUI_TELEPORT("messages.action_gui_teleport", "§5Teleport to home"),
    ACTION_GUI_TELEPORT_SUCCESS("messages.action_gui_teleport_success", "§aSuccessfully teleported to home!"),
    ACTION_GUI_RENAME("messages.action_gui_rename", "§6Rename home"),
    ACTION_GUI_RENAME_TEXT_1("messages.action_gui_rename_text_1", "§eYou can now use /rename <Text> to rename your home!"),
    ACTION_GUI_RENAME_TEXT_2("messages.action_gui_renane_text_2", "§eType cancel to cancel the renaming process"),
    ACTION_GUI_NAVIGATE("messages.action_gui_navigate", "§3Navigate to home"),
    ADD_HOME_USAGE("messages.add_home_usage", "§6/addhomeperm <name> <homeamount>"),
    ADD_HOME_WRONG_INPUT("messages.add_home_wrong_input", "§cWrong input!"),
    HOMES_GUI_ACCESS("messages.homes_gui_access", "§2Leftclick to view more!"),
    HOMES_GUI_DELETE("messages.homes_gui_delete", "§cRightclick to delete home!"),
    HOMES_GUI_DELETE_CANCEL_SUCCESS("messages.homes_gui_delete_cancel_success", "§aSuccessfully cancelled action!"),
    HOMES_GUI_DELETE_HOME_SUCCESS("messages.homes_gui_delete_home_success", "§aSuccessfully deleted home!"),
    HOMES_GUI_DELETE_TITLE("messages.homes_gui_delete_title", "§cDelete home"),
    HOMES_GUI_DELETE_CONFIRM("messages.homes_gui_delete_confirm", "§2Confirm deletion"),
    HOMES_GUI_DELETE_CANCEL("messages.homes_gui_delete_cancel", "§cCancel deletion"),
    HOMES_GUI_NEXT_SITE("messages.homes_next_site", "§7§l> §eView next site"),
    HOMES_GUI_TITLE("messages.homes_gui_title", "§3Your Homes §7(§b%homes% §7/ §b%maxhomes%§7)"),
    HOMES_NO_HOMES_1("messages.no_homes_1", "§cYou don't have any homes!"),
    HOMES_NO_HOMES_2("messages.no_homes_2", "§eYou can create a new home with §6/addhome <name>"),
    HOMES_NO_SPACE("messages.home_no_space", "§cYou don't have enough space to add more homes"),
    HOME_SAVED_SUCCESS_1("messages.home_save_success_1", "§aSuccessfully saved home!"),
    HOME_SAVED_SUCCESS_2("messages.home_save_success_2", "§aYou can now access it with /homes"),
    NAVIGATION_DISTANCE("messages.navigation_distance", "<> §6Blocks distance"),
    NAVIGATION_REACHED("messages.navigation_reached", "§aHome reached"),

    RELOAD_MESSAGE_SUCCESS("messages.reload_messages_success", "§aSuccessfully reloaded messages"),
    RENAME_WRONG_INPUT("messages.rename_wrong_input", "§cYou can't use spaces or leave it empty! Use _ Instead"),
    RENAME_CANCELLED("messages.rename_cancelled", "§aSuccessfully renamed home"),
    RENAME_SUCCESS("messages.rename_success", "§aSuccessfully renamed home");



    private final String path;
    private final String defaultMessage;


    MessageEnum(String path, String defaultMessage) {
        this.path = path;
        this.defaultMessage = defaultMessage;
    }

    public String getPath() {
        return path;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public static HashMap<String, String> getLegacyMessage() {
        HashMap<String, String> map = new HashMap<>();
        for (MessageEnum m : MessageEnum.values()) {
            map.put(m.getPath(), m.getDefaultMessage());
        }
        return map;
    }

    public static HashMap<String, Component> getDefaultMessages() {
        HashMap<String, Component> map = new HashMap<>();
        for (MessageEnum m : MessageEnum.values()) {
            if (m.getDefaultMessage().contains("<mm>")) {
                String msg = m.getDefaultMessage();
                msg = msg.replace("<mm>", "");
                Component comp = MiniMessage.miniMessage().deserialize(msg);
                map.put(m.getPath(), comp);
            } else {
                map.put(m.getPath(), Component.text(m.getDefaultMessage()));
            }
        }
        return map;
    }

    public static HashMap<String, Component> getMessagesFromFile() {
        HashMap<String, Component> map = new HashMap<>();
        for (MessageEnum m : MessageEnum.values()) {
            String path = m.getPath();
            String message = MessageConfig.messageConfig.getString(path);
            if(message.contains("<mm>")) {
                message = message.replace("<mm>", "");
                map.put(path, MiniMessage.miniMessage().deserialize(message));
            } else {
                map.put(path, Component.text(message));
            }
        }
        return map;
    }
}
