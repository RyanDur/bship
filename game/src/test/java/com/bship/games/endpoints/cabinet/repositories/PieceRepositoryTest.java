package com.bship.games.endpoints.cabinet.repositories;

import com.bship.DBHelper;
import com.bship.games.endpoints.cabinet.entity.Piece;
import com.bship.games.endpoints.cabinet.entity.Point;
import com.bship.games.logic.definitions.Harbor;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.bship.games.logic.definitions.Direction.DOWN;
import static com.bship.games.logic.definitions.Direction.NONE;
import static com.bship.games.logic.definitions.Harbor.AIRCRAFT_CARRIER;
import static com.bship.games.logic.definitions.Harbor.BATTLESHIP;
import static com.bship.games.logic.definitions.Harbor.DESTROYER;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class PieceRepositoryTest {

    private NamedParameterJdbcTemplate template;
    private PieceRepository pieces;

    @Before
    public void setup() {
        template = new NamedParameterJdbcTemplate(DBHelper.reset());
        template.update("INSERT INTO games(id, name) VALUE (default, 'BATTLESHIP')", new HashMap<>());
        template.update("INSERT INTO boards(game_id) VALUE (1)", new HashMap<>());
        template.update("INSERT INTO boards(game_id) VALUE (1)", new HashMap<>());
        pieces = new PieceRepository(template);
    }

    @Test
    public void createAll_shouldCreateAllTheShips() {
        List<Piece> expected = getPieces();

        List<Piece> actual = pieces.createAll(1L, Harbor.getPieces());

        assertThat(actual.size(), is(expected.size()));
        assertThat(actual, is(expected));
    }

    @Test
    public void getAll_shouldRetrieveAllTheShipsForABoard() {
        List<Piece> expected = pieces.createAll(1L, Harbor.getPieces());
        List<Piece> actual = pieces.getAll(1L);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void getAll_shouldReturnAnEmptyListIfNoShips() {
        List<Piece> actual = pieces.getAll(10L);

        assertThat(actual, is(empty()));
    }

    @Test
    public void save_shouldSaveShips() {
        pieces.createAll(1L, Harbor.getPieces());
        List<Piece> opponents = pieces.createAll(2L, Harbor.getPieces())
                .stream().map(ship -> of(ship)
                        .filter(destroyer -> destroyer.getType().equals(DESTROYER))
                        .map(destroyer -> destroyer.copy().withTaken(true).build())
                        .orElse(ship))
                .collect(toList());
        pieces.save(opponents);
        pieces.save(opponents);
        List<Piece> actual = pieces.getAll(2L);

        assertThat(actual, is(equalTo(opponents)));
    }

    @Test
    public void save_shouldReturnEmptyIfNoShips() {
        pieces.createAll(1L, Harbor.getPieces());

        List<Piece> actual = pieces.getAll(2L);

        assertThat(actual, is(empty()));
    }

    @Test
    public void save_shouldSaveAShip() {
        List<Piece> all = pieces.createAll(1L, Harbor.getPieces());
        Piece piece = all.get(0).copy()
                .withPlacement(new Point(0, 0))
                .withOrientation(DOWN)
                .withBoardId(1L)
                .build();
        pieces.save(piece);
        Piece actual = pieces.getAll(1L).stream()
                .filter(o -> o.getId() == 1L)
                .findFirst().orElse(null);

        assertThat(actual, is(equalTo(piece)));
    }

    @Test
    public void getAllOpponents_shouldReturnAllTheOpponentsSunkShip() {
        pieces.createAll(1L, Harbor.getPieces());
        List<Piece> opponents = pieces.createAll(2L, Harbor.getPieces())
                .stream().map(ship -> of(ship)
                        .filter(destroyer -> destroyer.getType().equals(DESTROYER))
                        .map(destroyer -> destroyer.copy().withTaken(true).build())
                        .orElse(ship))
                .collect(toList());
        pieces.save(opponents);
        List<Piece> shipsAllOpponents = pieces.getAllOpponents(1L, 1L);

        assertThat(shipsAllOpponents, is(equalTo(opponents.stream()
                .filter(Piece::isTaken).collect(toList()))));
    }

    @Test
    public void getAllOpponents_shouldReturnAllTheOpponentsSunkShipFromSpecificGame() {
        template.update("INSERT INTO games(id, name) VALUE (default, 'BATTLESHIP')", new HashMap<>());
        template.update("INSERT INTO boards(game_id) VALUE (2)", new HashMap<>());
        template.update("INSERT INTO boards(game_id) VALUE (2)", new HashMap<>());

        template.update("INSERT INTO games(id, name) VALUE (default, 'BATTLESHIP')", new HashMap<>());
        template.update("INSERT INTO boards(game_id) VALUE (3)", new HashMap<>());
        template.update("INSERT INTO boards(game_id) VALUE (3)", new HashMap<>());

        pieces.createAll(1L, Harbor.getPieces());
        pieces.createAll(2L, Harbor.getPieces());

        List<Piece> ships3 = pieces.createAll(3L, Harbor.getPieces());
        pieces.createAll(4L, Harbor.getPieces());

        pieces.createAll(5L, Harbor.getPieces());
        List<Piece> ships6 = pieces.createAll(6L, Harbor.getPieces());

        List<Piece> opponents = ships3
                .stream().map(ship -> of(ship)
                        .filter(destroyer -> destroyer.getType().equals(DESTROYER))
                        .map(destroyer -> destroyer.copy().withTaken(true).build())
                        .orElse(ship))
                .collect(toList());
        pieces.save(opponents);
        List<Piece> opponents3 = opponents
                .stream().map(ship -> of(ship)
                        .filter(carrier -> carrier.getType().equals(AIRCRAFT_CARRIER))
                        .map(carrier -> carrier.copy().withTaken(true).build())
                        .orElse(ship))
                .collect(toList());
        pieces.save(opponents3);

        List<Piece> opponents6 = ships6
                .stream().map(ship -> of(ship)
                        .filter(battleship -> battleship.getType().equals(BATTLESHIP))
                        .map(battleship -> battleship.copy().withTaken(true).build())
                        .orElse(ship))
                .collect(toList());
        pieces.save(opponents6);


        List<Piece> shipsAllOpponents = pieces.getAllOpponents(2L, 4L);

        assertThat(shipsAllOpponents, is(equalTo(opponents3.stream()
                .filter(Piece::isTaken).collect(toList()))));
    }

    public List<Piece> getPieces() {
        return Harbor.getPieces().map(ship -> Piece.builder()
                .withType(ship)
                .withId(1L + ((Harbor) ship).ordinal())
                .withPlacement(new Point())
                .withOrientation(NONE)
                .withBoardId(1L)
                .build()).collect(Collectors.toList());
    }
}