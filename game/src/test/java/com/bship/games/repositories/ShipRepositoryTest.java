package com.bship.games.repositories;

import com.bship.DBHelper;
import com.bship.games.domains.Harbor;
import com.bship.games.domains.Point;
import com.bship.games.domains.Ship;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;

import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ShipRepositoryTest {

    private NamedParameterJdbcTemplate template;
    private ShipRepository ships;

    @Before
    public void setup() {
        template = new NamedParameterJdbcTemplate(DBHelper.reset());
        template.update("INSERT INTO games(id) VALUE (default)", new HashMap<>());
        template.update("INSERT INTO boards(game_id) VALUE (1)", new HashMap<>());
        template.update("INSERT INTO boards(game_id) VALUE (1)", new HashMap<>());
        ships = new ShipRepository(template);
    }

    @Test
    public void createAll_shouldCreateAllTheShips() {
        List<Ship> expected = Harbor.getShips().stream().map(ship -> Ship.builder()
                .withType(ship)
                .withSize(ship.getSize())
                .withBoardId(1L)
                .withStart(new Point())
                .withEnd(new Point())
                .withId(ship.ordinal() + 1L)
                .build()).collect(toList());

        List<Ship> actual = ships.createAll(1L);

        assertThat(actual.size(), is(expected.size()));
        assertThat(actual, is(expected));
    }

    @Test
    public void getAll_shouldRetrieveAllTheShipsForABoard() {
        List<Ship> expected = ships.createAll(1L);
        List<Ship> actual = ships.getAll(1L);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void getAll_shouldReturnAnEmptyListIfNoShips() {
        List<Ship> actual = ships.getAll(10L);

        assertThat(actual, is(empty()));
    }

    @Test
    public void save_shouldSaveShips() {
        ships.createAll(1L);
        List<Ship> opponents = ships.createAll(2L)
                .stream().map(ship -> of(ship)
                        .filter(destroyer -> destroyer.getType().equals(Harbor.DESTROYER))
                        .map(destroyer -> destroyer.copy().withSunk(true).build())
                        .orElse(ship))
                .collect(toList());
        ships.save(opponents);
        ships.save(opponents);
        List<Ship> actual = ships.getAll(2L);

        assertThat(actual, is(equalTo(opponents)));
    }

    @Test
    public void save_shouldReturnEmptyIfNoShips() {
        ships.createAll(1L);

        List<Ship> actual = ships.getAll(2L);

        assertThat(actual, is(empty()));
    }

    @Test
    public void save_shouldSaveAShip() {
        List<Ship> all = ships.createAll(1L);
        Ship ship = all.get(0).copy()
                .withStart(new Point(0, 0))
                .withEnd(new Point(0, 4))
                .withBoardId(1L)
                .build();
        ships.save(ship);
        Ship actual = ships.getAll(1L).stream()
                .filter(o -> o.getId() == 1L)
                .findFirst().get();

        assertThat(actual, is(equalTo(ship)));
    }

    @Test
    public void getAllOpponents_shouldReturnAllTheOpponentsSunkShip() {
        ships.createAll(1L);
        List<Ship> opponents = ships.createAll(2L)
                .stream().map(ship -> of(ship)
                        .filter(destroyer -> destroyer.getType().equals(Harbor.DESTROYER))
                        .map(destroyer -> destroyer.copy().withSunk(true).build())
                        .orElse(ship))
                .collect(toList());
        ships.save(opponents);
        List<Ship> shipsAllOpponents = ships.getAllOpponents(1L, 1L);

        assertThat(shipsAllOpponents, is(equalTo(opponents.stream()
                .filter(Ship::isSunk).collect(toList()))));
    }
    @Test
    public void getAllOpponents_shouldReturnAllTheOpponentsSunkShipFromSpecificGame() {
        template.update("INSERT INTO games(id) VALUE (default)", new HashMap<>());
        template.update("INSERT INTO boards(game_id) VALUE (2)", new HashMap<>());
        template.update("INSERT INTO boards(game_id) VALUE (2)", new HashMap<>());

        template.update("INSERT INTO games(id) VALUE (default)", new HashMap<>());
        template.update("INSERT INTO boards(game_id) VALUE (3)", new HashMap<>());
        template.update("INSERT INTO boards(game_id) VALUE (3)", new HashMap<>());

        List<Ship> ships1 = ships.createAll(1L);
        List<Ship> ships2 = ships.createAll(2L);

        List<Ship> ships3 = ships.createAll(3L);
        List<Ship> ships4 = ships.createAll(4L);

        List<Ship> ships5 = ships.createAll(5L);
        List<Ship> ships6 = ships.createAll(6L);

        List<Ship> opponents = ships3
                .stream().map(ship -> of(ship)
                        .filter(destroyer -> destroyer.getType().equals(Harbor.DESTROYER))
                        .map(destroyer -> destroyer.copy().withSunk(true).build())
                        .orElse(ship))
                .collect(toList());
        ships.save(opponents);
        List<Ship> opponents3 = opponents
                .stream().map(ship -> of(ship)
                        .filter(carrier -> carrier.getType().equals(Harbor.AIRCRAFT_CARRIER))
                        .map(carrier -> carrier.copy().withSunk(true).build())
                        .orElse(ship))
                .collect(toList());
        ships.save(opponents3);

        List<Ship> opponents6 = ships6
                .stream().map(ship -> of(ship)
                        .filter(battleship -> battleship.getType().equals(Harbor.BATTLESHIP))
                        .map(battleship -> battleship.copy().withSunk(true).build())
                        .orElse(ship))
                .collect(toList());
        ships.save(opponents6);


        List<Ship> shipsAllOpponents = ships.getAllOpponents(2L, 4L);

        assertThat(shipsAllOpponents, is(equalTo(opponents3.stream()
                .filter(Ship::isSunk).collect(toList()))));
    }

}