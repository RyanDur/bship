package com.bship.games.repositories;

import com.bship.games.domains.Harbor;
import com.bship.games.domains.Move;
import com.bship.games.domains.Ship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
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

    public Optional<Ship> create(Ship ship, BigInteger boardId) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = con ->
                prepareInsertStatement(ship.copy().withBoardId(boardId).build(), con);
        template.update(preparedStatementCreator, holder);
        return ofNullable(get(BigInteger.valueOf(holder.getKey().longValue())));
    }

    public List<Ship> getAll(BigInteger boardId) {
        return template.query(GET_SHIPS_FOR_BOARD, new Object[]{boardId}, shipRowMapper);
    }

    public Optional<Ship> update(Ship ship) {
        template.update(con -> prepareUpdateStatement(ship, con));
        return ofNullable(get(ship.getId()));
    }

    private Ship get(BigInteger id) {
        return template.queryForObject(GET_SHIP, new Object[]{id}, shipRowMapper);
    }

    private PreparedStatement prepareInsertStatement(Ship ship, Connection con) throws SQLException {
        PreparedStatement statement = con.prepareStatement(INSERT_NEW_SHIP, RETURN_GENERATED_KEYS);
        statement.setString(1, ship.getType().name());
        statement.setInt(2, toIndex(ship.getStart()));
        statement.setInt(3, toIndex(ship.getEnd()));
        statement.setLong(4, ship.getBoardId().longValue());
        return statement;
    }

    private PreparedStatement prepareUpdateStatement(Ship ship, Connection con) throws SQLException {
        PreparedStatement statement = con.prepareStatement(UPDATE_SHIP_SET_SUNK);
        statement.setBoolean(1, ship.isSunk());
        statement.setLong(2, ship.getId().longValue());
        return statement;
    }

    private RowMapper<Ship> shipRowMapper = (rs, rowNum) -> Ship.builder()
            .withId(BigInteger.valueOf(rs.getLong("id")))
            .withType(Harbor.valueOf(rs.getString("type")))
            .withStart(toPoint(rs.getInt("start")))
            .withEnd(toPoint(rs.getInt("end")))
            .withSunk(rs.getBoolean("sunk"))
            .withBoardId(BigInteger.valueOf(rs.getLong("ship_board_id"))).build();

    public List<Ship> createAll(BigInteger boardId) {
        return null;
    }

    public List<Ship> getAllOpponents(BigInteger gameId, BigInteger id) {
        return null;
    }

    public void save(List<Ship> ships) {

    }
}
