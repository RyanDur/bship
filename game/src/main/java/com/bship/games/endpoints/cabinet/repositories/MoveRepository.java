package com.bship.games.endpoints.cabinet.repositories;

import com.bship.games.endpoints.cabinet.entity.Move;
import com.bship.games.logic.rules.MoveStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import static com.bship.games.util.Util.toIndex;
import static com.bship.games.util.Util.toPoint;
import static java.util.Objects.isNull;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;

@Component
public class MoveRepository implements SQL {

    private final NamedParameterJdbcTemplate template;

    @Autowired
    public MoveRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public void save(List<Move> moves) {
        of(getNewMoves(moves))
                .map(movesBatch)
                .map(batch -> template.batchUpdate(join(SEP, INSERT_INTO, MOVES), batch));
    }

    public List<Move> getAll(Long boardId) {
        return template.query(join(SEP, SELECT_ALL, MOVES_FOR_BOARD),
                new MapSqlParameterSource("board_id", boardId),
                buildMove);
    }

    public List<Move> getAllOpponents(Long gameId, Long boardId) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("game_id", gameId);
        source.addValue("board_id", boardId);

        return template.query(SELECT_All_OPPONENTS_MOVES, source, buildMove);
    }

    private List<Move> getNewMoves(List<Move> moves) {
        return moves.stream().filter(move -> isNull(move.getId())).collect(toList());
    }

    private RowMapper<Move> buildMove = (rs, rowNum) -> Move.builder()
            .withStatus(MoveStatus.valueOf(rs.getString("status")))
            .withId(rs.getLong("id"))
            .withBoardId(rs.getLong("move_board_id"))
            .withPoint(toPoint(rs.getInt("point"))).build();

    private Function<List<Move>, SqlParameterSource[]> movesBatch = createBatch(move ->
            new HashMap<String, Object>() {{
                put("board_id", move.getBoardId());
                put("point", toIndex(move.getPoint()));
                put("status", move.getStatus().name());
            }});

}
