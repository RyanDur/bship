package com.bship.games.endpoints.cabinet.repositories

import com.bship.DBHelper
import com.bship.games.endpoints.cabinet.entity.Board
import com.bship.games.endpoints.cabinet.entity.Game
import com.bship.games.endpoints.cabinet.entity.Piece
import com.bship.games.logic.definitions.Harbor
import com.bship.games.logic.definitions.PieceType
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Test
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import java.util.*
import java.util.Collections.emptyList
import java.util.stream.Collectors
import java.util.stream.Stream

class BoardRepositoryTest {

    private lateinit var boards: BoardRepository
    private lateinit var game: Game
    private lateinit var pieceList: List<Piece>
    private var shipRepo = mock<PieceRepository>()
    private var moveRepo = mock<MoveRepository>()

    @Before
    fun setup() {
        val template = NamedParameterJdbcTemplate(DBHelper.reset())
        boards = BoardRepository(template, shipRepo, moveRepo)

        game = Game.builder().withId(1L).build()
        template.update("INSERT INTO games(id, name) VALUE(:id, 'BATTLESHIP')",
                MapSqlParameterSource("id", game.id))
        template.update("INSERT INTO games(id, name) VALUE(:id, 'BATTLESHIP')",
                MapSqlParameterSource("id", game.id + 1L))
        pieceList = getShips()
        whenever(shipRepo.createAll(any(), any())).thenReturn(pieceList)
    }

    @Test
    fun create_shouldReturnABoard() {
        val board = boards.create(game.id, Harbor.getPieces())
        assertThat(board, `is`(instanceOf(Board::class.java)))
    }

    @Test
    fun create_shouldHaveAListOfUnplacedShips() {
        val expected = Board.build {
            withId { 1 }
            withGameId { 1 }
            withPieces { pieceList }
            withOpponentPieces { emptyList() }
            withMoves { emptyList() }
            withOpponentMoves { emptyList() }
        }

        whenever(shipRepo.createAll(any(), any<Stream<PieceType>>())).thenReturn(pieceList)

        val board = boards.create(game.id, Harbor.getPieces())

        assertThat(board, `is`(equalTo(expected)))
    }

    @Test
    fun get_shouldRetrieveABordFromTheRepository() {
        whenever(shipRepo.getAll(any())).thenReturn(pieceList)
        val board = boards.create(game.id, Harbor.getPieces())
        val actual = boards[board.id].orElse(null)

        assertThat(actual, `is`(board))
    }

    @Test
    fun get_shouldReturnEmptyWhenThereIsNotAGame() {
        val game = boards.create(game.id, Harbor.getPieces())
        val actual = boards[game.id + 1L]

        assertThat(actual, `is`<Optional<out Any>>(Optional.empty()))
    }

    @Test
    fun getAll_shouldGetAllTheBoardsForAGame() {
        whenever(shipRepo.getAll(any())).thenReturn(pieceList)
        boards.create(game.id + 1L, Harbor.getPieces())
        val board1 = boards.create(game.id, Harbor.getPieces())
        val board2 = boards.create(game.id, Harbor.getPieces())

        val boardList = boards.getAll(game.id)
        assertThat(boardList.size, `is`(2))
        assertThat(boardList, containsInAnyOrder(board1, board2))
    }

    @Test
    fun getAll_shouldGetEmptyForAGameThatDoesNotExist() {
        whenever(shipRepo.getAll(any())).thenReturn(pieceList)
        boards.create(game.id + 1L, Harbor.getPieces())
        boards.create(game.id, Harbor.getPieces())
        boards.create(game.id, Harbor.getPieces())

        val boardList = boards.getAll(game.id + 10L)
        assertThat(boardList, `is`(empty()))
    }

    @Test
    fun save_shouldSaveABoard() {
        whenever(shipRepo.getAll(any())).thenReturn(pieceList)
        val board = boards.create(game.id, Harbor.getPieces())
        val expected = board.copy { withWinner { true } }
        boards.save(expected)
        val actual = boards[expected.id]

        assertThat(actual.orElse(null), `is`(equalTo(expected)))
    }

    @Test
    fun save_shouldReturnTheSavedBoard() {
        whenever(shipRepo.getAll(any())).thenReturn(pieceList)
        val board = boards.create(game.id, Harbor.getPieces())
        val expected = board.copy { withWinner { true } }
        val actual = boards.save(expected)

        assertThat(actual.orElse(null), `is`(equalTo(expected)))
    }

    @Test
    fun save_shouldSaveTheShips() {
        val board = boards.create(game.id, Harbor.getPieces())
        boards.save(board)

        verify<PieceRepository>(shipRepo).save(board.pieces)
    }

    @Test
    fun save_shouldSaveTheMoves() {
        val board = boards.create(game.id, Harbor.getPieces())
        boards.save(board)

        verify(moveRepo).save(board.moves)
    }

    private fun getShips(): List<Piece> {
        return Harbor.getPieces().map { ship ->
            Piece.build {
                withBoardId { 1 }
                withType { ship }
            }
        }.collect(Collectors.toList())
    }
}