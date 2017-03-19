package com.bship.games.util;

import com.bship.games.domains.Point;
import com.bship.games.domains.Piece;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
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
    public void pointsRange_shouldReturnAListOfPointsWithinTheXRange() {
        Point start = new Point(3, 3);
        Point end = new Point(7, 3);
        List<Point> actual = Util.pointsRange(start, end);

        assertThat(actual, containsInAnyOrder(
                start,
                new Point(4, 3),
                new Point(5, 3),
                new Point(6, 3),
                end));
    }

    @Test
    public void pointsRange_shouldReturnAListOfPointsWithinTheYRange() {
        Point start = new Point(4, 2);
        Point end = new Point(4, 5);
        List<Point> actual = Util.pointsRange(start, end);

        assertThat(actual, containsInAnyOrder(
                start,
                new Point(4, 3),
                new Point(4, 4),
                end));
    }

    @Test
    public void pointsRange_shouldHandleStartWithNoX() {
        Point start = new Point(null, 3);
        Point end = new Point(7, 3);

        List<Point> points = Util.pointsRange(start, end);
        assertThat(points, is(equalTo(asList(start, end))));
    }

    @Test
    public void pointsRange_shouldHandleStartWithNoY() {
        Point start = new Point(0, null);
        Point end = new Point(7, 3);

        List<Point> points = Util.pointsRange(start, end);
        assertThat(points, is(equalTo(asList(start, end))));
    }

    @Test
    public void pointsRange_shouldHandleEndWithNoX() {
        Point start = new Point(0, 3);
        Point end = new Point(null, 3);

        List<Point> points = Util.pointsRange(start, end);
        assertThat(points, is(equalTo(asList(start, end))));
    }

    @Test
    public void pointsRange_shouldHandleEndWithNoY() {
        Point start = new Point(0, 3);
        Point end = new Point(7, null);

        List<Point> points = Util.pointsRange(start, end);
        assertThat(points, is(equalTo(asList(start, end))));
    }

    @Test
    public void detectCollision_shouldDetectACollisionIfPointsIntersect() {
        Point startA = new Point(3, 3);
        Point endA = new Point(7, 3);
        List<Point> a = Util.pointsRange(startA, endA);

        Point startB = new Point(4, 2);
        Point endB = new Point(4, 5);
        List<Point> b = Util.pointsRange(startB, endB);

        Boolean actual = Util.detectCollision(a, b);

        assertThat(actual, is(true));
    }

    @Test
    public void detectCollision_shouldNotDetectACollisionIfPointsDoNotIntersect() {
        Point startA = new Point(0, 3);
        Point endA = new Point(0, 9);
        List<Point> a = Util.pointsRange(startA, endA);

        Point startB = new Point(4, 2);
        Point endB = new Point(4, 5);
        List<Point> b = Util.pointsRange(startB, endB);

        Boolean actual = Util.detectCollision(a, b);

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
//
//    @Test
//    public void isSet_shouldKnowIfAPointIsSet() {
//        Point point = new Point(1, 2);
//        boolean set = Util.isSet(point);
//        assertThat(set, is(true));
//    }
//
//    @Test
//    public void isSet_shouldBeFalseIfXIsNotSet() {
//        Point point = new Point(null, 2);
//        boolean set = Util.isSet(point);
//        assertThat(set, is(false));
//    }
//
//    @Test
//    public void isSet_shouldBeFalseIfYIsNotSet() {
//        Point point = new Point(5, null);
//        boolean set = Util.isSet(point);
//        assertThat(set, is(false));
//    }
//
//    @Test
//    public void isSet_shouldBeFalseIfThereIsNoPoint() {
//        boolean set = Util.isSet(null);
//        assertThat(set, is(false));
//    }

    @Test
    public void isPlaced_shouldKnowIfAShipIsPlaced() {
        Piece piece = Piece.builder()
                .withStart(new Point(1, 2))
                .withEnd(new Point(3, 4)).build();
        boolean placed = Util.isPlaced(piece);

        assertThat(placed, is(true));
    }

    @Test
    public void isPlaced_shouldKnowIfAShipStartIsNotSet() {
        Piece piece = Piece.builder()
                .withEnd(new Point(3, 4)).build();
        boolean placed = Util.isPlaced(piece);

        assertThat(placed, is(false));
    }

    @Test
    public void isPlaced_shouldKnowIfAShipEndIsNotSet() {
        Piece piece = Piece.builder()
                .withStart(new Point(3, 4)).build();
        boolean placed = Util.isPlaced(piece);

        assertThat(placed, is(false));
    }

    @Test
    public void isPlaced_shouldKnowIfAShipDoesNotExist() {
        boolean placed = Util.isPlaced(null);

        assertThat(placed, is(false));
    }
}