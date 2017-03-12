package com.bship.games.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.math.BigInteger;
import java.util.List;

import static com.bship.games.util.Util.addTo;

@JsonDeserialize(builder = Board.Builder.class)
public class Board {

    @JsonIgnore
    private BigInteger gameId;
    private BigInteger id;

    private List<Ship> ships;
    private List<Ship> opponentShips;
    private List<Move> moves;
    private List<Move> opponentMoves;
    private final boolean winner;

    private Board(Builder builder) {
        id = builder.id;
        gameId = builder.gameId;
        ships = builder.ships;
        opponentShips = builder.opponentShips;
        moves = builder.moves;
        opponentMoves = builder.opponentMoves;
        winner = builder.winner;
    }

    public BigInteger getGameId() {
        return gameId;
    }

    public BigInteger getId() {
        return id;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public List<Ship> getOpponentShips() {
        return opponentShips;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public List<Move> getOpponentMoves() {
        return opponentMoves;
    }

    public boolean isWinner() {
        return winner;
    }

    public Builder copy() {
        return builder()
                .withId(getId())
                .withGameId(getGameId())
                .withShips(getShips())
                .withOpponentShips(getOpponentShips())
                .withMoves(getMoves())
                .withOpponentMoves(getOpponentMoves())
                .withWinner(isWinner());

    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static final class Builder {
        private BigInteger id;
        private BigInteger gameId;
        private List<Ship> ships;
        private List<Ship> opponentShips;
        private List<Move> moves;
        private List<Move> opponentMoves;
        private boolean winner;

        public Builder withId(BigInteger id) {
            this.id = id;
            return this;
        }

        public Builder withGameId(BigInteger gameId) {
            this.gameId = gameId;
            return this;
        }

        public Builder addShip(Ship ship) {
            ships = addTo(ships, ship);
            return this;
        }

        public Builder withShips(List<Ship> ships) {
            this.ships = ships;
            return this;
        }

        public Builder withOpponentShips(List<Ship> opponentShips) {
            this.opponentShips = opponentShips;
            return this;
        }

        public Builder addOpponentShip(Ship ship) {
            opponentShips = addTo(opponentShips, ship);
            return this;
        }

        public Builder addMove(Move move) {
            moves = addTo(moves, move);
            return this;
        }

        public Builder withMoves(List<Move> moves) {
            this.moves = moves;
            return this;
        }

        public Builder withOpponentMoves(List<Move> opponentMoves) {
            this.opponentMoves = opponentMoves;
            return this;
        }

        public Builder addOpponentMove(Move move) {
            opponentMoves = addTo(opponentMoves, move);
            return this;
        }

        public Builder withWinner(boolean winner) {
            this.winner = winner;
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

        if (winner != board.winner) return false;
        if (gameId != null ? !gameId.equals(board.gameId) : board.gameId != null) return false;
        if (id != null ? !id.equals(board.id) : board.id != null) return false;
        if (ships != null ? !ships.equals(board.ships) : board.ships != null) return false;
        if (opponentShips != null ? !opponentShips.equals(board.opponentShips) : board.opponentShips != null)
            return false;
        if (moves != null ? !moves.equals(board.moves) : board.moves != null) return false;
        return opponentMoves != null ? opponentMoves.equals(board.opponentMoves) : board.opponentMoves == null;
    }

    @Override
    public int hashCode() {
        int result = gameId != null ? gameId.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (ships != null ? ships.hashCode() : 0);
        result = 31 * result + (opponentShips != null ? opponentShips.hashCode() : 0);
        result = 31 * result + (moves != null ? moves.hashCode() : 0);
        result = 31 * result + (opponentMoves != null ? opponentMoves.hashCode() : 0);
        result = 31 * result + (winner ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "\"gameId\":" + getGameId() +
                ", \"id\":" + getId() +
                ", \"ships\":" + getShips() +
                ", \"opponentShips\":" + getOpponentShips() +
                ", \"moves\":" + getMoves() +
                ", \"opponentMoves\":" + getOpponentMoves() +
                ", \"winner\":" + isWinner() +
                '}';
    }
}
