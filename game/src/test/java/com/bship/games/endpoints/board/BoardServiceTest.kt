package com.bship.games.endpoints.board

import com.bship.games.endpoints.board.errors.exceptions.BoardExistence
import com.bship.games.endpoints.board.errors.exceptions.BoardValidation
import com.bship.games.endpoints.board.errors.exceptions.ShipCollisionCheck
import com.bship.games.endpoints.cabinet.entity.Board
import com.bship.games.endpoints.cabinet.entity.Piece
import com.bship.games.endpoints.cabinet.entity.Point
import com.bship.games.endpoints.cabinet.repositories.BoardRepository
import com.bship.games.logic.GameLogic
import com.bship.games.logic.definitions.Direction.NONE
import com.bship.games.logic.definitions.Direction.RIGHT
import com.bship.games.logic.definitions.Harbor
import com.bship.games.logic.definitions.Harbor.AIRCRAFT_CARRIER
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.equalTo
import org.junit.Before
import org.junit.Test
import java.util.*
import java.util.Collections.emptyList
import java.util.Optional.of
import java.util.function.Predicate
import java.util.stream.Collectors.toList

class BoardServiceTest {

    private lateinit var service: BoardService
    private var logic: GameLogic = mock()
    private var repository: BoardRepository = mock()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        service = BoardService(repository, logic)
    }

    @Test
    fun placeShip_shouldPlaceAShipOnTheBoard() {
        val boardId = 1L
        val board = Board.build {
            withId { boardId }
            withPieces { ships }
        }
        val piece = Piece.build {
            withPlacement { Point(3, 2) }
            withOrientation { RIGHT }
            withBoardId { boardId }
            withType { AIRCRAFT_CARRIER }
        }

        val expected = board.copy { withPieces { board.pieces + piece } }
        val pieces = listOf(piece)

        whenever(repository[boardId]).thenReturn(of(board))
        whenever(logic.placementCheck(pieces)).thenReturn(Predicate { _ -> true })
        whenever(repository.save(any())).thenReturn(of(expected))

        val actual = service.placePiece(boardId, pieces)

        verify(repository).save(expected)
        assertThat(actual, `is`(equalTo(expected)))
    }

    @Test
    fun placeShip_shouldThrowABoardExistenceIfThereIsNoBoard() {
        val boardId = 1L
        val board = Board.build {}
        val piece = Piece.build {}
        val expected = board.copy { withPieces { board.pieces + piece } }

        whenever(repository[boardId]).thenReturn(Optional.empty())
        whenever(logic.placementCheck(listOf(piece))).thenReturn(Predicate { _ -> true })
        whenever(repository.save(expected)).thenReturn(of(expected))

        assertThatThrownBy { service.placePiece(boardId, listOf(piece)) }
                .isInstanceOf(BoardExistence::class.java)
                .hasMessage("Board Does Not Exist!")
    }

    @Test
    fun placeShip_shouldThrowABoardExistenceIfThereIsNoBoardAfterSaving() {
        val boardId = 1L
        val board = Board.build { withPieces { emptyList() } }
        val piece = Piece.build {}

        whenever(repository[boardId]).thenReturn(of(board))
        whenever(logic.placementCheck(listOf(piece))).thenReturn(Predicate { _ -> true })
        whenever(repository.save(any())).thenReturn(Optional.empty())

        assertThatThrownBy { service.placePiece(boardId, listOf(piece)) }
                .isInstanceOf(BoardExistence::class.java)
                .hasMessage("Board Does Not Exist!")
    }

    @Test
    fun placeShip_shouldThrowABoardValidationIfThereIsNoBoardAfterCheck() {
        val boardId = 1L
        val board = Board.build {}
        val piece = Piece.build {}
        val expected = board.copy { withPieces { board.pieces + piece } }

        whenever(repository[boardId]).thenReturn(of(board))
        whenever(logic.placementCheck(any())).thenThrow(ShipCollisionCheck())
        whenever(repository.save(expected)).thenReturn(Optional.empty())

        assertThatThrownBy { service.placePiece(boardId, listOf(piece)) }
                .isInstanceOf(BoardValidation::class.java)
    }

    private val ships: List<Piece>
        get() = Harbor.getPieces().map { ship ->
            Piece.build {
                withType { ship }
                withPlacement { Point() }
                withOrientation { NONE }
                withBoardId { 1L }
            }
        }.collect(toList())
}