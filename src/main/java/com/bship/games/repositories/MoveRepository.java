package com.bship.games.repositories;

import com.bship.games.domains.Move;
import com.bship.games.domains.MoveStatus;
import com.bship.games.domains.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.bship.games.util.Util.toIndex;
import static com.bship.games.util.Util.toPoint;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.util.Optional.ofNullable;

@Component
public class MoveRepository {

    private final JdbcTemplate template;
    @Autowired
    public MoveRepository(JdbcTemplate template) {
        this.template = template;
    }

    public Optional<Move> create(Long boardId, Point point, MoveStatus hit) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        Move move = Move.builder()
                .withBoardId(boardId)
                .withPoint(point)
                .withStatus(hit).build();
        template.update(con -> prepareInsertStatement(move, con), holder);

        return getMove(holder.getKey().longValue());
    }

    public Optional<List<Move>> getAll(Long boardId) {
        return ofNullable(template.query("SELECT * FROM moves WHERE move_board_id = ?",
                new Object[]{boardId}, moveRowMapper)).filter(Objects::nonNull);
    }

    private Optional<Move> getMove(Long id) {
        return ofNullable(template.queryForObject("SELECT * FROM moves WHERE id = ?",
                new Object[]{id}, moveRowMapper));
    }

    private PreparedStatement prepareInsertStatement(Move move, Connection con) throws SQLException {
        PreparedStatement statement = con.prepareStatement(
                "INSERT INTO moves(move_board_id, point, status) VALUE(?, ?, ?)",
                RETURN_GENERATED_KEYS);
        statement.setLong(1, move.getBoardId());
        statement.setInt(2, toIndex(move.getPoint()));
        statement.setString(3, move.getStatus().name());
        return statement;
    }

    private RowMapper<Move> moveRowMapper = (rs, rowNum) -> Move.builder()
            .withStatus(MoveStatus.valueOf(rs.getString("status")))
            .withId(rs.getLong("id"))
            .withBoardId(rs.getLong("move_board_id"))
            .withPoint(toPoint(rs.getInt("point"))).build();
}
