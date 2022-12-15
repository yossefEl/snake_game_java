package com.programming.tech.assignment3.core.models;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.programming.tech.assignment3.helpers.DBHelper;

public class Score {
    private int score;
    private String name;
    private int level;
    private int id;

    public Score(int score, String name) {
        this.score = score;
        this.name = name;
        this.level = 1;
    }

    public Score(int score, String name, int level) {
        this.score = score;
        this.name = name;
        this.level = level;
    }

    public boolean create() throws SQLException {
        DBHelper dbInstance = DBHelper.getInstance();
        boolean conected = dbInstance.connectToDatabase();
        if (conected) {
            // sort by score
            Statement stmt = dbInstance.getConnection().createStatement();
            String sql = "INSERT INTO scores (score, name, level) VALUES (" + this.score + ", '" + this.name
                    + "', "
                    + this.level + ")";
            int resp = stmt.executeUpdate(sql);
            if (resp == 1) {
                return true;
            }
        }
        return false;
    }

    public void get() throws SQLException {
        var list = new ArrayList<Score>();
        DBHelper dbInstance = DBHelper.getInstance();
        boolean conected = dbInstance.connectToDatabase();
        if (conected) {
            // sort by score
            Statement stmt = dbInstance.getConnection().createStatement();
            String sql = "SELECT * FROM scores WHERE id = " + id;
            var rs = stmt.executeQuery(sql);
            while (rs.next()) {
                var score = new Score(rs.getInt("score"), rs.getString("name"), rs.getInt("level"));
                score.setId(rs.getInt("id"));
                list.add(score);
            }

        }
    }

    public void delete() throws SQLException {
        DBHelper dbInstance = DBHelper.getInstance();
        boolean conected = dbInstance.connectToDatabase();
        if (conected) {
            // sort by score
            Statement stmt = dbInstance.getConnection().createStatement();
            String sql = "DELETE FROM scores WHERE id = " + id;
            stmt.executeUpdate(sql);
        }
    }

    public void update() throws SQLException {

        DBHelper dbInstance = DBHelper.getInstance();
        boolean conected = dbInstance.connectToDatabase();
        if (conected) {
            // sort by score
            Statement stmt = dbInstance.getConnection().createStatement();
            String sql = "UPDATE scores SET score = " + this.score + ", name = '" + this.name + "', level = "
                    + this.level + " WHERE id = " + this.id;
            stmt.executeUpdate(sql);
        }

    }

    public static ArrayList<Score> findAll(int limit) throws SQLException {
        var list = new ArrayList<Score>();
        DBHelper dbInstance = DBHelper.getInstance();
        boolean conected = dbInstance.connectToDatabase();
        if (conected) {
            // sort by score

            Statement stmt = dbInstance.getConnection().createStatement();
            String sql = "SELECT * FROM scores ORDER BY score DESC";
            if (limit > 0) {
                sql += " LIMIT " + limit;
            }
            var rs = stmt.executeQuery(sql);
            while (rs.next()) {
                var score = new Score(rs.getInt("score"), rs.getString("name"), rs.getInt("level"));
                score.setId(rs.getInt("id"));
                list.add(score);
            }

        }

        return list;

    }

    /**
     * @return int return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return int return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
