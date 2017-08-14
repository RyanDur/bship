package com.bship.games.endpoints.game.errors;

abstract public class GameValidation  extends Exception {
    public GameValidation(String message) {
        super(message);
    }
}
