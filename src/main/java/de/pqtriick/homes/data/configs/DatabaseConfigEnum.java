package de.pqtriick.homes.data.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DatabaseConfigEnum {

    DB_ENABLED("database.enabled", "false"),
    DB_HOST("database.host", "localhost"),
    DB_DB("database.database", "homes"),
    DB_USER("database.user", "user"),
    DB_PASS("database.password", "password");

    final String path;
    final String value;

}
