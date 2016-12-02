package com.bship.games.repositories;

import com.bship.DBHelper;
import com.bship.games.domains.Harbor;
import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import com.bship.games.domains.Point;
import com.bship.games.domains.Ship;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static com.bship.games.util.Util.toPoint;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ShipRepositoryTest {

    private JdbcTemplate template;
    private ShipRepository repository;
    private Board board;

    @Before
    public void setup() {
        template = new JdbcTemplate(DBHelper.reset());
        repository = new ShipRepository(template);

        Game game = Game.builder().withId(1L).build();
        board = Board.builder().withId(1L).withGameId(game.getId()).build();
        template.update("INSERT INTO games(id) VALUE(?) ", game.getId());
        template.update("INSERT INTO boards(id, game_id) VALUE(?, ?) ", board.getId(), board.getGameId());
    }

    @Test
    public void create_shouldReturnAShipWithTheBoardIdAttributedToIt() {
        Point start = new Point(0, 0);
        Point end = new Point(0, 2);
        Harbor type = Harbor.BATTLESHIP;
        Ship battleship = Ship.builder().withShipType(type).withStart(start).withEnd(end).build();
        Ship ship = repository.create(battleship, board.getId());

        assertThat(ship.getBoardId(), is(equalTo(board.getId())));
    }

    @Test
    public void create_shouldPersistTheShip() {
        Point start = new Point(0, 0);
        Point end = new Point(0, 2);
        Harbor type = Harbor.BATTLESHIP;
        Ship battleship = Ship.builder().withShipType(type).withStart(start).withEnd(end).build();
        Ship ship = repository.create(battleship, board.getId());

        Ship expected = template.queryForObject("SELECT * FROM ships WHERE board_id = " + board.getId(), (rs, rowNum) -> Ship.builder()
                .withId(rs.getLong("id"))
                .withShipType(Harbor.valueOf(rs.getString("type")))
                .withStart(toPoint(rs.getInt("start")))
                .withEnd(toPoint(rs.getInt("end")))
                .withBoardId(rs.getLong("board_id")).build());

        assertThat(ship, is(equalTo(expected)));
    }
}