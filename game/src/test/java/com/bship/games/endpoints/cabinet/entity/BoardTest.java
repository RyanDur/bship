package com.bship.games.endpoints.cabinet.entity;

import com.bship.games.logic.definitions.Direction;
import com.bship.games.logic.definitions.Harbor;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class BoardTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Piece battleship;
    private Piece aircraftCarrier;

    @Before
    public void setup() {
        Point start = new Point(0, 0);
        Harbor type = Harbor.BATTLESHIP;
        battleship = Piece.builder()
                .withType(type)
                .withPlacement(start)
                .withOrientation(Direction.DOWN)
                .build();

        Point start1 = new Point(1, 0);
        Harbor type1 = Harbor.AIRCRAFT_CARRIER;
        aircraftCarrier = Piece.builder()
                .withType(type1)
                .withPlacement(start1)
                .withOrientation(Direction.DOWN)
                .build();
    }

    @Test
    public void addShip_shouldBeAbleToAddAShipToTheBoard() {
        Board board = Board.builder().addPiece(battleship).build();

        assertThat(board.getPieces(), contains(battleship));
    }

    @Test
    public void addShip_shouldBeAbleToAddMultipleShipsToTheBoard() {
        Board board = Board.builder().addPiece(battleship).build();
        Board newBoard = board.copy().addPiece(aircraftCarrier).build();

        assertThat(newBoard.getPieces(), contains(battleship, aircraftCarrier));
    }

    @Test
    public void getShips__with_addShip_shouldBeImmutable() {
        thrown.expect(UnsupportedOperationException.class);

        Board board = Board.builder().addPiece(battleship).build();

        board.getPieces().add(aircraftCarrier);
    }

    @Test
    public void getShips__with_withShips_shouldBeImmutable() {
        thrown.expect(UnsupportedOperationException.class);

        Board board = Board.builder().withPieces(singletonList(battleship)).build();

        board.getPieces().add(aircraftCarrier);
    }
}