package com.bship.games.domains;

import com.bship.games.domains.validations.BoundsCheck;
import com.bship.games.domains.validations.NonEmpty;
import com.bship.games.domains.validations.PlacementCheck;
import com.bship.games.domains.validations.ShipExists;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.math.BigInteger;

@JsonDeserialize(builder = Ship.Builder.class)
@PlacementCheck
public class Ship {

    @NonEmpty
    @ShipExists
    private Harbor type;

    @NonEmpty
    @BoundsCheck
    private Point start;

    @NonEmpty
    @BoundsCheck
    private Point end;

    private BigInteger boardId;

    private Integer size;
    private boolean sunk;
    private BigInteger id;

    private Ship(Builder builder) {
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

    public BigInteger getId() {
        return id;
    }

    public BigInteger getBoardId() {
        return boardId;
    }

    public Integer getSize() {
        return size;
    }

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

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static final class Builder {
        private Harbor type;
        private Point start;
        private Point end;
        private BigInteger boardId;
        private BigInteger id;
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

        public Builder withBoardId(BigInteger boardId) {
            this.boardId = boardId;
            return this;
        }

        public Builder withId(BigInteger id) {
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

        public Ship build() {
            return new Ship(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ship ship = (Ship) o;

        if (sunk != ship.sunk) return false;
        if (type != ship.type) return false;
        if (start != null ? !start.equals(ship.start) : ship.start != null) return false;
        if (end != null ? !end.equals(ship.end) : ship.end != null) return false;
        if (boardId != null ? !boardId.equals(ship.boardId) : ship.boardId != null) return false;
        return id != null ? id.equals(ship.id) : ship.id == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + (boardId != null ? boardId.hashCode() : 0);
        result = 31 * result + (sunk ? 1 : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\":" + "\"" + type + "\"" +
                ", \"start\":" + start +
                ", \"end\":" + end +
                ", \"boardId\":" + boardId +
                ", \"sunk\":" + sunk +
                ", \"id\":" + id +
                '}';
    }
}
