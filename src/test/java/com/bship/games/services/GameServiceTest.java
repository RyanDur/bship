package com.bship.games.services;

import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import com.bship.games.domains.Harbor;
import com.bship.games.domains.Point;
import com.bship.games.domains.Ship;
import com.bship.games.exceptions.ShipExistsCheck;
import com.bship.games.repositories.BoardRepository;
import com.bship.games.repositories.GameRepository;
import com.bship.games.repositories.ShipRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GameServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private GameService gameService;
    private Board firstStubbedBoard;
    private Board secondStubbedBoard;
    private BoardRepository mockBoardRepository;
    private ShipRepository shipRepository;
    private Ship ship;

    @Before
    public void setup() {
        mockBoardRepository = mock(BoardRepository.class);
        GameRepository mockGameRepository = mock(GameRepository.class);
        shipRepository = mock(ShipRepository.class);
        Game game = Game.builder().build();
        firstStubbedBoard = Board.builder().build();
        secondStubbedBoard = Board.builder().build();

        Point start = new Point(0, 0);
        Point end = new Point(0, 4);
        ship = Ship.builder()
                .withType(Harbor.AIRCRAFT_CARRIER)
                .withStart(start)
                .withEnd(end)
                .build();

        when(mockGameRepository.createGame()).thenReturn(game);
        when(mockBoardRepository.create(game)).thenReturn(Arrays.asList(firstStubbedBoard, secondStubbedBoard));

        gameService = new GameService(mockBoardRepository, mockGameRepository, shipRepository);
    }

    @Test
    public void getNewGame_shouldReturnANewGame() {
        Game actualGame = gameService.getNewGame();

        assertThat(actualGame, is(instanceOf(Game.class)));
    }

    @Test
    public void getNewGame_shouldCreateTwoNewBoardsAndAssociateThemWithTheGame() {
        Game actualGame = gameService.getNewGame();

        assertThat(actualGame.getBoards(), contains(firstStubbedBoard, secondStubbedBoard));
    }

    @Test
    public void placeShip_shouldPlaceAShipOnTheBoard() throws ShipExistsCheck {
        long boardId = 1L;
        Board board = Board.builder().build();

        when(mockBoardRepository.get(boardId)).thenReturn(board);
        when(shipRepository.create(ship, boardId)).thenReturn(ship);

        Board actual = gameService.placeShip(boardId, ship);

        verify(mockBoardRepository).get(boardId);
        verify(shipRepository).create(ship, boardId);
        assertThat(actual.getShips(), contains(ship));
    }

    @Test
    public void placeShip_shouldGuardAgainstPlacingDuplicateShip() throws ShipExistsCheck {
        thrown.expect(ShipExistsCheck.class);
        thrown.expectMessage("Ship already exists on board.");

        long boardId = 1L;
        List<Ship> ships = Arrays.asList(ship.copy().build());
        when(shipRepository.getAll(boardId)).thenReturn(ships);

        gameService.placeShip(boardId, ship);
    }
}