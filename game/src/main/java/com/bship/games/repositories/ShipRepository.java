package com.bship.games.repositories;

import com.bship.games.domains.Harbor;
import com.bship.games.domains.Point;
import com.bship.games.domains.Ship;
import com.bship.games.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils.createBatch;

@Repository
public class ShipRepository {

    private static final String INSERT_INTO_SHIPS = "INSERT INTO ships(type, size, ship_board_id) VALUES (:type, :size, :ship_board_id)";
    public static final String SELECT_OPPONENTS_SUNK_SHIPS = "SELECT s.* FROM ships s JOIN boards b ON s.ship_board_id = b.id WHERE b.game_id = :game_id AND s.ship_board_id <> :board_id AND s.sunk IS TRUE;";
    public static final String SELECT_MY_SHIPS = "SELECT * FROM ships WHERE ship_board_id = :board_id";
    public static final String UPDATE_SHIPS = "UPDATE ships SET sunk = :sunk WHERE id = :id";
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public ShipRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public List<Ship> createAll(BigInteger boardId) {
        template.batchUpdate(INSERT_INTO_SHIPS,
                createBatch(getNewShipBatch(boardId)));

        return getAll(boardId);
    }

    public List<Ship> getAll(BigInteger boardId) {
        return template.query(SELECT_MY_SHIPS,
                new MapSqlParameterSource("board_id", boardId),
                buildShip);
    }

    public List<Ship> getAllOpponents(BigInteger gameId, BigInteger boardId) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("game_id", gameId);
        source.addValue("board_id", boardId);
        return template.query(SELECT_OPPONENTS_SUNK_SHIPS, source, buildShip);
    }

    public void save(List<Ship> ships) {
        of(ships).map(getUpdateShipBatch()).map(batch ->
                template.batchUpdate(UPDATE_SHIPS, createBatch(batch)));
    }

    private RowMapper<Ship> buildShip = (rs, rowNum) -> Ship.builder()
            .withId(BigInteger.valueOf(rs.getLong("id")))
            .withType(Harbor.valueOf(rs.getString("type")))
            .withStart(getPoint(rs.getString("start")))
            .withEnd(getPoint(rs.getString("end")))
            .withSunk(rs.getBoolean("sunk"))
            .withSize(rs.getInt("size"))
            .withBoardId(BigInteger.valueOf(rs.getLong("ship_board_id"))).build();

    private Point getPoint(String point) throws SQLException {
        return ofNullable(point)
                .map(Integer::valueOf)
                .map(Util::toPoint)
                .orElse(new Point());
    }

    private HashMap[] getNewShipBatch(final BigInteger boardId) {
        return Harbor.getShips().stream().map(ship ->
                new HashMap<String, Object>() {{
                    put("type", ship.name());
                    put("size", ship.getSize());
                    put("ship_board_id", boardId);
                }})
                .collect(toList()).toArray(new HashMap[0]);
    }

    private Function<List<Ship>, HashMap[]> getUpdateShipBatch() {
        return ships -> ships.stream().map(ship ->
                new HashMap<String, Object>() {{
                    put("id", ship.getId());
                    put("sunk", ship.isSunk());
                }})
                .collect(toList()).toArray(new HashMap[0]);
    }
}
