package com.programming.tech.assignment3.core.components;

import javax.swing.JComponent;
import java.awt.Graphics;
import com.programming.tech.assignment3.helpers.AssetsProvider;

public class Snake extends JComponent {

    private int xCordinates[];
    private int yCordinates[];
    private final int FIELD_SIZE;
    private int body = 2;
    private char direction;
    private int foodsEaten = 0;
    private int parentWidth;
    private int parentHeight;

    /*
     * Constructor for the snake class, it takes the total number of field units and
     * the size of each unit as parameters
     * then inits the snake's body
     */
    public Snake(int pwidth, int pheight, int totalFieldUnits, int unit) {
        this.parentWidth = pwidth;
        this.parentHeight = pheight;
        this.FIELD_SIZE = unit;
        final char[] directions = { 'U', 'R', 'D', 'L' };
        direction = directions[(int) (Math.random() * 4)];
        xCordinates = new int[totalFieldUnits];
        yCordinates = new int[totalFieldUnits];
        initSnakeBody();

        setVisible(true);

    }

    /*
     * inits the snake's body by randomizing its head position and setting the
     */
    private void initSnakeBody() {

        // center the snake
        xCordinates[0] = (parentWidth / 2) - (parentWidth / 2) % FIELD_SIZE;
        yCordinates[0] = (parentHeight / 2) - (parentHeight / 2) % FIELD_SIZE;
        xCordinates[1] = xCordinates[0] - FIELD_SIZE;
        yCordinates[1] = yCordinates[0];

    }

    /*
     * runs the snake's body by moving each part of the body to the position of the
     * part in front of it
     * then moves the head to the next position based on the direction the snake is
     * moving in
     */

    public void run() {
        for (int i = body; i > 0; i--) {
            xCordinates[i] = xCordinates[i - 1];
            yCordinates[i] = yCordinates[i - 1];
        }

        switch (direction) {
            case 'U':
                yCordinates[0] = yCordinates[0] - FIELD_SIZE;
                break;
            case 'D':
                yCordinates[0] = yCordinates[0] + FIELD_SIZE;
                break;
            case 'L':
                xCordinates[0] = xCordinates[0] - FIELD_SIZE;
                break;
            case 'R':
                xCordinates[0] = xCordinates[0] + FIELD_SIZE;
                break;
        }

    }

    /*
     * checks if the snake is colliding with itself or the walls or the rocks
     */

    public boolean isCollidingWith(int verticalWall, int horizontalWall, Rock rocks[]) {
        for (int i = body; i > 0; i--) {
            if ((xCordinates[0] == xCordinates[i]) && (yCordinates[0] == yCordinates[i])) {
                return false;
            }
        }
        if ((xCordinates[0] < 0) ||
                (xCordinates[0] > horizontalWall - 10) ||
                (yCordinates[0] < 0) ||
                (yCordinates[0] > verticalWall)) {
            return false;
        }
        if (rocks != null) {
            for (Rock rock : rocks) {
                if (rock != null && (xCordinates[0] == rock.getX()) && (yCordinates[0] == rock.getY())) {
                    return false;
                }
            }
        }

        return true;

    }

    /*
     * paints and refreshes the snake's body
     */
    @Override
    protected void paintComponent(Graphics g) {

        for (int i = 0; i < body; i++) {
            if (i == 0) {
                g.drawImage(AssetsProvider.getSnakeHead(direction), xCordinates[0],
                        yCordinates[0], FIELD_SIZE,
                        FIELD_SIZE, null);
            } else {
                g.drawImage(AssetsProvider.getSnakeBody, xCordinates[i], yCordinates[i],
                        FIELD_SIZE, FIELD_SIZE, null);
            }
        }

        super.paintComponent(g);
    }

    /*
     * checks if the snake is eating the food, if it is, it increases the snake's
     * body and the number of foods eaten
     * 
     * @param food the food to check if the snake is eating it
     * 
     * @return true if the snake is eating the food, false otherwise
     */
    public boolean eatFood(Food food) {
        if (food == null) {
            return false;
        }
        if ((xCordinates[0] == food.getX()) && (yCordinates[0] == food.getY())) {
            foodsEaten++;
            body++;
            return true;
        }
        return false;

    }

    /*
     * @return int return the xCordinates[]
     */
    public int[] getXCordinates() {
        return xCordinates;
    }

    /*
     * @param xCordinates[] the xCordinates[] to set
     */
    public void setXCordinates(int[] xCordinates) {
        this.xCordinates = xCordinates;
    }

    /*
     * @return int return the yCordinates[]
     */
    public int[] getYCordinates() {
        return yCordinates;
    }

    /*
     * @param yCordinates[] the yCordinates[] to set
     */
    public void setYCordinates(int[] yCordinates) {
        this.yCordinates = yCordinates;
    }

    /*
     * @return int return the body
     */
    public int getBody() {
        return body;
    }

    /*
     * @param body the body to set
     */
    public void setBody(int body) {
        this.body = body;
    }

    /*
     * @return char return the direction
     */
    public char getDirection() {
        return direction;
    }

    /*
     * Handles the direction of the snake based on the key pressed by the user
     * Arrows or SWAD keys are used to control the snake
     * 
     * @param int key the key to set the direction to based on the key pressed
     * 
     */
    public void setDirection(int key) {
        // WASD or arrow keys
        if (key == 87 || key == 38) {
            if (direction != 'D') {
                direction = 'U';
            }
        } else if (key == 68 || key == 39) {
            if (direction != 'L') {
                direction = 'R';
            }
        } else if (key == 83 || key == 40) {
            if (direction != 'U') {
                direction = 'D';
            }
        } else if (key == 65 || key == 37) {
            if (direction != 'R') {
                direction = 'L';
            }
        }

    }

    /*
     * @return int return the foodsEaten
     */
    public int getFoodsEaten() {
        return foodsEaten;
    }
}
