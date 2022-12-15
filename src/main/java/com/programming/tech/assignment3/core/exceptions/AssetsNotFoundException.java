package com.programming.tech.assignment3.core.exceptions;

public class AssetsNotFoundException extends Exception {
    public AssetsNotFoundException() {
        super("Assets not found, please make sure you have the assets folder in the same directory as the application (src/main/assets)");
    }

}
