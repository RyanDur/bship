package com.bship.games.util

import com.bship.games.endpoints.cabinet.entity.Piece
import com.bship.games.endpoints.cabinet.entity.Point
import com.bship.games.logic.definitions.Direction.*
import com.bship.games.logic.definitions.Harbor.*
import com.bship.games.logic.definitions.PieceType.Dummy.INVALID_PIECE
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Test
import java.util.*
import java.util.Arrays.asList
import java.util.Collections.emptyList
import java.util.Optional.of

class UtilTest {

    @Test
    fun toIndex_shouldTakeAPointAndConvertToIndexForFirstRowFirstColumn() {
        val point = Point(0, 0)
        val index = Util.toIndex(point)

        assertThat(index, `is`(0))
    }

    @Test
    fun toPoint_shouldTakeAnIndexAndTurnItIntoAPointForFirstRowFirstColumn() {
        val actual = Util.toPoint(0)
        val expected = Point(0, 0)

        assertThat(actual, `is`(equalTo(expected)))
    }

    @Test
    fun toIndex_shouldTakeAPointAndConvertToIndexForSecondRowFirstColumn() {
        val point = Point(1, 0)
        val index = Util.toIndex(point)

        assertThat(index, `is`(10))
    }

    @Test
    fun toPoint_shouldTakeAnIndexAndTurnItIntoAPointForSecondRowFirstColumn() {
        val actual = Util.toPoint(10)
        val expected = Point(1, 0)

        assertThat(actual, `is`(equalTo(expected)))
    }

    @Test
    fun toIndex_shouldTakeAPointAndConvertToIndexForSecondRowThirdColumn() {
        val point = Point(1, 2)
        val index = Util.toIndex(point)

        assertThat(index, `is`(12))
    }

    @Test
    fun toPoint_shouldTakeAnIndexAndTurnItIntoAPointForSecondRowThirdColumn() {
        val actual = Util.toPoint(12)
        val expected = Point(1, 2)

        assertThat(actual, `is`(equalTo(expected)))
    }

    @Test
    fun toIndex_shouldTakeAPointAndConvertToIndexForTenthRowFourthColumn() {
        val point = Point(9, 3)
        val index = Util.toIndex(point)

        assertThat(index, `is`(93))
    }

    @Test
    fun toIndex_shouldReturnNullIfPointIsNotSetCompletely() {
        val point = Point()
        val index = Util.toIndex(point)

        assertThat(index, `is`(nullValue()))
    }

    @Test
    fun toIndex_shouldReturnNullIfPointXIsNotSet() {
        val point = Point(null, 5)
        val index = Util.toIndex(point)

        assertThat(index, `is`(nullValue()))
    }

    @Test
    fun toIndex_shouldReturnNullIfNoPoint() {
        val index = Util.toIndex(null)

        assertThat(index, `is`(nullValue()))
    }

    @Test
    fun toIndex_shouldReturnNullIfPointYIsNotSet() {
        val point = Point(5, null)
        val index = Util.toIndex(point)

        assertThat(index, `is`(nullValue()))
    }

    @Test
    fun toPoint_shouldTakeAnIndexAndTurnItIntoAPointForTenthRowFourthColumn() {
        val actual = Util.toPoint(93)
        val expected = Point(9, 3)

        assertThat(actual, `is`(equalTo(expected)))
    }

    @Test
    fun pointsRange_shouldReturnAListOfPointsWithinTheXRangeGoingRight() {
        val placement = Point(3, 3)
        val end = Point(7, 3)
        val piece = Piece.build {
            withPlacement { placement }
            withType { AIRCRAFT_CARRIER }
            withOrientation { RIGHT }
        }

        val actual = Util.pointsRange(piece)

        assertThat(actual, contains(
                placement,
                Point(4, 3),
                Point(5, 3),
                Point(6, 3),
                end))
    }

    @Test
    fun pointsRange_shouldReturnAListOfPointsWithinTheXRangeGoingLeft() {
        val placement = Point(9, 9)
        val end = Point(5, 9)
        val piece = Piece.build {
            withPlacement { placement }
            withType { AIRCRAFT_CARRIER }
            withOrientation { LEFT }
        }

        val actual = Util.pointsRange(piece)

        assertThat(actual, containsInAnyOrder(
                placement,
                Point(8, 9),
                Point(7, 9),
                Point(6, 9),
                end))
    }

    @Test
    fun pointsRange_shouldReturnAListOfPointsWithinTheYRangeGoingDown() {
        val placement = Point(4, 2)
        val end = Point(4, 5)
        val piece = Piece.build {
            withPlacement { placement }
            withType { BATTLESHIP }
            withOrientation { DOWN }
        }

        val actual = Util.pointsRange(piece)

        assertThat(actual, containsInAnyOrder(
                placement,
                Point(4, 3),
                Point(4, 4),
                end))
    }

    @Test
    fun pointsRange_shouldReturnAListOfPointsWithinTheYRangeGoingUP() {
        val placement = Point(7, 8)
        val end = Point(7, 5)
        val piece = Piece.build {
            withPlacement { placement }
            withType { BATTLESHIP }
            withOrientation { UP }
        }

        val actual = Util.pointsRange(piece)

        assertThat(actual, containsInAnyOrder(
                placement,
                Point(7, 7),
                Point(7, 6),
                end))
    }

    @Test
    fun pointsRange_shouldReturnAListOfPointRangeGoingNONE() {
        val placement = Point(7, 8)
        val piece = Piece.build {
            withPlacement { placement }
            withType { CRUISER }
            withOrientation { NONE }
        }

        val actual = Util.pointsRange(piece)

        assertThat(actual, `is`(equalTo(listOf(placement))))
    }

    @Test
    fun pointsRange_shouldHandlePlacementWithNoX() {
        val piece = Piece.build {
            withPlacement { Point(null, 3) }
            withType { BATTLESHIP }
            withOrientation { DOWN }
        }

        val points = Util.pointsRange(piece)
        assertThat(points, `is`(equalTo(emptyList<Any>())))
    }

    @Test
    fun pointsRange_shouldHandlePlacementWithNoY() {
        val piece = Piece.build {
            withOrientation { UP }
            withPlacement { Point(0, null) }
            withType { DESTROYER }
        }

        val points = Util.pointsRange(piece)
        assertThat(points, `is`(equalTo(emptyList<Any>())))
    }

    @Test
    fun pointsRange_shouldHandleNoSize() {
        val piece = Piece.build {
            withPlacement { Point(0, 3) }
            withType { INVALID_PIECE }
            withOrientation { DOWN }
        }

        val points = Util.pointsRange(piece)
        assertThat(points, `is`(equalTo(emptyList<Any>())))
    }

    @Test
    fun pointsRange_shouldHandleNullDirection() {
        val point = Point(0, 3)
        val piece = Piece.build {
            withPlacement { point }
            withType { AIRCRAFT_CARRIER }
        }

        val points = Util.pointsRange(piece)
        assertThat(points, `is`(equalTo(listOf(point))))
    }

    @Test
    fun detectCollision_shouldDetectACollisionIfPointsIntersect() {
        val pieceA = Piece.build {
            withPlacement { Point(3, 3) }
            withOrientation { RIGHT }
            withType { AIRCRAFT_CARRIER }
        }

        val pieceB = Piece.build {
            withPlacement { Point(4, 2) }
            withType { BATTLESHIP }
            withOrientation { DOWN }
        }

        val actual = Util.detectCollision(pieceA, pieceB)

        assertThat(actual, `is`(true))
    }

    @Test
    fun detectCollision_shouldDetectIfAnyPointsDoNotIntersect() {
        val pieceA = Piece.build {
            withPlacement { Point(3, 3) }
            withOrientation { RIGHT }
            withType { AIRCRAFT_CARRIER }
        }

        val pieceB = Piece.build {
            withPlacement { Point(4, 2) }
            withType { BATTLESHIP }
            withOrientation { DOWN }
        }

        val actual = Util.detectCollision(Arrays.asList(pieceA, pieceB))

        assertThat(actual, `is`(true))
    }

    @Test
    fun detectCollision_shouldDetectIfAnyPointsDoIntersect() {
        val pieceA = Piece.build {
            withPlacement { Point(3, 3) }
            withOrientation { LEFT }
            withType { AIRCRAFT_CARRIER }
        }

        val pieceB = Piece.build {
            withPlacement { Point(4, 2) }
            withType { BATTLESHIP }
            withOrientation { DOWN }
        }

        val actual = Util.detectCollision(Arrays.asList(pieceA, pieceB))

        assertThat(actual, `is`(false))
    }

    @Test
    fun detectCollision_shouldNotDetectACollisionIfPointsDoNotIntersect() {
        val pieceA = Piece.build {
            withPlacement { Point(0, 3) }
            withType { AIRCRAFT_CARRIER }
            withOrientation { DOWN }
        }

        val pieceB = Piece.build {
            withPlacement { Point(4, 5) }
            withType { AIRCRAFT_CARRIER }
            withOrientation { UP }
        }

        val actual = Util.detectCollision(pieceA, pieceB)

        assertThat(actual, `is`(false))
    }

    @Test
    fun addTo_shouldReturnAListWithAnotherElementAddedToIt() {
        val list = asList(1, 2, 3, 4, 5)
        val expected = asList(1, 2, 3, 4, 5, 6)

        val actual = Util.addTo(list, 6)
        assertThat(actual, `is`(equalTo(expected)))
    }

    @Test
    fun addTo_functionReturn_shouldReturnAList() {
        val list = asList(3, 2)
        val actual = of(1).map(Util.addTo(list)).orElse(emptyList())

        assertThat(actual.size, `is`(3))
        assertThat(actual, contains(3, 2, 1))
    }

    @Test
    fun concat_shouldCombineTwiLists() {
        val list1 = asList(1, 2)
        val list2 = asList(3, 4)
        val actual = Util.concat(list1, list2)

        assertThat(actual.size, `is`(4))
        assertThat(actual, contains(1, 2, 3, 4))

    }

    @Test
    fun concat_functionReturn_shouldCombineTwiLists() {
        val list1 = asList(1, 2)
        val list2 = asList(3, 4)
        val actual = of(list1).map(Util.concat(list2)).orElse(emptyList())

        assertThat(actual.size, `is`(4))
        assertThat(actual, contains(1, 2, 3, 4))
    }

    @Test
    fun isPlaced_shouldKnowIfAPieceIsPlaced() {
        val piece = Piece.build {
            withPlacement { Point(1, 2) }
        }

        val placed = Util.isPlaced(piece)

        assertThat(placed, `is`(true))
    }

    @Test
    fun isPlaced_shouldKnowIfAShipDoesNotExist() {
        val placed = Util.isPlaced(null)

        assertThat(placed, `is`(false))
    }

    @Test
    fun validRange_shouldKnowIfARangeGoesOffTheLeftSideOfBoard() {
        val piece = Piece.build {
            withPlacement { Point(0, 0) }
            withType { DESTROYER }
            withOrientation { LEFT }
        }

        val actual = Util.validRange(piece)
        assertThat(actual, `is`(false))
    }

    @Test
    fun validRange_shouldKnowIfARangeGoesOffTheRightSideOfBoard() {
        val piece = Piece.build {
            withPlacement { Point(9, 9) }
            withType { SUBMARINE }
            withOrientation { RIGHT }
        }

        val actual = Util.validRange(piece)
        assertThat(actual, `is`(false))
    }

    @Test
    fun validRange_shouldKnowIfARangeGoesOffTheTopOfTheBoard() {
        val piece = Piece.build {
            withPlacement { Point(9, 0) }
            withType { SUBMARINE }
            withOrientation { UP }
        }

        val actual = Util.validRange(piece)
        assertThat(actual, `is`(false))
    }

    @Test
    fun validRange_shouldKnowIfARangeGoesOffTheBottomOfTheBoard() {
        val piece = Piece.build {
            withPlacement { Point(0, 9) }
            withType { SUBMARINE }
            withOrientation { DOWN }
        }

        val actual = Util.validRange(piece)
        assertThat(actual, `is`(false))
    }

    @Test
    fun validRange_shouldKnowIfARangeIsGood() {
        val piece = Piece.build {
            withPlacement { Point(0, 9) }
            withType { SUBMARINE }
            withOrientation { UP }
        }

        val actual = Util.validRange(piece)
        assertThat(actual, `is`(true))
    }
}