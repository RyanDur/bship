package com.bship.games.services;

import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import com.bship.games.domains.Harbor;
import com.bship.games.domains.Point;
import com.bship.games.domains.Ship;
import com.bship.games.exceptions.ShipCollisionCheck;
import com.bship.games.exceptions.ShipExistsCheck;
import com.bship.games.repositories.BoardRepository;
import com.bship.games.repositories.GameRepository;
import com.bship.games.repositories.ShipRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
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
        when(mockBoardRepository.create(game)).thenReturn(asList(firstStubbedBoard, secondStubbedBoard));

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
    public void placeShip_shouldPlaceAShipOnTheBoard() throws ShipExistsCheck, ShipCollisionCheck {
        long boardId = 1L;
        Board board = Board.builder().withShips(emptyList()).build();
        when(mockBoardRepository.get(boardId)).thenReturn(board);
        when(shipRepository.create(ship, boardId)).thenReturn(ship);

        Board actual = gameService.placeShip(boardId, ship);

        verify(mockBoardRepository).get(boardId);
        verify(shipRepository).create(ship, boardId);
        assertThat(actual.getShips(), contains(ship));
    }

    @Test
    public void placeShip_shouldGuardAgainstPlacingDuplicateShip() throws ShipExistsCheck, ShipCollisionCheck {
        thrown.expect(ShipExistsCheck.class);
        thrown.expectMessage("Ship already exists on board.");

        long boardId = 1L;
        List<Ship> ships = singletonList(ship.copy().build());
        Board board = Board.builder().withShips(ships).build();
        when(mockBoardRepository.get(boardId)).thenReturn(board);

        gameService.placeShip(boardId, ship);
    }

    @Test
    public void placeShip_shouldGuardAgainstShipCollisions() throws ShipExistsCheck, ShipCollisionCheck {
        thrown.expect(ShipCollisionCheck.class);
        thrown.expectMessage("Ship collision.");

        long boardId = 1L;
        Point startA = new Point(0, 0);
        Point endA = new Point(0, 2);
        Ship cruiser = ship.copy().withStart(startA).withEnd(endA).withType(Harbor.CRUISER).build();
        List<Ship> ships = singletonList(cruiser);
        Board board = Board.builder().withShips(ships).build();
        when(mockBoardRepository.get(boardId)).thenReturn(board);

        Point startB = new Point(0, 1);
        Point endB = new Point(0, 3);
        Ship sub = ship.copy().withStart(startB).withEnd(endB).withType(Harbor.SUBMARINE).build();

        gameService.placeShip(boardId, sub);
    }

    @Test
    public void placeShip_shouldSetTheBoardToReadyUntilItIs() throws ShipExistsCheck, ShipCollisionCheck {
        long boardId = 1L;
        Board board = Board.builder().withShips(emptyList()).build();
        when(mockBoardRepository.get(boardId)).thenReturn(board);
        when(shipRepository.create(ship, boardId)).thenReturn(ship);

        Board actual = gameService.placeShip(boardId, ship);

        verify(mockBoardRepository).get(boardId);
        verify(shipRepository).create(ship, boardId);
        assertThat(actual.isReady(), is(false));
    }

    @Test
    public void placeShip_shouldSetTheBoardToReady() throws ShipExistsCheck, ShipCollisionCheck {
        long boardId = 1L;
        Point endA = new Point(0, 2);
        Board board = Board.builder().withShips(emptyList()).build();
        Ship cruiser = ship.copy().withStart(new Point(0, 0)).withEnd(endA).withType(Harbor.CRUISER).build();
        Ship carrier = ship.copy().withStart(new Point(1, 1)).withEnd(endA).withType(Harbor.AIRCRAFT_CARRIER).build();
        Ship battleship = ship.copy().withStart(new Point(2, 2)).withEnd(endA).withType(Harbor.BATTLESHIP).build();
        Ship destroyer = ship.copy().withStart(new Point(3, 3)).withEnd(endA).withType(Harbor.DESTROYER).build();
        List<Ship> ships = Stream.of(cruiser, carrier, battleship, destroyer).collect(toList());

        when(mockBoardRepository.get(boardId)).thenReturn(board.copy().withShips(ships).build());

        Point startB = new Point(4, 1);
        Point endB = new Point(4, 3);
        Ship sub = ship.copy().withStart(startB).withEnd(endB).withType(Harbor.SUBMARINE).build();

        Board actual = gameService.placeShip(boardId, sub);
        assertThat(actual.isReady(), is(true));
    }
}