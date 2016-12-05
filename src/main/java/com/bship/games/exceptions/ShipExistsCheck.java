package com.bship.games.exceptions;

public class ShipExistsCheck extends Exception {

    public ShipExistsCheck() {
        super("Ship already exists on board.");
    }
}
