package com.bship.games.util;

import com.bship.games.domains.Point;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class UtilTest {

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
}