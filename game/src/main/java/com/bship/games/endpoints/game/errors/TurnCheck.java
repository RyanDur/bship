package com.bship.games.endpoints.game.errors;

public class TurnCheck extends GameValidation {

    public TurnCheck() {
        super("It is not your turn.");
    }
}
