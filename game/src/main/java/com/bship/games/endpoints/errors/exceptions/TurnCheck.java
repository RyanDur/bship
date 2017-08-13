package com.bship.games.endpoints.errors.exceptions;

public class TurnCheck extends GameValidation {

    public TurnCheck() {
        super("It is not your turn.");
    }
}
