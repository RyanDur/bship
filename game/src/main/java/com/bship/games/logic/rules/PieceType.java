package com.bship.games.logic.rules;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public interface PieceType {

    enum Harbor implements PieceType {
        AIRCRAFT_CARRIER(5), BATTLESHIP(4), SUBMARINE(3), CRUISER(3), DESTROYER(2);

        private final Integer size;

        Harbor(Integer size) {
            this.size = size;
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

        public static Optional<Harbor> createShip(String value) {
            return Stream.of(Harbor.values())
                    .filter(ship -> ship.getName().equals(value))
                    .findFirst();
        }

        @Override
        public String toString() {
            return "\"" + name() + "\"";
        }
    }

    enum Dummy implements PieceType {
        INVALID_PIECE;

        @Override
        public String getName() {
            return name();
        }

        @Override
        public Integer getSize() {
            return null;
        }
    }

    String getName();

    Integer getSize();

    @JsonCreator
    static PieceType create(String value) {
        Optional<Harbor> pieceType = Harbor.createShip(value);
        if (pieceType.isPresent()) return pieceType.get();
        else return Dummy.INVALID_PIECE;
    }
}
