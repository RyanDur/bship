package com.bship.games.repositories;

import com.bship.games.domains.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Optional.of;

@Repository
public class GameRepository {

    private static final String SELECT_FROM_GAMES = "SELECT * FROM games";
    private static final String WHERE = "WHERE";
    private static final String ID_ID = "id = :id";
    private static final String INSERT_INTO_GAMES_ID = "INSERT INTO games(id)";
    private static final String VALUE_DEFAULT = "VALUE (default)";
    private static final String SEP = " ";
    private static final String UPDATE_GAMES = "UPDATE Games";
    private static final String SET = "SET";
    private static final String TURN_TURN = "turn = :turn";
    private final NamedParameterJdbcTemplate template;
    private final BoardRepository boards;

    @Autowired
    public GameRepository(NamedParameterJdbcTemplate template,
                          BoardRepository boardRepository) {
        this.template = template;
        this.boards = boardRepository;
    }

    public Game create() {
        BigInteger id = generateGame();
        return Game.builder().withId(id)
                .withBoards(asList(boards.create(id), boards.create(id)))
                .build();
    }

    public Optional<Game> get(BigInteger id) {
        return template.query(join(SELECT_FROM_GAMES, WHERE, ID_ID),
                new MapSqlParameterSource("id", id),
                buildGame(boards))
                .stream()
                .findFirst();
    }

    public List<Game> getAll() {
        return template.query(SELECT_FROM_GAMES, buildGame(boards));
    }

    public Optional<Game> save(Game game) {
        game.getBoards().forEach(boards::save);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("id", game.getId());
        source.addValue("turn", game.getTurn());
        template.update(join(UPDATE_GAMES, SET, TURN_TURN, WHERE, ID_ID), source);
        return get(game.getId());
    }

    private BigInteger generateGame() {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        template.update(join(INSERT_INTO_GAMES_ID, VALUE_DEFAULT), null, holder);
        return BigInteger.valueOf(holder.getKey().longValue());
    }

    private String join(String... sqls) {
        return Stream.of(sqls).collect(Collectors.joining(SEP));
    }

    private RowMapper<Game> buildGame(BoardRepository boards) {
        return (rs, rowNum) -> Game.builder()
                .withId(BigInteger.valueOf(rs.getLong("id")))
                .withBoards(boards.getAll(BigInteger.valueOf(rs.getLong("id"))))
                .withTurn(of(BigInteger.valueOf(rs.getLong("turn")))
                        .filter(turn -> turn.compareTo(BigInteger.ZERO) > 0)
                        .orElse(null))
                .build();
    }
}
