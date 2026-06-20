package de.pqtriick.homes.data.configs;

import de.pqtriick.homes.Homes;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
public class MessageConfig {

    public HashMap<String, Component> messages = new HashMap<>();

    private File messageFile;
    private FileConfiguration messageConfig;

    public MessageConfig() {
        messageFile = new File(Homes.getInstance().getDataFolder().getPath(), "messages.yml");
        if (!messageFile.exists()) {
            Homes.getInstance().getConfigManager().createFile(messageFile);
        }
        messageConfig = YamlConfiguration.loadConfiguration(messageFile);
        for (MessageEnum entry : MessageEnum.values()) {
            if (!messageConfig.contains(entry.getPath())) {
                messageConfig.set(entry.getPath(), entry.getValue());
            }
        }
        Homes.getInstance().getConfigManager().saveFile(messageConfig, messageFile);
        initMessages();
    }

    public void initMessages() {
        messages.clear();
        messages = MessageEnum.getMessagesFromFile(messageConfig);
    }

    public Component getMSG(String path) {
        return messages.get(path);
    }
}
