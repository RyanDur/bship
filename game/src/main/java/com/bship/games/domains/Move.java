package com.bship.games.domains;

import com.bship.games.domains.validations.BoundsCheck;
import com.bship.games.domains.validations.NonEmpty;
import com.bship.games.domains.validations.ValidPoint;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.math.BigInteger;

import static java.util.Optional.ofNullable;

@JsonDeserialize(builder = Move.Builder.class)
public class Move {

    private BigInteger boardId;

    private BigInteger id;

    private MoveStatus status;

    @NonEmpty
    @BoundsCheck
    @ValidPoint
    private Point point;

    public Move() {}

    private Move(Builder builder) {
        this.point = builder.point;
        this.status = builder.status;
        this.id = builder.id;
        this.boardId = builder.boardId;
    }

    public Point getPoint() {
        return point;
    }

    public MoveStatus getStatus() {
        return status;
    }

    public BigInteger getId() {
        return id;
    }

    public BigInteger getBoardId() {
        return boardId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder copy() {
        return builder().withId(id)
                .withBoardId(boardId)
                .withPoint(point)
                .withStatus(status);
    }

    @JsonPOJOBuilder
    public static class Builder {
        private Point point;
        private MoveStatus status;
        private BigInteger id;
        private BigInteger boardId;

        public Move build() {
            return new Move(this);
        }

        public Builder withPoint(Point point) {
            this.point = point;
            return this;
        }

        public Builder withStatus(MoveStatus status) {
            this.status = status;
            return this;
        }

        public Builder withId(BigInteger id) {
            this.id = id;
            return this;
        }

        public Builder withBoardId(BigInteger boardId) {
            this.boardId = boardId;
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move move = (Move) o;

        return point != null ? point.equals(move.point) : move.point == null;
    }

    @Override
    public int hashCode() {
        return point != null ? point.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "{" +
                "\"boardId\":" + boardId +
                ", \"id\":" + id +
                ", \"status\":" + ofNullable(status).map(s -> "\"" + s + "\"").orElse(null) +
                ", \"point\":" + point +
                '}';
    }
}
