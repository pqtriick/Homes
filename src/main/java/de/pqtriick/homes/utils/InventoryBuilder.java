package de.pqtriick.homes.utils;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class InventoryBuilder implements Listener {

    @Getter
    private Inventory inventory;
    private final Map<Integer, ClickAction> actions = new HashMap<>();

    public InventoryBuilder create(Plugin plugin, Component title, int size) {
        this.inventory = Bukkit.createInventory(null, size, title);
        Bukkit.getPluginManager().registerEvents(this, plugin);
        return this;
    }

    public InventoryBuilder item(int slot, ItemStack item) {
        inventory.setItem(slot, item);
        return this;
    }

    public InventoryBuilder item(int slot, ItemStack item, ClickAction action) {
        inventory.setItem(slot, item);
        actions.put(slot, action);
        return this;
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getView().getTopInventory().equals(inventory)) return;
        event.setCancelled(true);
        ClickAction action = actions.get(event.getRawSlot());
        if (action != null && event.getWhoClicked() instanceof Player player) {
            action.action(player, event);
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (!event.getView().getTopInventory().equals(inventory)) return;
        event.setCancelled(true);
    }

    @FunctionalInterface
    public interface ClickAction {
        void action(Player player, InventoryClickEvent event);
    }
}