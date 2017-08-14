package com.bship.games.logic.definitions;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.StringJoiner;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BoardDimensions {
    BATTLESHIP_BOARD(10, 10);

    private final int width;
    private final int height;

    BoardDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "{", "}")
                .add("\"width\": " + width)
                .add("\"height\":" + height)
                .toString();
    }
}
