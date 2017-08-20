package com.bship.games.logic.definitions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import static com.bship.games.logic.definitions.PieceType.Dummy.INVALID_PIECE;

public interface PieceType {

    @JsonIgnore
    default String getName() {
        return null;
    }

    @JsonIgnore
    default Integer getSize() {
        return null;
    }

    @JsonCreator
    static PieceType create(String name) {
        if (Harbor.getPieces().anyMatch(it -> it.getName().equals(name))) {
            return Harbor.valueOf(name);
        } else return INVALID_PIECE;
    }

    enum Dummy implements PieceType {
        INVALID_PIECE;

        @Override
        public String getName() {
            return name();
        }
    }
}
