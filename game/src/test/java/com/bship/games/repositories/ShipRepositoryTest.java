package com.bship.games.repositories;

import com.bship.DBHelper;
import com.bship.games.domains.Harbor;
import com.bship.games.domains.Point;
import com.bship.games.domains.Ship;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import static java.math.BigInteger.ONE;
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
                .withBoardId(ONE)
                .withStart(new Point())
                .withEnd(new Point())
                .withId(BigInteger.valueOf(ship.ordinal() + 1))
                .build()).collect(toList());

        List<Ship> actual = ships.createAll(ONE);

        assertThat(actual.size(), is(expected.size()));
        assertThat(actual, is(expected));
    }

    @Test
    public void getAll_shouldRetrieveAllTheShipsForABoard() {
        List<Ship> expected = ships.createAll(ONE);
        List<Ship> actual = ships.getAll(ONE);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void getAll_shouldReturnAnEmptyListIfNoShips() {
        List<Ship> actual = ships.getAll(BigInteger.TEN);

        assertThat(actual, is(empty()));
    }

    @Test
    public void save_shouldSaveShips() {
        ships.createAll(ONE);
        List<Ship> opponents = ships.createAll(ONE.add(ONE))
                .stream().map(ship -> of(ship)
                        .filter(destroyer -> destroyer.getType().equals(Harbor.DESTROYER))
                        .map(destroyer -> destroyer.copy().withSunk(true).build())
                        .orElse(ship))
                .collect(toList());
        ships.save(opponents);
        ships.save(opponents);
        List<Ship> actual = ships.getAll(ONE.add(ONE));

        assertThat(actual, is(equalTo(opponents)));
    }

    @Test
    public void save_shouldReturnEmptyIfNoShips() {
        ships.createAll(ONE);

        List<Ship> actual = ships.getAll(ONE.add(ONE));

        assertThat(actual, is(empty()));
    }

    @Test
    public void getAllOpponents_shouldReturnAllTheOpponentsSunkShip() {
        ships.createAll(ONE);
        List<Ship> opponents = ships.createAll(ONE.add(ONE))
                .stream().map(ship -> of(ship)
                        .filter(destroyer -> destroyer.getType().equals(Harbor.DESTROYER))
                        .map(destroyer -> destroyer.copy().withSunk(true).build())
                        .orElse(ship))
                .collect(toList());
        ships.save(opponents);
        List<Ship> shipsAllOpponents = ships.getAllOpponents(ONE, ONE);

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

        List<Ship> ships1 = ships.createAll(ONE);
        List<Ship> ships2 = ships.createAll(ONE.add(ONE));

        List<Ship> ships3 = ships.createAll(ONE.add(ONE).add(ONE));
        List<Ship> ships4 = ships.createAll(ONE.add(ONE).add(ONE).add(ONE));

        List<Ship> ships5 = ships.createAll(ONE.add(ONE).add(ONE).add(ONE).add(ONE));
        List<Ship> ships6 = ships.createAll(ONE.add(ONE).add(ONE).add(ONE).add(ONE).add(ONE));

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


        List<Ship> shipsAllOpponents = ships.getAllOpponents(ONE.add(ONE), ONE.add(ONE).add(ONE).add(ONE));

        assertThat(shipsAllOpponents, is(equalTo(opponents3.stream()
                .filter(Ship::isSunk).collect(toList()))));
    }

}