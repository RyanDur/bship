package com.bship.games.exceptions;

public class TurnCheck extends Exception {

    public TurnCheck() {
        super("It is not your turn.");
    }
}
