package com.bship.games.domains;

import com.bship.games.Configuration.HarborDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import static java.util.stream.Collectors.toList;

@JsonDeserialize(using = HarborDeserializer.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Harbor {
    AIRCRAFT_CARRIER(5), BATTLESHIP(4), SUBMARINE(3), CRUISER(3), DESTROYER(2), INVALID_SHIP(null);

    private final Integer size;

    Harbor(Integer size) {
        this.size = size;
    }

    public String getName() {
        return name();
    }

    public Integer getSize() {
        return size;
    }

    public static Integer size() {
        return Harbor.values().length - 1;
    }

    public static List<Harbor> getShips() {
        return Arrays.stream(Harbor.values())
                .filter(ship -> !ship.equals(INVALID_SHIP))
                .collect(toList());
    }

    public static Harbor create(String value) {
        return Arrays.stream(Harbor.values())
                .filter(ship -> ship.name().equals(value))
                .findFirst().orElse(INVALID_SHIP);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "{", "}")
                .add("\"name\": " + "\"" + name() + "\"")
                .add("\"size\": " + size)
                .toString();
    }
}
