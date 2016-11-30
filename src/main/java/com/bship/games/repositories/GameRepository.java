package com.bship.games.repositories;

import com.bship.games.models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class GameRepository {

    private final JdbcTemplate template;

    @Autowired
    public GameRepository(JdbcTemplate template) {
        this.template = template;
    }

    public Game createGame() {
        Game game = new Game();
        int id = template.update("INSERT INTO games(id) VALUE(?) ", game.getId());
        game.setId(id);
        return game;
    }
}
