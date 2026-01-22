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
                System.out.println("! Could not connect to database! Check your config.");
            }
        }

    }

    public void close() {
        if (isConnected()) {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException("! Could not close database connection.");
            }
        }

    }

    public Connection getCon() {
        try {
            if (con == null || con.isClosed() || !con.isValid(2)) {
                connect();
            }
            return con;
        } catch (SQLException e) {
            throw new RuntimeException("! Failed to get Connection!");
        }
    }


    private boolean isConnected() {
        return con != null;
    }
}