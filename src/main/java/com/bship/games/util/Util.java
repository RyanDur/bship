package com.bship.games.util;

import com.bship.games.domains.Point;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;
import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.empty;

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


    public static  <T> List<T> addTo(List<T> list, T elem) {
        return concat(ofNullable(list).map(Collection::stream).orElse(empty()), Stream.of(elem))
                .filter(Objects::nonNull)
                .collect(collectingAndThen(toList(), Collections::unmodifiableList));
    }
}
