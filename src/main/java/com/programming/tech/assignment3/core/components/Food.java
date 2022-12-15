package com.programming.tech.assignment3.core.components;

import java.awt.Graphics;
import java.util.Random;

import javax.swing.JComponent;
import com.programming.tech.assignment3.helpers.AssetsProvider;

import java.awt.*;

public class Food extends JComponent {
    private int FIELD_SIZE;
    private int parentHeight;
    private int parentWidth;

    public Food(int parentWidth, int parentHeight, int fieldSize) {
        this.FIELD_SIZE = fieldSize;

        this.parentHeight = parentHeight;
        this.parentWidth = parentWidth;
        relocate();
        setVisible(true);

    }

    public void relocate() {
        // within the range of the parent component
        final int x = (int) new Random().nextInt(parentWidth / FIELD_SIZE) * FIELD_SIZE;
        final int y = (int) new Random().nextInt(parentHeight / FIELD_SIZE) * FIELD_SIZE;
        setBounds(x, y, FIELD_SIZE, FIELD_SIZE);
    }

    @Override
    public String toString() {
        return "Food [x=" + this.getX() + ", y=" + this.getY() + "]";
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            g.drawImage(AssetsProvider.foodImageIcon, 0, 0, FIELD_SIZE, FIELD_SIZE, null);
        } catch (Exception e) {
            setBackground(Color.red);
        }
        setVisible(true);
    }

}
