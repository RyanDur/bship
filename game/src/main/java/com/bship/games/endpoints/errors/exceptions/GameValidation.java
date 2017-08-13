package com.bship.games.endpoints.errors.exceptions;

abstract public class GameValidation  extends Exception {
    public GameValidation(String message) {
        super(message);
    }
}
