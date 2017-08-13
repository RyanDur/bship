package com.bship.games.util;

import com.bship.games.endpoints.cabinet.entity.Piece;
import com.bship.games.endpoints.cabinet.entity.Point;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.bship.games.logic.rules.Direction.DOWN;
import static com.bship.games.logic.rules.Direction.LEFT;
import static com.bship.games.logic.rules.Direction.NONE;
import static com.bship.games.logic.rules.Direction.RIGHT;
import static com.bship.games.logic.rules.Direction.UP;
import static com.bship.games.logic.rules.Harbor.AIRCRAFT_CARRIER;
import static com.bship.games.logic.rules.Harbor.BATTLESHIP;
import static com.bship.games.logic.rules.Harbor.CRUISER;
import static com.bship.games.logic.rules.Harbor.DESTROYER;
import static com.bship.games.logic.rules.Harbor.SUBMARINE;
import static com.bship.games.logic.rules.PieceType.Dummy.INVALID_PIECE;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Optional.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class UtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toIndex_shouldTakeAPointAndConvertToIndexForFirstRowFirstColumn() {
        Point point = new Point(0, 0);
        Integer index = Util.toIndex(point);

        assertThat(index, is(0));
    }

    @Test
    public void toPoint_shouldTakeAnIndexAndTurnItIntoAPointForFirstRowFirstColumn() {
        Point actual = Util.toPoint(0);
        Point expected = new Point(0, 0);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void toIndex_shouldTakeAPointAndConvertToIndexForSecondRowFirstColumn() {
        Point point = new Point(1, 0);
        Integer index = Util.toIndex(point);

        assertThat(index, is(10));
    }

    @Test
    public void toPoint_shouldTakeAnIndexAndTurnItIntoAPointForSecondRowFirstColumn() {
        Point actual = Util.toPoint(10);
        Point expected = new Point(1, 0);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void toIndex_shouldTakeAPointAndConvertToIndexForSecondRowThirdColumn() {
        Point point = new Point(1, 2);
        Integer index = Util.toIndex(point);

        assertThat(index, is(12));
    }

    @Test
    public void toPoint_shouldTakeAnIndexAndTurnItIntoAPointForSecondRowThirdColumn() {
        Point actual = Util.toPoint(12);
        Point expected = new Point(1, 2);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void toIndex_shouldTakeAPointAndConvertToIndexForTenthRowFourthColumn() {
        Point point = new Point(9, 3);
        Integer index = Util.toIndex(point);

        assertThat(index, is(93));
    }

    @Test
    public void toIndex_shouldReturnNullIfPointIsNotSetCompletely() {
        Point point = new Point();
        Integer index = Util.toIndex(point);

        assertThat(index, is(nullValue()));
    }

    @Test
    public void toIndex_shouldReturnNullIfPointXIsNotSet() {
        Point point = new Point(null, 5);
        Integer index = Util.toIndex(point);

        assertThat(index, is(nullValue()));
    }

    @Test
    public void toIndex_shouldReturnNullIfNoPoint() {
        Integer index = Util.toIndex(null);

        assertThat(index, is(nullValue()));
    }

    @Test
    public void toIndex_shouldReturnNullIfPointYIsNotSet() {
        Point point = new Point(5, null);
        Integer index = Util.toIndex(point);

        assertThat(index, is(nullValue()));
    }

    @Test
    public void toPoint_shouldTakeAnIndexAndTurnItIntoAPointForTenthRowFourthColumn() {
        Point actual = Util.toPoint(93);
        Point expected = new Point(9, 3);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void pointsRange_shouldReturnAListOfPointsWithinTheXRangeGoingRight() {
        Point placement = new Point(3, 3);
        Point end = new Point(7, 3);
        Piece piece = Piece.builder()
                .withPlacement(placement)
                .withType(AIRCRAFT_CARRIER)
                .withOrientation(RIGHT)
                .build();

        List<Point> actual = Util.pointsRange(piece);

        assertThat(actual, contains(
                placement,
                new Point(4, 3),
                new Point(5, 3),
                new Point(6, 3),
                end));
    }

    @Test
    public void pointsRange_shouldReturnAListOfPointsWithinTheXRangeGoingLeft() {
        Point placement = new Point(9, 9);
        Point end = new Point(5, 9);
        Piece piece = Piece.builder()
                .withPlacement(placement)
                .withType(AIRCRAFT_CARRIER)
                .withOrientation(LEFT)
                .build();

        List<Point> actual = Util.pointsRange(piece);

        assertThat(actual, containsInAnyOrder(
                placement,
                new Point(8, 9),
                new Point(7, 9),
                new Point(6, 9),
                end));
    }

    @Test
    public void pointsRange_shouldReturnAListOfPointsWithinTheYRangeGoingDown() {
        Point placement = new Point(4, 2);
        Point end = new Point(4, 5);
        Piece piece = Piece.builder()
                .withPlacement(placement)
                .withType(BATTLESHIP)
                .withOrientation(DOWN)
                .build();

        List<Point> actual = Util.pointsRange(piece);

        assertThat(actual, containsInAnyOrder(
                placement,
                new Point(4, 3),
                new Point(4, 4),
                end));
    }

    @Test
    public void pointsRange_shouldReturnAListOfPointsWithinTheYRangeGoingUP() {
        Point placement = new Point(7, 8);
        Point end = new Point(7, 5);
        Piece piece = Piece.builder()
                .withPlacement(placement)
                .withType(BATTLESHIP)
                .withOrientation(UP)
                .build();


        List<Point> actual = Util.pointsRange(piece);

        assertThat(actual, containsInAnyOrder(
                placement,
                new Point(7, 7),
                new Point(7, 6),
                end));
    }

    @Test
    public void pointsRange_shouldReturnAListOfPointRangeGoingNONE() {
        Point placement = new Point(7, 8);
        Piece piece = Piece.builder()
                .withPlacement(placement)
                .withType(CRUISER)
                .withOrientation(NONE)
                .build();

        List<Point> actual = Util.pointsRange(piece);

        assertThat(actual, is(equalTo(singletonList(placement))));
    }

    @Test
    public void pointsRange_shouldHandlePlacementWithNoX() {
        Piece piece = Piece.builder()
                .withPlacement(new Point(null, 3))
                .withType(BATTLESHIP)
                .withOrientation(DOWN)
                .build();

        List<Point> points = Util.pointsRange(piece);
        assertThat(points, is(equalTo(emptyList())));
    }

    @Test
    public void pointsRange_shouldHandlePlacementWithNoY() {
        Piece piece = Piece.builder()
                .withOrientation(UP)
                .withPlacement(new Point(0, null))
                .withType(DESTROYER)
                .build();

        List<Point> points = Util.pointsRange(piece);
        assertThat(points, is(equalTo(emptyList())));
    }

    @Test
    public void pointsRange_shouldHandleNoSize() {
        Piece piece = Piece.builder()
                .withPlacement(new Point(0, 3))
                .withType(INVALID_PIECE)
                .withOrientation(DOWN)
                .build();

        List<Point> points = Util.pointsRange(piece);
        assertThat(points, is(equalTo(emptyList())));
    }

    @Test
    public void pointsRange_shouldHandleNullDirection() {
        Piece piece = Piece.builder()
                .withPlacement(new Point(0, 3))
                .withType(AIRCRAFT_CARRIER)
                .build();

        List<Point> points = Util.pointsRange(piece);
        assertThat(points, is(equalTo(emptyList())));
    }

    @Test
    public void detectCollision_shouldDetectACollisionIfPointsIntersect() {
        Piece pieceA = Piece.builder()
                .withPlacement(new Point(3, 3))
                .withOrientation(RIGHT)
                .withType(AIRCRAFT_CARRIER)
                .build();

        Piece pieceB = Piece.builder()
                .withPlacement(new Point(4, 2))
                .withType(BATTLESHIP)
                .withOrientation(DOWN)
                .build();

        Boolean actual = Util.detectCollision(pieceA, pieceB);

        assertThat(actual, is(true));
    }

    @Test
    public void detectCollision_shouldDetectIfAnyPointsDoNotIntersect() {
        Piece pieceA = Piece.builder()
                .withPlacement(new Point(3, 3))
                .withOrientation(RIGHT)
                .withType(AIRCRAFT_CARRIER)
                .build();

        Piece pieceB = Piece.builder()
                .withPlacement(new Point(4, 2))
                .withType(BATTLESHIP)
                .withOrientation(DOWN)
                .build();

        Boolean actual = Util.detectCollision(Arrays.asList(pieceA, pieceB));

        assertThat(actual, is(true));
    }

    @Test
    public void detectCollision_shouldDetectIfAnyPointsDoIntersect() {
        Piece pieceA = Piece.builder()
                .withPlacement(new Point(3, 3))
                .withOrientation(LEFT)
                .withType(AIRCRAFT_CARRIER)
                .build();

        Piece pieceB = Piece.builder()
                .withPlacement(new Point(4, 2))
                .withType(BATTLESHIP)
                .withOrientation(DOWN)
                .build();

        Boolean actual = Util.detectCollision(Arrays.asList(pieceA, pieceB));

        assertThat(actual, is(false));
    }

    @Test
    public void detectCollision_shouldNotDetectACollisionIfPointsDoNotIntersect() {
        Piece pieceA = Piece.builder()
                .withPlacement(new Point(0, 3))
                .withType(AIRCRAFT_CARRIER)
                .withOrientation(DOWN)
                .build();

        Piece pieceB = Piece.builder()
                .withPlacement(new Point(4, 5))
                .withType(AIRCRAFT_CARRIER)
                .withOrientation(UP)
                .build();

        Boolean actual = Util.detectCollision(pieceA, pieceB);

        assertThat(actual, is(false));
    }

    @Test
    public void addTo_shouldReturnAListWithAnotherElementAddedToIt() {
        List<Integer> list = asList(1, 2, 3, 4, 5);
        List<Integer> expected = asList(1, 2, 3, 4, 5, 6);

        List<Integer> actual = Util.addTo(list, 6);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void addTo_shouldReturnAListOfOneWhenListIsNullAndElemIsNot() {
        List<Integer> expected = Collections.singletonList(6);

        List<Integer> actual = Util.addTo(null, 6);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void addTo_shouldReturnTheOriginalListIfElemIsNull() {
        List<Integer> expected = asList(1, 2, 3, 4, 5);

        List<Integer> actual = Util.addTo(expected, null);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void addTo_shouldReturnEmptyListIfPassedNothing() {
        List<Integer> expected = emptyList();

        List<Integer> actual = Util.addTo(null, null);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void addTo_shouldReturnImmutableList() {
        thrown.expect(UnsupportedOperationException.class);
        List<Integer> actual = Util.addTo(null, null);
        actual.add(1);
    }

    @Test
    public void addTo_functionReturn_shouldReturnImmutableList() {
        thrown.expect(UnsupportedOperationException.class);

        List<Integer> actual = Util.addTo(null, null);
        of(1).map(Util.addTo(actual)).map(l -> l.add(1));
    }

    @Test
    public void addTo_functionReturn_shouldReturnAList() {
        List<Integer> list = asList(3, 2);
        List<Integer> actual = of(1).map(Util.addTo(list)).orElse(emptyList());

        assertThat(actual.size(), is(3));
        assertThat(actual, contains(3, 2, 1));
    }

    @Test
    public void concat_shouldReturnImmutableList() {
        thrown.expect(UnsupportedOperationException.class);
        List<Integer> list1 = asList(3, 2);
        List<Integer> list2 = asList(3, 2);
        Util.concat(list1, list2).add(3);
    }

    @Test
    public void concat_shouldCombineTwiLists() {
        List<Integer> list1 = asList(1, 2);
        List<Integer> list2 = asList(3, 4);
        List<Integer> actual = Util.concat(list1, list2);

        assertThat(actual.size(), is(4));
        assertThat(actual, contains(1, 2, 3, 4));

    }

    @Test
    public void concat_functionReturn_shouldReturnImmutableList() {
        thrown.expect(UnsupportedOperationException.class);
        List<Integer> list1 = asList(3, 2);
        List<Integer> list2 = asList(3, 2);
        of(list1).map(Util.concat(list2)).get().add(3);
    }


    @Test
    public void concat_functionReturn_shouldCombineTwiLists() {
        List<Integer> list1 = asList(1, 2);
        List<Integer> list2 = asList(3, 4);
        List<Integer> actual = of(list1).map(Util.concat(list2)).orElse(emptyList());

        assertThat(actual.size(), is(4));
        assertThat(actual, contains(1, 2, 3, 4));
    }

    @Test
    public void isPlaced_shouldKnowIfAPieceIsPlaced() {
        Piece piece = Piece.builder()
                .withPlacement(new Point(1, 2))
                .build();
        boolean placed = Util.isPlaced(piece);

        assertThat(placed, is(true));
    }

    @Test
    public void isPlaced_shouldKnowIfAShipDoesNotExist() {
        boolean placed = Util.isPlaced(null);

        assertThat(placed, is(false));
    }

    @Test
    public void validRange_shouldKnowIfARangeGoesOffTheLeftSideOfBoard() {
        Piece piece = Piece.builder()
                .withPlacement(new Point(0, 0))
                .withType(DESTROYER)
                .withOrientation(LEFT)
                .build();
        boolean actual = Util.validRange(piece);
        assertThat(actual, is(false));
    }

    @Test
    public void validRange_shouldKnowIfARangeGoesOffTheRightSideOfBoard() {
        Piece piece = Piece.builder()
                .withPlacement(new Point(9, 9))
                .withType(SUBMARINE)
                .withOrientation(RIGHT)
                .build();
        boolean actual = Util.validRange(piece);
        assertThat(actual, is(false));
    }

    @Test
    public void validRange_shouldKnowIfARangeGoesOffTheTopOfTheBoard() {
        Piece piece = Piece.builder()
                .withPlacement(new Point(9, 0))
                .withType(SUBMARINE)
                .withOrientation(UP)
                .build();
        boolean actual = Util.validRange(piece);
        assertThat(actual, is(false));
    }

    @Test
    public void validRange_shouldKnowIfARangeGoesOffTheBottomOfTheBoard() {
        Piece piece = Piece.builder()
                .withPlacement(new Point(0, 9))
                .withType(SUBMARINE)
                .withOrientation(DOWN)
                .build();
        boolean actual = Util.validRange(piece);
        assertThat(actual, is(false));
    }

    @Test
    public void validRange_shouldKnowIfARangeIsGood() {
        Piece piece = Piece.builder()
                .withPlacement(new Point(0, 9))
                .withType(SUBMARINE)
                .withOrientation(UP)
                .build();
        boolean actual = Util.validRange(piece);
        assertThat(actual, is(true));
    }
}