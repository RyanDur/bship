package com.bship.games.endpoints.errors.validations

import com.bship.games.endpoints.cabinet.entity.Piece
import com.bship.games.endpoints.cabinet.entity.Point
import com.bship.games.logic.definitions.Direction.*
import com.bship.games.logic.definitions.Harbor.AIRCRAFT_CARRIER
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test

class PlacementCheckValidationTest {

    private var validation: PlacementCheckValidation? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        validation = PlacementCheckValidation()
    }

    @Test
    @Throws(Exception::class)
    fun isValid_shouldAllowAValidShip() {
        val piece = Piece.build {
            withType { AIRCRAFT_CARRIER }
            withPlacement { Point(0, 0) }
            withOrientation { DOWN }
        }

        assertThat(validation!!.isValid(piece, null), `is`(true))
    }

    @Test
    @Throws(Exception::class)
    fun isValid_shouldNotAllowAShipWithoutAType() {
        val piece = Piece.build {
            withPlacement { Point(0, 0) }
            withOrientation { DOWN }
        }

        assertThat(validation!!.isValid(piece, null), `is`(false))
    }

    @Test
    @Throws(Exception::class)
    fun isValid_shouldNotAllowAnInvalidRange() {
        val piece = Piece.build {
            withType { AIRCRAFT_CARRIER }
            withPlacement { Point(0, 0) }
            withOrientation { UP }
        }

        assertThat(validation!!.isValid(piece, null), `is`(false))
    }

    @Test
    @Throws(Exception::class)
    fun isValid_shouldNotAllowAPieceWithoutAnOrientation() {
        val piece = Piece.build {
            withType { AIRCRAFT_CARRIER }
            withPlacement { Point(0, 0) }
        }

        assertThat(validation!!.isValid(piece, null), `is`(false))
    }

    @Test
    @Throws(Exception::class)
    fun isValid_shouldNotAllowAPieceWithANoneOrientation() {
        val piece = Piece.build {
            withType { AIRCRAFT_CARRIER }
            withPlacement { Point(0, 0) }
            withOrientation { NONE }
        }

        assertThat(validation!!.isValid(piece, null), `is`(false))
    }
}