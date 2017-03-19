package com.bship.games.services;

import com.bship.games.domains.Board;
import com.bship.games.domains.Ship;
import com.bship.games.exceptions.BoardValidation;
import com.bship.games.exceptions.ShipCollisionCheck;
import com.bship.games.exceptions.ShipExistsCheck;
import com.bship.games.logic.GameLogic;
import com.bship.games.repositories.BoardRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigInteger;

import static java.util.Optional.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BoardServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private BoardService service;
    private GameLogic logic;
    private BoardRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = mock(BoardRepository.class);
        logic = mock(GameLogic.class);
        service = new BoardService(repository, logic);
    }

    @Test
    public void placeShip_shouldPlaceAShipOnTheBoard() throws BoardValidation {
        Board board = Board.builder().build();
        Ship ship = Ship.builder().build();
        Board board1 = board.copy().addShip(ship).build();

        when(repository.save(ship, BigInteger.valueOf(1L))).thenReturn(of(board1));
        when(repository.get(any(BigInteger.class))).thenReturn(of(board));

        Board actual = service.placeShip(BigInteger.valueOf(1L), ship).get();

        verify(repository).save(ship, BigInteger.valueOf(1L));
        assertThat(actual, is(equalTo(board1)));
    }

    @Test
    public void placeShip_shouldGuardAgainstPlacingDuplicateShip() throws BoardValidation {
        thrown.expect(ShipExistsCheck.class);
        thrown.expectMessage("Ship already exists on board.");

        Board board = Board.builder().build();
        Ship ship = Ship.builder().build();

        when(repository.get(any(BigInteger.class))).thenReturn(of(board));
        when(logic.exists(board, ship)).thenReturn(true);

        service.placeShip(BigInteger.valueOf(1L), ship);
    }

    @Test
    public void placeShip_shouldGuardAgainstShipCollisions() throws BoardValidation {
        thrown.expect(ShipCollisionCheck.class);
        thrown.expectMessage("Ship collision.");

        Board board = Board.builder().build();
        Ship ship = Ship.builder().build();

        when(repository.get(any(BigInteger.class))).thenReturn(of(board));
        when(logic.exists(board, ship)).thenReturn(false);
        when(logic.collision(board, ship)).thenReturn(true);

        service.placeShip(BigInteger.valueOf(1L), ship);
    }

}