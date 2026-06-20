package de.pqtriick.homes.data.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PermissionsConfigEnum {

    PERM_HOME_CREATE("homes.create", "homes.create"),
    PERM_HOME_USE("homes.use", "homes.use"),
    PERM_HOME_TELEPORT("homes.teleport", "homes.teleport"),
    PERM_HOME_NAVIGATE("homes.navigate", "homes.navigate"),
    PERM_HOME_ADMIN("homes.admin", "homes.admin"),
    PERM_HOME_PERMENABLED("homes.permissions.enabled", "FALSE"),
    PERM_HOME_RANKDEFAULT("homes.permissions.default", "40");

    final String path;
    final String value;

}
