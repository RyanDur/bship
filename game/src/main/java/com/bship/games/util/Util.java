package com.bship.games.util;

import com.bship.games.domains.Point;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;

public class Util {

    public static final int SIDE = 10;

    public static Integer toIndex(Point point) {
        return ofNullable(point).filter(Point::isSet)
                .map(p -> p.getY() + (p.getX() * SIDE))
                .orElse(null);
    }

    public static Point toPoint(Integer index) {
        return new Point(index / SIDE, index % SIDE);
    }

    public static List<Point> pointsRange(Point start, Point end) {
        Optional<Integer> startY = ofNullable(start).map(Point::getY);
        Optional<Integer> startX = ofNullable(start).map(Point::getX);
        Optional<Integer> endY = ofNullable(end).map(Point::getY);
        Optional<Integer> endX = ofNullable(end).map(Point::getX);

        if (startX.isPresent() && startY.isPresent() && endX.isPresent() && endY.isPresent()) {
            IntFunction<Point> mapper;
            IntStream range;
            if (Objects.equals(startY, endY)) {
                mapper = x -> new Point(x, startY.get());
                range = rangeClosed(startX.get(), endX.get());
            } else {
                mapper = y -> new Point(startX.get(), y);
                range = rangeClosed(startY.get(), endY.get());
            }

            return range.mapToObj(mapper).collect(toList());
        }
        return asList(start, end);
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
}
