package com.bship.games.endpoints.errors.exceptions;

abstract public class BoardValidation extends Exception {
    public BoardValidation(String message) {
        super(message);
    }
}
