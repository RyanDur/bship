package com.bship.games;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.flywaydb.core.Flyway;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BoardRepositoryTest {

    @Test
    public void createBoards_shouldReturnAListOfTwoBoards() {
        BoardRepository repository = new BoardRepository(null);
        List<Board> boards = repository.createBoards();
        assertThat(boards.size(), is(equalTo(2)));
    }

    @Test
    public void createBoards_shouldPersistTwoNewBoards() {

        DataSource dataSource = new DataSource();
        dataSource.setUrl("jdbc:mysql://localhost/bs");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("");

        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.migrate();


        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        BoardRepository repository = new BoardRepository(jdbcTemplate);

        repository.createBoards();

        List<Board> boards = jdbcTemplate.query("SELECT * FROM boards", (rs, rowNum) -> {
            Board board = new Board();
            board.setId(rs.getInt("id"));
            return board;
        });

        assertThat(boards, hasSize(2));

        flyway.clean();
    }
}