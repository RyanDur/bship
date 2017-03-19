package com.bship.games.domains.validations;

import com.bship.games.domains.Harbor;
import com.bship.games.domains.Point;
import com.bship.games.domains.Piece;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PlacementCheckValidationTest {

    private PlacementCheckValidation validation;

    @Before
    public void setUp() throws Exception {
        validation = new PlacementCheckValidation();
    }

    @Test
    public void isValid_shouldAllowAValidAircraftCarrier() throws Exception {
        Point start = new Point(0, 0);
        Point end = new Point(0, 4);
        Harbor type = Harbor.AIRCRAFT_CARRIER;

        Piece piece = Piece.builder().withType(type).withStart(start).withEnd(end).build();

        assertThat(validation.isValid(piece, null), is(true));
    }

    @Test
    public void isValid_shouldNotAllowAnInvalidAircraftCarrier() throws Exception {
        Point start = new Point(0, 0);
        Point end = new Point(0, 3);
        Harbor type = Harbor.AIRCRAFT_CARRIER;

        Piece piece = Piece.builder().withType(type).withStart(start).withEnd(end).build();

        assertThat(validation.isValid(piece, null), is(false));
    }

    @Test
    public void isValid_shouldAllowAValidBattleship() throws Exception {
        Point start = new Point(6, 9);
        Point end = new Point(9, 9);
        Harbor type = Harbor.BATTLESHIP;

        Piece piece = Piece.builder().withType(type).withStart(start).withEnd(end).build();

        assertThat(validation.isValid(piece, null), is(true));
    }

    @Test
    public void isValid_shouldNotAllowAnInvalidBattleship() throws Exception {
        Point start = new Point(5, 9);
        Point end = new Point(9, 9);
        Harbor type = Harbor.BATTLESHIP;

        Piece piece = Piece.builder().withType(type).withStart(start).withEnd(end).build();

        assertThat(validation.isValid(piece, null), is(false));
    }

    @Test
    public void isValid_shouldAllowAValidSubmarine() throws Exception {
        Point start = new Point(9, 7);
        Point end = new Point(9, 9);
        Harbor type = Harbor.SUBMARINE;

        Piece piece = Piece.builder().withType(type).withStart(start).withEnd(end).build();

        assertThat(validation.isValid(piece, null), is(true));
    }

    @Test
    public void isValid_shouldNotAllowAnInvalidSubmarine() throws Exception {
        Point start = new Point(7, 7);
        Point end = new Point(9, 9);
        Harbor type = Harbor.SUBMARINE;

        Piece piece = Piece.builder().withType(type).withStart(start).withEnd(end).build();

        assertThat(validation.isValid(piece, null), is(false));
    }

    @Test
    public void isValid_shouldAllowAValidCruiser() throws Exception {
        Point start = new Point(2, 9);
        Point end = new Point(0, 9);
        Harbor type = Harbor.CRUISER;

        Piece piece = Piece.builder().withType(type).withStart(start).withEnd(end).build();

        assertThat(validation.isValid(piece, null), is(true));
    }

    @Test
    public void isValid_shouldNotAllowAnInvalidCruiser() throws Exception {
        Point start = new Point(2, 7);
        Point end = new Point(0, 9);
        Harbor type = Harbor.CRUISER;

        Piece piece = Piece.builder().withType(type).withStart(start).withEnd(end).build();

        assertThat(validation.isValid(piece, null), is(false));
    }

    @Test
    public void isValid_shouldAllowAValidDestroyer() throws Exception {
        Point start = new Point(9, 1);
        Point end = new Point(9, 0);
        Harbor type = Harbor.DESTROYER;

        Piece piece = Piece.builder().withType(type).withStart(start).withEnd(end).build();

        assertThat(validation.isValid(piece, null), is(true));
    }

    @Test
    public void isValid_shouldNotAllowAnInvalidDestroyer() throws Exception {
        Point start = new Point(8, 1);
        Point end = new Point(9, 0);
        Harbor type = Harbor.DESTROYER;

        Piece piece = Piece.builder().withType(type).withStart(start).withEnd(end).build();

        assertThat(validation.isValid(piece, null), is(false));
    }

    @Test
    public void isValid_shouldIgnoreAnInvalidShip() {
        Point start = new Point(8, 1);
        Point end = new Point(9, 0);
        Harbor type = Harbor.INVALID_SHIP;

        Piece piece = Piece.builder().withType(type).withStart(start).withEnd(end).build();
        assertThat(validation.isValid(piece, null), is(true));
    }
}