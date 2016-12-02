package com.bship.games.repositories;

import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.util.stream.Collectors.toList;

@Repository
public class BoardRepository {
    public static final int NUM_OF_BOARDS = 2;
    private JdbcTemplate template;

    @Autowired
    public BoardRepository(JdbcTemplate template) {
        this.template = template;
    }

    public List<Board> create(Game game) {
        return IntStream.range(0, NUM_OF_BOARDS)
                .mapToObj(i -> save.apply(game))
                .collect(toList());
    }

    public Board get(Long id) {
        return template.queryForObject("SELECT * FROM boards WHERE id = ?",
                new Object[]{id},
                (rs, rowNum) -> Board.builder()
                        .withId(rs.getLong("id"))
                        .withGameId(rs.getLong("game_id")).build());
    }

    private Function<Game, Board> save = (game) -> Board.builder()
            .withId(getGeneratedId(game))
            .withGameId(game.getId())
            .build();

    private Long getGeneratedId(Game game) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        template.update(con -> prepareInsertStatement(game, con), holder);
        return holder.getKey().longValue();
    }

    private PreparedStatement prepareInsertStatement(Game game, Connection con) throws SQLException {
        PreparedStatement statement = con.prepareStatement("INSERT INTO boards(game_id) VALUE(?)",
                RETURN_GENERATED_KEYS);
        statement.setLong(1, game.getId());
        return statement;
    }
}