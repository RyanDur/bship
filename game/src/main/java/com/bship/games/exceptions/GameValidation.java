package com.bship.games.exceptions;

abstract public class GameValidation  extends Exception {
    public GameValidation(String message) {
        super(message);
    }
}
