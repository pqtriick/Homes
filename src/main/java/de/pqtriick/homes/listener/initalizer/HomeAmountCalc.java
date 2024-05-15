package de.pqtriick.homes.listener.initalizer;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.files.Config;
import org.bukkit.entity.Player;

import java.io.File;

public class HomeAmountCalc {

    private static File optionsFile = new File(Homes.getInstance().getDataFolder().getPath(), "options.yml");
    public static int getHomeAmount(Player player) {
        int amt = 0;
        for (String rankperm : Config.getConfiguration(optionsFile).getConfigurationSection("options.homes").getKeys(false)) {
            String amount = Config.getConfiguration(optionsFile).getString("options.homes." + rankperm + ".maxsize");
            if (player.hasPermission(rankperm)) {
                amt = Integer.parseInt(amount);
            }
        }
        return amt;
    }

}
