package de.pqtriick.homes.utils;

import de.cubbossa.tinytranslations.Message;
import de.cubbossa.tinytranslations.MessageFormat;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author pqtriick_
 * @created 19:28, 04.10.2023
 */

public class ItemBuilder {

    private ItemStack item;
    private ItemMeta meta;


    public ItemBuilder(Material material, short ID) {
        item = new ItemStack(material, 1, ID);
        meta = item.getItemMeta();
    }
    public ItemBuilder(Material material) {
        this(material, (short) 0);
    }
    public ItemBuilder setName(String name) {
        meta.setDisplayName(name);
        return this;
    }
    public ItemBuilder setName(Message message) {
        meta.setDisplayName(message.toString(MessageFormat.LEGACY_PARAGRAPH));
        return this;
    }

    public ItemBuilder setLore(String... Lore) {
        meta.setLore(Arrays.asList(Lore));
        return this;
    }
    public ItemBuilder setLore(Message message) {
		List<String> values = Arrays.asList(message.toString(MessageFormat.LEGACY_PARAGRAPH).split("\n"));
        meta.setLore(values);
        return this;
    }

    public ItemBuilder setAmount(int amt) {
        item.setAmount(amt);
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }
}
