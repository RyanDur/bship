package com.bship.games.repositories;

import com.bship.DBHelper;
import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static com.bship.games.repositories.BoardRepository.NUM_OF_BOARDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class BoardRepositoryTest {

    private JdbcTemplate template;
    private BoardRepository repository;
    private Game game;

    @Before
    public void setup() {
        template = new JdbcTemplate(DBHelper.reset());
        repository = new BoardRepository(template);

        game = Game.builder().withId(1L).build();
        template.update("INSERT INTO games(id) VALUE(?) ", game.getId());
    }

    @Test
    public void create_shouldReturnAListOfTwoBoards() {
        List<Board> boards = repository.create(game);
        assertThat(boards, hasSize(NUM_OF_BOARDS));
    }

    @Test
    public void create_shouldPersistTwoNewBoards() {
        List<Board> boardList = repository.create(game);

        List<Board> boards = template.query("SELECT * FROM boards",
                (rs, rowNum) -> Board.builder()
                        .withId(rs.getLong("id"))
                        .withGameId(rs.getLong("game_id")).build());

        assertThat(boards, containsInAnyOrder(boardList.toArray()));
    }

    @Test
    public void get_shouldRetrieveABordFromTheRepository() {
        List<Board> boardList = repository.create(game);
        Board board1 = boardList.get(1);

        Board expected = template.queryForObject("SELECT * FROM boards WHERE id = " + board1.getId(),
                (rs, rowNum) -> Board.builder()
                        .withId(rs.getLong("id"))
                        .withGameId(rs.getLong("game_id")).build());

        Board actual = repository.get(board1.getId());

        assertThat(actual, is(equalTo(expected)));
    }
}