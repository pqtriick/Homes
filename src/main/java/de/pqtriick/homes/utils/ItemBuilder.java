package de.pqtriick.homes.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

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
    public ItemBuilder setLore(String... Lore) {
        meta.setLore(Arrays.asList(Lore));
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
