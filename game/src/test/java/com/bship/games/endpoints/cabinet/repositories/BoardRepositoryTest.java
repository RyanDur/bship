package com.bship.games.endpoints.cabinet.repositories;

import com.bship.DBHelper;
import com.bship.games.endpoints.cabinet.entity.Board;
import com.bship.games.endpoints.cabinet.entity.Game;
import com.bship.games.endpoints.cabinet.entity.Piece;
import com.bship.games.endpoints.cabinet.entity.Point;
import com.bship.games.logic.definitions.Harbor;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bship.games.logic.definitions.Direction.NONE;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
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
        template.update("INSERT INTO games(id, name) VALUE(:id, 'BATTLESHIP')",
                new MapSqlParameterSource("id", game.getId()));
        template.update("INSERT INTO games(id, name) VALUE(:id, 'BATTLESHIP')",
                new MapSqlParameterSource("id", game.getId() + 1L));
        pieceList = getShips();
        when(ships.createAll(any(), any())).thenReturn(pieceList);
    }

    @Test
    public void create_shouldReturnABoard() {
        Board board = boards.create(game.getId(), Harbor.getPieces());
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

        when(ships.createAll(anyLong(), any())).thenReturn(pieceList);

        Board board = boards.create(game.getId(), Harbor.getPieces());

        assertThat(board, is(equalTo(expected)));
    }

    @Test
    public void get_shouldRetrieveABordFromTheRepository() {
        when(ships.getAll(anyLong())).thenReturn(pieceList);
        Board board = boards.create(game.getId(), Harbor.getPieces());
        Board actual = boards.get(board.getId()).orElse(null);

        assertThat(actual, is(board));
    }

    @Test
    public void get_shouldReturnEmptyWhenThereIsNotAGame() {
        Board board = boards.create(game.getId(), Harbor.getPieces());
        Optional<Board> actual = boards.get(board.getId() + 1L);

        assertThat(actual, is(Optional.empty()));
    }

    @Test
    public void getAll_shouldGetAllTheBoardsForAGame() {
        when(ships.getAll(anyLong())).thenReturn(pieceList);
        boards.create(game.getId() + 1L, Harbor.getPieces());
        Board board1 = boards.create(game.getId(), Harbor.getPieces());
        Board board2 = boards.create(game.getId(), Harbor.getPieces());

        List<Board> boardList = boards.getAll(game.getId());
        assertThat(boardList.size(), is(2));
        assertThat(boardList, containsInAnyOrder(board1, board2));
    }

    @Test
    public void getAll_shouldGetEmptyForAGameThatDoesNotExist() {
        when(ships.getAll(anyLong())).thenReturn(pieceList);
        boards.create(game.getId() + 1L, Harbor.getPieces());
        boards.create(game.getId(), Harbor.getPieces());
        boards.create(game.getId(), Harbor.getPieces());

        List<Board> boardList = boards.getAll(game.getId() + 10L);
        assertThat(boardList, is(empty()));
    }

    @Test
    public void save_shouldSaveABoard() {
        when(ships.getAll(anyLong())).thenReturn(pieceList);
        Board board = boards.create(game.getId(), Harbor.getPieces());
        Board expected = board.copy().withWinner(true).build();
        boards.save(expected);
        Optional<Board> actual = boards.get(expected.getId());

        assertThat(actual.orElse(null), is(equalTo(expected)));
    }

    @Test
    public void save_shouldReturnTheSavedBoard() {
        when(ships.getAll(anyLong())).thenReturn(pieceList);
        Board board = boards.create(game.getId(), Harbor.getPieces());
        Board expected = board.copy().withWinner(true).build();
        Optional<Board> actual = boards.save(expected);

        assertThat(actual.orElse(null), is(equalTo(expected)));
    }

    @Test
    public void save_shouldSaveTheShips() {
        Board board = boards.create(game.getId(), Harbor.getPieces());
        boards.save(board);

        verify(ships).save(board.getPieces());
    }

    @Test
    public void save_shouldSaveTheMoves() {
        Board board = boards.create(game.getId(), Harbor.getPieces());
        boards.save(board);

        verify(moves).save(board.getMoves());
    }

    private List<Piece> getShips() {
        return Harbor.getPieces().map(ship -> Piece.builder()
                .withType(ship)
                .withPlacement(new Point())
                .withOrientation(NONE)
                .withBoardId(1L)
                .build()).collect(Collectors.toList());
    }
}