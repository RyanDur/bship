package com.bship.games.exceptions;

public class MoveCollision extends Exception {

    public MoveCollision() {
        super("Move already exists on board.");
    }
}
