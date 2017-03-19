package com.bship.games.repositories;

import com.bship.games.domains.Move;
import com.bship.games.domains.MoveStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import static com.bship.games.util.Util.toIndex;
import static com.bship.games.util.Util.toPoint;
import static java.util.stream.Collectors.toList;
import static org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils.createBatch;

@Component
public class MoveRepository {

    private static final String INSERT_MOVES = "INSERT INTO moves(move_board_id, point, status) VALUES (:board_id, :point, :status)";
    private static final String SELECT_ALL_MOVES_FOR_BOARD = "SELECT * FROM moves WHERE move_board_id = :board_id";
    private static final String SELECT_All_OPPONENTS_MOVES = "SELECT m.* FROM moves m JOIN boards b ON m.move_board_id = b.id WHERE b.game_id = :game_id AND m.move_board_id <> :board_id;";
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public MoveRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public void save(List<Move> moves) {
        template.batchUpdate(INSERT_MOVES, createBatch(getNewMoveBatch(moves)));
    }

    public List<Move> getAll(BigInteger boardId) {
        return template.query(SELECT_ALL_MOVES_FOR_BOARD,
                new MapSqlParameterSource("board_id", boardId),
                buildMove);
    }

    public List<Move> getAllOpponents(BigInteger gameId, BigInteger boardId) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("game_id", gameId);
        source.addValue("board_id", boardId);

        return template.query(SELECT_All_OPPONENTS_MOVES, source, buildMove);
    }

    private HashMap[] getNewMoveBatch(final List<Move> moves) {
        return moves.stream().map(move ->
                new HashMap<String, Object>() {{
                    put("board_id", move.getBoardId());
                    put("point", toIndex(move.getPoint()));
                    put("status", move.getStatus().name());
                }})
                .collect(toList()).toArray(new HashMap[0]);
    }

    private RowMapper<Move> buildMove = (rs, rowNum) -> Move.builder()
            .withStatus(MoveStatus.valueOf(rs.getString("status")))
            .withId(BigInteger.valueOf(rs.getLong("id")))
            .withBoardId(BigInteger.valueOf(rs.getLong("move_board_id")))
            .withPoint(toPoint(rs.getInt("point"))).build();
}
