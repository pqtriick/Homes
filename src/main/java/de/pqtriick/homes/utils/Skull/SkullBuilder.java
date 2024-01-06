package de.pqtriick.homes.utils.Skull;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.cubbossa.tinytranslations.Message;
import de.cubbossa.tinytranslations.MessageFormat;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;

/**
 * @author pqtriick_
 * @created 19:31, 04.10.2023
 */

public class SkullBuilder {

    public static ItemStack getCustomSkull(String url, Message name, Message lore) {
        return getCustomSkull(url, name.toString(MessageFormat.LEGACY_PARAGRAPH), lore.toString(MessageFormat.LEGACY_PARAGRAPH).split("\n"));
    }

    public static ItemStack getCustomSkull(String url, String name, String... lore) {

        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        if (url.isEmpty()) return head;

        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", url));

        try {
            Method mtd = skullMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            mtd.setAccessible(true);
            mtd.invoke(skullMeta, profile);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            ex.printStackTrace();
        }
        skullMeta.setDisplayName(name);
        skullMeta.setLore(Arrays.asList(lore));
        head.setItemMeta(skullMeta);
        return head;
    }
}
