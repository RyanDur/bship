package com.bship.games.domains;

public enum Direction {
    LEFT, RIGHT, UP, DOWN, NONE;

    @Override
    public String toString() {
        return "\"" + name() + "\"";
    }
}
