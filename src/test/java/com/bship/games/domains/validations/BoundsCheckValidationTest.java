package com.bship.games.domains.validations;

import com.bship.games.domains.Point;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BoundsCheckValidationTest {


    private BoundsCheckValidation validation;

    @Before
    public void setup() {
        validation = new BoundsCheckValidation();
    }

    @Test
    public void isValid_shouldNotAllowAPointXLessThanZero() {
        Point point = new Point(-1, 0);

        assertThat(validation.isValid(point, null), is(false));
    }

    @Test
    public void isValid_shouldNotAllowAPointYLessThanZero() {
        Point point = new Point(1, 10);

        assertThat(validation.isValid(point, null), is(false));
    }

    @Test
    public void isValid_shouldAllowAValidPoint() {
        Point point = new Point(1, 0);

        assertThat(validation.isValid(point, null), is(true));
    }

    @Test
    public void isValid_shouldNotAllowAnXGreaterThanTheUpperBound() {
        Point point = new Point(10, 0);

        assertThat(validation.isValid(point, null), is(false));
    }

    @Test
    public void isValid_shouldNotAllowAnYGreaterThanTheUpperBound() {
        Point point = new Point(0, 10);

        assertThat(validation.isValid(point, null), is(false));
    }
}