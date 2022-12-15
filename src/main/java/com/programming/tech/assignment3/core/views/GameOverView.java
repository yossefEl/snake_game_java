package com.programming.tech.assignment3.core.views;

import java.sql.SQLException;
import java.awt.*;
import javax.swing.*;

import com.programming.tech.assignment3.core.models.Score;
import com.programming.tech.assignment3.helpers.DialogHelper;

public class GameOverView extends JFrame {
    private int score = 0;
    private int level = 1;

    public GameOverView(int score) {
        super("Game Over");
        this.score = score;
        this.level = LevelOptionsView.levelOption;
        LevelOptionsView.levelOption = 0;
        setSize(600, 350);

        buildUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setFocusable(true);
        // pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /*
     * Builds the UI of the GameOver View
     */
    private void buildUI() {
        // setLayout to BoxLayout so that we can align the components
        // divide the window into 2 parts
        setLayout(new GridLayout(1, 2));
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        // add score label
        JLabel gameOver = new JLabel("Game Over");
        gameOver.setFont(new java.awt.Font("Arial", 1, 24));
        JLabel scoreLabel = new JLabel("Your score is: " + score);
        JTextField usernameField = new JTextField();
        // for one line only
        usernameField.setMaximumSize(new Dimension(200, 30));
        usernameField.setText("Enter your name");
        JButton saveScoreButton = new JButton("Save score");
        saveScoreButton.addActionListener(e -> {
            if (usernameField.getText().equals("Enter your name") || usernameField.getText().isBlank()) {
                DialogHelper.showErrorMessage(this, "Error", "Please enter your name");
                return;
            } else {
                final String name = usernameField.getText();
                final int score = this.score;
                final int level = this.level;
                new Thread(() -> {
                    try {
                        Score scoreInstance = new Score(score, name, level);
                        boolean created = scoreInstance.create();
                        if (created) {
                            DialogHelper.showInfoMessage(this, "Score saved successfully");
                        } else {
                            DialogHelper.showErrorMessage(this, "Error",
                                    "Error while saving score ");
                        }
                    } catch (SQLException ex) {
                        DialogHelper.showErrorMessage(this, "Error", "Error while saving score " + ex.getMessage());
                    }
                }).start();

            }
        });

        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> {
            dispose();
            new LevelOptionsView();

        });
        JButton topScoresButton = new JButton("Top Scores");
        topScoresButton.addActionListener(e -> {
            new TopScoresView();
        });

        leftPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        rightPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        // center the components
        gameOver.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveScoreButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        topScoresButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.add(gameOver);
        leftPanel.add(scoreLabel);
        leftPanel.add(usernameField);
        leftPanel.add(saveScoreButton);
        rightPanel.add(restartButton);
        rightPanel.add(topScoresButton);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);

    }

}