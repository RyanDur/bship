package com.bship.games.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;
import java.util.Objects;

import static com.bship.games.util.Util.addTo;

@JsonDeserialize(builder = Board.Builder.class)
public class Board {

    @JsonIgnore
    private Long gameId;
    private Long id;

    private List<Piece> pieces;
    private List<Piece> opponentPieces;
    private List<Move> moves;
    private List<Move> opponentMoves;
    private final boolean winner;

    private Board(Builder builder) {
        id = builder.id;
        gameId = builder.gameId;
        pieces = builder.pieces;
        opponentPieces = builder.opponentPieces;
        moves = builder.moves;
        opponentMoves = builder.opponentMoves;
        winner = builder.winner;
    }

    public Long getGameId() {
        return gameId;
    }

    public Long getId() {
        return id;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public List<Piece> getOpponentPieces() {
        return opponentPieces;
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
                .withPieces(getPieces())
                .withOpponentPieces(getOpponentPieces())
                .withMoves(getMoves())
                .withOpponentMoves(getOpponentMoves())
                .withWinner(isWinner());

    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board that = (Board) o;

        return Objects.equals(this.gameId, that.gameId) &&
                Objects.equals(this.id, that.id) &&
                Objects.equals(this.moves, that.moves) &&
                Objects.equals(this.opponentMoves, that.opponentMoves) &&
                Objects.equals(this.opponentPieces, that.opponentPieces) &&
                Objects.equals(this.pieces, that.pieces) &&
                Objects.equals(this.winner, that.winner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, id, moves, opponentMoves, opponentPieces, pieces,
                winner);
    }

    @JsonPOJOBuilder
    public static final class Builder {
        private Long id;
        private Long gameId;
        private List<Piece> pieces;
        private List<Piece> opponentPieces;
        private List<Move> moves;
        private List<Move> opponentMoves;
        private boolean winner;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withGameId(Long gameId) {
            this.gameId = gameId;
            return this;
        }

        public Builder addPiece(Piece piece) {
            pieces = addTo(pieces, piece);
            return this;
        }

        public Builder withPieces(List<Piece> pieces) {
            this.pieces = pieces;
            return this;
        }

        public Builder withOpponentPieces(List<Piece> opponentPieces) {
            this.opponentPieces = opponentPieces;
            return this;
        }

        public Builder addOpponentPieces(Piece piece) {
            opponentPieces = addTo(opponentPieces, piece);
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
    public String toString() {
        return "{" +
                "\"gameId\":" + getGameId() +
                ", \"id\":" + getId() +
                ", \"ships\":" + getPieces() +
                ", \"opponentPieces\":" + getOpponentPieces() +
                ", \"moves\":" + getMoves() +
                ", \"opponentMoves\":" + getOpponentMoves() +
                ", \"winner\":" + isWinner() +
                '}';
    }
}
