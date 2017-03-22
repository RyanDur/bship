package com.bship.games.domains.validations;

import com.bship.games.domains.Piece;
import com.bship.games.domains.Point;
import org.junit.Before;
import org.junit.Test;

import static com.bship.games.domains.Direction.DOWN;
import static com.bship.games.domains.Direction.NONE;
import static com.bship.games.domains.Direction.UP;
import static com.bship.games.domains.Harbor.AIRCRAFT_CARRIER;
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
                .withSize(5)
                .build();

        assertThat(validation.isValid(piece, null), is(true));
    }

    @Test
    public void isValid_shouldNotAllowAnInvalidAircraftCarrierSize() throws Exception {
        Piece piece = Piece.builder()
                .withType(AIRCRAFT_CARRIER)
                .withPlacement(new Point(0, 0))
                .withOrientation(DOWN)
                .withSize(4)
                .build();

        assertThat(validation.isValid(piece, null), is(false));
    }

    @Test
    public void isValid_shouldNotAllowAShipWithoutAType() throws Exception {
        Piece piece = Piece.builder()
                .withPlacement(new Point(0, 0))
                .withOrientation(DOWN)
                .withSize(4)
                .build();

        assertThat(validation.isValid(piece, null), is(false));
    }

    @Test
    public void isValid_shouldNotAllowAnInvalidRange() throws Exception {
        Piece piece = Piece.builder()
                .withType(AIRCRAFT_CARRIER)
                .withPlacement(new Point(0, 0))
                .withOrientation(UP)
                .withSize(5)
                .build();

        assertThat(validation.isValid(piece, null), is(false));
    }

    @Test
    public void isValid_shouldNotAllowAPieceWithoutASize() throws Exception {
        Piece piece = Piece.builder()
                .withType(AIRCRAFT_CARRIER)
                .withPlacement(new Point(0, 0))
                .withOrientation(DOWN)
                .build();

        assertThat(validation.isValid(piece, null), is(false));
    }

    @Test
    public void isValid_shouldNotAllowAPieceWithoutAnOrientation() throws Exception {
        Piece piece = Piece.builder()
                .withType(AIRCRAFT_CARRIER)
                .withPlacement(new Point(0, 0))
                .withSize(5)
                .build();

        assertThat(validation.isValid(piece, null), is(false));
    }

    @Test
    public void isValid_shouldNotAllowAPieceWithANoneOrientation() throws Exception {
        Piece piece = Piece.builder()
                .withType(AIRCRAFT_CARRIER)
                .withPlacement(new Point(0, 0))
                .withOrientation(NONE)
                .withSize(5)
                .build();

        assertThat(validation.isValid(piece, null), is(false));
    }
}