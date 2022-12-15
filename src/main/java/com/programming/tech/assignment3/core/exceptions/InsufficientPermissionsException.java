package com.programming.tech.assignment3.core.exceptions;

public class InsufficientPermissionsException extends Exception {
    public InsufficientPermissionsException() {
        super("Insufficient permissions, you need to grant the application the required permissions to run, read and write files (database)");
    }
}