package com.bship.games.domains;

import com.bship.games.domains.validations.BoundsCheck;
import com.bship.games.domains.validations.NonEmpty;
import com.bship.games.domains.validations.PlacementCheck;
import com.bship.games.domains.validations.ShipExists;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Objects;
import java.util.StringJoiner;

@JsonDeserialize(builder = Piece.Builder.class)
@PlacementCheck
public class Piece {

    @NonEmpty
    @ShipExists
    private Harbor type;

    @NonEmpty
    @BoundsCheck
    private Point start;

    @NonEmpty
    @BoundsCheck
    private Point end;

    @NonEmpty
    private Long id;

    private Long boardId;
    private Integer size;
    private boolean sunk;

    private Piece(Builder builder) {
        type = builder.type;
        start = builder.start;
        end = builder.end;
        boardId = builder.boardId;
        id = builder.id;
        sunk = builder.sunk;
        size = builder.size;
    }

    public Harbor getType() {
        return type;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public boolean isSunk() {
        return sunk;
    }

    public Long getId() {
        return id;
    }

    public Long getBoardId() {
        return boardId;
    }

    public Integer getSize() {
        return size;
    }

    @JsonIgnore
    public Builder copy() {
        return builder()
                .withId(id)
                .withType(type)
                .withStart(start)
                .withEnd(end)
                .withBoardId(boardId)
                .withSunk(sunk)
                .withSize(size);
    }

    @JsonIgnore
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static final class Builder {

        private Harbor type;
        private Point start;
        private Point end;
        private Long boardId;
        private Long id;
        private boolean sunk;
        private Integer size;

        public Builder withType(Harbor shipType) {
            this.type = shipType;
            return this;
        }

        public Builder withStart(Point start) {
            this.start = start;
            return this;
        }

        public Builder withEnd(Point end) {
            this.end = end;
            return this;
        }

        public Builder withBoardId(Long boardId) {
            this.boardId = boardId;
            return this;
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withSunk(boolean sunk) {
            this.sunk = sunk;
            return this;
        }

        public Builder withSize(Integer size) {
            this.size = size;
            return this;
        }

        public Piece build() {
            return new Piece(this);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Piece that = (Piece) o;

        return Objects.equals(this.boardId, that.boardId) &&
                Objects.equals(this.end, that.end) &&
                Objects.equals(this.id, that.id) &&
                Objects.equals(this.size, that.size) &&
                Objects.equals(this.start, that.start) &&
                Objects.equals(this.sunk, that.sunk) &&
                Objects.equals(this.type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardId, end, id, size, start, sunk,
                type);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "{", "}")
                .add("\"boardId\": " + boardId)
                .add("\"end\": " + end)
                .add("\"id\": " + id)
                .add("\"size\": " + size)
                .add("\"start\": " + start)
                .add("\"sunk\": " + sunk)
                .add("\"type\": " + "\"" + type + "\"")
                .toString();
    }
}