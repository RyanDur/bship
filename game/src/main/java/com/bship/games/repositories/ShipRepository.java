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

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import static com.bship.games.util.Util.toIndex;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils.createBatch;

@Repository
public class ShipRepository {

    private static final String INSERT_INTO_SHIPS = "INSERT INTO ships(type, size, ship_board_id) VALUES (:type, :size, :ship_board_id)";
    private static final String SELECT_OPPONENTS_SUNK_SHIPS = "SELECT s.* FROM ships s JOIN boards b ON s.ship_board_id = b.id WHERE b.game_id = :game_id AND s.ship_board_id <> :ship_board_id AND s.sunk IS TRUE;";
    private static final String SELECT_MY_SHIPS = "SELECT * FROM ships WHERE ship_board_id = :ship_board_id";
    private static final String UPDATE_SHIPS = "UPDATE ships SET sunk = :sunk, start = :start, end = :end WHERE id = :id";
    private static final String UPDATE_SHIP_POSITION = "UPDATE ships SET start = :start, end = :end WHERE id = :id";
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public ShipRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public List<Ship> createAll(long boardId) {
        template.batchUpdate(INSERT_INTO_SHIPS,
                createBatch(getNewShipBatch(boardId)));

        return getAll(boardId);
    }

    public List<Ship> getAll(long boardId) {
        return template.query(SELECT_MY_SHIPS,
                new MapSqlParameterSource("ship_board_id", boardId),
                buildShip);
    }

    public List<Ship> getAllOpponents(long gameId, long boardId) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("game_id", gameId);
        source.addValue("ship_board_id", boardId);
        return template.query(SELECT_OPPONENTS_SUNK_SHIPS, source, buildShip);
    }

    public void save(List<Ship> ships) {
        of(ships).map(getUpdateShipBatch()).map(batch ->
                template.batchUpdate(UPDATE_SHIPS, createBatch(batch)));
    }

    public void save(Ship ship) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("id", ship.getId());
        source.addValue("start", toIndex(ship.getStart()));
        source.addValue("end", toIndex(ship.getEnd()));

        template.update(UPDATE_SHIP_POSITION, source);
    }

    private RowMapper<Ship> buildShip = (rs, rowNum) -> Ship.builder()
            .withId(rs.getLong("id"))
            .withType(Harbor.valueOf(rs.getString("type")))
            .withStart(getPoint(rs.getString("start")))
            .withEnd(getPoint(rs.getString("end")))
            .withSunk(rs.getBoolean("sunk"))
            .withSize(rs.getInt("size"))
            .withBoardId(rs.getLong("ship_board_id")).build();

    private Point getPoint(String point) throws SQLException {
        return ofNullable(point)
                .map(Integer::valueOf)
                .map(Util::toPoint)
                .orElse(new Point());
    }

    private HashMap[] getNewShipBatch(final long boardId) {
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
                    put("start", toIndex(ship.getStart()));
                    put("end", toIndex(ship.getEnd()));
                }})
                .collect(toList()).toArray(new HashMap[0]);
    }
}
