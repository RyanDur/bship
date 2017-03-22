package com.bship.games.repositories;

import com.bship.DBHelper;
import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import com.bship.games.domains.Harbor;
import com.bship.games.domains.Piece;
import com.bship.games.domains.Point;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bship.games.domains.Direction.NONE;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BoardRepositoryTest {

    private BoardRepository boards;
    private Game game;
    private PieceRepository ships;
    private List<Piece> pieceList;
    private MoveRepository moves;

    @Before
    public void setup() {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(DBHelper.reset());
        ships = mock(PieceRepository.class);
        moves = mock(MoveRepository.class);
        boards = new BoardRepository(template, ships, moves);

        game = Game.builder().withId(1L).build();
        template.update("INSERT INTO games(id) VALUE(:id)",
                new MapSqlParameterSource("id", game.getId()));
        template.update("INSERT INTO games(id) VALUE(:id)",
                new MapSqlParameterSource("id", game.getId() + 1L));
        pieceList = getShips();
        when(ships.createAll(anyLong())).thenReturn(pieceList);
    }

    @Test
    public void create_shouldReturnABoard() {
        Board board = boards.create(game.getId());
        assertThat(board, is(instanceOf(Board.class)));
    }

    @Test
    public void create_shouldHaveAListOfUnplacedShips() {
        Board expected = Board.builder().withPieces(pieceList)
                .withId(1L)
                .withGameId(1L)
                .withOpponentPieces(emptyList())
                .withMoves(emptyList())
                .withOpponentMoves(emptyList())
                .build();

        when(ships.createAll(anyLong())).thenReturn(pieceList);

        Board board = boards.create(game.getId());

        assertThat(board, is(equalTo(expected)));
    }

    @Test
    public void get_shouldRetrieveABordFromTheRepository() {
        when(ships.getAll(anyLong())).thenReturn(pieceList);
        Board board = boards.create(game.getId());
        Board actual = boards.get(board.getId()).get();

        assertThat(actual, is(board));
    }

    @Test
    public void get_shouldReturnEmptyWhenThereIsNotAGame() {
        Board board = boards.create(game.getId());
        Optional<Board> actual = boards.get(board.getId() + 1L);

        assertThat(actual, is(Optional.empty()));
    }

    @Test
    public void getAll_shouldGetAllTheBoardsForAGame() {
        when(ships.getAll(anyLong())).thenReturn(pieceList);
        boards.create(game.getId() + 1L);
        Board board1 = boards.create(game.getId());
        Board board2 = boards.create(game.getId());

        List<Board> boardList = boards.getAll(game.getId());
        assertThat(boardList.size(), is(2));
        assertThat(boardList, containsInAnyOrder(board1, board2));
    }

    @Test
    public void getAll_shouldGetEmptyForAGameThatDoesNotExist() {
        when(ships.getAll(anyLong())).thenReturn(pieceList);
        boards.create(game.getId() + 1L);
        boards.create(game.getId());
        boards.create(game.getId());

        List<Board> boardList = boards.getAll(game.getId() + 10L);
        assertThat(boardList, is(empty()));
    }

    @Test
    public void save_shouldSaveABoard() {
        when(ships.getAll(anyLong())).thenReturn(pieceList);
        Board board = boards.create(game.getId());
        Board expected = board.copy().withWinner(true).build();
        boards.save(expected);
        Optional<Board> actual = boards.get(expected.getId());

        assertThat(actual.get(), is(equalTo(expected)));
    }

    @Test
    public void save_shouldReturnTheSavedBoard() {
        when(ships.getAll(anyLong())).thenReturn(pieceList);
        Board board = boards.create(game.getId());
        Board expected = board.copy().withWinner(true).build();
        Optional<Board> actual = boards.save(expected);

        assertThat(actual.get(), is(equalTo(expected)));
    }

    @Test
    public void save_shouldSaveTheShips() {
        Board board = boards.create(game.getId());
        boards.save(board);

        verify(ships).save(board.getPieces());
    }

    @Test
    public void save_shouldSaveTheMoves() {
        Board board = boards.create(game.getId());
        boards.save(board);

        verify(moves).save(board.getMoves());
    }

    public List<Piece> getShips() {
        return Harbor.getShips().stream().map(ship -> Piece.builder()
                .withType(ship)
                .withPlacement(new Point())
                .withOrientation(NONE)
                .withSize(ship.getSize())
                .withBoardId(1L)
                .build()).collect(Collectors.toList());
    }
}