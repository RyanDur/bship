package com.bship.games.endpoints.game.errors;

public class MoveCollision extends GameValidation {

    public MoveCollision() {
        super("Move already exists on board.");
    }
}
