package com.bship.games.services;

import com.bship.games.domains.Board;
import com.bship.games.domains.Ship;
import com.bship.games.exceptions.ShipCollisionCheck;
import com.bship.games.exceptions.ShipExistsCheck;
import com.bship.games.repositories.BoardRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyLong;
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
    public void placeShip_shouldPlaceAShipOnTheBoard() throws ShipExistsCheck, ShipCollisionCheck {
        Board board = Board.builder().build();
        Ship ship = Ship.builder().build();
        Board board1 = board.copy().addShip(ship).build();

        when(repository.save(board1)).thenReturn(board1);
        when(repository.get(anyLong())).thenReturn(board);

        Board actual = service.placeShip(1L, ship);

        verify(repository).save(board1);
        assertThat(actual, is(equalTo(board1)));
    }

    @Test
    public void placeShip_shouldGuardAgainstPlacingDuplicateShip() throws ShipExistsCheck, ShipCollisionCheck {
        thrown.expect(ShipExistsCheck.class);
        thrown.expectMessage("Ship already exists on board.");

        Board board = Board.builder().build();
        Ship ship = Ship.builder().build();

        when(repository.get(anyLong())).thenReturn(board);
        when(logic.exists(board, ship)).thenReturn(true);

        service.placeShip(1L, ship);
    }

    @Test
    public void placeShip_shouldGuardAgainstShipCollisions() throws ShipExistsCheck, ShipCollisionCheck {
        thrown.expect(ShipCollisionCheck.class);
        thrown.expectMessage("Ship collision.");

        Board board = Board.builder().build();
        Ship ship = Ship.builder().build();

        when(repository.get(anyLong())).thenReturn(board);
        when(logic.exists(board, ship)).thenReturn(false);
        when(logic.collision(board, ship)).thenReturn(true);

        service.placeShip(1L, ship);
    }

}