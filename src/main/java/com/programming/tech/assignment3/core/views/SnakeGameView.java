package com.programming.tech.assignment3.core.views;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.programming.tech.assignment3.helpers.AppColors;
import com.programming.tech.assignment3.helpers.DBHelper;
import com.programming.tech.assignment3.helpers.DialogHelper;
import com.programming.tech.assignment3.core.components.*;

public class SnakeGameView
        extends JFrame implements ActionListener {

    final int GAME_BOARD_WIDTH = 500;
    final int GAME_BOARD_HEIGHT = 500;
    int DELAY = 170;
    int FIELD_SIZE = GAME_BOARD_WIDTH / 20;
    int GAME_FIELDS_NUMBER;
    private Snake snake;
    private Food food;
    private boolean isRunning = false;
    // private boolean isGameOver = false;
    // private Rock[] rocks;
    private Timer timer;
    // private Timer timeCounter;
    private boolean started = false;
    private int level = 0;
    private int NUMBER_OF_ROCKS;
    private Rock[] rocks;
    private GameInfoPanel infoPanel;

    public SnakeGameView() {

        this.level = LevelOptionsView.levelOption;
        System.out.println("Level: " + level);
        DBHelper dbHelper = DBHelper.getInstance();
        if (!dbHelper.connectToDatabase()) {
            DialogHelper.showErrorMessage(this, "Error", "Error connecting to database");
            System.exit(0);
        }
        init();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(this.getParent());
        setVisible(true);
    }

    // JPanel panel;

    private void init() {
        this.addKeyListener(new SnakeGameKeyAdapter());
        final Dimension screenSize = new Dimension(GAME_BOARD_WIDTH, GAME_BOARD_HEIGHT + (120));
        this.setSize(screenSize);
        this.setPreferredSize(screenSize);
        this.setMaximumSize(screenSize);
        this.setMinimumSize(screenSize);
        GAME_FIELDS_NUMBER = (GAME_BOARD_WIDTH * GAME_BOARD_HEIGHT) / (FIELD_SIZE * FIELD_SIZE);
        setBounds(0, 0, GAME_BOARD_WIDTH, GAME_BOARD_HEIGHT);
        setBackground(AppColors.grassColor);
        infoPanel = new GameInfoPanel(GAME_BOARD_WIDTH);
        add(infoPanel, BorderLayout.SOUTH);
        snake = new Snake(GAME_FIELDS_NUMBER, FIELD_SIZE);

        // add a panel to the center of the frame

        //
        handleLevel();
        food = new Food(GAME_BOARD_WIDTH, GAME_BOARD_HEIGHT, FIELD_SIZE);
        add(food);
        handleFoodLocation();
        add(snake);

        start();

        setVisible(true);

    }

    public void start() {
        isRunning = true;
        timer = new Timer(DELAY, (ActionListener) this);
        timer.start();
        snake.run();

    }

    /*
     * Handles the proper location of the rocks & food
     * 
     * @param x - x coordinate
     * 
     * @param y - y coordinate
     * 
     * @return true if the position is good, false otherwise
     */
    private boolean isGoodPosition(int x, int y) {
        if (x < 10 || y < 10) {
            return false;
        }
        if (x >= GAME_BOARD_WIDTH - 10 || y >= GAME_BOARD_HEIGHT - 10) {
            return false;
        }
        return true;
    }

    /*
     * Handles the level of the game
     * delays are calculated with the following formula : 170-(level-1)*10
     * which produces the following delays (slowest to fastest)
     * :170, 160, 150,140,130, 120, 110, 100,90, 80
     * Rocks by the fomula : level + 2;
     * Results : 3,4,5,6,7,8,9,10,11,12
     */
    private void handleLevel() {
        DELAY = 170 - (level - 1) * 10; // 170, 160, 150, 140, 130, 120, 110, 100, 90, 80
        NUMBER_OF_ROCKS = level + 2; // 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
        handleLevelRocks();

    }

    private void handleLevelRocks() {
        rocks = new Rock[NUMBER_OF_ROCKS];

        for (int i = 0; i < NUMBER_OF_ROCKS; i++) {
            // from 1 to 6
            final int id = (int) (Math.random() * 6) + 1;
            rocks[i] = new Rock(GAME_BOARD_WIDTH, GAME_BOARD_HEIGHT, FIELD_SIZE, id);
            while (!isGoodPosition(rocks[i].getX(), rocks[i].getY())) {
                rocks[i].relocate();
            }
            add(rocks[i]);
        }
    }

    public void handleFoodLocation() {
        food.relocate();
        while (!isGoodPosition(food.getX(), food.getY())) {
            food.relocate();
        }

    }

    // public void gameOver(Graphics g) {
    // g.setColor(Color.red);
    // isGameOver = true;

    // }

    public class SnakeGameKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            started = true;
            if (started && infoPanel.getTimeCounter() == null) {
                infoPanel.start();
            }
            final int key = e.getKeyCode();
            snake.setDirection(key);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!started) {
            return;
        }
        if (isRunning) {
            snake.run();
            if (snake.eatFood(food)) {
                infoPanel.updateScore(snake.getFoodsEaten());
                handleFoodLocation();
            }
            isRunning = snake.isCollidingWith(GAME_BOARD_WIDTH, GAME_BOARD_HEIGHT, rocks);
            if (!isRunning) {
                timer.stop();
                infoPanel.stop();
                dispose();
                new GameOverView(snake.getFoodsEaten());
                return;
            }
        }
        repaint();

    }

}