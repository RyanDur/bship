package com.bship.games.repositories;

import com.bship.games.domains.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Optional;

@Repository
public class GameRepository {

    private final JdbcTemplate template;

    @Autowired
    public GameRepository(JdbcTemplate template) {
        this.template = template;
    }

    public Game createGame() {
        return Game.builder().withId(getGeneratedId()).build();
    }

    public Optional<Game> getGame(Long gameId) {
        return null;
    }

    public Game save(Game game) {

        return null;
    }

    private Long getGeneratedId() {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        template.update(this::getPreparedStatement, holder);
        return holder.getKey().longValue();
    }

    private PreparedStatement getPreparedStatement(Connection con) throws SQLException {
        PreparedStatement statement = con.prepareStatement("INSERT INTO games(id) VALUE (?)",
                Statement.RETURN_GENERATED_KEYS);
        statement.setNull(1, Types.INTEGER);
        return statement;
    }
}
