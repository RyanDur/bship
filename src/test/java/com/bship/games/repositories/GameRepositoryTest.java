package com.bship.games.repositories;

import com.bship.games.models.Game;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class GameRepositoryTest {

    private GameRepository repository;
    private JdbcTemplate jdbcTemplate;

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

        jdbcTemplate = new JdbcTemplate(dataSource);
        repository = new GameRepository(jdbcTemplate);
    }

    @Test
    public void createGame_shouldCreateAGame() {
        Game game = repository.createGame();

        assertThat(game, is(instanceOf(Game.class)));
    }

    @Test
    public void createGame_shouldPersistGames() {
        Game game1 = repository.createGame();
        Game game2 = repository.createGame();

        List<Game> games = jdbcTemplate.query("SELECT * FROM games", (rs, rowNum) -> {
            Game game = new Game();
            game.setId(rs.getLong("id"));
            return game;
        });

        assertThat(games, is(not(empty())));
        assertThat(games, contains(game1, game2));
        assertThat(game2.getId(), is(2L));
    }
}