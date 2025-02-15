package de.pqtriick.homes.data.configs;

import de.pqtriick.homes.Homes;
import de.pqtriick.homes.data.Config;
import de.pqtriick.homes.utils.enums.MessageEnum;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

public class MessageConfig {

    public static HashMap<String, Component> messages = new HashMap<>();

    public static File messageFile = new File(Homes.getInstance().getDataFolder().getPath(), "messages.yml");
    public static FileConfiguration messageConfig = YamlConfiguration.loadConfiguration(messageFile);

    public static void init() {
        if (!messageFile.exists()) {
            Config.createFile(messageFile);
            HashMap<String, String> legacy = MessageEnum.getLegacyMessage();
            for (String k : legacy.keySet()) {
                messageConfig.set(k, legacy.get(k));
            }
            Config.saveFile(messageConfig, messageFile);
            messages = MessageEnum.getDefaultMessages();
        } else {
            getMessages();
        }
    }

    public static void getMessages() {
        messageConfig = YamlConfiguration.loadConfiguration(messageFile);
        messages.clear();
        messages = MessageEnum.getMessagesFromFile();
    }

    public static Component getMSG(String path) {
        return messages.get(path);
    }
}
