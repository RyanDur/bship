package com.bship.games.domains;

public enum Harbor {
    AIRCRAFT_CARRIER(5), BATTLESHIP(4), SUBMARINE(3), CRUISER(3), DESTROYER(2);

    private final Integer size;

    Harbor(Integer size) {
        this.size = size;
    }

    public Integer getSize() {
        return size;
    }
}
