package com.programming.tech.assignment3.core.views;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class LevelOptionsView extends JFrame {
    public static int levelOption = 1;

    public LevelOptionsView() {
        buildUI();
    }

    /*
     * Builds the UI of the LevelOptions View and manages the user selection
     * after the user selects a level and clicks on the start button, this view
     * disposes and the game starts
     */
    private void buildUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(300, 300));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // elements
        JComboBox<String> levelOptions = new JComboBox<>();
        for (int i = 1; i <= 10; i++) {
            levelOptions.addItem("Level " + i);
        }
        // don't make levelOptions to be stretched
        levelOptions.setMaximumSize(new Dimension(200, 30));
        levelOptions.addActionListener(e -> {
            @SuppressWarnings("unchecked")
            JComboBox<String> cb = (JComboBox<String>) e.getSource();
            String level = (String) cb.getSelectedItem();
            levelOption = Integer.parseInt(level.split(" ")[1]);
        });

        JLabel label = new JLabel("Please select a level");

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            if (levelOption == 0) {
                levelOption = 1;
            }
            dispose();
            new SnakeGameView();
        });
        // align to the center
        // make a space from top to the first element
        JLabel emptyLabel = new JLabel(" ");
        emptyLabel.setAlignmentX(CENTER_ALIGNMENT);
        emptyLabel.setAlignmentY(CENTER_ALIGNMENT);
        emptyLabel.setMaximumSize(new Dimension(200, 80));

        label.setAlignmentX(CENTER_ALIGNMENT);
        levelOptions.setAlignmentX(CENTER_ALIGNMENT);
        startButton.setAlignmentX(CENTER_ALIGNMENT);
        label.setAlignmentY(CENTER_ALIGNMENT);
        levelOptions.setAlignmentY(CENTER_ALIGNMENT);
        startButton.setAlignmentY(CENTER_ALIGNMENT);
        // add elements
        add(emptyLabel);
        add(label);
        add(levelOptions);
        add(startButton);

        // jframe config

        setResizable(false);
        setAlwaysOnTop(true);
        pack();
        setFocusable(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
