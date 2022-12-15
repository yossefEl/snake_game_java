package com.programming.tech.assignment3.helpers;

import java.sql.*;

/**
 * This class manages the database connections as well as its configurations
 */
public class DBHelper {

    // singleton instance
    private static DBHelper instance = null;
    private Connection connection = null;

    private final String DB_NAME = "snake.db";
    private final String TABLE_NAME = "scores";

    private DBHelper() {
    }

    public static DBHelper getInstance() {
        if (instance == null) {
            instance = new DBHelper();
        }
        return instance;
    }

    // connect to a sqlite db
    public boolean connectToDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);
            if (!checkTable()) {
                createTable();
            }
        } catch (ClassNotFoundException | SQLException e) {
            return false;
        }
        return true;
    }

    // check if the table exists
    public boolean checkTable() {
        // check if the table scores exists
        final String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='" + TABLE_NAME + "';";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.next()) {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }

        return true;

    }

    public void createTable() {
        // id, name, score, level
        final String sql = "CREATE TABLE " + TABLE_NAME + " (\n" + " id integer PRIMARY KEY,\n"
                + " name text NOT NULL,\n"
                + " score integer NOT NULL,\n" + " level integer NOT NULL\n" + ");";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // close the connection
    public void closeConnection() throws SQLException {
        connection.close();
    }

    public Connection getConnection() {
        return connection;
    }
}
