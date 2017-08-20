package com.bship.games.util

import com.bship.games.endpoints.cabinet.entity.Piece
import com.bship.games.endpoints.cabinet.entity.Point
import com.bship.games.logic.definitions.Direction.*
import java.util.*
import java.util.Collections.emptyList
import java.util.Objects.nonNull
import java.util.Optional.ofNullable
import java.util.function.Function
import java.util.function.IntFunction
import java.util.stream.Collectors.collectingAndThen
import java.util.stream.Collectors.toList
import java.util.stream.IntStream
import java.util.stream.IntStream.rangeClosed
import java.util.stream.Stream

class Util {
    companion object {
        val SIDE = 10

        fun toIndex(point: Point?): Int? {
            return ofNullable(point).filter({ isSet(it) })
                    .map { p -> p.y + p.x * SIDE }
                    .orElse(null)
        }

        fun toPoint(index: Int?): Point {
            return Point(index!! / SIDE, index % SIDE)
        }

        fun isPlaced(piece: Piece?): Boolean {
            return ofNullable(piece)
                    .filter { (_, _, _, _, placement) -> isSet(placement) }
                    .isPresent
        }

        fun detectCollision(pieceA: Piece, pieceB: Piece): Boolean {
            val a = pointsRange(pieceA)
            val b = pointsRange(pieceB)
            return a.any({ b.contains(it) })
        }

        fun <T> addTo(list: List<T>): Function<T, List<T>> {
            return Function { elem -> addTo(list, elem) }
        }

        fun <T> addTo(list: List<T>, elem: T): List<T> {
            return concat(list, listOf(elem))
        }

        fun <T> concat(listA: List<T>, listB: List<T>): List<T> {
            return toImmutableList(listA, listB)
        }

        fun <T> concat(listB: List<T>): Function<List<T>, List<T>> {
            return Function { listA -> listA + listB }
        }

        fun validRange(piece: Piece): Boolean {
            val points = pointsRange(piece)
            return points.stream()
                    .noneMatch { p -> p.x < 0 } &&
                    points.stream()
                            .noneMatch { p -> p.x > SIDE - 1 } &&
                    points.stream()
                            .noneMatch { p -> p.y < 0 } &&
                    points.stream()
                            .noneMatch { p -> p.y > SIDE - 1 }
        }

        fun pointsRange(piece: Piece): List<Point> {
            if (isSet(piece.placement) && nonNull(piece.type.size)) {
                val mapper: IntFunction<Point>
                val range: IntStream
                when (piece.orientation) {
                    LEFT -> {
                        mapper = IntFunction { x -> Point(x, piece.placement.y) }
                        range = IntStream.rangeClosed(
                                piece.placement.x!! - piece.type.size!! + 1,
                                piece.placement.x!!)
                    }
                    RIGHT -> {
                        mapper = IntFunction { x -> Point(x, piece.placement.y) }
                        range = IntStream.rangeClosed(
                                piece.placement.x!!,
                                piece.placement.x!! + piece.type.size!! - 1)
                    }
                    DOWN -> {
                        mapper = IntFunction { y -> Point(piece.placement.x, y) }
                        range = IntStream.rangeClosed(
                                piece.placement.y!!,
                                piece.placement.y!! + piece.type.size!! - 1)
                    }
                    UP -> {
                        mapper = IntFunction { y -> Point(piece.placement.x, y) }
                        range = rangeClosed(
                                piece.placement.y!! - piece.type.size!! + 1,
                                piece.placement.y!!)
                    }
                    else -> return listOf(piece.placement)
                }
                return range.mapToObj(mapper).collect(toList())
            }
            return emptyList()
        }

        private fun <T> toImmutableList(originalList: List<T>, newList: List<T>): List<T> {
            return Stream.of(originalList, newList)
                    .filter({ Objects.nonNull(it) })
                    .flatMap { list -> list.stream().filter({ Objects.nonNull(it) }) }
                    .collect(collectingAndThen(toList(), { Collections.unmodifiableList(it) }))
        }

        private fun isSet(point: Point): Boolean {
            return ofNullable(point)
                    .filter { p -> nonNull(p.x) }
                    .filter { p -> nonNull(p.y) }
                    .isPresent
        }

        fun detectCollision(pieces: List<Piece>): Boolean {
            return pieces.stream()
                    .map { pointsRange(it) }
                    .flatMap { it.stream() }
                    .distinct()
                    .collect(toList()).size != pieces
                    .stream().map { pointsRange(it) }
                    .flatMap { it.stream() }
                    .collect(toList()).size
        }
    }

}
