package com.programming.tech.assignment3.core.views;

import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import com.programming.tech.assignment3.core.models.Score;
import com.programming.tech.assignment3.helpers.DialogHelper;

public class TopScoresView extends JFrame {
    private static JTable table;

    public TopScoresView() {
        super("Top Scores");
        setSize(500, 500);
        buildTable();
        setVisible(true);
    }

    // read and return the database

    // build a table with the data
    private void buildTable() {

        String[] columnNames = { "Player", "Score", "level" };

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setFocusable(true);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // start model
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        table = new JTable();
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // start fetch
        ArrayList<Score> scores = new ArrayList<>();
        try {
            scores = Score.findAll(10);
        } catch (SQLException e) {
            DialogHelper.showErrorMessage(this, "Error", "Error while fetching scores");
            dispose();
        }

        for (Score score : scores) {
            model.addRow(new Object[] { score.getName(), score.getScore(), score.getLevel() });
        }

        // end fetch
        add(scroll);
        setVisible(true);
        setSize(400, 300);
    }

    // private abstract void buildTable();
}
