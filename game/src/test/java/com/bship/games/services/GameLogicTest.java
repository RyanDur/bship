package com.bship.games.services;

import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import com.bship.games.domains.Harbor;
import com.bship.games.domains.Move;
import com.bship.games.domains.Point;
import com.bship.games.domains.Ship;
import com.bship.games.exceptions.MoveCollision;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bship.games.domains.MoveStatus.HIT;
import static com.bship.games.domains.MoveStatus.MISS;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class GameLogicTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private GameLogic logic;

    @Before
    public void setUp() throws Exception {
        logic = new GameLogic();
    }

    @Test
    public void turnCheck_shouldDecideIfTheCorrectTurnIsInPlay() throws Exception {
        Long gameId = 2L;

        boolean actual = logic.turnCheck(gameId).test(Game.builder().withTurn(gameId).build());
        assertThat(actual, is(true));
    }

    @Test
    public void turnCheck_shouldDecideIfTheWrongTurnIsInPlay() throws Exception {
        Long gameId = 2L;

        boolean actual = logic.turnCheck(gameId).test(Game.builder().withTurn(gameId + 1).build());
        assertThat(actual, is(false));
    }

    @Test
    public void turnCheck_shouldReturnTrueIfNoTurnIsSetSinceItIsAnybodiesTurn() throws Exception {
        Long gameId = 2L;

        boolean actual = logic.turnCheck(gameId).test(Game.builder().build());
        assertThat(actual, is(true));
    }

    @Test
    public void nextTurn_shouldKnowIfTheIdIsTheNextToPlay() {
        Long boardId = 1L;
        Long nextBoardId = 2L;
        boolean actual = logic.nextTurn(boardId).test(nextBoardId);

        assertThat(actual, is(true));
    }

    @Test
    public void nextTurn_shouldNotAllowIfTheCurrentPlayIdIsUnknown() {
        Long boardId = null;
        Long nextBoardId = 1L;
        boolean actual = logic.nextTurn(nextBoardId).test(boardId);

        assertThat(actual, is(false));
    }

    @Test
    public void nextTurn_shouldNotAllowIfTheNextPlayIdIsUnknown() {
        Long boardId = 1L;
        Long nextBoardId = null;
        boolean actual = logic.nextTurn(nextBoardId).test(boardId);

        assertThat(actual, is(false));
    }

    @Test
    public void exists_shouldKnowIfAShipIsAlreadyOnTheBoard() {
        Ship ship = Ship.builder().withType(Harbor.BATTLESHIP).build();
        Board board = Board.builder().addShip(ship).build();

        boolean actual = logic.exists(board, ship);

        assertThat(actual, is(true));
    }

    @Test
    public void exists_shouldKnowIfAShipIsNotAlreadyOnAnEmptyBoard() {
        Ship ship = Ship.builder().withType(Harbor.BATTLESHIP).build();
        Board board = Board.builder().build();

        boolean actual = logic.exists(board, ship);

        assertThat(actual, is(false));
    }

    @Test
    public void exists_shouldKnowIfAShipIsNotAlreadyOnTheBoard() {
        Ship ship = Ship.builder().withType(Harbor.BATTLESHIP).build();
        Board board = Board.builder().addShip(Ship.builder().withType(Harbor.SUBMARINE).build()).build();

        boolean actual = logic.exists(board, ship);

        assertThat(actual, is(false));
    }

    @Test
    public void exists_shouldReturnFalseForANonExistentBoard() {
        Ship ship = Ship.builder().withType(Harbor.BATTLESHIP).build();
        Board board = null;

        boolean actual = logic.exists(board, ship);

        assertThat(actual, is(false));
    }

    @Test
    public void exists_shouldReturnFalseForANonExistentShip() {
        Ship ship = null;
        Board board = Board.builder().addShip(Ship.builder().withType(Harbor.SUBMARINE).build()).build();

        boolean actual = logic.exists(board, ship);

        assertThat(actual, is(false));
    }

    @Test
    public void collision_shouldBeAbleToDetectACollision() {
        Ship battleship = Ship.builder().withType(Harbor.BATTLESHIP).withStart(new Point(0, 0)).withEnd(new Point(0, 3)).build();
        Ship carrier = Ship.builder().withType(Harbor.AIRCRAFT_CARRIER).withStart(new Point(0, 0)).withEnd(new Point(4, 0)).build();
        Board board = Board.builder().withShips(singletonList(battleship)).build();

        boolean actual = logic.collision(board, carrier);

        assertThat(actual, is(true));
    }

    @Test
    public void collision_shouldBeAbleToDetectWhenThereIsNotACollision() {
        Ship battleship = Ship.builder().withType(Harbor.BATTLESHIP).withStart(new Point(0, 0)).withEnd(new Point(0, 3)).build();
        Ship carrier = Ship.builder().withType(Harbor.AIRCRAFT_CARRIER).withStart(new Point(1, 1)).withEnd(new Point(4, 1)).build();
        Board board = Board.builder().withShips(singletonList(battleship)).build();

        boolean actual = logic.collision(board, carrier);

        assertThat(actual, is(false));
    }

    @Test
    public void collision_shouldBeFalseIfThereIsNotABoard() {
        Ship carrier = Ship.builder().withType(Harbor.AIRCRAFT_CARRIER).withStart(new Point(1, 1)).withEnd(new Point(4, 1)).build();
        Board board = null;

        boolean actual = logic.collision(board, carrier);

        assertThat(actual, is(false));
    }

    @Test
    public void collision_shouldBeFalseIfThereIsNotAShip() {
        Ship battleship = Ship.builder().withType(Harbor.BATTLESHIP).withStart(new Point(0, 0)).withEnd(new Point(0, 3)).build();
        Ship carrier = null;
        Board board = Board.builder().withShips(singletonList(battleship)).build();

        boolean actual = logic.collision(board, carrier);

        assertThat(actual, is(false));
    }

    @Test
    public void playMove_shouldReturnAGameWithThePlayedMove() throws MoveCollision {
        long gameId = 1L;
        long boardId = 1L;
        long opponentBoardId = 2L;

        Move move = getMove(9, 9, boardId);
        Game game = getGame(gameId, boardId, opponentBoardId);

        Optional<Game> actual = logic.playMove(game, boardId, move);

        List<Board> boards = game.getBoards();
        Board board1 = boards.stream().filter(o -> o.getId().equals(boardId)).findFirst().get();
        Board opponentBoard1 = boards.stream().filter(o -> !o.getId().equals(boardId)).findFirst().get();
        opponentBoard1.copy().addOpponentMove(move).build();

        Game expected = game.copy().withBoards(asList(
                board1.copy().addMove(move.copy().withStatus(MISS).build()).build(),
                opponentBoard1.copy().addOpponentMove(move.copy().withStatus(MISS).build()).build()
        )).build();

        assertThat(actual.get(), is(equalTo(expected)));
    }

    @Test
    public void playMove_shouldNotBeAbleToPlayAMoveTwice() throws Exception {
        thrown.expect(MoveCollision.class);
        thrown.expectMessage("Move already exists on board.");

        long gameId = 1L;
        long boardId = 1L;
        long opponentBoardId = 2L;

        Move move = getMove(9, 9, boardId);
        Game game = getGame(gameId, boardId, opponentBoardId);

        game = logic.playMove(game, boardId, move).get();
        logic.playMove(game.copy().build(), boardId, move.copy().build());
    }

    @Test
    public void playMove_shouldBeAbleToHitAShip() throws Exception {
        long gameId = 1L;
        long boardId = 1L;
        long opponentBoardId = 2L;

        Move move = getMove(0, 0, boardId);
        Game game = getGame(gameId, boardId, opponentBoardId);

        Optional<Game> actual = logic.playMove(game, boardId, move);

        List<Board> boards = game.getBoards();
        Board board1 = boards.stream().filter(o -> o.getId().equals(boardId)).findFirst().get();
        Board opponentBoard1 = boards.stream().filter(o -> !o.getId().equals(boardId)).findFirst().get();
        opponentBoard1.copy().addOpponentMove(move).build();

        Game expected = game.copy().withBoards(asList(
                board1.copy().addMove(
                        move.copy().withStatus(HIT).build()
                ).build(),
                opponentBoard1.copy().addOpponentMove(
                        move.copy().withStatus(HIT).build()
                ).build()
        )).build();

        assertThat(actual.get(), is(equalTo(expected)));
    }

    @Test
    public void playMove_shouldBeAbleToSinkAShip() throws Exception {
        long gameId = 1L;
        long boardId = 1L;
        long opponentBoardId = 2L;

        Move move = getMove(4, 0, boardId);
        Game game = getGame(gameId, boardId, opponentBoardId);

        Optional<Game> game1 = logic.playMove(game, boardId, move);

        Move move2 = move.copy().withPoint(new Point(4, 1)).build();
        Optional<Game> actual = logic.playMove(game1.get(), boardId, move2);

        Map<Boolean, Board> boardMap = partitionBoards(game1.get(), boardId);
        Board current = boardMap.get(move.getBoardId().equals(boardId));
        Board other = boardMap.get(!move.getBoardId().equals(boardId));

        Ship sunkShip = getShips(boardId).stream()
                .filter(o -> o.getType().equals(Harbor.DESTROYER))
                .findFirst()
                .map(Ship::copy)
                .map(o -> o.withSunk(true))
                .map(Ship.Builder::build).get();

        List<Ship> shipList = getShips(boardId).stream()
                .filter(o -> !o.getType().equals(Harbor.DESTROYER))
                .collect(Collectors.toList());

        Game expected = game.copy().withBoards(asList(
                current.copy().withMoves(asList(
                        move.copy().withStatus(HIT).build(),
                        move2.copy().withStatus(HIT).build()
                )).addOpponentShip(sunkShip).build(),

                other.copy().withOpponentMoves(asList(
                        move.copy().withStatus(HIT).build(),
                        move2.copy().withStatus(HIT).build()
                )).withShips(shipList).addShip(sunkShip).build()

        )).build();

        assertThat(actual.get(), is(equalTo(expected)));
    }

    private Game getGame(long gameId, long boardId, long opponentBoardId) {
        List<Ship> ships = getShips(boardId);
        List<Ship> opponentShips = getShips(boardId);
        Board board = getBoard(boardId, ships);
        Board opponentBoard = getBoard(opponentBoardId, opponentShips);
        return Game.builder()
                .withBoards(asList(board, opponentBoard))
                .withId(gameId)
                .build();
    }

    private Move getMove(int x, int y, Long boardId) {
        return Move.builder()
                .withBoardId(boardId)
                .withPoint(new Point(x, y)).build();
    }

    private Board getBoard(long boardId, List<Ship> ships) {
        return Board.builder()
                .withShips(ships)
                .withId(boardId)
                .build();
    }

    private List<Ship> getShips(long boardId) {
        return asList(Ship.builder().withType(Harbor.AIRCRAFT_CARRIER)
                        .withStart(new Point(0, 0))
                        .withEnd(new Point(0, 4))
                        .withSunk(false)
                        .withId(boardId).build(),
                Ship.builder().withType(Harbor.BATTLESHIP)
                        .withStart(new Point(1, 0))
                        .withEnd(new Point(1, 3))
                        .withSunk(false)
                        .withId(boardId).build(),
                Ship.builder().withType(Harbor.SUBMARINE)
                        .withStart(new Point(2, 0))
                        .withEnd(new Point(2, 2))
                        .withSunk(false)
                        .withId(boardId).build(),
                Ship.builder().withType(Harbor.CRUISER)
                        .withStart(new Point(3, 0))
                        .withEnd(new Point(3, 2))
                        .withSunk(false)
                        .withId(boardId).build(),
                Ship.builder().withType(Harbor.DESTROYER)
                        .withStart(new Point(4, 0))
                        .withEnd(new Point(4, 1))
                        .withSunk(false)
                        .withId(boardId).build());
    }

    private Map<Boolean, Board> partitionBoards(Game game, Long boardId) {
        return game.getBoards()
                .stream()
                .collect(partitioningBy(board -> board.getId().equals(boardId)))
                .entrySet()
                .stream()
                .collect(toMap(Map.Entry::getKey,
                        entry -> entry.getValue().stream().findFirst().get()));
    }
}