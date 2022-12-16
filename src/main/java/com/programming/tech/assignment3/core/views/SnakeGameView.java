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
    private Timer timer;
    private boolean started = false;
    private int level = 0;
    private int NUMBER_OF_ROCKS;
    private Rock[] rocks;
    private GameInfoPanel infoPanel;

    /*
     * Constructor for the SnakeGameView class, it reads the LevelOption from the
     * LevelOptionsView
     * then inits the game, the game starts after the user presses on a key
     */
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

    /*
     * Initializes the game, by setting the size of the window, the background
     * color,
     * the number of fields in the game board,
     * the snake, the food, the rocks, the info panel, and the key listener..etc
     */
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

        snake = new Snake(GAME_BOARD_WIDTH, GAME_BOARD_HEIGHT, GAME_FIELDS_NUMBER, FIELD_SIZE);
        handleLevel();
        food = new Food(GAME_BOARD_WIDTH, GAME_BOARD_HEIGHT, FIELD_SIZE);
        add(food);
        handleFoodLocation();
        add(snake);
        start();
        setVisible(true);

    }

    /*
     * Starts the game by initializing the timer, the snake and setting the
     * isRunning to true
     */
    public void start() {
        isRunning = true;
        timer = new Timer(DELAY, (ActionListener) this);
        timer.start();
    }

    /*
     * Handles the proper location of the rocks & food
     * 
     * @param int x - x coordinate
     * 
     * @param int y - y coordinate
     * 
     * @return true if the position is good, false otherwise
     */
    private boolean isWithinBounds(int x, int y) {
        if (x < 10 || y < 10) {
            return false;
        }
        if (x >= GAME_BOARD_WIDTH - 10 || y >= GAME_BOARD_HEIGHT - 10) {
            return false;
        }
        // not on the snake
        for (int i = 0; i < snake.getBody(); i++) {
            if (snake.getXCordinates()[i] == x && snake.getXCordinates()[i] == y) {
                return false;
            }
        }
        return true;
    }

    /*
     * Handles the logic of the levels of the game
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

    /*
     * Handles the rocks in the game
     * based on the Number of rocks in the level
     * it creates an array of rocks and adds them to the game board on ramdon &
     * fixed locations
     */
    private void handleLevelRocks() {
        rocks = new Rock[NUMBER_OF_ROCKS];
        for (int i = 0; i < NUMBER_OF_ROCKS; i++) {
            final int id = (int) (Math.random() * 6) + 1;
            rocks[i] = new Rock(GAME_BOARD_WIDTH, GAME_BOARD_HEIGHT, FIELD_SIZE, id);
            while (!isWithinBounds(rocks[i].getX(), rocks[i].getY())) {
                rocks[i].relocate();
            }
            add(rocks[i]);
        }
    }

    /*
     * Handles the food location
     * it relocates the food on a random location
     * then checks if the location is good
     * if not it relocates it again
     */
    public void handleFoodLocation() {
        food.relocate();
        while (!isWithinBounds(food.getX(), food.getY())) {
            food.relocate();
        }

    }

    /*
     * Handles the key events
     * if the game is not started, it starts it & the timer of the game information
     * panel
     */
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

    /*
     * Handles the timer of the game
     * if the game is not started, it does nothing
     * if the game is running, it runs the snake, and checks if it eats food, if it
     * does it updates the score and handles the food location
     * if the snake collides with the rocks, the walls or itself it stops the timer
     * and displays the game over screen
     */
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