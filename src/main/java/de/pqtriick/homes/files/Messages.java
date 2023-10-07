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
    private static FileConfiguration msgconfig;

    public static void initMessageFile() {
        if(!msgfile.exists()) {
            Config.createFile(msgfile);
            msgconfig = YamlConfiguration.loadConfiguration(msgfile);
            msgconfig.set();


        }

    }
}
