package de.pqtriick.homes.data.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;


@AllArgsConstructor
@Getter
public enum HomeGUIConfigEnum {

    SLOT_1("gui.slot0", Material.BLACK_STAINED_GLASS_PANE),
    SLOT_2("gui.slot1", Material.ENDER_EYE),
    SLOT_3("gui.slot2", Material.BLACK_STAINED_GLASS_PANE),
    SLOT_4("gui.slot3", Material.BLACK_STAINED_GLASS_PANE),
    SLOT_5("gui.slot4", Material.NAME_TAG),
    SLOT_6("gui.slot5", Material.BLACK_STAINED_GLASS_PANE),
    SLOT_7("gui.slot6", Material.BLACK_STAINED_GLASS_PANE),
    SLOT_8("gui.slot7", Material.RECOVERY_COMPASS),
    SLOT_9("gui.slot8", Material.BLACK_STAINED_GLASS_PANE);

    final String path;
    final Material material;
}
