package com.bship.games.repositories;

import com.bship.games.domains.Board;
import com.bship.games.domains.Ship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Repository
public class BoardRepository {
    private NamedParameterJdbcTemplate template;
    private final ShipRepository ships;
    private final MoveRepository moves;

    @Autowired
    public BoardRepository(NamedParameterJdbcTemplate template,
                           ShipRepository ships, MoveRepository moves) {
        this.template = template;
        this.ships = ships;
        this.moves = moves;
    }

    @Transactional
    public Board create(BigInteger gameId) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        template.update("INSERT INTO boards(game_id) VALUE(:id)",
                new MapSqlParameterSource("id", gameId), holder);

        BigInteger id = BigInteger.valueOf(holder.getKey().longValue());

        return Board.builder()
                .withId(id)
                .withGameId(gameId)
                .withShips(ships.createAll(id))
                .withOpponentShips(emptyList())
                .withMoves(emptyList())
                .withOpponentMoves(emptyList())
                .build();
    }

    @Transactional(readOnly = true)
    public Optional<Board> get(BigInteger id) {
        return template.query("SELECT * FROM boards WHERE id = :id",
                new MapSqlParameterSource("id", id),
                buildBoard()
        ).stream().findFirst();
    }

    public List<Board> getAll(BigInteger gameId) {
        return template.query("SELECT * FROM boards WHERE game_id = :game_id",
                new MapSqlParameterSource("game_id", gameId),
                buildBoard());
    }

    public Optional<Board> save(Board board) {
        ships.save(board.getShips());
        moves.save(board.getMoves());

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("winner", board.isWinner());
        source.addValue("id", board.getId());
        template.update("UPDATE boards SET winner = :winner WHERE id = :id", source);

        return get(board.getId());
    }

    public Optional<Board> save(Ship ship, BigInteger boardId) {
        ships.save(ship.copy().withBoardId(boardId).build());
        return get(boardId);
    }

    private RowMapper<Board> buildBoard() {
        return (rs, rowNum) -> Board.builder()
                .withId(BigInteger.valueOf(rs.getLong("id")))
                .withGameId(BigInteger.valueOf(rs.getLong("game_id")))
                .withWinner(rs.getBoolean("winner"))
                .withShips(ships.getAll(BigInteger.valueOf(rs.getLong("id"))))
                .withOpponentShips(ships.getAllOpponents(
                        BigInteger.valueOf(rs.getLong("game_id")),
                        BigInteger.valueOf(rs.getLong("id"))
                ))
                .withMoves(moves.getAll(BigInteger.valueOf(rs.getLong("id"))))
                .withOpponentMoves(moves.getAllOpponents(
                        BigInteger.valueOf(rs.getLong("game_id")),
                        BigInteger.valueOf(rs.getLong("id"))
                )).build();
    }
}