package com.bship.games.logic.definitions;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

import static com.bship.games.logic.definitions.Orientation.Dummy.INVALID_ORIENTATION;

public interface Orientation {

    String name();

    @JsonCreator
    static Orientation create(String name) {
        if (Stream.of(Direction.values()).anyMatch(it -> it.name().equals(name))) {
            return Direction.valueOf(name);
        } else return INVALID_ORIENTATION;
    }

    enum Dummy implements Orientation {
        INVALID_ORIENTATION
    }
}
