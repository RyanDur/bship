package com.bship.games.endpoints.game.errors;

public class InvalidGame extends GameValidation {
    public InvalidGame() {
        super("Game Does Not Exist!");
    }
}
