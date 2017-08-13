package com.bship.games.endpoints.errors.exceptions;

public class ShipExistsCheck extends BoardValidation {

    public ShipExistsCheck() {
        super("Ship already exists on board.");
    }
}
