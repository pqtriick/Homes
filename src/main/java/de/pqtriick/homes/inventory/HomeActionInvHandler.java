package de.pqtriick.homes.inventory;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.commands.player.RenameCommand;
import de.pqtriick.homes.data.configs.MessageEnum;
import de.pqtriick.homes.data.configs.OptionsConfigEnum;
import de.pqtriick.homes.data.configs.PermissionsConfigEnum;
import de.pqtriick.homes.data.homes.HomeObject;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class HomeActionInvHandler {

    private static HashMap<Player, BukkitTask> teleportDelayMap = new HashMap<>();

    public static void teleport(Player player, HomeObject home) {
        if (!Homes.getInstance().getPermissionConfig().hasPermission(player, PermissionsConfigEnum.PERM_HOME_TELEPORT)) {
            player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.PREFIX.getPath()).append(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.NO_PERMISSION.getPath())));
            return;
        }
        player.closeInventory();
        if (Homes.getInstance().getOptionsConfig().getOptionsConfig().get(OptionsConfigEnum.OPTIONS_TPDELAY_ENABLED.getPath()).equals("true")) {
            try {
                int delayInSec = Integer.parseInt(Homes.getInstance().getOptionsConfig().getOptionsConfig().getString(OptionsConfigEnum.OPTIONS_TPDELAY_DELAY.getPath()));
                if (teleportDelayMap.containsKey(player)) {
                    teleportDelayMap.get(player).cancel();
                }
                HomeObject finalHome = home;
                BukkitTask tpRunnable = new BukkitRunnable() {
                    int times = delayInSec;
                    @Override
                    public void run() {
                        if (times == 0) {
                            player.teleport(new Location(finalHome.getWorld(), finalHome.getX(), finalHome.getY(), finalHome.getZ()));
                            player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.PREFIX.getPath()).append(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.ACTION_GUI_TELEPORT_SUCCESS.getPath())));
                            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 2);
                            this.cancel();
                            teleportDelayMap.remove(player);
                        } else {
                            Component message = Homes.getInstance().getMessageConfig().getMSG(MessageEnum.TELEPORT_ACTIONBAR.getPath());
                            message = message.replaceText(TextReplacementConfig.builder().matchLiteral("%seconds%").replacement(Component.text(times)).build());
                            player.sendActionBar(message);
                            times--;
                        }
                    }
                }.runTaskTimer(Homes.getInstance(), 0, 20L);
                teleportDelayMap.put(player, tpRunnable);
                return;
            } catch (Exception e) {
                System.out.println("{HOMES ERROR} Variable delay for teleport is not an Integer!");
            }
        }
        player.teleport(new Location(home.getWorld(), home.getX(), home.getY(), home.getZ()));
        player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.PREFIX.getPath()).append(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.ACTION_GUI_TELEPORT_SUCCESS.getPath())));
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 2);
    }

    public static void rename(Player player, HomeObject home) {
        RenameCommand.selectedHomeMap.put(player, home.getName());
        player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.PREFIX.getPath()).append(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.ACTION_GUI_RENAME_TEXT_1.getPath())));
        player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.PREFIX.getPath()).append(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.ACTION_GUI_RENAME_TEXT_2.getPath())));
        player.closeInventory();
    }

    public static void navigate(Player player, HomeObject home) {
        if (!Homes.getInstance().getPermissionConfig().hasPermission(player, PermissionsConfigEnum.PERM_HOME_NAVIGATE)) {
            player.sendMessage(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.PREFIX.getPath()).append(Homes.getInstance().getMessageConfig().getMSG(MessageEnum.NO_PERMISSION.getPath())));
            return;
        }
        Homes.getInstance().getNavigationScheduler().activeNavigation.put(player, new Location(home.getWorld(), home.getX(), home.getY(), home.getZ()));
        player.closeInventory();
    }
}
