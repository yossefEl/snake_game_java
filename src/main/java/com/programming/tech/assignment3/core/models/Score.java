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

    public Score(int id) {
        this.id = id;
    }

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

    /*
     * Saves this score to the database
     * 
     * @return boolean, true if the score was saved successfully and false otherwise
     */
    public boolean create() throws SQLException {
        DBHelper dbInstance = DBHelper.getInstance();
        boolean conected = dbInstance.connectToDatabase();
        if (conected) {
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

    /*
     * fetchs a score from the database using this instance's id
     */
    public void get() throws SQLException {
        DBHelper dbInstance = DBHelper.getInstance();
        boolean conected = dbInstance.connectToDatabase();
        if (conected) {
            // sort by score
            Statement stmt = dbInstance.getConnection().createStatement();
            String sql = "SELECT * FROM scores WHERE id = " + id;
            var rs = stmt.executeQuery(sql);
            while (rs.next()) {
                this.score = rs.getInt("score");
                this.name = rs.getString("name");
                this.level = rs.getInt("level");
            }

        }
    }

    /*
     * removes this score from the database
     */
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

    /*
     * updates this score in the database
     */

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

    /*
     * fetchs a list of the top scores from the database
     * 
     * @param limit, the number of scores to fetch, if limit is 0, all scores are
     * fetched
     * 
     * @return ArrayList<Score>, the list of scores
     */
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

    /*
     * @return int return the score
     */
    public int getScore() {
        return score;
    }

    /*
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /*
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /*
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * @return int return the level
     */
    public int getLevel() {
        return level;
    }

    /*
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /*
     * @return int return the id
     */
    public int getId() {
        return id;
    }

    /*
     * @param id the id to set
     */

    public void setId(int id) {
        this.id = id;
    }

}
