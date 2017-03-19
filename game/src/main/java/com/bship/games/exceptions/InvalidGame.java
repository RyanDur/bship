package com.bship.games.exceptions;

public class InvalidGame extends GameValidation {
    public InvalidGame() {
        super("Game Does Not Exist!");
    }
}
