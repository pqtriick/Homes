package de.pqtriick.homes.database;


import de.pqtriick.homes.Homes;
import de.pqtriick.homes.data.homes.HomeObject;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class SQLMethods {

    public SQLMethods() {
        try {
            Homes.getInstance().getSql().getCon().createStatement().executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Homes(uuid VARCHAR(36), name VARCHAR(36), x VARCHAR(36), y VARCHAR(36), z VARCHAR(36), world VARCHAR(36))");
            Homes.getInstance().getSql().getCon().createStatement().executeUpdate(
                    "CREATE TABLE IF NOT EXISTS HomeAmounts(uuid VARCHAR(36), amount VARCHAR(4))");
        } catch (SQLException e) {
            throw new RuntimeException("! Failed to create database tables.");
        }
    }

    public CompletableFuture<List<HomeObject>> getHomes(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            List<HomeObject> homes = new ArrayList<>();
            try (PreparedStatement stmt = Homes.getInstance().getSql().getCon().prepareStatement(
                    "SELECT name, x, y, z, world FROM Homes WHERE uuid = ? ")) {
                stmt.setString(1, uuid.toString());
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    homes.add(new HomeObject(rs.getString("name"), rs.getDouble("x"), rs.getDouble("y"),
                            rs.getDouble("z"), Bukkit.getWorld(rs.getString("world"))));
                }
                return homes;
            } catch (SQLException e) {
                throw new RuntimeException("! Failed to get homes of player (UUID : " + uuid + ")");
            }
        });

    }

    public CompletableFuture<HomeObject> getHomeByName(UUID uuid, String name) {
        return CompletableFuture.supplyAsync(() -> {
            try (PreparedStatement stmt = Homes.getInstance().getSql().getCon().prepareStatement("SELECT x, y, z, world FROM Homes WHERE uuid = ? AND name = ?")) {
                stmt.setString(1, uuid.toString());
                stmt.setString(2, name);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new HomeObject(name, rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"),
                            Bukkit.getWorld("world"));
                }
                return null;
            } catch (SQLException e) {
                throw new RuntimeException("! Failed to get home by name of player!");
            }
        });
    }

    public CompletableFuture<Boolean> userExists(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            try (PreparedStatement stmt = Homes.getInstance().getSql().getCon().prepareStatement("SELECT uuid FROM Homes WHERE uuid = ?")) {
                stmt.setString(1, uuid.toString());
                ResultSet rs = stmt.executeQuery();
                return rs.next();
            } catch (SQLException e) {
                throw new RuntimeException("! Failed to check if user exists!");
            }
        });
    }

    public CompletableFuture<Void> setHomeAmount(UUID uuid, int amount) {
        return CompletableFuture.runAsync(() -> {
            try (PreparedStatement stmt = Homes.getInstance().getSql().getCon().prepareStatement("UPDATE HomeAmount SET amount = ? WHERE uuid = ?")) {
                stmt.setInt(1, amount);
                stmt.setString(2, uuid.toString());
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("! Failed to Update homeamount for player!");
            }
        });
    }

    public CompletableFuture<Integer> getHomeAmount(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            try (PreparedStatement stmt = Homes.getInstance().getSql().getCon().prepareStatement("SELECT amount FROM HomeAmount WHERE uuid = ?")) {
                stmt.setString(1, uuid.toString());
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("amount");
                }
            } catch (SQLException e) {
                throw new RuntimeException("! Failed to get Homeamount of player!");
            }
            return -1;
        });
    }

    public CompletableFuture<Void> addHome(UUID uuid, String name, double x, double y, double z, String world) {
        return CompletableFuture.runAsync(() -> {
            try (PreparedStatement stmt = Homes.getInstance().getSql().getCon().prepareStatement("INSERT INTO Homes(uuid, name, x, y, z, world) VALUES (?, ?, ?, ?, ?, ?)")) {
                stmt.setString(1, uuid.toString());
                stmt.setString(2, name);
                stmt.setDouble(3, x);
                stmt.setDouble(4, y);
                stmt.setDouble(5, z);
                stmt.setString(6, world);
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("! Failed to insert new Home into database!");
            }
        });
    }

    public CompletableFuture<Void> deleteHome(UUID uuid, String name) {
        return CompletableFuture.runAsync(() -> {
            try (PreparedStatement stmt = Homes.getInstance().getSql().getCon().prepareStatement("DELETE FROM homes WHERE uuid = ? AND name = ?")) {
                stmt.setString(1, uuid.toString());
                stmt.setString(2, name);
            } catch (SQLException e) {
                throw new RuntimeException("! Failed to delete home of player from database!");
            }
        });
    }
}