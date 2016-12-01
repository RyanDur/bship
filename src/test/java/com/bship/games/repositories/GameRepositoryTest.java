package com.bship.games.repositories;

import com.bship.DBHelper;
import com.bship.games.domains.Game;
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
        jdbcTemplate = new JdbcTemplate(DBHelper.reset());
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

        List<Game> games = jdbcTemplate.query("SELECT * FROM games", (rs, rowNum) -> Game.builder()
                .withId(rs.getLong("id")).build());

        assertThat(games, is(not(empty())));
        assertThat(games, contains(game1, game2));
        assertThat(game2.getId(), is(2L));
    }
}