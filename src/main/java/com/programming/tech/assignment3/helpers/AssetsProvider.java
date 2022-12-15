package com.programming.tech.assignment3.helpers;

import javax.swing.ImageIcon;

import com.programming.tech.assignment3.core.exceptions.*;

import static com.programming.tech.assignment3.helpers.PathHelper.PROJECT_ASSETS_PATH;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*
* @author Youssef ELMOUMEN
* this class is the handler of this project's assets
*/
public class AssetsProvider {

        /*
         * the apple image
         */
        public final static Image foodImageIcon = new ImageIcon(
                        PROJECT_ASSETS_PATH + "food/apple.png").getImage();

        /*
         * returns the snake head image based on the direction of the snake
         * 
         * @param char: the direction of the snake
         * 
         * @return Image: the snake head image
         * 
         */
        public static Image getSnakeHead(char direction) {
                return new ImageIcon(PROJECT_ASSETS_PATH + "snake/snake_head_" + direction + ".png").getImage();
        }

        /*
         * the snake body image
         */
        public static Image getSnakeBody = new ImageIcon(PROJECT_ASSETS_PATH + "snake/snake_body.png").getImage();

        /*
         * returns the rock image based on the rock id
         * 
         * @param int: the id of the rock
         * 
         * @return Image: the rock image
         * 
         */
        public static Image getRockImage(int rockId) {
                return new ImageIcon(PROJECT_ASSETS_PATH + "rocks/rock_" + rockId + ".png").getImage();
        }

        /*
         * checks if the project has the required assets to run
         */
        public static void handleAppAssetsExistance() throws AssetsNotFoundException {
                if (!new File(PROJECT_ASSETS_PATH).exists()) {
                        throw new AssetsNotFoundException();
                }
                List<String> assets = new ArrayList<String>();
                assets.add("food/apple.png");
                assets.add("snake/snake_head_u.png");
                assets.add("snake/snake_head_d.png");
                assets.add("snake/snake_head_l.png");
                assets.add("snake/snake_head_r.png");
                assets.add("snake/snake_body.png");
                assets.add("rocks/rock_1.png");
                assets.add("rocks/rock_2.png");
                assets.add("rocks/rock_3.png");
                assets.add("rocks/rock_4.png");
                assets.add("rocks/rock_5.png");
                assets.add("rocks/rock_6.png");
                for (String asset : assets) {
                        if (!new File(PROJECT_ASSETS_PATH + asset).exists()) {
                                throw new AssetsNotFoundException();
                        }
                }

        }

        /*
         * checks if the project has the required permissions to run
         */
        public static void handleFilePermissions() throws InsufficientPermissionsException {
                final File projectLocation = new File(PathHelper.getProjectLocation());
                if (!projectLocation.canRead() || !projectLocation.canWrite()) {
                        throw new InsufficientPermissionsException();
                }

        }
}
