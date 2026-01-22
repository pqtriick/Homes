package de.pqtriick.homes.database;


import de.pqtriick.homes.Homes;
import de.pqtriick.homes.data.configs.MessageConfig;
import de.pqtriick.homes.data.homes.HomeObject;
import de.pqtriick.homes.utils.enums.MessageEnum;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class SQLMethods {

    public static void init() {
        try {
            Homes.getSql().getCon().createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS Homes(uuid VARCHAR(36), name VARCHAR(36), x VARCHAR(36), y VARCHAR(36), z VARCHAR(36), world VARCHAR(36))");
            Homes.getSql().getCon().createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS HomeAmounts(uuid VARCHAR(36), amount VARCHAR(4))");
        } catch (SQLException e) {
            throw new RuntimeException("! Failed to create database tables.");
        }
    }

    public static CompletableFuture<List<HomeObject>> getHomes(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            List<HomeObject> homes = new ArrayList<>();
            try (PreparedStatement stmt = Homes.getSql().getCon().prepareStatement(
                    "SELECT name, x, y, z, world FROM Homes WHERE uuid = ? ")) {
                stmt.setString(1, uuid.toString());
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    homes.add(new HomeObject(rs.getString("name"), rs.getDouble("x"), rs.getDouble("y")),
                            rs.getDouble("z"), Bukkit.getWorld(rs.getString("world")));
                }
                return homes;
            } catch (SQLException e) {
                throw new RuntimeException("! Failed to get homes of player (UUID : " + uuid + ")");
            }
        });

    }

    public static CompletableFuture<HomeObject> getHomeByName(UUID uuid, String name) {
        return CompletableFuture.supplyAsync(() -> {

        })
    }

    //NOT SURE IF THIS WORK THO
    public static HomeObject getHomeByName(Player player, String name) {
        try {
            ResultSet rs = Homes.getSql().getResult("SELECT * FROM Homes WHERE uuid = '" + player.getUniqueId() + "' AND name = '" + name + "'");
            while (rs.next()) {
                return new HomeObject(name, Double.parseDouble(rs.getString("x")),
                        Double.parseDouble(rs.getString("y")),
                        Double.parseDouble(rs.getString("z")),
                       Bukkit.getWorld(rs.getString("world")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean userExists(Player player) {
        try {
            ResultSet rs = Homes.getSql().getResult("SELECT uuid FROM Homes WHERE uuid = '" + player.getUniqueId() + "'");
            while (rs.next()) {
                return rs.getString("uuid") != null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void setHomeAmount(Player player, int amount) {
        Homes.getSql().update("UPDATE HomeAmounts SET amount = '" + amount + "' WHERE uuid = '" + player.getUniqueId() + "'");
        player.sendMessage(MessageConfig.getMSG(MessageEnum.PREFIX.getPath()).append(MessageConfig.getMSG(MessageEnum.HOME_SAVED_SUCCESS_1.getPath())));
        player.sendMessage(MessageConfig.getMSG(MessageEnum.PREFIX.getPath()).append(MessageConfig.getMSG(MessageEnum.HOME_SAVED_SUCCESS_2.getPath())));
    }

    public static Integer getHomeAmount(Player player) {
        try {
            ResultSet rs = Homes.getSql().getResult("SELECT amount FROM HomeAmounts WHERE uuid = '" + player.getUniqueId() + "'");
            while (rs.next()) {
                return Integer.parseInt(rs.getString("amount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void addHome(Player player, String name, double x, double y, double z, String world) {
        Homes.getSql().update("INSERT INTO Homes (uuid, name, x, y, z, world) VALUES ('" + player.getUniqueId() + "','" + name + "','" + x + "', '" + y + "', '" + z +"', '" + world + "')");
    }

    public static void deleteHome(Player player, String name) {
        Homes.getSql().update("DELETE FROM Homes WHERE uuid = '" + player.getUniqueId() + "' AND name = '" + name + "'");
    }
}
