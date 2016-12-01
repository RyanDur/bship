package com.bship.games.util;

import com.bship.games.domains.Point;

public class Util {

    public static final int SIDE = 10;

    public static Integer toIndex(Point point) {
        return (point.getX() * SIDE) + point.getY();
    }

    public static Point toPoint(Integer index) {
        int row = Math.floorDiv(index, SIDE);
        int column = index - (row * SIDE);
        return new Point(row, column);
    }
}
