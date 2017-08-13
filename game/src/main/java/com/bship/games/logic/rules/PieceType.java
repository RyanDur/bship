package com.bship.games.logic.rules;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

import static com.bship.games.logic.rules.PieceType.Dummy.INVALID_PIECE;

public interface PieceType {

    List<PieceType> store = new ArrayList<>();

    @JsonIgnore
    default void registerPiece(PieceType type) {
        store.add(type);
    }

    @JsonIgnore
    default String getName() {
        return null;
    }

    @JsonIgnore
    default Integer getSize() {
        return null;
    }

    @JsonCreator
    static PieceType createPiece(String name) {
        return store.stream()
                .filter(i -> i.getName().equals(name)).findAny()
                .orElse(INVALID_PIECE);
    }

    enum Dummy implements PieceType {
        INVALID_PIECE;

        @Override
        public String getName() {
            return name();
        }
    }
}
