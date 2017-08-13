package com.bship.games.endpoints.cabinet.entity;

import com.bship.games.logic.rules.Direction;
import com.bship.games.logic.rules.Harbor;
import com.bship.games.endpoints.errors.validations.NonEmpty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Objects;
import java.util.StringJoiner;

@JsonDeserialize(builder = Piece.Builder.class)
public class Piece {

    @NonEmpty
    private Harbor type;

    @NonEmpty
    private Point placement;

    @NonEmpty
    private Direction orientation;

    @NonEmpty
    private Long id;

    private Long boardId;
    private boolean taken;

    private Piece(Builder builder) {
        type = builder.type;
        placement = builder.placement;
        orientation = builder.orientation;
        boardId = builder.boardId;
        id = builder.id;
        taken = builder.taken;
    }

    public Harbor getType() {
        return type;
    }

    public boolean isTaken() {
        return taken;
    }

    public Long getId() {
        return id;
    }

    public Long getBoardId() {
        return boardId;
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
                .withTaken(taken);
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
                Objects.equals(this.orientation, that.orientation) &&
                Objects.equals(this.placement, that.placement) &&
                Objects.equals(this.taken, that.taken) &&
                Objects.equals(this.type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardId, id, orientation, placement, taken,
                type);
    }

    @JsonPOJOBuilder
    public static final class Builder {

        private Harbor type;
        private Long boardId;
        private Long id;
        private boolean taken;
        private Point placement;
        private Direction orientation;

        private Builder() {}

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

        public Builder withTaken(boolean taken) {
            this.taken = taken;
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
                .add("\"orientation\": " + orientation)
                .add("\"taken\": " + taken)
                .add("\"type\": " + type)
                .toString();
    }
}
