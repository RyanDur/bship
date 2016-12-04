package com.bship.games.repositories;

import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import com.bship.games.domains.Ship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import static com.bship.games.domains.Harbor.valueOf;
import static com.bship.games.util.Util.toPoint;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.util.stream.Collectors.toList;

@Repository
public class BoardRepository {
    public static final int NUM_OF_BOARDS = 2;
    private JdbcTemplate template;

    @Autowired
    public BoardRepository(JdbcTemplate template) {
        this.template = template;
    }

    public List<Board> create(Game game) {
        return IntStream.range(0, NUM_OF_BOARDS)
                .mapToObj(i -> save.apply(game))
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public Board get(Long id) {
        return getBoard(id).copy().withShips(getShips(id)).build();
//        return template.queryForObject("SELECT b.id, game_id, " +
//                        "group_concat(s.id, ' ', game_id,' ', type,' ', start,' ', end) AS ship " +
//                        "FROM boards b LEFT JOIN ships s ON b.id = s.board_id " +
//                        "WHERE b.id = ?", new Object[]{id},
//                (rs, rowNum) -> Board.builder()
//                        .withId(rs.getLong("id"))
//                        .withGameId(rs.getLong("game_id"))
//                        .withShips(ofNullable(rs.getString("ship"))
//                                .map(ship -> Arrays.stream(ship.split(","))
//                                        .map(s -> s.split(" "))
//                                        .map(list -> Ship.builder()
//                                                .withId(new Long(list[0]))
//                                                .withBoardId(new Long(list[1]))
//                                                .withType(valueOf(list[2]))
//                                                .withStart(toPoint(Integer.parseInt(list[3])))
//                                                .withEnd(toPoint(Integer.parseInt(list[4])))
//                                                .build())).orElse(Stream.empty())
//                                .collect(toList())).build());
    }

    private List<Ship> getShips(Long id) {
        return template.query("SELECT * FROM ships WHERE board_id = ?", new Object[]{id}, shipRowMapper);
    }

    private Board getBoard(Long id) {
        return template.queryForObject("SELECT * FROM boards WHERE id = ?", new Object[]{id}, boardRowMapper);
    }

    private Function<Game, Board> save = (game) -> Board.builder()
            .withId(getGeneratedId(game))
            .withGameId(game.getId())
            .build();

    private Long getGeneratedId(Game game) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        template.update(con -> prepareInsertStatement(game, con), holder);
        return holder.getKey().longValue();
    }

    private PreparedStatement prepareInsertStatement(Game game, Connection con) throws SQLException {
        PreparedStatement statement = con.prepareStatement("INSERT INTO boards(game_id) VALUE(?)",
                RETURN_GENERATED_KEYS);
        statement.setLong(1, game.getId());
        return statement;
    }

    private RowMapper<Board> boardRowMapper = (rs, rowNum) -> Board.builder()
            .withId(rs.getLong("id"))
            .withGameId(rs.getLong("game_id")).build();

    private RowMapper<Ship> shipRowMapper = (rs, rowNum) -> Ship.builder()
            .withId(rs.getLong("id"))
            .withBoardId(rs.getLong("board_id"))
            .withStart(toPoint(rs.getInt("start")))
            .withEnd(toPoint(rs.getInt("end")))
            .withType(valueOf(rs.getString("type")))
            .build();
}