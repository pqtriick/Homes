package de.pqtriick.homes.utils.Skull;


import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author pqtriick_
 * @created 19:31, 04.10.2023
 */

public class SkullBuilder {

    public static ItemStack getCustomSkull(String texture, String name, String... lore) {
        final ItemStack head = new ItemStack(Material.PLAYER_HEAD);

        head.editMeta(SkullMeta.class, skullMeta -> {
            final UUID uuid = UUID.randomUUID();
            final PlayerProfile playerProfile = Bukkit.createProfile(uuid, uuid.toString().substring(0, 16));
            playerProfile.setProperty(new ProfileProperty("textures", texture));

            skullMeta.setPlayerProfile(playerProfile);
            skullMeta.displayName(Component.text(name));
            skullMeta.setLore(Arrays.asList(lore));
            head.setItemMeta(skullMeta);
        });
        return head;
    }
}
