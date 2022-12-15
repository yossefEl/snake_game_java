package com.programming.tech.assignment3.helpers;

// import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;

import static com.programming.tech.assignment3.helpers.PathHelper.PROJECT_ASSETS_PATH;

import java.awt.Image;

/*
* @author Youssef ELMOUMEN
* this class is the handler of this project's assets
* the purpose behind it is minimizing the memory usage by declaring all reusable resources
 */
public class AssetsProvider {
        // db.config contains our database connection credentials
        public final static File dbConfigFile = new File(
                        PROJECT_ASSETS_PATH + "db.config");
        public final static Image foodImageIcon = new ImageIcon(
                        PROJECT_ASSETS_PATH + "food/apple.png").getImage();
        public final static Image snakeHeadImageIcon = new ImageIcon(
                        PROJECT_ASSETS_PATH + "snake/snake_head_d.png").getImage();

        public static Image getSnakeHead(char direction) {
                return new ImageIcon(PROJECT_ASSETS_PATH + "snake/snake_head_" + direction + ".png").getImage();
        }

        public static Image getSnakeTail(char direction) {
                return new ImageIcon(PROJECT_ASSETS_PATH + "snake/snake_tail_" + direction + ".png").getImage();
        }

        public static Image getSnakeBody = new ImageIcon(PROJECT_ASSETS_PATH + "snake/snake_body.png").getImage();

        public static Image getRockImage(int rockId) {
                return new ImageIcon(PROJECT_ASSETS_PATH + "rocks/rock_" + rockId + ".png").getImage();
        }

}
