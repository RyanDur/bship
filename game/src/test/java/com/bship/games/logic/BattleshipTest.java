package com.bship.games.logic;

import com.bship.games.endpoints.cabinet.entity.Board;
import com.bship.games.endpoints.cabinet.entity.Game;
import com.bship.games.endpoints.cabinet.entity.Move;
import com.bship.games.endpoints.cabinet.entity.Piece;
import com.bship.games.endpoints.cabinet.entity.Point;
import com.bship.games.endpoints.errors.exceptions.GameValidation;
import com.bship.games.endpoints.errors.exceptions.MoveCollision;
import com.bship.games.endpoints.errors.exceptions.ShipCollisionCheck;
import com.bship.games.endpoints.errors.exceptions.ShipExistsCheck;
import com.bship.games.endpoints.errors.exceptions.TurnCheck;
import com.bship.games.util.Util;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.bship.games.logic.definitions.Direction.DOWN;
import static com.bship.games.logic.definitions.Direction.NONE;
import static com.bship.games.logic.definitions.Direction.RIGHT;
import static com.bship.games.logic.definitions.Harbor.AIRCRAFT_CARRIER;
import static com.bship.games.logic.definitions.Harbor.BATTLESHIP;
import static com.bship.games.logic.definitions.Harbor.CRUISER;
import static com.bship.games.logic.definitions.Harbor.DESTROYER;
import static com.bship.games.logic.definitions.Harbor.SUBMARINE;
import static com.bship.games.logic.definitions.MoveStatus.HIT;
import static com.bship.games.logic.definitions.MoveStatus.MISS;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class BattleshipTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private GameLogic logic;
    private Board loser;
    private Game lostGame;
    private Piece notTaken;

    @Before
    public void setUp() throws Exception {
        logic = new Battleship();
        loser = Board.builder()
                .withWinner(false).build();
        lostGame = Game.builder().withBoards(asList(loser, loser)).build();
        notTaken = Piece.builder().withTaken(false).build();
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
        long boardId2 = 2L;
        Move move = Move.builder().withBoardId(boardId2).build();
        Board board1 = Board.builder().withMoves(emptyList()).withId(1L).build();
        Board board2 = Board.builder().withMoves(emptyList()).withId(boardId2).build();
        Game game = Game.builder().withBoards(asList(board1, board2)).build();

        Game actual = logic.valid(move).apply(game);

        assertThat(actual, is(equalTo(game)));
    }

    @Test
    public void placementCheck_shouldReturnTheBoard() throws ShipExistsCheck, ShipCollisionCheck {
        Piece piece1 = Piece.builder()
                .withType(BATTLESHIP)
                .withPlacement(new Point(0, 0))
                .withOrientation(DOWN)
                .build();

        Piece piece2 = Piece.builder()
                .withType(AIRCRAFT_CARRIER)
                .withPlacement(new Point(1, 0))
                .withOrientation(DOWN)
                .build();

        Board board = Board.builder().addPiece(piece1).build();

        Board actual = logic.placementCheck(singletonList(piece2)).apply(board);
        assertThat(actual, is(equalTo(board)));
    }


    @Test
    public void placementCheck_shouldKnowIfAShipIsAlreadyOnTheBoard() throws ShipExistsCheck, ShipCollisionCheck {
        thrown.expect(ShipExistsCheck.class);
        thrown.expectMessage("Ship already exists on board.");

        Piece piece1 = Piece.builder()
                .withType(BATTLESHIP)
                .withPlacement(new Point(0, 0))
                .withOrientation(DOWN)
                .build();

        Piece piece2 = Piece.builder()
                .withType(BATTLESHIP)
                .withPlacement(new Point(1, 0))
                .withOrientation(DOWN)
                .build();

        Board board = Board.builder().addPiece(piece1).build();

        logic.placementCheck(singletonList(piece2)).apply(board);
    }

    @Test
    public void placementCheck_shouldKnowIfAShipIsAlreadySetOnTheBoard() throws ShipExistsCheck, ShipCollisionCheck {
        Piece piece1 = Piece.builder()
                .withType(BATTLESHIP)
                .withPlacement(new Point())
                .withOrientation(NONE)
                .build();

        Piece piece2 = Piece.builder()
                .withType(BATTLESHIP)
                .withPlacement(new Point(1, 0))
                .withOrientation(DOWN)
                .build();

        Board board = Board.builder().addPiece(piece1).build();

        Board actual = logic.placementCheck(singletonList(piece2)).apply(board);
        assertThat(actual, is(equalTo(board)));
    }

    @Test
    public void placementCheck_shouldBeAbleToDetectACollision() throws ShipExistsCheck, ShipCollisionCheck {
        thrown.expect(ShipCollisionCheck.class);
        thrown.expectMessage("Ship collision.");

        Piece battleship = Piece.builder()
                .withType(BATTLESHIP)
                .withPlacement(new Point(0, 0))
                .withOrientation(DOWN)
                .build();
        Piece carrier = Piece.builder()
                .withType(AIRCRAFT_CARRIER)
                .withPlacement(new Point(0, 0))
                .withOrientation(RIGHT)
                .build();

        Board board = Board.builder().withPieces(singletonList(battleship)).build();

        logic.placementCheck(singletonList(carrier)).apply(board);
    }

    @Test
    public void playMove_shouldReturnAGameWithThePlayedMove() {
        long gameId = 1L;
        long boardId = 1L;
        long opponentBoardId = 2L;

        Move move = getMove(9, 9, boardId);
        Game game = getGame(gameId, boardId, opponentBoardId);

        Game actual = logic.play(move).apply(game).orElse(lostGame);

        List<Board> boards = game.getBoards();
        Board board1 = boards.stream().filter(o -> o.getId() == boardId).findFirst().orElse(loser);
        Board opponentBoard1 = boards.stream().filter(o -> o.getId() != boardId).findFirst().orElse(loser);
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

        Game actual = logic.play(move).apply(game).orElse(lostGame);

        Map<Boolean, Board> boardMap = partitionBoards(game, move);
        Board board1 = boardMap.get(true);
        Board opponentBoard1 = boardMap.get(false);
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

        Move move1 = getMove(4, 0, boardId);
        Game game1 = getGame(gameId, boardId, opponentBoardId);
        Game game2 = logic.play(move1).apply(game1).orElse(lostGame);

        Move move2 = getMove(4, 1, boardId);
        Game actual = logic.play(move2).apply(game2).orElse(lostGame);

        Map<Boolean, Board> boardMap = partitionBoards(actual, move2);

        Board other = boardMap.get(move2.getBoardId() != boardId);
        Optional<Piece> taken = other.getPieces().stream().filter(Piece::isTaken).findFirst();
        assertThat(taken.orElse(notTaken).isTaken(), is(true));
        assertThat(taken.orElse(notTaken).getType(), is(DESTROYER));
    }

    @Test
    public void playMove_shouldSetGameOverIfItIs() {
        long gameId = 1L;
        long boardId = 1L;
        long opponentBoardId = 2L;

        Move move1 = getMove(4, 0, boardId);
        Game game1 = getGame(gameId, boardId, opponentBoardId);
        Map<Boolean, Board> boards = partitionBoards(game1, move1);

        Board current = boards.get(game1.getTurn().equals(boardId));
        Board other = boards.get(!game1.getTurn().equals(boardId));

        Map<Boolean, List<Piece>> pieceMap = other.getPieces().stream()
                .collect(partitioningBy(piece -> piece.getType().equals(AIRCRAFT_CARRIER)));

        List<Piece> carrier = pieceMap.get(true);
        List<Piece> theRest = pieceMap.get(false);

        List<Piece> taken = theRest.stream().map(o -> o.copy().withTaken(true).build()).collect(Collectors.toList());
        List<Piece> otherShips = Util.concat(carrier, taken);

        List<Move> moves = otherShips.stream().flatMap(p -> Util.pointsRange(p).stream())
                .filter(p -> !p.equals(new Point(0, 0)))
                .map(p -> Move.builder().withPoint(p))
                .map(m -> m.withBoardId(current.getId()))
                .map(m -> m.withStatus(HIT))
                .map(Move.Builder::build)
                .collect(Collectors.toList());

        Board board = current.copy().withMoves(moves)
                .withOpponentMoves(emptyList())
                .withOpponentPieces(taken).build();
        Board board1 = other.copy().withPieces(otherShips).build();

        Game game2 = logic.play(Move.builder()
                .withPoint(new Point(0, 0))
                .withBoardId(current.getId())
                .build())
                .apply(game1.copy().withBoards(asList(board, board1)).build()).orElse(lostGame);

        assertThat(game2.isOver(), is(true));
    }

    @Test
    public void playMove_shouldSetWinnerIfGameIsOver() {
        long gameId = 1L;
        long boardId = 1L;
        long opponentBoardId = 2L;

        Move move1 = getMove(4, 0, boardId);
        Game game1 = getGame(gameId, boardId, opponentBoardId);
        Map<Boolean, Board> boards = partitionBoards(game1, move1);

        Board current = boards.get(game1.getTurn().equals(boardId));
        Board other = boards.get(!game1.getTurn().equals(boardId));

        Map<Boolean, List<Piece>> pieceMap = other.getPieces().stream()
                .collect(partitioningBy(piece -> piece.getType().equals(AIRCRAFT_CARRIER)));

        List<Piece> carrier = pieceMap.get(true);
        List<Piece> theRest = pieceMap.get(false);

        List<Piece> taken = theRest.stream().map(o -> o.copy().withTaken(true).build()).collect(Collectors.toList());
        List<Piece> otherShips = Util.concat(carrier, taken);

        List<Move> moves = otherShips.stream().flatMap(p -> Util.pointsRange(p).stream())
                .filter(p -> !p.equals(new Point(0, 0)))
                .map(p -> Move.builder().withPoint(p))
                .map(m -> m.withBoardId(current.getId()))
                .map(m -> m.withStatus(HIT))
                .map(Move.Builder::build)
                .collect(Collectors.toList());

        Board board = current.copy().withMoves(moves)
                .withOpponentMoves(emptyList())
                .withOpponentPieces(taken).build();
        Board board1 = other.copy().withPieces(otherShips).build();

        Game game2 = logic.play(Move.builder()
                .withPoint(new Point(0, 0))
                .withBoardId(current.getId())
                .build())
                .apply(game1.copy().withBoards(asList(board, board1)).build()).orElse(lostGame);

        assertThat(game2.isOver(), is(true));
        Optional<Board> winner = game2.getBoards().stream()
                .filter(o -> o.getId().equals(current.getId()))
                .findFirst();
        assertThat(winner.orElse(loser).isWinner(), is(true));
    }

    @Test
    public void setNextTurn_shouldSetToNullIfGameIsOver() {
        long gameId = 1L;
        long boardId = 1L;
        long opponentBoardId = 2L;

        Move move1 = getMove(4, 0, boardId);
        Game game1 = getGame(gameId, boardId, opponentBoardId);
        Map<Boolean, Board> boards = partitionBoards(game1, move1);

        Board current = boards.get(game1.getTurn().equals(boardId));
        Board other = boards.get(!game1.getTurn().equals(boardId));

        Map<Boolean, List<Piece>> pieceMap = other.getPieces().stream()
                .collect(partitioningBy(piece -> piece.getType().equals(AIRCRAFT_CARRIER)));

        List<Piece> carrier = pieceMap.get(true);
        List<Piece> theRest = pieceMap.get(false);

        List<Piece> taken = theRest.stream().map(o -> o.copy().withTaken(true).build()).collect(Collectors.toList());
        List<Piece> otherShips = Util.concat(carrier, taken);

        List<Move> moves = otherShips.stream().flatMap(p -> Util.pointsRange(p).stream())
                .filter(p -> !p.equals(new Point(0, 0)))
                .map(p -> Move.builder().withPoint(p))
                .map(m -> m.withBoardId(current.getId()))
                .map(m -> m.withStatus(HIT))
                .map(Move.Builder::build)
                .collect(Collectors.toList());

        Board board = current.copy().withMoves(moves)
                .withOpponentMoves(emptyList())
                .withOpponentPieces(taken).build();
        Board board1 = other.copy().withPieces(otherShips).build();

        Move move = Move.builder()
                .withPoint(new Point(0, 0))
                .withBoardId(current.getId())
                .build();

        Game game2 = logic.play(move)
                .apply(game1.copy().withBoards(asList(board, board1)).build())
                .orElse(lostGame);

        assertThat(game2.isOver(), is(true));
        Optional<Board> winner = game2.getBoards().stream()
                .filter(o -> o.getId().equals(current.getId()))
                .findFirst();
        assertThat(winner.isPresent(), is(true));
        assertThat(winner.orElse(loser).isWinner(), is(true));

        Game game = logic.setNextTurn(move).apply(game2);

        assertThat(game.getTurn(), is(nullValue()));
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
        List<Piece> pieces = getShips(boardId);
        List<Piece> opponentPieces = getShips(opponentBoardId);
        Board board = getBoard(boardId, pieces);
        Board opponentBoard = getBoard(opponentBoardId, opponentPieces);
        return Game.builder()
                .withBoards(asList(board, opponentBoard))
                .withId(gameId)
                .withTurn(boardId)
                .build();
    }

    private Move getMove(int x, int y, long boardId) {
        return Move.builder()
                .withBoardId(boardId)
                .withPoint(new Point(x, y))
                .build();
    }

    private Board getBoard(long boardId, List<Piece> pieces) {
        return Board.builder()
                .withPieces(pieces)
                .withId(boardId)
                .withMoves(emptyList())
                .build();
    }

    private List<Piece> getShips(long boardId) {
        return asList(
                Piece.builder().withType(AIRCRAFT_CARRIER)
                        .withPlacement(new Point(0, 0))
                        .withOrientation(DOWN)
                        .withTaken(false)
                        .withBoardId(boardId).build(),
                Piece.builder().withType(BATTLESHIP)
                        .withPlacement(new Point(1, 0))
                        .withOrientation(DOWN)
                        .withTaken(false)
                        .withBoardId(boardId).build(),
                Piece.builder().withType(SUBMARINE)
                        .withPlacement(new Point(2, 0))
                        .withOrientation(DOWN)
                        .withTaken(false)
                        .withBoardId(boardId).build(),
                Piece.builder().withType(CRUISER)
                        .withPlacement(new Point(3, 0))
                        .withOrientation(DOWN)
                        .withTaken(false)
                        .withBoardId(boardId).build(),
                Piece.builder().withType(DESTROYER)
                        .withPlacement(new Point(4, 0))
                        .withOrientation(DOWN)
                        .withTaken(false)
                        .withBoardId(boardId).build());
    }

    private Map<Boolean, Board> partitionBoards(Game game, Move move) {
        return game.getBoards().stream().collect(toMap(
                board -> board.getId().equals(move.getBoardId()),
                Function.identity()));
    }
}