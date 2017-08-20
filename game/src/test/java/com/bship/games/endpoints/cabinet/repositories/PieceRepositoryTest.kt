package com.bship.games.endpoints.cabinet.repositories

import com.bship.DBHelper
import com.bship.games.endpoints.cabinet.entity.Piece
import com.bship.games.endpoints.cabinet.entity.Point
import com.bship.games.logic.definitions.Direction.DOWN
import com.bship.games.logic.definitions.Direction.NONE
import com.bship.games.logic.definitions.Harbor
import com.bship.games.logic.definitions.Harbor.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Test
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import java.util.*
import java.util.Optional.of
import java.util.stream.Collectors
import java.util.stream.Collectors.toList

class PieceRepositoryTest {

    private lateinit var template: NamedParameterJdbcTemplate
    private lateinit var pieces: PieceRepository

    @Before
    fun setup() {
        template = NamedParameterJdbcTemplate(DBHelper.reset())
        template.update("INSERT INTO games(id, name) VALUE (default, 'BATTLESHIP')", HashMap<String, Any>())
        template.update("INSERT INTO boards(game_id) VALUE (1)", HashMap<String, Any>())
        template.update("INSERT INTO boards(game_id) VALUE (1)", HashMap<String, Any>())
        pieces = PieceRepository(template)
    }

    @Test
    fun createAll_shouldCreateAllTheShips() {
        val expected = getPieces()

        val actual = pieces.createAll(1L, Harbor.getPieces())

        assertThat(actual.size, `is`(expected.size))
        assertThat(actual, `is`(expected))
    }

    @Test
    fun getAll_shouldRetrieveAllTheShipsForABoard() {
        val expected = pieces.createAll(1L, Harbor.getPieces())
        val actual = pieces.getAll(1L)

        assertThat(actual, `is`(equalTo(expected)))
    }

    @Test
    fun getAll_shouldReturnAnEmptyListIfNoShips() {
        val actual = pieces.getAll(10L)

        assertThat(actual, `is`(empty()))
    }

    @Test
    fun save_shouldSaveShips() {
        pieces.createAll(1L, Harbor.getPieces())
        val opponents = pieces.createAll(2L, Harbor.getPieces())
                .map(take(DESTROYER))
        pieces.save(opponents)
        pieces.save(opponents)
        val actual = pieces.getAll(2L)

        assertThat(actual, `is`(equalTo(opponents)))
    }

    @Test
    fun save_shouldReturnEmptyIfNoShips() {
        pieces.createAll(1L, Harbor.getPieces())

        val actual = pieces.getAll(2L)

        assertThat(actual, `is`(empty()))
    }

    @Test
    fun save_shouldSaveAShip() {
        val all = pieces.createAll(1L, Harbor.getPieces())
        val piece = all[0].copy(
                placement = Point(0, 0),
                orientation = DOWN,
                boardId = 1
        )

        pieces.save(piece)
        val actual = pieces.getAll(1L).stream()
                .filter { (_, id) -> id == 1L }
                .findFirst().orElse(null)

        assertThat(actual, `is`(equalTo(piece)))
    }

    @Test
    fun getAllOpponents_shouldReturnAllTheOpponentsSunkShip() {
        pieces.createAll(1L, Harbor.getPieces())
        val opponents = pieces.createAll(2L, Harbor.getPieces())
                .map(take(DESTROYER))

        pieces.save(opponents)
        val shipsAllOpponents = pieces.getAllOpponents(1L, 1L)

        assertThat(shipsAllOpponents, `is`(equalTo(opponents.stream()
                .filter({ it.taken }).collect(toList<Piece>()))))
    }

    @Test
    fun getAllOpponents_shouldReturnAllTheOpponentsSunkShipFromSpecificGame() {
        template.update("INSERT INTO games(id, name) VALUE (default, 'BATTLESHIP')", HashMap<String, Any>())
        template.update("INSERT INTO boards(game_id) VALUE (2)", HashMap<String, Any>())
        template.update("INSERT INTO boards(game_id) VALUE (2)", HashMap<String, Any>())

        template.update("INSERT INTO games(id, name) VALUE (default, 'BATTLESHIP')", HashMap<String, Any>())
        template.update("INSERT INTO boards(game_id) VALUE (3)", HashMap<String, Any>())
        template.update("INSERT INTO boards(game_id) VALUE (3)", HashMap<String, Any>())

        pieces.createAll(1L, Harbor.getPieces())
        pieces.createAll(2L, Harbor.getPieces())

        val ships3 = pieces.createAll(3L, Harbor.getPieces())
        pieces.createAll(4L, Harbor.getPieces())

        pieces.createAll(5L, Harbor.getPieces())
        val ships6 = pieces.createAll(6L, Harbor.getPieces())

        val opponents = ships3.map(take(DESTROYER))

        pieces.save(opponents)

        val opponents3 = opponents.map(take(AIRCRAFT_CARRIER))

        pieces.save(opponents3)

        val opponents6 = ships6.map(take(BATTLESHIP))

        pieces.save(opponents6)


        val shipsAllOpponents = pieces.getAllOpponents(2L, 4L)

        assertThat(shipsAllOpponents, `is`(equalTo(opponents3.filter { it.taken })))
    }

    private fun take(pieceType: Harbor): (Piece) -> Piece {
        return { ship ->
            of(ship).filter { (type) -> type == pieceType }
                    .map { it.copy(taken = true) }
                    .orElse(ship)
        }
    }

    private fun getPieces(): List<Piece> {
        return Harbor.getPieces().map { ship ->
            Piece.build {
                withType { ship }
                withId { 1L + (ship as Harbor).ordinal }
                withPlacement { Point() }
                withOrientation { NONE }
                withBoardId { 1L }
            }
        }.collect(Collectors.toList())
    }
}