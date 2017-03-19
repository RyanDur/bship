package com.bship.games.services;

import com.bship.games.domains.Board;
import com.bship.games.domains.Harbor;
import com.bship.games.domains.Point;
import com.bship.games.domains.Ship;
import com.bship.games.exceptions.BoardExistence;
import com.bship.games.exceptions.BoardValidation;
import com.bship.games.exceptions.ShipCollisionCheck;
import com.bship.games.logic.GameLogic;
import com.bship.games.repositories.BoardRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.bship.games.domains.Harbor.AIRCRAFT_CARRIER;
import static java.util.Collections.emptyList;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
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
        BigInteger boardId = BigInteger.valueOf(1L);
        Board board = Board.builder().withId(boardId).withShips(getShips()).build();
        Ship ship = Ship.builder()
                .withStart(new Point(3,2))
                .withEnd(new Point(0,2))
                .withBoardId(boardId)
                .withType(AIRCRAFT_CARRIER).build();

        List<Ship> unplaced = board.getShips().stream()
                .filter(o -> !o.getType().equals(ship.getType()))
                .collect(toList());

        Board expected = board.copy().withShips(unplaced).addShip(ship).build();

        when(repository.get(boardId)).thenReturn(of(board));
        when(logic.placementCheck(ship)).thenReturn(b -> b);
        when(repository.save(any(Board.class))).thenReturn(of(expected));

        Board actual = service.placeShip(boardId, ship);

        verify(repository).save(expected);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void placeShip_shouldThrowABoardExistenceIfThereIsNoBoard() throws BoardValidation {
        thrown.expect(BoardExistence.class);
        thrown.expectMessage("Board Does Not Exist!");

        BigInteger boardId = BigInteger.valueOf(1L);
        Board board = Board.builder().build();
        Ship ship = Ship.builder().build();
        Board expected = board.copy().addShip(ship).build();

        when(repository.get(boardId)).thenReturn(Optional.empty());
        when(logic.placementCheck(ship)).thenReturn(b -> b);
        when(repository.save(expected)).thenReturn(of(expected));

        service.placeShip(boardId, ship);
    }

    @Test
    public void placeShip_shouldThrowABoardExistenceIfThereIsNoBoardAfterSaving() throws BoardValidation {
        thrown.expect(BoardExistence.class);
        thrown.expectMessage("Board Does Not Exist!");

        BigInteger boardId = BigInteger.valueOf(1L);
        Board board = Board.builder().withShips(emptyList()).build();
        Ship ship = Ship.builder().build();

        when(repository.get(boardId)).thenReturn(of(board));
        when(logic.placementCheck(ship)).thenReturn(b -> b);
        when(repository.save(any(Board.class))).thenReturn(Optional.empty());

        service.placeShip(boardId, ship);
    }

    @Test
    public void placeShip_shouldThrowABoardValidationIfThereIsNoBoardAfterCheck() throws BoardValidation {
        thrown.expect(BoardValidation.class);

        BigInteger boardId = BigInteger.valueOf(1L);
        Board board = Board.builder().build();
        Ship ship = Ship.builder().build();
        Board expected = board.copy().addShip(ship).build();

        when(repository.get(boardId)).thenReturn(of(board));
        doThrow(new ShipCollisionCheck()).when(logic).placementCheck(ship);
        when(repository.save(expected)).thenReturn(Optional.empty());

        service.placeShip(boardId, ship);
    }

    public List<Ship> getShips() {
        return Harbor.getShips().stream().map(ship -> Ship.builder()
                .withType(ship)
                .withStart(new Point())
                .withEnd(new Point())
                .withBoardId(BigInteger.ONE)
                .build()).collect(toList());
    }
}