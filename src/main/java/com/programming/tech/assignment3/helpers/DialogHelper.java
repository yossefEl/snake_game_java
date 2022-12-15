package com.programming.tech.assignment3.helpers;

import javax.swing.*;

import java.awt.*;

/*
 * This class handles the dialogs to show if case success/ error /confirmation
 */
public class DialogHelper {
        /*
         * shows an error message
         * 
         * @param Component: the parent component
         * 
         * @param String: the message to show
         * 
         */
        public static void showErrorMessage(Component parent, String message) {
                final JLabel messageLabel = new JLabel("<html><div style='width:300px'>" + message + "</div></html>",
                                SwingConstants.LEFT);
                JOptionPane.showMessageDialog(
                                parent,
                                messageLabel,
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
        }

        /*
         * shows an error message
         * 
         * @param Component: the parent component
         * 
         * @param String: the title of the dialog
         * 
         * @param String: the message to show
         */
        public static void showErrorMessage(Component parent, String title, String message) {
                final JLabel messageLabel = new JLabel("<html><div style='width:300px'>" + message + "</div></html>",
                                SwingConstants.LEFT);
                JOptionPane.showMessageDialog(
                                parent,
                                messageLabel,
                                title,
                                JOptionPane.ERROR_MESSAGE);
        }

        /*
         * shows an info message
         * 
         * @param Component: the parent component
         * 
         * @param String: the message to show
         * 
         */
        public static void showInfoMessage(Component parent, String message) {
                final JLabel messageLabel = new JLabel("<html><div style='width:300px'>" + message + "</div></html>",
                                SwingConstants.LEFT);
                JOptionPane.showMessageDialog(
                                parent,
                                messageLabel,
                                "Info",
                                JOptionPane.INFORMATION_MESSAGE);
        }

        /*
         * shows a confirmation dialog
         * 
         * @param Component: the parent component
         * 
         * @param String: the message to show
         * 
         * @return int: the user choice, 0 for ok, 1 for cancel
         */
        public static int showConfirmationDialog(Component parent, String message) {
                return JOptionPane.showConfirmDialog(
                                parent,
                                message,
                                "Confirmation",
                                JOptionPane.OK_CANCEL_OPTION);
        }
}
