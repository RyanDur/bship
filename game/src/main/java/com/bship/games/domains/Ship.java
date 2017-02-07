package com.bship.games.domains;

import com.bship.games.domains.validations.BoundsCheck;
import com.bship.games.domains.validations.NonEmpty;
import com.bship.games.domains.validations.PlacementCheck;
import com.bship.games.domains.validations.ShipExists;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @JsonIgnore
    private Long boardId;

    private boolean sunk;
    private Long id;

    public Ship() {
    }

    private Ship(Builder builder) {
        this.type = builder.type;
        this.start = builder.start;
        this.end = builder.end;
        this.boardId = builder.boardId;
        this.id = builder.id;
        this.sunk = builder.sunk;
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

    public Builder copy() {
        return builder()
                .withId(id)
                .withType(type)
                .withStart(start)
                .withEnd(end)
                .withBoardId(boardId)
                .withSunk(sunk);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Harbor type;
        private Point start;
        private Point end;
        private Long boardId;
        private Long id;
        private boolean sunk;

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

        public Ship build() {
            return new Ship(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ship ship = (Ship) o;

        return type == ship.type;
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
        return "Ship{" +
                "type=" + type +
                ", start=" + start +
                ", end=" + end +
                ", boardId=" + boardId +
                ", sunk=" + sunk +
                ", id=" + id +
                '}';
    }
}
