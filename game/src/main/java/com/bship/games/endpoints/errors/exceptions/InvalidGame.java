package com.bship.games.endpoints.errors.exceptions;

public class InvalidGame extends GameValidation {
    public InvalidGame() {
        super("Game Does Not Exist!");
    }
}
