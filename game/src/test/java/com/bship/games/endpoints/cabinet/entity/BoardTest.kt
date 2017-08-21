package com.bship.games.endpoints.cabinet.entity

import com.bship.games.endpoints.cabinet.entity.Piece.Companion.build
import com.bship.games.logic.definitions.Direction.DOWN
import com.bship.games.logic.definitions.Harbor
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class BoardTest {

    private lateinit var battleship: Piece
    private lateinit var aircraftCarrier: Piece

    @Before
    fun setup() {
        val start = Point(0, 0)
        val type = Harbor.BATTLESHIP
        battleship = build {
            withId { 1 }
            withType { type }
            withPlacement { start }
            withOrientation { DOWN }
        }

        val start1 = Point(1, 0)
        val type1 = Harbor.AIRCRAFT_CARRIER
        aircraftCarrier = build {
            withId { 1 }
            withType { type1 }
            withPlacement { start1 }
            withOrientation { DOWN }
        }
    }

    @Test
    fun addShip_shouldBeAbleToAddAShipToTheBoard() {
        val board = Board.build {
            withPieces { listOf(battleship) }
        }

        assertThat(board.pieces).contains(battleship)
    }

    @Test
    fun addShip_shouldBeAbleToAddMultipleShipsToTheBoard() {
        val board = Board.build { withPieces { listOf(battleship) } }
        val newBoard = board.copy { withPieces { board.pieces + aircraftCarrier } }

        assertThat(newBoard.pieces).contains(battleship, aircraftCarrier)
    }
}