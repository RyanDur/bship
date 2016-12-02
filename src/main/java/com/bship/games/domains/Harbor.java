package com.bship.games.domains;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum Harbor {
    AIRCRAFT_CARRIER(5), BATTLESHIP(4), SUBMARINE(3), CRUISER(3), DESTROYER(2), INVALID_SHIP(-1);

    private final Integer size;

    Harbor(Integer size) {
        this.size = size;
    }

    public Integer getSize() {
        return size;
    }

    @JsonCreator
    public static Harbor create (String value) {
        return Arrays.stream(Harbor.values())
                .filter(ship -> ship.name().equals(value))
                .findFirst().orElse(INVALID_SHIP);
    }
}
