package com.bship.games.domains;

import com.bship.games.domains.validations.BoundsCheck;
import com.bship.games.domains.validations.NonEmpty;
import com.bship.games.domains.validations.PlacementCheck;
import com.bship.games.domains.validations.ShipExists;
import com.bship.games.domains.validations.ValidPoint;
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
    @ValidPoint
    private Point placement;

    @NonEmpty
    private Direction orientation;

    @NonEmpty
    private Long id;

    private Long boardId;
    private Integer size;
    private boolean sunk;

    private Piece(Builder builder) {
        type = builder.type;
        placement = builder.placement;
        orientation = builder.orientation;
        boardId = builder.boardId;
        id = builder.id;
        sunk = builder.sunk;
        size = builder.size;
    }

    public Harbor getType() {
        return type;
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

    public Point getPlacement() {
        return placement;
    }

    public Direction getOrientation() {
        return orientation;
    }

    @JsonIgnore
    public Builder copy() {
        return builder()
                .withId(id)
                .withType(type)
                .withPlacement(placement)
                .withOrientation(orientation)
                .withBoardId(boardId)
                .withSunk(sunk)
                .withSize(size);
    }

    @JsonIgnore
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Piece that = (Piece) o;

        return Objects.equals(this.boardId, that.boardId) &&
                Objects.equals(this.id, that.id) &&
                Objects.equals(this.orientation, that.orientation) &&
                Objects.equals(this.placement, that.placement) &&
                Objects.equals(this.size, that.size) &&
                Objects.equals(this.sunk, that.sunk) &&
                Objects.equals(this.type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardId, id, orientation, placement, size, sunk,
                type);
    }

    @JsonPOJOBuilder
    public static final class Builder {

        private Harbor type;
        private Long boardId;
        private Long id;
        private boolean sunk;
        private Integer size;
        private Point placement;
        private Direction orientation;

        public Builder withType(Harbor shipType) {
            this.type = shipType;
            return this;
        }

        public Builder withPlacement(Point placement) {
            this.placement = placement;
            return this;
        }

        public Builder withOrientation(Direction orientation) {
            this.orientation = orientation;
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
    public String toString() {
        return new StringJoiner(", ", "{", "}")
                .add("\"id\": " + id)
                .add("\"boardId\": " + boardId)
                .add("\"placement\": " + placement)
                .add("\"orientation\": " + "\"" + orientation + "\"")
                .add("\"size\": " + size)
                .add("\"sunk\": " + sunk)
                .add("\"type\": " + "\"" + type + "\"")
                .toString();
    }
}
