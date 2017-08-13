package com.bship.games.endpoints.errors.validations;

import com.bship.games.logic.rules.Harbor;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PieceExistsValidationTest {

    @Test
    public void isValid_shouldAllowAValidShip() {
        String battleship = Harbor.BATTLESHIP.name();
        ShipExistsValidation validation = new ShipExistsValidation();

        assertThat(validation.isValid(Harbor.create(battleship), null), is(true));
    }

    @Test
    public void isValid_shouldNotAllowAnInvalidShip() {
        String schooner = "SCHOONER";
        ShipExistsValidation validation = new ShipExistsValidation();

        assertThat(validation.isValid(Harbor.create(schooner), null), is(false));
    }

    @Test
    public void isValid_shouldNotAllowAnInvalidEmptyShip() {
        String empty = "";
        ShipExistsValidation validation = new ShipExistsValidation();

        assertThat(validation.isValid(Harbor.create(empty), null), is(false));
    }
}