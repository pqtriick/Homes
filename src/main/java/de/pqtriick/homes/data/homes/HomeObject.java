package de.pqtriick.homes.data.homes;

import org.bukkit.World;

public class HomeObject {

    private String name;
    private double x;
    private double y;
    private double z;
    private World world;

    public HomeObject(String name, double x, double y, double z, World world) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public World getWorld() {
        return world;
    }

    public void setName(String name) {
        this.name = name;
    }
}
