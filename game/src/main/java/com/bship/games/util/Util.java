package com.bship.games.util;

import com.bship.games.domains.Point;
import com.bship.games.domains.Ship;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;

public class Util {

    public static final int SIDE = 10;

    public static Integer toIndex(Point point) {
        return ofNullable(point).filter(Util::isSet)
                .map(p -> p.getY() + (p.getX() * SIDE))
                .orElse(null);
    }

    public static Point toPoint(Integer index) {
        return new Point(index / SIDE, index % SIDE);
    }

    public static List<Point> pointsRange(Point start, Point end) {
        if (isSet(start) && isSet(end)) {
            IntFunction<Point> mapper;
            IntStream range;
            if (Objects.equals(start.getY(), end.getY())) {
                mapper = x -> new Point(x, start.getY());
                range = rangeClosed(start.getX(), end.getX());
            } else {
                mapper = y -> new Point(start.getX(), y);
                range = rangeClosed(start.getY(), end.getY());
            }

            return range.mapToObj(mapper).collect(toList());
        }
        return asList(start, end);
    }

    public static boolean isPlaced(Ship ship) {
        return ofNullable(ship)
                .filter(s -> isSet(s.getStart()))
                .filter(s -> isSet(s.getEnd()))
                .isPresent();
    }

    public static Boolean detectCollision(List<Point> a, List<Point> b) {
        return a.stream().anyMatch(b::contains);
    }

    public static <T> Function<T, List<T>> addTo(List<T> list) {
        return elem -> addTo(list, elem);
    }

    public static <T> List<T> addTo(List<T> list, T elem) {
        return concat(list, singletonList(elem));
    }

    public static <T> Function<List<T>, List<T>> concat(List<T> listB) {
        return listA -> concat(listA, listB);
    }

    public static <T> List<T> concat(List<T> listA, List<T> listB) {
        return toImmutableList(listA, listB);
    }

    private static <T> List<T> toImmutableList(List<T> originalList, List<T> newList) {
        return Stream.of(originalList, newList)
                .filter(Objects::nonNull)
                .flatMap(list -> list.stream().filter(Objects::nonNull))
                .collect(collectingAndThen(toList(), Collections::unmodifiableList));
    }

    private static boolean isSet(Point point) {
        return ofNullable(point)
                .filter(p -> nonNull(p.getX()))
                .filter(p -> nonNull(p.getY()))
                .isPresent();
    }
}
