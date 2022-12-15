package com.programming.tech.assignment3;

import com.programming.tech.assignment3.core.views.LevelOptionsView;

public class Main {

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                new LevelOptionsView();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
