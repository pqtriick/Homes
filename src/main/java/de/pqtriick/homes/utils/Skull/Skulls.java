package de.pqtriick.homes.utils.Skull;

/**
 * @author pqtriick_
 * @created 19:40, 04.10.2023
 */

public enum Skulls {
    A("");



    private final String idTag;

    private Skulls(String texture) {
        this.idTag = texture;
    }

    public String getTexture() {
        return idTag;

    }
}
