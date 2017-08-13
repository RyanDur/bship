package com.bship.games.logic.rules;

import java.util.Arrays;
import java.util.stream.Stream;

public enum Harbor implements PieceType {
    AIRCRAFT_CARRIER(5), BATTLESHIP(4), SUBMARINE(3), CRUISER(3), DESTROYER(2);

    private final Integer size;

    Harbor(Integer size) {
        this.size = size;
        registerPiece(this);
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public Integer getSize() {
        return size;
    }

    public static Stream<PieceType> getPieces() {
        return Arrays.stream(Harbor.values());
    }

    @Override
    public String toString() {
        return "\"" + name() + "\"";
    }
}
