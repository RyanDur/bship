package com.bship.games.domains;

import com.bship.games.Harbor;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Ship {
    @JsonProperty("type")
    private Harbor shipType;
    private Point start;
    private Point end;

    public Ship() {}

    public Ship(Harbor shipType, Point start, Point end) {
        this.shipType = shipType;
        this.start = start;
        this.end = end;
    }

    public void setShipType(Harbor shipType) {
        this.shipType = shipType;
    }

    public Harbor getShipType() {
        return shipType;
    }


    public void setStart(Point start) {
        this.start = start;
    }

    public Point getStart() {
        return start;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public Point getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ship ship = (Ship) o;

        if (getShipType() != ship.getShipType()) return false;
        if (getStart() != null ? !getStart().equals(ship.getStart()) : ship.getStart() != null) return false;
        return getEnd() != null ? getEnd().equals(ship.getEnd()) : ship.getEnd() == null;

    }

    @Override
    public int hashCode() {
        int result = getShipType() != null ? getShipType().hashCode() : 0;
        result = 31 * result + (getStart() != null ? getStart().hashCode() : 0);
        result = 31 * result + (getEnd() != null ? getEnd().hashCode() : 0);
        return result;
    }
}
