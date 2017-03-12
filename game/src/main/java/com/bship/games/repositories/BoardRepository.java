package com.bship.games.repositories;

import com.bship.games.domains.Board;
import com.bship.games.domains.Ship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

import static com.bship.games.domains.Harbor.valueOf;
import static com.bship.games.util.Util.toPoint;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

@Repository
public class BoardRepository {
    private JdbcTemplate template;

    @Autowired
    public BoardRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Transactional
    public Board create(BigInteger gameId) {
        return save.apply(gameId);
    }

    @Transactional(readOnly = true)
    public Board get(Long id) {
        return getBoard(id).copy().withShips(getShips(id)).build();
    }

    private List<Ship> getShips(Long id) {
        return template.query("SELECT * FROM ships WHERE ship_board_id = ?", new Object[]{id}, shipRowMapper);
    }

    private Board getBoard(Long id) {
        return template.queryForObject("SELECT * FROM boards WHERE id = ?", new Object[]{id}, boardRowMapper);
    }

    private Function<BigInteger, Board> save = (gameId) -> Board.builder()
            .withId(getGeneratedId(gameId))
            .withGameId(gameId)
            .build();

    private BigInteger getGeneratedId(BigInteger gameId) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        template.update(con -> prepareInsertStatement(gameId, con), holder);
        return BigInteger.valueOf(holder.getKey().longValue());
    }

    private PreparedStatement prepareInsertStatement(BigInteger gameId, Connection con) throws SQLException {
        PreparedStatement statement = con.prepareStatement("INSERT INTO boards(game_id) VALUE(?)",
                RETURN_GENERATED_KEYS);
        statement.setInt(1, gameId.intValue());
        return statement;
    }

    private RowMapper<Board> boardRowMapper = (rs, rowNum) -> Board.builder()
            .withId(BigInteger.valueOf(rs.getLong("id")))
            .withGameId(BigInteger.valueOf(rs.getLong("game_id"))).build();

    private RowMapper<Ship> shipRowMapper = (rs, rowNum) -> Ship.builder()
            .withId(BigInteger.valueOf(rs.getLong("id")))
            .withBoardId(BigInteger.valueOf(rs.getLong("ship_board_id")))
            .withStart(toPoint(rs.getInt("start")))
            .withEnd(toPoint(rs.getInt("end")))
            .withType(valueOf(rs.getString("type")))
            .build();

    public Board save(Board board) {
        return null;
    }

    public List<Board> getAll(BigInteger id) {
        return null;
    }
}