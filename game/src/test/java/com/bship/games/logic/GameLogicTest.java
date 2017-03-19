package com.bship.games.logic;

import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import com.bship.games.domains.Harbor;
import com.bship.games.domains.Move;
import com.bship.games.domains.Point;
import com.bship.games.domains.Ship;
import com.bship.games.exceptions.GameValidation;
import com.bship.games.exceptions.MoveCollision;
import com.bship.games.exceptions.ShipCollisionCheck;
import com.bship.games.exceptions.ShipExistsCheck;
import com.bship.games.exceptions.TurnCheck;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.bship.games.domains.MoveStatus.HIT;
import static com.bship.games.domains.MoveStatus.MISS;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
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
    public void valid_shouldReturnTheGame() throws GameValidation {
        long boardId = 1L;
        Move move = Move.builder().withBoardId(boardId).build();
        Board board1 = Board.builder().withMoves(emptyList()).withId(boardId).build();
        Board board2 = Board.builder().withMoves(emptyList()).withId(boardId + 1L).build();
        Game game = Game.builder().withBoards(asList(board1, board2)).withTurn(boardId).build();

        Game actual = logic.valid(move).apply(game);

        assertThat(actual, is(equalTo(game)));
    }

    @Test
    public void valid_shouldThrowTurnCheckIfTheMoveIsPlayedOutOfTurn() throws GameValidation {
        thrown.expect(TurnCheck.class);
        thrown.expectMessage("It is not your turn.");

        long boardId = 1L;
        Move move = Move.builder().withBoardId(boardId).build();
        Game game = Game.builder().withTurn(boardId + 1L).build();

        logic.valid(move).apply(game);
    }

    @Test
    public void valid_shouldThrowMoveCollisionIfMoveAlreadyPlayed() throws MoveCollision, TurnCheck {
        thrown.expect(MoveCollision.class);
        thrown.expectMessage("Move already exists on board.");

        long boardId = 1L;
        Move move1 = Move.builder().withPoint(new Point(1, 2)).withId(1L).withBoardId(boardId).build();
        Move move2 = Move.builder().withPoint(new Point(1, 2)).withBoardId(boardId).build();
        Board board1 = Board.builder().addMove(move1).withId(boardId).build();
        Board board2 = Board.builder().withId(boardId + 1L).build();
        Game game = Game.builder().withBoards(asList(board1, board2)).withTurn(boardId).build();

        logic.valid(move2).apply(game);
    }

    @Test
    public void valid_shouldNotCareWhoGoesFirst() throws MoveCollision, TurnCheck {
        long boardId2 = 1L;
        Move move = Move.builder().withBoardId(boardId2).build();
        Board board1 = Board.builder().withMoves(emptyList()).withId(1L).build();
        Board board2 = Board.builder().withMoves(emptyList()).withId(boardId2).build();
        Game game = Game.builder().withBoards(asList(board1, board2)).build();

        Game actual = logic.valid(move).apply(game);

        assertThat(actual, is(equalTo(game)));
    }

    @Test
    public void placementCheck_shouldReturnTheBoard() throws ShipExistsCheck, ShipCollisionCheck {
        Ship ship1 = Ship.builder()
                .withType(Harbor.BATTLESHIP)
                .withStart(new Point(0, 0))
                .withEnd(new Point(0, 4))
                .build();

        Ship ship2 = Ship.builder()
                .withType(Harbor.AIRCRAFT_CARRIER)
                .withStart(new Point(1, 0))
                .withEnd(new Point(1, 4))
                .build();

        Board board = Board.builder().addShip(ship1).build();

        Board actual = logic.placementCheck(ship2).apply(board);
        assertThat(actual, is(equalTo(board)));
    }


    @Test
    public void placementCheck_shouldKnowIfAShipIsAlreadyOnTheBoard() throws ShipExistsCheck, ShipCollisionCheck {
        thrown.expect(ShipExistsCheck.class);
        thrown.expectMessage("Ship already exists on board.");

        Ship ship1 = Ship.builder()
                .withType(Harbor.BATTLESHIP)
                .withStart(new Point(0, 0))
                .withEnd(new Point(0, 4))
                .build();

        Ship ship2 = Ship.builder()
                .withType(Harbor.BATTLESHIP)
                .withStart(new Point(1, 0))
                .withEnd(new Point(1, 4))
                .build();

        Board board = Board.builder().addShip(ship1).build();

        logic.placementCheck(ship2).apply(board);
    }

    @Test
    public void placementCheck_shouldKnowIfAShipIsAlreadySetOnTheBoard() throws ShipExistsCheck, ShipCollisionCheck {
        Ship ship1 = Ship.builder()
                .withType(Harbor.BATTLESHIP)
                .withStart(new Point())
                .withEnd(new Point())
                .build();

        Ship ship2 = Ship.builder()
                .withType(Harbor.BATTLESHIP)
                .withStart(new Point(1, 0))
                .withEnd(new Point(1, 4))
                .build();

        Board board = Board.builder().addShip(ship1).build();

        Board actual = logic.placementCheck(ship2).apply(board);
        assertThat(actual, is(equalTo(board)));
    }

    @Test
    public void placementCheck_shouldBeAbleToDetectACollision() throws ShipExistsCheck, ShipCollisionCheck {
        thrown.expect(ShipCollisionCheck.class);
        thrown.expectMessage("Ship collision.");

        Ship battleship = Ship.builder().withType(Harbor.BATTLESHIP).withStart(new Point(0, 0)).withEnd(new Point(0, 3)).build();
        Ship carrier = Ship.builder().withType(Harbor.AIRCRAFT_CARRIER).withStart(new Point(0, 0)).withEnd(new Point(4, 0)).build();
        Board board = Board.builder().withShips(singletonList(battleship)).build();

        logic.placementCheck(carrier).apply(board);
    }

    @Test
    public void playMove_shouldReturnAGameWithThePlayedMove() {
        long gameId = 1L;
        long boardId = 1L;
        long opponentBoardId = 2L;

        Move move = getMove(9, 9, boardId);
        Game game = getGame(gameId, boardId, opponentBoardId);

        Game actual = logic.play(move).apply(game);

        List<Board> boards = game.getBoards();
        Board board1 = boards.stream().filter(o -> o.getId() == boardId).findFirst().get();
        Board opponentBoard1 = boards.stream().filter(o -> o.getId() != boardId).findFirst().get();
        opponentBoard1.copy().addOpponentMove(move).build();

        Game expected = game.copy().withBoards(asList(
                board1.copy().addMove(move.copy().withStatus(MISS).build()).build(),
                opponentBoard1.copy().addOpponentMove(move.copy().withStatus(MISS).build()).build()
        )).build();

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void playMove_shouldBeAbleToHitAShip() throws Exception {
        long gameId = 1L;
        long boardId = 1L;
        long opponentBoardId = 2L;

        Move move = getMove(0, 0, boardId);
        Game game = getGame(gameId, boardId, opponentBoardId);

        Game actual = logic.play(move).apply(game);

        List<Board> boards = game.getBoards();
        Board board1 = boards.stream().filter(o -> o.getId() == boardId).findFirst().get();
        Board opponentBoard1 = boards.stream().filter(o -> o.getId() != boardId).findFirst().get();
        opponentBoard1.copy().addOpponentMove(move).build();

        Game expected = game.copy().withBoards(asList(
                board1.copy().addMove(
                        move.copy().withStatus(HIT).build()
                ).build(),
                opponentBoard1.copy().addOpponentMove(
                        move.copy().withStatus(HIT).build()
                ).build()
        )).build();

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void playMove_shouldBeAbleToSinkAShip() throws Exception {
        long gameId = 1L;
        long boardId = 1L;
        long opponentBoardId = 2L;

        Move move = getMove(4, 0, boardId);
        Game game = getGame(gameId, boardId, opponentBoardId);

        Game game1 = logic.play(move).apply(game);

        Move move2 = move.copy().withPoint(new Point(4, 1)).build();
        Game actual = logic.play(move2).apply(game1);

        Map<Boolean, Board> boardMap = partitionBoards(game1, boardId);
        Board current = boardMap.get(move.getBoardId() == boardId);
        Board other = boardMap.get(move.getBoardId() != boardId);

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

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void setNextTurn_shouldSetTheNextTurnToBPlayed() {
        long boardId1 = 1L;
        long boardId2 = 1L + 1L;
        Move move = Move.builder().withBoardId(boardId1).build();
        Board board1 = Board.builder().withId(boardId1).build();
        Board board2 = Board.builder().withId(boardId2).build();
        Game game = Game.builder().withBoards(asList(board1, board2)).withTurn(boardId1).build();
        Game expected = Game.builder().withBoards(asList(board1, board2)).withTurn(boardId2).build();

        Game actual = logic.setNextTurn(move).apply(game);
        assertThat(actual, is(equalTo(expected)));
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

    private Move getMove(int x, int y, long boardId) {
        return Move.builder()
                .withBoardId(boardId)
                .withPoint(new Point(x, y)).build();
    }

    private Board getBoard(long boardId, List<Ship> ships) {
        return Board.builder()
                .withShips(ships)
                .withId(boardId)
                .withMoves(emptyList())
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

    private Map<Boolean, Board> partitionBoards(Game game, long boardId) {
        return game.getBoards()
                .stream()
                .collect(partitioningBy(board -> board.getId() == boardId))
                .entrySet()
                .stream()
                .collect(toMap(Map.Entry::getKey,
                        entry -> entry.getValue().stream().findFirst().get()));
    }
}