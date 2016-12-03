package com.bship.games.util;

import com.bship.games.domains.Point;

import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;

public class Util {

    public static final int SIDE = 10;

    public static Integer toIndex(Point point) {
        return point.getY() + (point.getX() * SIDE);
    }

    public static Point toPoint(Integer index) {
        return new Point(index / SIDE, index % SIDE);
    }

    public static List<Point> pointsRange(Point start, Point end) {
        int startY = start.getY();
        int startX = start.getX();
        int endY = end.getY();
        int endX = end.getX();
        IntFunction<Point> mapper;
        IntStream range;

        if (startY == endY) {
            mapper = x -> new Point(x, startY);
            range = rangeClosed(startX, endX);
        } else {
            mapper = y -> new Point(startX, y);
            range = rangeClosed(startY, endY);
        }

        return range.mapToObj(mapper).collect(toList());
    }


    public static Boolean detectCollision(List<Point> a, List<Point> b) {
        return a.stream().anyMatch(b::contains);
    }
}
