package com.bship.games.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import static com.bship.games.util.Util.addTo;
import static com.bship.games.util.Util.concat;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

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
        return ofNullable(ships).orElse(emptyList());
    }

    public List<Move> getMoves() {
        return ofNullable(moves).orElse(emptyList());
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
            ships = addTo(ships, ship);
            return this;
        }

        public Builder withShips(List<Ship> shipList) {
            ships = concat(ships, shipList);
            return this;
        }

        public Builder addMove(Move move) {
            moves = addTo(moves, move);
            return this;
        }

        public Builder withMoves(List<Move> moveList) {
            moves = concat(moves, moveList);
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
                '}';
    }
}
