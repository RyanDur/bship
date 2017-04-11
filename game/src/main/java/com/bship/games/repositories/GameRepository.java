package com.bship.games.repositories;

import com.bship.games.domains.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Optional.of;

@Repository
public class GameRepository implements SQL {

    private final NamedParameterJdbcTemplate template;
    private final BoardRepository boards;

    @Autowired
    public GameRepository(NamedParameterJdbcTemplate template,
                          BoardRepository boardRepository) {
        this.template = template;
        this.boards = boardRepository;
    }

    public Game create() {
        Long id = generateGame();
        return Game.builder().withId(id)
                .withBoards(asList(boards.create(id), boards.create(id)))
                .build();
    }

    public Optional<Game> get(Long id) {
        return template.query(join(SEP, SELECT_ALL, FROM_GAMES, WHERE, ID),
                new MapSqlParameterSource("id", id),
                buildGame(boards))
                .stream()
                .findFirst();
    }

    public List<Game> getAll() {
        return template.query(join(SEP, SELECT_ALL, FROM_GAMES),
                buildGame(boards));
    }

    public Optional<Game> delete(Game game) {
        template.update(join(SEP, DELETE, FROM_GAMES, WHERE, ID),
                new MapSqlParameterSource("id", game.getId()));
        return of(game);
    }

    public Optional<Game> save(Game game) {
        game.getBoards().forEach(boards::save);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("id", game.getId());
        source.addValue("turn", game.getTurn());
        source.addValue("over", game.isOver());
        template.update(join(SEP, UPDATE_GAMES, SET, join(COMMA, TURN, OVER), WHERE, ID), source);
        return get(game.getId());
    }

    private Long generateGame() {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        template.update(join(SEP, INSERT_INTO, GAMES, VALUE_DEFAULT), null, holder);
        return holder.getKey().longValue();
    }

    private RowMapper<Game> buildGame(BoardRepository boards) {
        return (rs, rowNum) -> Game.builder()
                .withId(rs.getLong("id"))
                .withBoards(boards.getAll(rs.getLong("id")))
                .withOver(rs.getBoolean("over"))
                .withTurn(of(rs.getLong("turn"))
                        .filter(turn -> turn.compareTo(0L) > 0)
                        .orElse(null))
                .build();
    }
}
