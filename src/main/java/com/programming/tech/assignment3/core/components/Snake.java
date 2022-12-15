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

    public Snake(int totalFieldUnits, int unit) {
        this.FIELD_SIZE = unit;
        final char[] directions = { 'U', 'R', 'D', 'L' };
        direction = directions[(int) (Math.random() * 4)];
        xCordinates = new int[totalFieldUnits];
        yCordinates = new int[totalFieldUnits];
        initSnakeBody();
        setVisible(true);

    }

    private void initSnakeBody() {
        // randomize the snake's head position
        xCordinates[0] = (int) (Math.random() * 20) * FIELD_SIZE;
        yCordinates[1] = (int) (Math.random() * 20) * FIELD_SIZE;
        yCordinates[0] = xCordinates[1] = xCordinates[0];
        yCordinates[1] = yCordinates[0] + FIELD_SIZE;

    }

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

    /**
     * @return int return the xCordinates[]
     */
    public int[] getXCordinates() {
        return xCordinates;
    }

    /**
     * @param xCordinates[] the xCordinates[] to set
     */
    public void setXCordinates(int[] xCordinates) {
        this.xCordinates = xCordinates;
    }

    /**
     * @return int return the yCordinates[]
     */
    public int[] getYCordinates() {
        return yCordinates;
    }

    /**
     * @param yCordinates[] the yCordinates[] to set
     */
    public void setYCordinates(int[] yCordinates) {
        this.yCordinates = yCordinates;
    }

    /**
     * @return int return the body
     */
    public int getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(int body) {
        this.body = body;
    }

    /**
     * @return char return the direction
     */
    public char getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
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

    public int getFoodsEaten() {
        return foodsEaten;
    }

    public void setFoodsEaten(int foodsEaten) {
        this.foodsEaten = foodsEaten;
    }
}
