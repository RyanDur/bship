package com.bship.games.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Collections.unmodifiableList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public class Board {

    @JsonIgnore
    private Long gameId;

    private Long id;
    private List<Ship> ships;

    private Board(Builder builder) {
        id = builder.id;
        gameId = builder.gameId;
        ships = builder.ships;
    }

    public Long getGameId() {
        return gameId;
    }

    public Long getId() {
        return id;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public boolean isReady() {
        return ofNullable(ships).orElse(emptyList()).size() == Harbor.size();
    }

    public Builder copy() {
        return builder().withId(id).withGameId(gameId).withShips(ships);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private Long gameId;
        private List<Ship> ships;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withGameId(Long gameId) {
            this.gameId = gameId;
            return this;
        }

        public Builder addShip(Ship ship) {
            return withShips(singletonList(ship));
        }

        public Builder withShips(List<Ship> shipList) {
            ships = ofNullable(ships).map(list -> Stream.of(list, shipList)
                    .flatMap(Collection::stream)
                    .collect(collectingAndThen(toList(), Collections::unmodifiableList)))
                    .orElse(unmodifiableList(ofNullable(shipList).orElse(emptyList())));
            return this;
        }

        public Board build() {
            return new Board(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        if (id != null ? !id.equals(board.id) : board.id != null) return false;
        if (gameId != null ? !gameId.equals(board.gameId) : board.gameId != null) return false;
        return ships != null ? ships.equals(board.ships) : board.ships == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (gameId != null ? gameId.hashCode() : 0);
        result = 31 * result + (ships != null ? ships.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Board{" +
                "gameId=" + gameId +
                ", id=" + id +
                ", ships=" + ships +
                ", ready=" + isReady() +
                '}';
    }
}
