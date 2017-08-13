package com.bship.games.endpoints.errors.exceptions;

public class MoveCollision extends GameValidation {

    public MoveCollision() {
        super("Move already exists on board.");
    }
}
