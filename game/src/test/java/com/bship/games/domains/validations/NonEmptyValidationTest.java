package com.bship.games.domains.validations;

import com.bship.games.domains.Point;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class NonEmptyValidationTest {

    private NonEmptyValidation validation;

    @Before
    public void setUp() throws Exception {
        validation = new NonEmptyValidation();
    }

    @Test
    public void isValid_shouldAllowObjects() {
        assertThat(validation.isValid("hello", null), is(true));
        assertThat(validation.isValid(new Point(1,2), null), is(true));
    }

    @Test
    public void isValid_shouldNotAllowNull() {
        assertThat(validation.isValid(null, null), is(false));
    }

    @Test
    public void isValid_shouldNotAllowEmptyString() {
        assertThat(validation.isValid("", null), is(false));
    }

    @Test
    public void isValid_shouldNotAllowEmptyStringOfSpaces() {
        assertThat(validation.isValid("       ", null), is(false));
    }
}