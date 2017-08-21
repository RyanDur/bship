package com.bship.games.logic

import com.bship.games.endpoints.board.errors.exceptions.ShipCollisionCheck
import com.bship.games.endpoints.board.errors.exceptions.ShipExistsCheck
import com.bship.games.endpoints.cabinet.entity.*
import com.bship.games.endpoints.game.errors.GameValidation
import com.bship.games.endpoints.game.errors.MoveCollision
import com.bship.games.endpoints.game.errors.TurnCheck
import com.bship.games.logic.definitions.Direction.DOWN
import com.bship.games.logic.definitions.Direction.RIGHT
import com.bship.games.logic.definitions.Harbor.*
import com.bship.games.logic.definitions.MoveStatus.HIT
import com.bship.games.logic.definitions.MoveStatus.MISS
import com.bship.games.util.Util
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Test
import java.util.Arrays.asList
import java.util.Collections.emptyList
import java.util.stream.Collectors
import java.util.stream.Collectors.partitioningBy

class BattleshipTest {

    private lateinit var logic: GameLogic
    private lateinit var loser: Board
    private lateinit var lostGame: Game
    private lateinit var notTaken: Piece

    @Before
    @Throws(Exception::class)
    fun setUp() {
        logic = Battleship()
        loser = Board.build { withWinner { false } }
        lostGame = Game.builder().withBoards(asList<Board>(loser, loser)).build()
        notTaken = Piece.build {}
    }

    @Test
    @Throws(GameValidation::class)
    fun valid_shouldReturnTheGame() {
        val boardId = 1L
        val move = Move.builder().withBoardId(boardId).build()
        val board1 = Board.build {
            withMoves { emptyList() }
            withId { boardId }
        }
        val board2 = Board.build {
            withMoves { emptyList() }
            withId { boardId + 1 }
        }
        val game = Game.builder().withBoards(asList(board1, board2)).withTurn(boardId).build()

        val actual = logic.valid(move).test(game)

        assertThat(actual, `is`(equalTo(true)))
    }

    @Test
    @Throws(GameValidation::class)
    fun valid_shouldThrowTurnCheckIfTheMoveIsPlayedOutOfTurn() {
        val boardId = 1L
        val move = Move.builder().withBoardId(boardId).build()
        val game = Game.builder().withTurn(boardId + 1L).build()

        assertThatThrownBy { logic.valid(move).test(game) }
                .isInstanceOf(TurnCheck::class.java)
                .hasMessage("It is not your turn.")
    }

    @Test
    @Throws(MoveCollision::class, TurnCheck::class)
    fun valid_shouldThrowMoveCollisionIfMoveAlreadyPlayed() {
        val boardId = 1L
        val move1 = Move.builder().withPoint(Point(1, 2)).withId(1L).withBoardId(boardId).build()
        val move2 = Move.builder().withPoint(Point(1, 2)).withBoardId(boardId).build()
        val board1 = Board.build {
            withMoves { moves.orElse(emptyList()) + move1 }
            withId { boardId }
        }
        val board2 = Board.build {
            withId { boardId + 1 }
        }
        val game = Game.builder().withBoards(asList(board1, board2)).withTurn(boardId).build()

        assertThatThrownBy { logic.valid(move2).test(game) }
                .isInstanceOf(MoveCollision::class.java)
                .hasMessage("Move already exists on board.")
    }

    @Test
    @Throws(MoveCollision::class, TurnCheck::class)
    fun valid_shouldNotCareWhoGoesFirst() {
        val boardId2 = 2L
        val move = Move.builder().withBoardId(boardId2).build()
        val board1 = Board.build {
            withMoves { emptyList() }
            withId { 1 }
        }
        val board2 = Board.build {
            withMoves { emptyList() }
            withId { boardId2 }
        }
        val game = Game.builder().withBoards(asList(board1, board2)).build()

        val actual = logic.valid(move).test(game)

        assertThat(actual, `is`(equalTo(true)))
    }

    @Test
    @Throws(ShipExistsCheck::class, ShipCollisionCheck::class)
    fun placementCheck_shouldReturnTheBoard() {
        val piece1 = Piece.build {
            withId { 1 }
            withType { BATTLESHIP }
            withPlacement { Point(0, 0) }
            withOrientation { DOWN }
        }

        val piece2 = Piece.build {
            withId { 1 }
            withType { AIRCRAFT_CARRIER }
            withPlacement { Point(1, 0) }
            withOrientation { DOWN }
        }

        val board = Board.build { withPieces { pieces.orElse(emptyList()) + piece1 } }

        val actual = logic.placementCheck(listOf(piece2)).test(board)
        assertThat(actual, `is`(equalTo(true)))
    }


    @Test
    @Throws(ShipExistsCheck::class, ShipCollisionCheck::class)
    fun placementCheck_shouldKnowIfAShipIsAlreadyOnTheBoard() {
        val piece1 = Piece.build {
            withType { BATTLESHIP }
            withPlacement { Point(0, 0) }
            withOrientation { DOWN }
        }

        val piece2 = Piece.build {
            withId { 1 }
            withType { BATTLESHIP }
            withPlacement { Point(1, 0) }
            withOrientation { DOWN }
        }
        val board = Board.build { withPieces { pieces.orElse(emptyList()) + piece1 } }

        assertThatThrownBy { logic.placementCheck(listOf(piece2)).test(board) }
                .isInstanceOf(ShipExistsCheck::class.java)
                .hasMessage("Ship already exists on board.")
    }

    @Test
    @Throws(ShipExistsCheck::class, ShipCollisionCheck::class)
    fun placementCheck_shouldKnowIfAShipIsAlreadySetOnTheBoard() {
        val piece1 = Piece.build {
            withType { BATTLESHIP }
        }

        val piece2 = Piece.build {
            withType { BATTLESHIP }
            withPlacement { Point(1, 0) }
            withOrientation { DOWN }
        }

        val board = Board.build { withPieces { pieces.orElse(emptyList()) + piece1 } }

        val actual = logic.placementCheck(listOf(piece2)).test(board)
        assertThat(actual, `is`(equalTo(true)))
    }

    @Test
    @Throws(ShipExistsCheck::class, ShipCollisionCheck::class)
    fun placementCheck_shouldBeAbleToDetectACollision() {
        val battleship = Piece.build {
            withType { BATTLESHIP }
            withPlacement { Point(0, 0) }
            withOrientation { DOWN }
        }

        val carrier = Piece.build {
            withType { AIRCRAFT_CARRIER }
            withPlacement { Point(0, 0) }
            withOrientation { RIGHT }
        }

        val board = Board.build { withPieces { listOf(battleship) } }

        assertThatThrownBy { logic.placementCheck(listOf(carrier)).test(board) }
                .isInstanceOf(ShipCollisionCheck::class.java)
                .hasMessage("Ship collision.")
    }

    @Test
    fun playMove_shouldReturnAGameWithThePlayedMove() {
        val gameId = 1L
        val boardId = 1L
        val opponentBoardId = 2L

        val move = getMove(9, 9, boardId)
        val game = getGame(gameId, boardId, opponentBoardId)

        val actual = logic.play(move).apply(game).orElse(lostGame)

        val boards = game.boards
        val board1 = boards.stream().filter { it.id == boardId }.findFirst().orElse(loser)
        val opponentBoard1 = boards.stream().filter { it.id != boardId }.findFirst().orElse(loser)
        opponentBoard1.copy { withOpponentMoves { opponentBoard1.opponentMoves + move } }

        val expected = game.copy().withBoards(asList(
                board1.copy { withMoves { board1.moves + move.copy().withStatus(MISS).build() } },
                opponentBoard1.copy { withOpponentMoves { opponentBoard1.opponentMoves + move.copy().withStatus(MISS).build() } }
        )).build()

        assertThat(actual, `is`(equalTo(expected)))
    }

    @Test
    @Throws(Exception::class)
    fun playMove_shouldBeAbleToHitAShip() {
        val gameId = 1L
        val boardId = 1L
        val opponentBoardId = 2L

        val move = getMove(0, 0, boardId)
        val game = getGame(gameId, boardId, opponentBoardId)

        val actual = logic.play(move).apply(game).orElse(lostGame)

        val boardMap = partitionBoards(game, move)
        val board1 = boardMap[true]!!
        val opponentBoard1 = boardMap[false]!!
        opponentBoard1.copy { withOpponentMoves { opponentBoard1.opponentMoves + move } }

        val expected = game.copy().withBoards(asList(
                board1.copy { withMoves { board1.moves + move.copy().withStatus(HIT).build() } },
                opponentBoard1.copy { withOpponentMoves { opponentBoard1.opponentMoves + move.copy().withStatus(HIT).build() } }))
                .build()

        assertThat(actual, `is`(equalTo(expected)))
    }

    @Test
    @Throws(Exception::class)
    fun playMove_shouldBeAbleToSinkAShip() {
        val gameId = 1L
        val boardId = 1L
        val opponentBoardId = 2L

        val move1 = getMove(4, 0, boardId)
        val game1 = getGame(gameId, boardId, opponentBoardId)
        val game2 = logic.play(move1).apply(game1).orElse(lostGame)

        val move2 = getMove(4, 1, boardId)
        val actual = logic.play(move2).apply(game2).orElse(lostGame)

        val boardMap = partitionBoards(actual, move2)

        val other = boardMap[move2.boardId != boardId]!!
        val taken = other.pieces.firstOrNull { it.taken }
        assertThat(taken?.taken, `is`(true))
        assertThat(taken?.type?.getName(), `is`(equalTo(DESTROYER.name)))
    }

    @Test
    fun playMove_shouldSetGameOverIfItIs() {
        val gameId = 1L
        val boardId = 1L
        val opponentBoardId = 2L

        val move1 = getMove(4, 0, boardId)
        val game1 = getGame(gameId, boardId, opponentBoardId)
        val boards = partitionBoards(game1, move1)

        val current = boards[game1.turn == boardId]!!
        val other = boards[game1.turn != boardId]!!

        val pieceMap = other.pieces.stream()
                .collect(partitioningBy { piece -> piece.type == AIRCRAFT_CARRIER })

        val carrier = pieceMap[true]!!
        val theRest = pieceMap[false]!!

        val taken = theRest.map { o -> o.copy { withTaken { true } } }
        val otherShips = Util.concat(carrier, taken)

        val moves = otherShips.stream().flatMap { p -> Util.pointsRange(p).stream() }
                .filter { p -> p != Point(0, 0) }
                .map { p -> Move.builder().withPoint(p) }
                .map { m -> m.withBoardId(current.id) }
                .map { m -> m.withStatus(HIT) }
                .map({ it.build() })
                .collect(Collectors.toList())

        val board = current.copy {
            withMoves { moves }
            withOpponentMoves { emptyList() }
            withOpponentPieces { current.opponentPieces + taken }
        }
        val board1 = other.copy { withPieces { otherShips } }

        val game2 = logic.play(Move.builder()
                .withPoint(Point(0, 0))
                .withBoardId(current.id)
                .build())
                .apply(game1.copy().withBoards(asList(board, board1)).build()).orElse(lostGame)

        assertThat(game2.isOver, `is`(true))
    }

    @Test
    fun playMove_shouldSetWinnerIfGameIsOver() {
        val gameId = 1L
        val boardId = 1L
        val opponentBoardId = 2L

        val move1 = getMove(4, 0, boardId)
        val game1 = getGame(gameId, boardId, opponentBoardId)
        val boards = partitionBoards(game1, move1)

        val current = boards[game1.turn == boardId]!!
        val other = boards[game1.turn != boardId]!!

        val pieceMap = other.pieces.groupBy { it.type == AIRCRAFT_CARRIER }

        val carrier = pieceMap[true]!!
        val theRest = pieceMap[false]!!

        val taken = theRest.map { o -> o.copy { withTaken { true } } }
        val otherShips = Util.concat(carrier, taken)

        val moves = otherShips.stream().flatMap { p -> Util.pointsRange(p).stream() }
                .filter { p -> p != Point(0, 0) }
                .map { p -> Move.builder().withPoint(p) }
                .map { m -> m.withBoardId(current.id) }
                .map { m -> m.withStatus(HIT) }
                .map { it.build() }
                .collect(Collectors.toList())

        val board = current.copy {
            withMoves { moves }
            withOpponentMoves { emptyList() }
            withOpponentPieces { current.opponentPieces + taken }
        }
        val board1 = other.copy { withPieces { otherShips } }

        val game2 = logic.play(Move.builder()
                .withPoint(Point(0, 0))
                .withBoardId(current.id)
                .build())
                .apply(game1.copy().withBoards(asList(board, board1)).build()).orElse(lostGame)

        assertThat(game2.isOver, `is`(true))
        val winner = game2.boards.first { it.id == current.id }
        assertThat(winner.winner, `is`(true))
    }

    @Test
    fun setNextTurn_shouldSetToNullIfGameIsOver() {
        val gameId = 1L
        val boardId = 1L
        val opponentBoardId = 2L

        val move1 = getMove(4, 0, boardId)
        val game1 = getGame(gameId, boardId, opponentBoardId)
        val boards = partitionBoards(game1, move1)

        val current = boards[game1.turn == boardId]!!
        val other = boards[game1.turn != boardId]!!

        val pieceMap = other.pieces.groupBy { it.type == AIRCRAFT_CARRIER }

        val carrier = pieceMap[true]!!
        val theRest = pieceMap[false]!!

        val taken = theRest.map { it.copy { withTaken { true } } }
        val otherShips = Util.concat(carrier, taken)

        val moves = otherShips.stream().flatMap { p -> Util.pointsRange(p).stream() }
                .filter { p -> p != Point(0, 0) }
                .map { p -> Move.builder().withPoint(p) }
                .map { m -> m.withBoardId(current.id) }
                .map { m -> m.withStatus(HIT) }
                .map { it.build() }
                .collect(Collectors.toList())

        val board = current.copy {
            withMoves { moves }
            withOpponentMoves { emptyList() }
            withOpponentPieces { current.opponentPieces + taken }
        }
        val board1 = other.copy { withPieces { otherShips } }

        val move = Move.builder()
                .withPoint(Point(0, 0))
                .withBoardId(current.id)
                .build()

        val game2 = logic.play(move)
                .apply(game1.copy().withBoards(asList(board, board1)).build())
                .orElse(lostGame)

        assertThat(game2.isOver, `is`(true))
        val winner = game2.boards.stream()
                .filter { it.id == current.id }
                .findFirst()
        assertThat(winner.isPresent, `is`(true))
        assertThat(winner.orElse(loser).winner, `is`(true))

        val game = logic.setNextTurn(move).apply(game2)

        assertThat(game.turn, `is`(nullValue()))
    }

    @Test
    fun setNextTurn_shouldSetTheNextTurnToBPlayed() {
        val boardId1 = 1L
        val boardId2 = 1L + 1L
        val move = Move.builder().withBoardId(boardId1).build()
        val board1 = Board.build {
            withId { boardId1 }
        }
        val board2 = Board.build {
            withId { boardId2 }
        }
        val game = Game.builder().withBoards(asList(board1, board2)).withTurn(boardId1).build()
        val expected = Game.builder().withBoards(asList(board1, board2)).withTurn(boardId2).build()

        val actual = logic.setNextTurn(move).apply(game)
        assertThat(actual, `is`(equalTo(expected)))
    }

    private fun getGame(gameId: Long, boardId: Long, opponentBoardId: Long): Game {
        val pieces = getShips(boardId)
        val opponentPieces = getShips(opponentBoardId)
        val board = getBoard(boardId, pieces)
        val opponentBoard = getBoard(opponentBoardId, opponentPieces)
        return Game.builder()
                .withBoards(asList(board, opponentBoard))
                .withId(gameId)
                .withTurn(boardId)
                .build()
    }

    private fun getMove(x: Int, y: Int, boardId: Long): Move {
        return Move.builder()
                .withBoardId(boardId)
                .withPoint(Point(x, y))
                .build()
    }

    private fun getBoard(boardId: Long, pieces: List<Piece>): Board {
        return Board.build {
            withPieces { pieces }
            withId { boardId }
            withMoves { emptyList() }
        }
    }

    private fun getShips(boardId: Long): List<Piece> {
        return asList(
                Piece.build {
                    withType { AIRCRAFT_CARRIER }
                    withPlacement { Point(0, 0) }
                    withOrientation { DOWN }
                    withBoardId { boardId }
                },
                Piece.build {
                    withType { BATTLESHIP }
                    withPlacement { Point(1, 0) }
                    withOrientation { DOWN }
                    withBoardId { boardId }
                },
                Piece.build {
                    withType { SUBMARINE }
                    withPlacement { Point(2, 0) }
                    withOrientation { DOWN }
                    withBoardId { boardId }
                },
                Piece.build {
                    withType { CRUISER }
                    withPlacement { Point(3, 0) }
                    withOrientation { DOWN }
                    withBoardId { boardId }
                },
                Piece.build {
                    withType { DESTROYER }
                    withPlacement { Point(4, 0) }
                    withOrientation { DOWN }
                    withBoardId { boardId }
                })
    }

    private fun partitionBoards(game: Game, move: Move): Map<Boolean, Board> {
        return game.boards.associateBy { it.id == move.boardId }
    }
}