package com.bship.games.repositories;

import com.bship.games.domains.Move;
import com.bship.games.domains.MoveStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

import static com.bship.games.util.Util.toIndex;
import static com.bship.games.util.Util.toPoint;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

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
        template.batchUpdate(INSERT_MOVES, createBatch(getNewMoves(moves)));
    }

    public List<Move> getAll(Long boardId) {
        return template.query(SELECT_ALL_MOVES_FOR_BOARD,
                new MapSqlParameterSource("board_id", boardId),
                buildMove);
    }

    public List<Move> getAllOpponents(Long gameId, Long boardId) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("game_id", gameId);
        source.addValue("board_id", boardId);

        return template.query(SELECT_All_OPPONENTS_MOVES, source, buildMove);
    }

    public static SqlParameterSource[] createBatch(List<Move> moves) {
        return moves.stream().map(move ->
                new HashMap<String, Object>() {{
                    put("board_id", move.getBoardId());
                    put("point", toIndex(move.getPoint()));
                    put("status", move.getStatus().name());
                }}).map(MapSqlParameterSource::new)
                .collect(toList())
                .toArray(new MapSqlParameterSource[0]);
    }

    private List<Move> getNewMoves(List<Move> moves) {
        return moves.stream().filter(move -> isNull(move.getId())).collect(toList());
    }

    private RowMapper<Move> buildMove = (rs, rowNum) -> Move.builder()
            .withStatus(MoveStatus.valueOf(rs.getString("status")))
            .withId(rs.getLong("id"))
            .withBoardId(rs.getLong("move_board_id"))
            .withPoint(toPoint(rs.getInt("point"))).build();
}
