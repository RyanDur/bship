package com.bship.games.repositories;

import com.bship.games.models.Board;
import com.bship.games.models.Game;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static com.bship.games.repositories.BoardRepository.NUM_OF_BOARDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

public class BoardRepositoryTest {

    private JdbcTemplate template;
    private BoardRepository repository;
    private Game game;

    @Before
    public void setup() {
        DataSource dataSource = new DataSource();
        dataSource.setUrl("jdbc:mysql://localhost/bs");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("");

        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.clean();
        flyway.migrate();
        template = new JdbcTemplate(dataSource);

        repository = new BoardRepository(template);

        game = new Game();
        game.setId(1L);
        template.update("INSERT INTO games(id) VALUE(?) ", game.getId());
    }

    @Test
    public void createBoards_shouldReturnAListOfTwoBoards() {
        List<Board> boards = repository.createBoards(game);
        assertThat(boards, hasSize(NUM_OF_BOARDS));
    }

    @Test
    public void createBoards_shouldPersistTwoNewBoards() {
        List<Board> boardList = repository.createBoards(game);

        List<Board> boards = template.query("SELECT * FROM boards", (rs, rowNum) -> {
            Board board = new Board();
            board.setId(rs.getLong("id"));
            return board;
        });

        assertThat(boards, containsInAnyOrder(boardList.toArray()));
    }
}