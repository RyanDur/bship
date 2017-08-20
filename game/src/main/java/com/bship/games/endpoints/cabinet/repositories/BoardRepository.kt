package com.bship.games.endpoints.cabinet.repositories

import com.bship.games.endpoints.cabinet.entity.Board
import com.bship.games.endpoints.cabinet.repositories.SQL.*
import com.bship.games.logic.definitions.PieceType
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.ResultSet
import java.util.*
import java.util.stream.Stream

@Repository
open class BoardRepository(private val template: NamedParameterJdbcTemplate,
            private val pieceRepo: PieceRepository,
            private val moveRepo: MoveRepository) : SQL {

    @Transactional
    open fun create(gameId: Long, pieceTypes: Stream<PieceType>): Board {
        return GeneratedKeyHolder().also {
            template.update(join(SEP, INSERT_INTO, BOARDS_FOR_GAME),
                    MapSqlParameterSource("game_id", gameId), it)
        }.let {
            Board.build {
                withId { it.key.toLong() }
                withGameId { gameId }
                withPieces { pieceRepo.createAll(it.key.toLong(), pieceTypes) }
            }
        }
    }

    @Transactional(readOnly = true) open
    operator fun get(id: Long?): Optional<Board> {
        return template.query(join(SEP, SELECT_ALL, FROM_BOARDS, WHERE, ID),
                MapSqlParameterSource("id", id),
                buildBoard())
                .stream()
                .findFirst()
    }

    open fun getAll(gameId: Long?): List<Board> {
        return template.query(join(SEP, SELECT_ALL, FROM_BOARDS, WHERE, GAME_ID),
                MapSqlParameterSource("game_id", gameId),
                buildBoard())
    }

    open fun save(board: Board): Optional<Board> {
        pieceRepo.save(board.pieces)
        moveRepo.save(board.moves)

        val source = MapSqlParameterSource()
        source.addValue("winner", board.winner)
        source.addValue("id", board.id)
        template.update(join(SEP, UPDATE_BOARDS, SET, WINNER, WHERE, ID), source)

        return get(board.id)
    }

    private fun buildBoard(): RowMapper<Board> {
        return RowMapper({ rs: ResultSet, _: Int ->
            Board.build {
                withId { rs.getLong("id") }
                withGameId { rs.getLong("game_id") }
                withWinner { rs.getBoolean("winner") }
                withPieces { pieceRepo.getAll(rs.getLong("id")) }
                withOpponentPieces {
                    pieceRepo.getAllOpponents(
                            rs.getLong("game_id"),
                            rs.getLong("id"))
                }
                withMoves { moveRepo.getAll(rs.getLong("id")) }
                withOpponentMoves {
                    moveRepo.getAllOpponents(
                            rs.getLong("game_id"),
                            rs.getLong("id"))
                }
            }
        })
    }
}