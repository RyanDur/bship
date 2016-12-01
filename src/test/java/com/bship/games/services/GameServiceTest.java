package com.bship.games.services;

import com.bship.games.Harbor;
import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import com.bship.games.domains.Point;
import com.bship.games.domains.Ship;
import com.bship.games.repositories.BoardRepository;
import com.bship.games.repositories.GameRepository;
import com.bship.games.repositories.ShipRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GameServiceTest {

    private GameService gameService;
    private Board firstStubbedBoard;
    private Board secondStubbedBoard;
    private BoardRepository mockBoardRepository;
    private ShipRepository shipRepository;

    @Before
    public void setup() {
        mockBoardRepository = mock(BoardRepository.class);
        GameRepository mockGameRepository = mock(GameRepository.class);
        shipRepository = mock(ShipRepository.class);
        Game game = Game.builder().build();
        firstStubbedBoard = Board.builder().build();
        secondStubbedBoard = Board.builder().build();

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
    public void placeShip_shouldPlaceAShipOnTheBoard() {
        Point start = new Point(0, 0);
        Point end = new Point(0, 4);
        Ship ship = Ship.builder().withShipType(Harbor.AIRCRAFT_CARRIER).withStart(start).withEnd(end).build();
        long boardId = 1L;
        Board board = Board.builder().build();


        when(mockBoardRepository.get(boardId)).thenReturn(board);
        when(shipRepository.create(ship, boardId)).thenReturn(ship);

        Board actual = gameService.placeShip(boardId, ship);

        verify(mockBoardRepository).get(boardId);
        verify(shipRepository).create(ship, boardId);
        assertThat(actual.getShips(), contains(ship));
        assertThat(actual, is(equalTo(board)));
    }
}