package com.bship.games.logic.definitions;

public enum Direction implements Orientation {
    LEFT, RIGHT, UP, DOWN, NONE;

    @Override
    public String toString() {
        return "\"" + name() + "\"";
    }
}
