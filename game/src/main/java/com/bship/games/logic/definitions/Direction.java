package com.bship.games.logic.definitions;

public enum Direction {
    LEFT, RIGHT, UP, DOWN, NONE;

    @Override
    public String toString() {
        return "\"" + name() + "\"";
    }
}
