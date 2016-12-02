package com.bship.games.domains;

import com.bship.games.domains.validations.BoundsCheck;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Ship {

    @JsonProperty("type")
    private Harbor shipType;

    @BoundsCheck
    private Point start;

    @BoundsCheck
    private Point end;

    private Long boardId;
    private Long id;

    public Ship() {}

    private Ship(Builder builder) {
        this.shipType = builder.shipType;
        this.start = builder.start;
        this.end = builder.end;
        this.boardId = builder.boardId;
        this.id = builder.id;
    }

    public Harbor getShipType() {
        return shipType;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public Long getBoardId() {
        return boardId;
    }

    public Builder copy() {
        return builder()
                .withShipType(shipType)
                .withStart(start)
                .withEnd(end)
                .withBoardId(boardId);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Harbor shipType;
        private Point start;
        private Point end;
        private Long boardId;
        private Long id;

        public Builder withShipType(Harbor shipType) {
            this.shipType = shipType;
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

        public Ship build() {
            return new Ship(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ship ship = (Ship) o;

        if (shipType != ship.shipType) return false;
        if (start != null ? !start.equals(ship.start) : ship.start != null) return false;
        if (end != null ? !end.equals(ship.end) : ship.end != null) return false;
        if (boardId != null ? !boardId.equals(ship.boardId) : ship.boardId != null) return false;
        return id != null ? id.equals(ship.id) : ship.id == null;
    }

    @Override
    public int hashCode() {
        int result = shipType != null ? shipType.hashCode() : 0;
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + (boardId != null ? boardId.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "shipType=" + shipType +
                ", start=" + start +
                ", end=" + end +
                ", boardId=" + boardId +
                ", id=" + id +
                '}';
    }
}
