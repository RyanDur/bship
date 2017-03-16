package com.bship.games.domains;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PointTest {

    @Test
    public void isSet_shouldKnowIfThePointIsSet() {
        Point point = new Point(1, 1);

        assertThat(point.isSet(), is(true));
    }

    @Test
    public void isSet_shouldNotBeSetIfXAndYIsNot() {
        Point point = new Point();

        assertThat(point.isSet(), is(false));
    }

    @Test
    public void isSet_shouldNotBeSetIfXIsNot() {
        Point point = new Point(null, 1);

        assertThat(point.isSet(), is(false));
    }

    @Test
    public void isSet_shouldNotBeSetIfYIsNot() {
        Point point = new Point(1, null);

        assertThat(point.isSet(), is(false));
    }

}