package de.pqtriick.homes.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQL {


    private Connection con;

    private String HOST = "";
    private String DATABASE = "";
    private String USER = "";
    private String PASSWORD = "";
    private String AUTOCONNECT = "";

    public SQL(String host, String database, String user, String password, String autoconnect) {
        this.HOST = host;
        this.DATABASE = database;
        this.USER = user;
        this.PASSWORD = password;
        this.AUTOCONNECT = autoconnect;
        connect();

    }


    public void connect() {
        if (!isConnected()) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + HOST + ":3306/" + DATABASE + "?autoReconnect=" + AUTOCONNECT, USER, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("[MySQL] Could not connecto to database! Check your config.");
            }
        }

    }

    public void close() {
        if (isConnected()) {
            try {
                con.close();
            } catch (SQLException e) {
            }
        }

    }

    public void update(String qry) {
        if (isConnected()) {
            try {
                con.createStatement().executeUpdate(qry);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ResultSet getResult(String qry) {
        if (isConnected()) {
            try {
                return con.createStatement().executeQuery(qry);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public boolean isConnected() {
        return con != null;
    }
}