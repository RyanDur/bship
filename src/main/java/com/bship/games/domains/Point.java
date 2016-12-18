package com.bship.games.domains;

import com.bship.games.domains.validations.BoundsCheck;

@BoundsCheck
public class Point {
    private Integer x;
    private Integer y;

    public Point() {}

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

        Point point = (Point) o;

        return (x != null ? x.equals(point.x) : point.x == null) &&
                (y != null ? y.equals(point.y) : point.y == null);
    }

    @Override
    public int hashCode() {
        int result = x != null ? x.hashCode() : 0;
        result = 31 * result + (y != null ? y.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
