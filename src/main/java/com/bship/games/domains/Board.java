package com.bship.games.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public class Board {

    @JsonIgnore
    private Long gameId;

    private Long id;
    private List<Ship> ships;
    private List<Move> moves;

    private Board(Builder builder) {
        id = builder.id;
        gameId = builder.gameId;
        ships = builder.ships;
        moves = builder.moves;
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

    public List<Move> getMoves() {
        return moves;
    }

    public Builder copy() {
        return builder()
                .withId(id)
                .withGameId(gameId)
                .withShips(ships)
                .withMoves(moves);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private Long gameId;
        private List<Ship> ships;
        private List<Move> moves;

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
            ships = toImmutableList(ships, shipList);
            return this;
        }

        public Builder addMove(Move move) {
            return withMoves(singletonList(move));
        }

        public Builder withMoves(List<Move> moveList) {
            moves = toImmutableList(moves, moveList);
            return this;
        }

        public Board build() {
            return new Board(this);
        }

        private <T> List<T> toImmutableList(List<T> originalList, List<T> newList) {
            return Stream.of(originalList, newList)
                    .filter(Objects::nonNull)
                    .flatMap(Collection::stream)
                    .collect(collectingAndThen(toList(), Collections::unmodifiableList));
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
                '}';
    }
}
