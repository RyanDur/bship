package com.bship.games.repositories;

import com.bship.DBHelper;
import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import com.bship.games.domains.Harbor;
import com.bship.games.domains.Point;
import com.bship.games.domains.Ship;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BoardRepositoryTest {

    private BoardRepository boards;
    private Game game;
    private ShipRepository ships;
    private List<Ship> shipList;
    private MoveRepository moves;

    @Before
    public void setup() {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(DBHelper.reset());
        ships = mock(ShipRepository.class);
        moves = mock(MoveRepository.class);
        boards = new BoardRepository(template, ships, moves);

        game = Game.builder().withId(BigInteger.ONE).build();
        template.update("INSERT INTO games(id) VALUE(:id)",
                new MapSqlParameterSource("id", game.getId()));
        template.update("INSERT INTO games(id) VALUE(:id)",
                new MapSqlParameterSource("id", game.getId().add(BigInteger.ONE)));
        shipList = getShips();
        when(ships.createAll(any(BigInteger.class))).thenReturn(shipList);
    }

    @Test
    public void create_shouldReturnABoard() {
        Board board = boards.create(game.getId());
        assertThat(board, is(instanceOf(Board.class)));
    }

    @Test
    public void create_shouldHaveAListOfUnplacedShips() {
        Board expected = Board.builder().withShips(shipList)
                .withId(BigInteger.ONE)
                .withGameId(BigInteger.ONE)
                .withOpponentShips(emptyList())
                .withMoves(emptyList())
                .withOpponentMoves(emptyList())
                .build();

        when(ships.createAll(any(BigInteger.class))).thenReturn(shipList);

        Board board = boards.create(game.getId());

        assertThat(board, is(equalTo(expected)));
    }

    @Test
    public void get_shouldRetrieveABordFromTheRepository() {
        when(ships.getAll(any(BigInteger.class))).thenReturn(shipList);
        Board board = boards.create(game.getId());
        Board actual = boards.get(board.getId()).get();

        assertThat(actual, is(board));
    }

    @Test
    public void get_shouldReturnEmptyWhenThereIsNotAGame() {
        Board board = boards.create(game.getId());
        Optional<Board> actual = boards.get(board.getId().add(BigInteger.ONE));

        assertThat(actual, is(Optional.empty()));
    }

    @Test
    public void getAll_shouldGetAllTheBoardsForAGame() {
        when(ships.getAll(any(BigInteger.class))).thenReturn(shipList);
        boards.create(game.getId().add(BigInteger.ONE));
        Board board1 = boards.create(game.getId());
        Board board2 = boards.create(game.getId());

        List<Board> boardList = boards.getAll(game.getId());
        assertThat(boardList.size(), is(2));
        assertThat(boardList, containsInAnyOrder(board1, board2));
    }

    @Test
    public void getAll_shouldGetEmptyForAGameThatDoesNotExist() {
        when(ships.getAll(any(BigInteger.class))).thenReturn(shipList);
        boards.create(game.getId().add(BigInteger.ONE));
        boards.create(game.getId());
        boards.create(game.getId());

        List<Board> boardList = boards.getAll(game.getId().add(BigInteger.TEN));
        assertThat(boardList, is(empty()));
    }

    @Test
    public void save_shouldSaveABoard() {
        when(ships.getAll(any(BigInteger.class))).thenReturn(shipList);
        Board board = boards.create(game.getId());
        Board expected = board.copy().withWinner(true).build();
        boards.save(expected);
        Optional<Board> actual = boards.get(expected.getId());

        assertThat(actual.get(), is(equalTo(expected)));
    }

    @Test
    public void save_shouldReturnTheSavedBoard() {
        when(ships.getAll(any(BigInteger.class))).thenReturn(shipList);
        Board board = boards.create(game.getId());
        Board expected = board.copy().withWinner(true).build();
        Optional<Board> actual = boards.save(expected);

        assertThat(actual.get(), is(equalTo(expected)));
    }

    @Test
    public void save_shouldSaveTheShips() {
        Board board = boards.create(game.getId());
        boards.save(board);

        verify(ships).save(board.getShips());
    }

    @Test
    public void save_shouldSaveTheMoves() {
        Board board = boards.create(game.getId());
        boards.save(board);

        verify(moves).save(board.getMoves());
    }

    public List<Ship> getShips() {
        return Harbor.getShips().stream().map(ship -> Ship.builder()
                .withType(ship)
                .withStart(new Point())
                .withEnd(new Point())
                .withBoardId(BigInteger.ONE)
                .build()).collect(Collectors.toList());
    }
}