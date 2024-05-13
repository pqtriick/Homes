package de.pqtriick.homes.commands.admin;

import de.pqtriick.homes.files.Config;
import de.pqtriick.homes.files.Messages;
import de.pqtriick.homes.files.Permissions;
import de.pqtriick.homes.utils.enums.MessageEnum;
import org.apache.logging.log4j.message.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadMessages implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        if (Permissions.permissionsConfig.getString("homes.admin") == null || player.hasPermission(Permissions.permissionsConfig.getString("homes.admin"))) {
            try {
                reloadMessages();
                player.sendMessage(Messages.PREFIX + " §aSuccessfully Reloaded Messages");
            } catch (Exception e) {
                player.sendMessage(" §cSomething went wrong while reloading Messages...");
            }
        }
        return false;
    }



    public static void reloadMessages() {
        Messages.PREFIX = MessageEnum.replaceString(MessageEnum.PREFIX.getPath(), null, null);
        Messages.NOPERM = MessageEnum.replaceString(MessageEnum.NOPERM.getPath(), null, null);
        Messages.HOME_EXISTS = MessageEnum.replaceString(MessageEnum.HOME_EXISTS.getPath(), null, null);
        Messages.ADD_INFO = MessageEnum.replaceString(MessageEnum.ADD_INFO.getPath(), null, null);
        Messages.LEFT_CLICK = MessageEnum.replaceString(MessageEnum.LEFT_CLICK.getPath(), null, null);
        Messages.RIGHT_CLICK = MessageEnum.replaceString(MessageEnum.RIGHT_CLICK.getPath(), null, null);
        Messages.HOME_REACHED = MessageEnum.replaceString(MessageEnum.HOME_REACHED.getPath(), null, null);
        Messages.HOME_TELEPORT = MessageEnum.replaceString(MessageEnum.HOME_TELEPORT.getPath(), null, null);
        Messages.ACTION_CANCEL = MessageEnum.replaceString(MessageEnum.ACTION_CANCEL.getPath(), null, null);
        Messages.HOME_DELETE = MessageEnum.replaceString(MessageEnum.HOME_DELETE.getPath(), null, null);
        Messages.HOME_DELETE_GUI = MessageEnum.replaceString(MessageEnum.HOME_DELETE_GUI.getPath(), null, null);
        Messages.HOME_DELETE_LORE_GUI = MessageEnum.replaceString(MessageEnum.HOME_DELETE_LORE_GUI.getPath(), null, null);
        Messages.ACTION_CANCEL_GUI = MessageEnum.replaceString(MessageEnum.HOME_CANCEL_ACTION_GUI.getPath(), null, null);
        Messages.ACTION_CANCEL_LORE_GUI = MessageEnum.replaceString(MessageEnum.HOME_CANCEL_ACTION_LORE_GUI.getPath(), null, null);
        Messages.HOME_TELEPORT_GUI = MessageEnum.replaceString(MessageEnum.HOME_TELEPORT_GUI.getPath(), null, null);
        Messages.HOME_TELEPORT_LORE_GUI = MessageEnum.replaceString(MessageEnum.HOME_TELEPORT_LORE_GUI.getPath(), null, null);
        Messages.HOME_NAVIGATION = MessageEnum.replaceString(MessageEnum.HOME_NAVIGATION_GUI.getPath(), null,null);
        Messages.HOME_NAVIGATION_LORE = MessageEnum.replaceString(MessageEnum.HOME_NAVIGATION_LORE_GUI.getPath(), null,null);


    }
}
