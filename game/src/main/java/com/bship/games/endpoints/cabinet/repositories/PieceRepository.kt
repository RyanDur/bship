package com.bship.games.endpoints.cabinet.repositories

import com.bship.games.endpoints.cabinet.entity.Piece
import com.bship.games.endpoints.cabinet.entity.Point
import com.bship.games.endpoints.cabinet.repositories.SQL.*
import com.bship.games.logic.definitions.Orientation
import com.bship.games.logic.definitions.PieceType
import com.bship.games.util.Util
import com.bship.games.util.Util.Companion.toIndex
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.stereotype.Repository
import java.sql.SQLException
import java.util.*
import java.util.Optional.of
import java.util.Optional.ofNullable
import java.util.function.Function
import java.util.stream.Stream

@Repository
open class PieceRepository(private val template: NamedParameterJdbcTemplate) : SQL {

    open fun createAll(boardId: Long, pieceTypes: Stream<PieceType>): List<Piece> {
        of(pieceTypes).map(batchNewPieces(boardId))
                .map { batch -> template.batchUpdate(join(SEP, INSERT_INTO, PIECES), batch) }

        return getAll(boardId)
    }

    open fun save(pieces: List<Piece>) {
        val SAVE_PIECES = join(SEP, UPDATE_PIECES, SET, join(COMMA, TAKEN, PLACEMENT, ORIENTATION), WHERE, ID)
        of(pieces.stream()).map(batchUpdatePieces).map { batch -> template.batchUpdate(SAVE_PIECES, batch) }
    }

    open fun getAll(boardId: Long?): List<Piece> {
        return template.query(join(SEP, SELECT_ALL, PIECES_FOR_BOARD),
                MapSqlParameterSource("piece_board_id", boardId),
                buildPiece)
    }

    open fun getAllOpponents(gameId: Long?, boardId: Long?): List<Piece> {
        val source = MapSqlParameterSource()
        source.addValue("game_id", gameId)
        source.addValue("piece_board_id", boardId)
        return template.query(SELECT_OPPONENTS_TAKEN_PIECES, source, buildPiece)
    }

    open fun save(piece: Piece) {
        val source = MapSqlParameterSource()
        source.addValue("id", piece.id)
        source.addValue("placement", toIndex(piece.placement))
        source.addValue("orientation", piece.orientation.name())

        template.update(join(SEP, UPDATE_PIECES, SET, join(COMMA, PLACEMENT, ORIENTATION), WHERE, ID), source)
    }

    private val buildPiece = RowMapper({ rs, _ ->
        Piece.build {
            withId { rs.getLong("id") }
            withType { PieceType.create(rs.getString("type")) }
            withPlacement { getPoint(rs.getString("placement")) }
            withOrientation { Orientation.create(rs.getString("orientation")) }
            withTaken { rs.getBoolean("taken") }
            withBoardId { rs.getLong("piece_board_id") }
        }
    })

    @Throws(SQLException::class)
    private fun getPoint(point: String?): Point {
        return ofNullable(point)
                .map { Integer.valueOf(it) }
                .map { Util.toPoint(it) }
                .orElse(Point())
    }

    private fun batchNewPieces(boardId: Long): Function<Stream<PieceType>, Array<SqlParameterSource>> {
        return createBatch({ piece ->
            object : HashMap<String, Any>() {
                init {
                    put("type", piece.name)
                    put("board_id", boardId)
                }
            }
        })
    }

    private val batchUpdatePieces = createBatch<Piece> {
        object : HashMap<String, Any?>() {
            init {
                put("id", it.id)
                put("taken", it.taken)
                put("placement", toIndex(it.placement))
                put("orientation", it.orientation.name())
            }
        }
    }
}
