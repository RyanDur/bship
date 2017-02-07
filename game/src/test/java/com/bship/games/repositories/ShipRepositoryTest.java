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
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

import static com.bship.games.util.Util.toPoint;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ShipRepositoryTest {

    private JdbcTemplate template;
    private ShipRepository repository;
    private Board board;
    private Ship savedBattleship;

    @Before
    public void setup() {
        template = new JdbcTemplate(DBHelper.reset());
        repository = new ShipRepository(template);

        Game game = Game.builder().withId(1L).build();
        board = Board.builder().withId(1L).withGameId(game.getId()).build();
        template.update("INSERT INTO games(id) VALUE(?) ", game.getId());
        template.update("INSERT INTO boards(id, game_id) VALUE(?, ?) ", board.getId(), board.getGameId());

        Point start = new Point(0, 0);
        Point end = new Point(0, 2);
        Harbor type = Harbor.BATTLESHIP;
        Ship battleship = Ship.builder().withType(type).withStart(start).withEnd(end).build();
        savedBattleship = repository.create(battleship, board.getId()).get();
    }

    @Test
    public void create_shouldReturnAShipWithTheBoardIdAttributedToIt() {
        assertThat(savedBattleship.getBoardId(), is(equalTo(board.getId())));
    }

    @Test
    public void create_shouldPersistTheShip() {
        Ship expected = template
                .queryForObject("SELECT * FROM ships WHERE ship_board_id = " + board.getId(), shipRowMapper);

        assertThat(savedBattleship, is(equalTo(expected)));
    }

    @Test
    public void getAll_shouldGetAllShipsForTheAssociatedBoard() {
        Point start = new Point(9, 0);
        Point end = new Point(9, 4);
        Harbor type = Harbor.AIRCRAFT_CARRIER;
        Ship carrier = Ship.builder().withType(type).withStart(start).withEnd(end).build();
        Optional<Ship> savedCarrier = repository.create(carrier, board.getId());

        Optional<List<Ship>> ships = repository.getAll(board.getId());

        assertThat(ships.get().size(), is(2));
        assertThat(ships.get(), containsInAnyOrder(savedCarrier.get(), savedBattleship));
    }

    @Test
    public void getAll_shouldReturnAnEmptyListIfNoMoves() {
        Game game = Game.builder().withId(2L).build();
        Board board = Board.builder().withId(3L).withGameId(game.getId()).build();
        template.update("INSERT INTO games(id) VALUE(?) ", game.getId());
        template.update("INSERT INTO boards(id, game_id) VALUE(?, ?) ", board.getId(), board.getGameId());

        Optional<List<Ship>> ships = repository.getAll(board.getId());

        assertThat(ships.get(), is(empty()));
    }

    @Test
    public void update_shouldUpdateTheShipToSunk() {
        Game game = Game.builder().withId(2L).build();
        Board board = Board.builder().withId(3L).withGameId(game.getId()).build();
        template.update("INSERT INTO games(id) VALUE(?) ", game.getId());
        template.update("INSERT INTO boards(id, game_id) VALUE(?, ?) ", board.getId(), board.getGameId());
        Ship ship = Ship.builder()
                .withType(Harbor.SUBMARINE)
                .withStart(new Point(0, 0))
                .withEnd(new Point(0, 4))
                .withBoardId(board.getId()).build();
        Optional<Ship> savedShip = repository.create(ship, board.getId());
        Optional<Ship> updatedShip = repository.update(savedShip.get().copy().withSunk(true).build());

        assertThat(savedShip.get().isSunk(), is(false));
        assertThat(updatedShip.get().isSunk(), is(true));
        assertThat(savedShip.get().getId(), is(equalTo(updatedShip.get().getId())));
    }

    private RowMapper<Ship> shipRowMapper = (rs, rowNum) -> Ship.builder()
            .withId(rs.getLong("id"))
            .withType(Harbor.valueOf(rs.getString("type")))
            .withStart(toPoint(rs.getInt("start")))
            .withEnd(toPoint(rs.getInt("end")))
            .withBoardId(rs.getLong("ship_board_id")).build();
}