package com.bship.games.endpoints.board.errors.exceptions;

public class ShipExistsCheck extends BoardValidation {

    public ShipExistsCheck() {
        super("Ship already exists on board.");
    }
}
