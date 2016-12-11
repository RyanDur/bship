package com.bship.games.repositories;

import com.bship.games.domains.Move;
import com.bship.games.domains.MoveStatus;
import com.bship.games.domains.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.bship.games.util.Util.toIndex;
import static com.bship.games.util.Util.toPoint;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

@Component
public class MoveRepository {

    private final JdbcTemplate template;

    @Autowired
    public MoveRepository(JdbcTemplate template) {
        this.template = template;
    }

    public Move create(long boardId, Point point) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        Move move = Move.builder().withBoardId(boardId).withPoint(point).build();
        template.update(con -> prepareInsertStatement(move, con), holder);
        return getMove(holder.getKey().longValue());
    }

    private Move getMove(Long id) {
        return template.queryForObject("SELECT * FROM moves WHERE id = ?",
                new Object[]{id}, (rs, rowNum) -> Move.builder()
                        .withStatus(MoveStatus.valueOf(rs.getString("status")))
                        .withId(rs.getLong("id"))
                        .withBoardId(rs.getLong("move_board_id"))
                        .withPoint(toPoint(rs.getInt("point"))).build());
    }

    private PreparedStatement prepareInsertStatement(Move move, Connection con) throws SQLException {
        PreparedStatement statement = con.prepareStatement(
                "INSERT INTO moves(move_board_id, point) VALUE(?, ?)",
                RETURN_GENERATED_KEYS);
        statement.setLong(1, move.getBoardId());
        statement.setInt(2, toIndex(move.getPoint()));
        return statement;
    }
}
