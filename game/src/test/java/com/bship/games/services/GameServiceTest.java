package com.bship.games.services;

import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import com.bship.games.domains.Harbor;
import com.bship.games.domains.Move;
import com.bship.games.domains.Point;
import com.bship.games.domains.Ship;
import com.bship.games.exceptions.MoveCollision;
import com.bship.games.exceptions.ShipCollisionCheck;
import com.bship.games.exceptions.ShipExistsCheck;
import com.bship.games.repositories.BoardRepository;
import com.bship.games.repositories.GameRepository;
import com.bship.games.repositories.MoveRepository;
import com.bship.games.repositories.ShipRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static com.bship.games.domains.MoveStatus.HIT;
import static com.bship.games.domains.MoveStatus.MISS;
import static com.bship.games.util.Util.pointsRange;
import static com.bship.games.util.Util.toPoint;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GameServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private GameService gameService;
    private Board firstStubbedBoard;
    private Board secondStubbedBoard;
    private BoardRepository boardRepository;
    private ShipRepository shipRepository;
    private MoveRepository moveRepository;
    private Ship ship;
    private Ship floating;
    private List<Ship> ships;

    @Before
    public void setup() {
        Ship sunk = Ship.builder()
                .withStart(new Point(0, 0))
                .withEnd(new Point(0, 4))
                .withSunk(true)
                .build();

        floating = Ship.builder()
                .withStart(new Point(3, 0))
                .withEnd(new Point(3, 2))
                .build();

        ships = Arrays.asList(
                sunk, sunk.copy()
                        .withStart(new Point(1, 0))
                        .withEnd(new Point(1, 3))
                        .build(), sunk.copy()
                        .withStart(new Point(2, 0))
                        .withEnd(new Point(2, 2))
                        .build(),
                floating, floating.copy()
                        .withStart(new Point(4, 0))
                        .withEnd(new Point(4, 1))
                        .build()
        );
        moveRepository = mock(MoveRepository.class);
        boardRepository = mock(BoardRepository.class);
        GameRepository gameRepository = mock(GameRepository.class);
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

        when(gameRepository.createGame()).thenReturn(game);
        when(boardRepository.create(game)).thenReturn(asList(firstStubbedBoard, secondStubbedBoard));

        gameService = new GameService(boardRepository, gameRepository, shipRepository, moveRepository);
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
        when(boardRepository.get(boardId)).thenReturn(board);
        when(shipRepository.create(ship, boardId)).thenReturn(of(ship));

        Board actual = gameService.placeShip(boardId, ship);

        verify(boardRepository).get(boardId);
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
        when(boardRepository.get(boardId)).thenReturn(board);

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
        when(boardRepository.get(boardId)).thenReturn(board);

        Point startB = new Point(0, 1);
        Point endB = new Point(0, 3);
        Ship sub = ship.copy().withStart(startB).withEnd(endB).withType(Harbor.SUBMARINE).build();

        gameService.placeShip(boardId, sub);
    }

    @Test
    public void placeMove_shouldPlaceAMoveOnTheBoard() throws MoveCollision {
        long boardId = 1L;
        Point point = new Point(5, 0);
        Move move = Move.builder().withId(boardId).withPoint(point).withStatus(MISS).build();

        when(shipRepository.getAll(anyLong())).thenReturn(of(ships));
        when(moveRepository.getAll(anyLong())).thenReturn(empty());
        when(moveRepository.create(boardId, point, MISS)).thenReturn(of(move));

        Board actual = gameService.placeMove(boardId, 1L, point);
        assertThat(actual.getMoves().get(0), is(equalTo(move)));
    }

    @Test
    public void placeMove_shouldNotBeAbleToPlaceAMoveAtopAnother() throws MoveCollision {
        thrown.expect(MoveCollision.class);
        thrown.expectMessage("Move already exists on board.");

        Point point = new Point(0, 4);
        Move move = Move.builder().withPoint(point).withStatus(MISS).build();
        when(moveRepository.getAll(anyLong())).thenReturn(of(singletonList(move)));

        gameService.placeMove(1L, 1L, point);
    }

    @Test
    public void placeMove_shouldKnowWhenAMoveHasHit() throws MoveCollision {
        Point point = new Point(0, 4);
        Move moveMiss = Move.builder().withPoint(point).withStatus(MISS).withBoardId(1L).build();
        Move moveHit = Move.builder().withPoint(point).withStatus(HIT).withBoardId(1L).build();
        Ship ship = Ship.builder()
                .withBoardId(1L)
                .withType(Harbor.AIRCRAFT_CARRIER)
                .withStart(toPoint(0))
                .withEnd(toPoint(4)).build();
        List<Move> moves = pointsRange(toPoint(10), toPoint(14))
                .stream().map(p -> Move.builder()
                        .withPoint(p)
                        .withBoardId(1L)
                        .withStatus(MISS)
                        .build()).collect(toList());

        when(moveRepository.getAll(anyLong())).thenReturn(of(moves));
        when(shipRepository.getAll(anyLong())).thenReturn(of(singletonList(ship)));
        when(moveRepository.create(1L, point, MISS)).thenReturn(of(moveMiss));
        when(moveRepository.create(1L, point, HIT)).thenReturn(of(moveHit));

        Board actual = gameService.placeMove(1L, 1L, point);

        assertThat(actual.getMoves().contains(moveHit), is(true));
    }

    @Test
    public void placeMove_shouldReturnTheSunkenShips() throws MoveCollision {
        List<Point> points = pointsRange(new Point(0, 0), new Point(0, 4));
        points.addAll(pointsRange(new Point(1, 0), new Point(1, 3)));
        points.addAll(pointsRange(new Point(2, 0), new Point(2, 2)));
        points.addAll(pointsRange(new Point(3, 0), new Point(3, 1)));

        Point point = new Point(3, 2);
        Move moveMiss = Move.builder().withPoint(point).withStatus(MISS).build();
        Move moveHit = Move.builder().withPoint(point).withStatus(HIT).build();
        Ship ship = floating.copy().withSunk(true).build();
        List<Move> moves = points.stream().map(p -> Move.builder()
                .withStatus(HIT).withPoint(p).build()).collect(toList());

        when(moveRepository.create(1L, point, MISS)).thenReturn(of(moveMiss));
        when(moveRepository.create(1L, point, HIT)).thenReturn(of(moveHit));
        when(moveRepository.getAll(anyLong())).thenReturn(of(moves));
        when(shipRepository.getAll(anyLong())).thenReturn(of(ships));
        when(shipRepository.update(any(Ship.class))).thenReturn(of(ship));

        Board actual = gameService.placeMove(1L, 1L, point);

        verify(shipRepository).update(any(Ship.class));
        assertThat(actual.getShips().size(), is(4));
    }

}