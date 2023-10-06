package de.pqtriick.homes.utils.Skull;

/**
 * @author pqtriick_
 * @created 19:40, 04.10.2023
 */

public enum Skulls {
    HOUSE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Y3Y2RlZWZjNmQzN2ZlY2FiNjc2YzU4NGJmNjIwODMyYWFhYzg1Mzc1ZTlmY2JmZjI3MzcyNDkyZDY5ZiJ9fX0="),
    GREEN("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWMwMWY2Nzk2ZWI2M2QwZThhNzU5MjgxZDAzN2Y3YjM4NDMwOTBmOWE0NTZhNzRmNzg2ZDA0OTA2NWM5MTRjNyJ9fX0="),
    RED("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTQ4ZDdkMWUwM2UxYWYxNDViMDEyNWFiODQxMjg1NjcyYjQyMTI2NWRhMmFiOTE1MDE1ZjkwNTg0MzhiYTJkOCJ9fX0=");



    private final String idTag;

    private Skulls(String texture) {
        this.idTag = texture;
    }

    public String getTexture() {
        return idTag;

    }
}
