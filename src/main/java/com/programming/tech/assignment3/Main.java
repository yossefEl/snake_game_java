package com.programming.tech.assignment3;

import com.programming.tech.assignment3.core.exceptions.*;
import com.programming.tech.assignment3.core.views.LevelOptionsView;
import com.programming.tech.assignment3.helpers.AssetsProvider;
import com.programming.tech.assignment3.helpers.DialogHelper;

public class Main {

    public static void main(String[] args) {
        try {
            AssetsProvider.handleFilePermissions();
        } catch (InsufficientPermissionsException e) {
            DialogHelper.showErrorMessage(null, e.getMessage());
            System.exit(0);
        }
        try {
            AssetsProvider.handleAppAssetsExistance();
        } catch (AssetsNotFoundException e) {
            DialogHelper.showErrorMessage(null, e.getMessage());
            System.exit(0);
        }
        new Thread(() -> {
            try {
                new LevelOptionsView();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
