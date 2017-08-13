package com.bship.games.endpoints.errors.validations;

import com.bship.games.endpoints.cabinet.entity.Point;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ValidPointValidationTest {

    @Test
    public void isValid_shouldAllowAValidPoint() {
        Point point = new Point(1, 2);
        ValidPointValidation validation = new ValidPointValidation();
        boolean valid = validation.isValid(point, null);

        assertThat(valid, is(true));
    }

    @Test
    public void isValid_shouldNotAllowANullPoint() {
        Point point = null;
        ValidPointValidation validation = new ValidPointValidation();
        boolean valid = validation.isValid(point, null);

        assertThat(valid, is(false));
    }

    @Test
    public void isValid_shouldNotAllowANullX() {
        Point point = new Point(null, 3);
        ValidPointValidation validation = new ValidPointValidation();
        boolean valid = validation.isValid(point, null);

        assertThat(valid, is(false));
    }

    @Test
    public void isValid_shouldNotAllowANullY() {
        Point point = new Point(4, null);
        ValidPointValidation validation = new ValidPointValidation();
        boolean valid = validation.isValid(point, null);

        assertThat(valid, is(false));
    }
}