package com.bship.games.repositories;

import com.bship.games.models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;

@Component
public class GameRepository {

    private final JdbcTemplate template;

    @Autowired
    public GameRepository(JdbcTemplate template) {
        this.template = template;
    }

    public Game createGame() {
        Game game = new Game();
        game.setId(getGeneratedId());
        return game;
    }

    private Long getGeneratedId() {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        template.update(con -> {
            PreparedStatement statement = con.prepareStatement("INSERT INTO games(id) VALUE (?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setNull(1, Types.INTEGER);
            return statement;
        }, holder);
        return holder.getKey().longValue();
    }
}
