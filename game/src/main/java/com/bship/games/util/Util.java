package com.bship.games.util;

import com.bship.games.domains.Piece;
import com.bship.games.domains.Point;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
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

    public static boolean isPlaced(Piece piece) {
        return ofNullable(piece)
                .filter(s -> isSet(s.getPlacement()))
                .isPresent();
    }

    public static Boolean detectCollision(Piece pieceA, Piece pieceB) {
        List<Point> a = pointsRange(pieceA);
        List<Point> b = pointsRange(pieceB);
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

    public static boolean validRange(Piece piece) {
        List<Point> points = pointsRange(piece);
        return points.stream()
                .noneMatch(p -> p.getX() < 0) &&
                points.stream()
                        .noneMatch(p -> p.getX() > SIDE - 1) &&
                points.stream()
                        .noneMatch(p -> p.getY() < 0) &&
                points.stream()
                        .noneMatch(p -> p.getY() > SIDE - 1);
    }

    public static List<Point> pointsRange(Piece piece) {
        if (isSet(piece.getPlacement()) && nonNull(piece.getSize()) &&
                nonNull(piece.getOrientation())) {
            IntFunction<Point> mapper;
            IntStream range;
            switch (piece.getOrientation()) {
                case LEFT:
                    mapper = x -> new Point(x, piece.getPlacement().getY());
                    range = IntStream.rangeClosed(
                            piece.getPlacement().getX() - piece.getSize() + 1,
                            piece.getPlacement().getX());
                    break;
                case RIGHT:
                    mapper = x -> new Point(x, piece.getPlacement().getY());
                    range = IntStream.rangeClosed(
                            piece.getPlacement().getX(),
                            piece.getPlacement().getX() + piece.getSize() - 1);
                    break;
                case DOWN:
                    mapper = y -> new Point(piece.getPlacement().getX(), y);
                    range = IntStream.rangeClosed(
                            piece.getPlacement().getY(),
                            piece.getPlacement().getY() + piece.getSize() - 1);
                    break;
                case UP:
                    mapper = y -> new Point(piece.getPlacement().getX(), y);
                    range = rangeClosed(
                            piece.getPlacement().getY() - piece.getSize() + 1,
                            piece.getPlacement().getY());
                    break;
                default:
                    return singletonList(piece.getPlacement());
            }
            return range.mapToObj(mapper).collect(toList());
        }
        return emptyList();
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
