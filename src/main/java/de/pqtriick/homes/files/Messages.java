package de.pqtriick.homes.files;

import de.cubbossa.tinytranslations.Message;
import de.cubbossa.tinytranslations.MessageBuilder;
import de.pqtriick.homes.Homes;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author pqtriick_
 * @created 16:11, 07.10.2023
 */

public class Messages {

	public static final Message PREFIX = new MessageBuilder("prefix")
			.withDefault("HOMES")
			.build();
	public static final Message UNKNOWN_USER = new MessageBuilder("admin.unknown_user")
			.withDefault("<prefix_negative>This user is not known by the plugin.</prefix_negative>")
			.build();
	public static final Message NO_HOMES_OTHER = new MessageBuilder("admin.no_homes")
			.withDefault("<prefix_negative>This user doesn't have any homes.</prefix_negative>")
			.build();
	public static final Message UPDATE_AVAILABLE = new MessageBuilder("admin.update_available")
			.withDefault("<primary>There is a new Update available for §3Homes !</primary>\n" +
					"<url>https://www.spigotmc.org/resources/homes.112984/</url>")
			.build();
	public static final Message RELOAD_SUCCESS = new MessageBuilder("admin.reload_success")
			.withDefault("<prefix>Sucessfully reloaded values!</prefix>")
			.build();
	public static final Message RELOAD_FAIL = new MessageBuilder("admin.reload_failure")
			.withDefault("<prefix_negative>Could not reload values!\n{reason}</prefix_negative>")
			.withPlaceholder("reason")
			.build();
	public static final Message NAVIGATE_SAME_WORLD = new MessageBuilder("navigate.same_world_required")
			.withDefault("<prefix_negative>You have to be in the same world to navigate to the location!</prefix_negative>")
			.build();
	public static final Message SYNTAX_CHECKHOMES = new MessageBuilder("command_syntax.checkhomes")
			.withDefault("<prefix_negative>Usage: <cmd_syntax>/checkhomes <arg>player</arg></cmd_syntax></prefix_negative>")
			.build();
	public static final Message SYNTAX_ADDHOME = new MessageBuilder("command_syntax.addhome")
			.withDefault("<prefix_negative>Usage: <cmd_syntax>/addhome <arg>name</arg></cmd_syntax></prefix_negative>")
			.build();
	public static final Message NO_HOMES = new MessageBuilder("no_homes")
			.withDefault("<prefix_negative>You don't have any homes!</prefix_negative>")
			.build();
	public static final Message HOMEEXISTS = new MessageBuilder("homeexists")
			.withDefault("<prefix_negative>This home already exists in your list of homes.")
			.build();
	public static final Message ADDSUCESS = new MessageBuilder("addsucess")
			.withDefault("<prefix>Sucessfully added <text_hl>{homename}</text_hl> to your homes.</prefix>")
			.build();
	public static final Message ADDINFO = new MessageBuilder("addinfo")
			.withDefault("<prefix_warning>You can access all of your homes with <cmd_syntax>/homes</cmd_syntax></prefix_warning>")
			.build();
	public static final Message MAX_HOMES = new MessageBuilder("max_homes")
			.withDefault("<prefix_negative>You have reached the maximum amount of homes.</prefix_negative>")
			.build();
	public static final Message HOME_NAME = new MessageBuilder("gui.home.name")
			.withDefault("<yellow>{home}</yellow>")
			.withPlaceholder("home")
			.build();
	public static final Message HOME_LORE = new MessageBuilder("gui.home.lore")
			.withDefault("<text>➥ </text><positive>Leftclick to access</positive>\n"
					+ "<text>➥ </text><negative>Rightclick to delete</negative>")
			.build();
	public static final Message REACHEDHOME = new MessageBuilder("reachedhome")
			.withDefault("<prefix>You have reached your home.</prefix>")
			.build();
	public static final Message TELEPORT = new MessageBuilder("teleport")
			.withDefault("<prefix>Sucessfully teleported to home!</prefix>")
			.build();
	public static final Message COMPASSDESC = new MessageBuilder("compassdesc")
			.withDefault("<text>➥ </text>&6Currently navigating to&7: §e{homename}")
			.build();
	public static final Message ACTIONCANCEL = new MessageBuilder("actioncancel")
			.withDefault("<prefix>Sucessfully cancelled action.</prefix>")
			.build();
	public static final Message HOMEDELETION = new MessageBuilder("homedeletion")
			.withDefault("<prefix_positive>Sucessfully deleted home.")
			.build();
	public static final Message GUI_DELHOME = new MessageBuilder("gui.delhome.name")
			.withDefault("<positive>Delete home</positive>")
			.build();
	public static final Message GUI_DELHOMELORE = new MessageBuilder("gui.delhome.lore")
			.withDefault("<text>➥ </text><positive>Click to delete</positive>")
			.build();
	public static final Message GUI_CANCELACTION = new MessageBuilder("gui.cancelaction.name")
			.withDefault("<negative>Cancel action")
			.build();
	public static final Message GUI_CANCELACTIONLORE = new MessageBuilder("gui.cancelaction.lore")
			.withDefault("<text>➥ </text><negative>Click to cancel")
			.build();
	public static final Message GUI_TPHOME = new MessageBuilder("gui.tphome.name")
			.withDefault("<light_purple>Teleport to home</light_purple>")
			.build();
	public static final Message GUI_TPHOMELORE = new MessageBuilder("gui.tphome.lore")
			.withDefault("<text>➥ </text><light_purple>Click to teleport</light_purple>")
			.build();
	public static final Message GUI_NAVIGATION = new MessageBuilder("gui.navigation.name")
			.withDefault("<gold>Navigate to home</gold>")
			.build();
	public static final Message GUI_NAVIGATIONLORE = new MessageBuilder("gui.navigation.lore")
			.withDefault("<text>➥ </text><gold>Click to navigate</gold>")
			.build();

	public static void send(CommandSender sender, ComponentLike message) {
		Homes.getInstance().sendMessage(sender, message);
	}
}
