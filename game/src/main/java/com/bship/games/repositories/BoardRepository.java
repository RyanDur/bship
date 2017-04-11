package com.bship.games.repositories;

import com.bship.games.domains.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Repository
public class BoardRepository implements SQL {

    private NamedParameterJdbcTemplate template;
    private final PieceRepository pieces;
    private final MoveRepository moves;

    @Autowired
    public BoardRepository(NamedParameterJdbcTemplate template,
                           PieceRepository ships, MoveRepository moves) {
        this.template = template;
        this.pieces = ships;
        this.moves = moves;
    }

    @Transactional
    public Board create(Long gameId) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        template.update(join(SEP, INSERT_INTO, BOARDS_FOR_GAME, VALUE_ID),
                new MapSqlParameterSource("id", gameId), holder);

        long id = holder.getKey().longValue();

        return Board.builder()
                .withId(id)
                .withGameId(gameId)
                .withPieces(pieces.createAll(id))
                .withOpponentPieces(emptyList())
                .withMoves(emptyList())
                .withOpponentMoves(emptyList())
                .build();
    }

    @Transactional(readOnly = true)
    public Optional<Board> get(Long id) {
        return template.query(join(SEP, SELECT_ALL, FROM_BOARDS, WHERE, ID),
                new MapSqlParameterSource("id", id),
                buildBoard())
                .stream()
                .findFirst();
    }

    public List<Board> getAll(Long gameId) {
        return template.query(join(SEP, SELECT_ALL, FROM_BOARDS, WHERE, GAME_ID),
                new MapSqlParameterSource("game_id", gameId),
                buildBoard());
    }

    public Optional<Board> save(Board board) {
        pieces.save(board.getPieces());
        moves.save(board.getMoves());

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("winner", board.isWinner());
        source.addValue("id", board.getId());
        template.update(join(SEP, UPDATE_BOARDS, SET, WINNER, WHERE, ID), source);

        return get(board.getId());
    }

    private RowMapper<Board> buildBoard() {
        return (rs, rowNum) -> Board.builder()
                .withId(rs.getLong("id"))
                .withGameId(rs.getLong("game_id"))
                .withWinner(rs.getBoolean("winner"))
                .withPieces(pieces.getAll(rs.getLong("id")))
                .withOpponentPieces(pieces.getAllOpponents(
                        rs.getLong("game_id"),
                        rs.getLong("id")
                ))
                .withMoves(moves.getAll(rs.getLong("id")))
                .withOpponentMoves(moves.getAllOpponents(
                        rs.getLong("game_id"),
                        rs.getLong("id")
                )).build();
    }
}