package com.bship.games.endpoints.cabinet.entity;

import com.bship.games.endpoints.errors.validations.BoundsCheck;

import java.util.Objects;
import java.util.StringJoiner;

@BoundsCheck
public class Point {

    private Integer x;
    private Integer y;

    public Point() {
    }

    public Point(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point that = (Point) o;

        return Objects.equals(this.x, that.x) &&
                Objects.equals(this.y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ","{", "}")
                .add("\"x\": " + x)
                .add("\"y\": " + y)
                .toString();
    }
}
