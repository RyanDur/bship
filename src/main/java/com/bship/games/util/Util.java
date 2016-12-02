package com.bship.games.util;

import com.bship.games.domains.Point;

public class Util {

    public static final int SIDE = 10;

    public static Integer toIndex(Point point) {
        return point.getY() + (point.getX() * SIDE);
    }

    public static Point toPoint(Integer index) {
        return new Point(index / SIDE, index % SIDE);
    }
}
