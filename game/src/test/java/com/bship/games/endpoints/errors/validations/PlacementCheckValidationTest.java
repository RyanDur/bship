package com.bship.games.endpoints.errors.validations;

import com.bship.games.endpoints.cabinet.entity.Piece;
import com.bship.games.endpoints.cabinet.entity.Point;
import org.junit.Before;
import org.junit.Test;

import static com.bship.games.logic.definitions.Direction.DOWN;
import static com.bship.games.logic.definitions.Direction.NONE;
import static com.bship.games.logic.definitions.Direction.UP;
import static com.bship.games.logic.definitions.Harbor.AIRCRAFT_CARRIER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PlacementCheckValidationTest {

    private PlacementCheckValidation validation;

    @Before
    public void setUp() throws Exception {
        validation = new PlacementCheckValidation();
    }

    @Test
    public void isValid_shouldAllowAValidShip() throws Exception {
        Piece piece = Piece.builder()
                .withType(AIRCRAFT_CARRIER)
                .withPlacement(new Point(0, 0))
                .withOrientation(DOWN)
                .build();

        assertThat(validation.isValid(piece, null), is(true));
    }

    @Test
    public void isValid_shouldNotAllowAShipWithoutAType() throws Exception {
        Piece piece = Piece.builder()
                .withPlacement(new Point(0, 0))
                .withOrientation(DOWN)
                .build();

        assertThat(validation.isValid(piece, null), is(false));
    }

    @Test
    public void isValid_shouldNotAllowAnInvalidRange() throws Exception {
        Piece piece = Piece.builder()
                .withType(AIRCRAFT_CARRIER)
                .withPlacement(new Point(0, 0))
                .withOrientation(UP)
                .build();

        assertThat(validation.isValid(piece, null), is(false));
    }

    @Test
    public void isValid_shouldNotAllowAPieceWithoutAnOrientation() throws Exception {
        Piece piece = Piece.builder()
                .withType(AIRCRAFT_CARRIER)
                .withPlacement(new Point(0, 0))
                .build();

        assertThat(validation.isValid(piece, null), is(false));
    }

    @Test
    public void isValid_shouldNotAllowAPieceWithANoneOrientation() throws Exception {
        Piece piece = Piece.builder()
                .withType(AIRCRAFT_CARRIER)
                .withPlacement(new Point(0, 0))
                .withOrientation(NONE)
                .build();

        assertThat(validation.isValid(piece, null), is(false));
    }
}