package com.bship.games.endpoints.board.errors.exceptions;

abstract public class BoardValidation extends Exception {
    public BoardValidation(String message) {
        super(message);
    }
}
