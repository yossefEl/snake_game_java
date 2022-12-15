package com.programming.tech.assignment3.core.components;

import java.awt.*;

import javax.swing.*;
import com.programming.tech.assignment3.helpers.AppColors;
import com.programming.tech.assignment3.helpers.AssetsProvider;

public class GameInfoPanel extends JPanel {
    JLabel timeLabel;
    private int score;
    private Timer timeCounter;
    private JLabel scoreLabel;
    private long startTime;

    public GameInfoPanel(int width) {
        setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(width, 60));
        this.setBackground(AppColors.grassColor);
        ImageIcon icon = new ImageIcon(AssetsProvider.foodImageIcon.getScaledInstance(20, 20,
                Image.SCALE_SMOOTH));

        scoreLabel = new JLabel(String.valueOf(score), icon, JLabel.LEFT);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scoreLabel.setForeground(Color.WHITE);

        scoreLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        this.add(scoreLabel, BorderLayout.WEST);

        timeLabel = new JLabel("00:00:00", JLabel.CENTER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timeLabel.setForeground(Color.WHITE);

        timeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        this.add(timeLabel, BorderLayout.EAST);
    }

    /*
     * This method returns the elapsed time in the format hh:mm:ss
     * 
     * @return String elapsed time in the format hh:mm:ss
     */
    public String getElapsedTime() {
        final long elapsedTimesMS = System.currentTimeMillis() - startTime;
        // format the time
        final long seconds = (elapsedTimesMS / 1000) % 60;
        final long minutes = (elapsedTimesMS / (1000 * 60)) % 60;
        final long hours = (elapsedTimesMS / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /*
     * This method starts the timer and sets the start time
     */
    public void start() {
        startTime = System.currentTimeMillis();
        timeCounter = new Timer(10, e -> {
            timeLabel.setText(getElapsedTime());
        });
    }

    /*
     * This method stops the timer
     */
    public void stop() {
        timeCounter.stop();
    }

    /*
     * This method updates the score
     * 
     * @param score the new score
     */
    public void updateScore(int score) {
        this.score = score;
        scoreLabel.setText(String.valueOf(score));
    }

    /*
     * Refreshes the time label using the getElapsedTime method
     */
    @Override
    protected void paintComponent(Graphics g) {

        if (timeCounter != null) {
            timeLabel.setText(getElapsedTime());
        }
        super.paintComponent(g);
    }

    /*
     * Time counter getter
     * 
     * @return Timer the time counter
     */

    public Timer getTimeCounter() {
        return timeCounter;
    }

}
