package com.bship.games.repositories;

import com.bship.games.domains.Harbor;
import com.bship.games.domains.Ship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.bship.games.util.Util.toIndex;
import static com.bship.games.util.Util.toPoint;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

@Repository
public class ShipRepository {

    private static final String INSERT_NEW_SHIP = "INSERT INTO ships(type, start, end, ship_board_id) VALUE(?,?,?,?)";
    private static final String UPDATE_SHIP_SET_SUNK = "UPDATE ships SET sunk = ? WHERE id = ?";
    private static final String GET_SHIP = "SELECT * FROM ships WHERE id = ?";
    private static final String GET_SHIPS_FOR_BOARD = "SELECT * FROM ships WHERE ship_board_id = ?";
    private final JdbcTemplate template;

    @Autowired
    public ShipRepository(JdbcTemplate template) {
        this.template = template;
    }

    public Ship create(Ship ship, Long boardId) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = con ->
                prepareInsertStatement(ship.copy().withBoardId(boardId).build(), con);
        template.update(preparedStatementCreator, holder);
        return ship.copy().withBoardId(boardId).withId(holder.getKey().longValue()).build();
    }

    public List<Ship> getAll(long boardId) {
        return template.query(GET_SHIPS_FOR_BOARD, new Object[]{boardId}, shipRowMapper);
    }

    public Ship update(Ship ship) {
        template.update(con -> prepareUpdateStatement(ship, con));
        return template.queryForObject(GET_SHIP, new Object[]{ship.getId()}, shipRowMapper);
    }

    private PreparedStatement prepareInsertStatement(Ship ship, Connection con) throws SQLException {
        PreparedStatement statement = con.prepareStatement(INSERT_NEW_SHIP, RETURN_GENERATED_KEYS);
        statement.setString(1, ship.getType().name());
        statement.setInt(2, toIndex(ship.getStart()));
        statement.setInt(3, toIndex(ship.getEnd()));
        statement.setLong(4, ship.getBoardId());
        return statement;
    }

    private PreparedStatement prepareUpdateStatement(Ship ship, Connection con) throws SQLException {
        PreparedStatement statement = con.prepareStatement(UPDATE_SHIP_SET_SUNK);
        statement.setBoolean(1, ship.isSunk());
        statement.setLong(2, ship.getId());
        return statement;
    }

    private RowMapper<Ship> shipRowMapper = (rs, rowNum) -> Ship.builder()
            .withId(rs.getLong("id"))
            .withType(Harbor.valueOf(rs.getString("type")))
            .withStart(toPoint(rs.getInt("start")))
            .withEnd(toPoint(rs.getInt("end")))
            .withSunk(rs.getBoolean("sunk"))
            .withBoardId(rs.getLong("ship_board_id")).build();
}
