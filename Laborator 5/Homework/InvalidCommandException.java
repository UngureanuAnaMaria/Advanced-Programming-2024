package org.example;

public class InvalidCommandException extends Exception {

    public InvalidCommandException(Exception ex) {
        super("Invalid command.", ex);
    }
}
