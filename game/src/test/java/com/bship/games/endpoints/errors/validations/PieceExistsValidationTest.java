package com.bship.games.endpoints.errors.validations;

import com.bship.games.logic.PieceType;
import org.junit.Test;

import static com.bship.games.logic.definitions.Harbor.BATTLESHIP;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PieceExistsValidationTest {

    @Test
    public void isValid_shouldAllowAValidShip() {
        String battleship = BATTLESHIP.name();
        PieceExistsValidation validation = new PieceExistsValidation();

        assertThat(validation.isValid(PieceType.createPiece(battleship), null), is(true));
    }

    @Test
    public void isValid_shouldNotAllowAnInvalidShip() {
        String schooner = "SCHOONER";
        PieceExistsValidation validation = new PieceExistsValidation();

        assertThat(validation.isValid(PieceType.createPiece(schooner), null), is(false));
    }

    @Test
    public void isValid_shouldNotAllowAnInvalidEmptyShip() {
        String empty = "";
        PieceExistsValidation validation = new PieceExistsValidation();

        assertThat(validation.isValid(PieceType.createPiece(empty), null), is(false));
    }
}