package com.dev.Pool.Exception;

public class InvalidFlagValueException extends Exception {
    public InvalidFlagValueException() {
        super("Unknown flag value was passed as a parameter.");
    }
}
