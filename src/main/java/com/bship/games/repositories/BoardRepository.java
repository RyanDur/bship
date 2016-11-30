package com.bship.games.repositories;

import com.bship.games.models.Board;
import com.bship.games.models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Component
public class BoardRepository {
    public static final int NUM_OF_BOARDS = 2;
    private JdbcTemplate template;

    @Autowired
    public BoardRepository(JdbcTemplate template) {
        this.template = template;
    }

    public List<Board> createBoards(Game game) {
        return IntStream.range(0, NUM_OF_BOARDS)
                .mapToObj(i -> save.apply(game))
                .collect(toList());
    }

    private Function<Game, Board> save = (game) -> {
        Board board = new Board();
        board.setId(getGeneratedId(game));
        return board;
    };

    private Long getGeneratedId(Game game) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        template.update(con -> {
            PreparedStatement statement = con.prepareStatement("INSERT INTO boards(game_id) VALUE(?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, game.getId());
            return statement;
        }, holder);
        return holder.getKey().longValue();
    }
}