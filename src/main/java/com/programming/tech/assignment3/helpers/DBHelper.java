package com.programming.tech.assignment3.helpers;

import java.sql.*;

/**
 * This class manages the database connections as well as its configurations
 */
public class DBHelper {

    private static DBHelper instance = null; // singleton instance

    private Connection connection = null;
    private final String DB_NAME = "snake.db";
    private final String TABLE_NAME = "scores";

    private DBHelper() {
    }

    /*
     * returns the singleton instance of this class
     * 
     * @return DBHelper: the singleton instance of this class
     */
    public static DBHelper getInstance() {
        if (instance == null) {
            instance = new DBHelper();
        }
        return instance;
    }

    /*
     * connects to the database, it creates the database if it doesn't exist and it
     * creates the table if it doesn't exist
     * 
     * @return boolean: true if the connection was successful, false otherwise
     */
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

    /*
     * checks if the table exists
     * 
     * @return boolean: true if the table exists, false otherwise
     */
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

    /*
     * creates the table if it doesn't exist
     */

    public void createTable() {
        // id, name, score, level
        final String sql = "CREATE TABLE " + TABLE_NAME + " (\n" + " id integer PRIMARY KEY,\n"
                + " name text NOT NULL,\n"
                + " score integer NOT NULL,\n" + " level integer NOT NULL\n" + ");";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            DialogHelper.showErrorMessage(null, "Error",
                    "Something went wrong while creating the table, please check if the application has the right permissions to create the database file");
            System.exit(1);
        }
    }

    /*
     * closes the connection to the database
     */
    public void closeConnection() throws SQLException {
        connection.close();
    }

    /*
     * returns the instance of the connection to the database to be used by other
     * classes
     * 
     * @return Connection: the connection to the database
     */
    public Connection getConnection() {
        return connection;
    }
}
