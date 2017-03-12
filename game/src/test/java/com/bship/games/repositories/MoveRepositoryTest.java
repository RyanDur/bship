package com.bship.games.repositories;

import com.bship.DBHelper;
import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import com.bship.games.domains.Move;
import com.bship.games.domains.MoveStatus;
import com.bship.games.domains.Point;
import com.bship.games.util.Util;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.bship.games.domains.MoveStatus.HIT;
import static com.bship.games.domains.MoveStatus.MISS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class MoveRepositoryTest {

    private JdbcTemplate template;
    private Board board;
    private MoveRepository repository;

    @Before
    public void setup() {
        template = new JdbcTemplate(DBHelper.reset());
        repository = new MoveRepository(template);
        Game game = Game.builder().withId(BigInteger.valueOf(1L)).build();
        board = Board.builder().withId(BigInteger.valueOf(1L)).withGameId(game.getId()).build();
        template.update("INSERT INTO games(id) VALUE(?) ", game.getId());
        template.update("INSERT INTO boards(id, game_id) VALUE(?, ?) ", board.getId(), board.getGameId());
    }

    @Test
    public void create_shouldSaveAMoveInTheRepo() {
        Point point = new Point(1, 2);

        Optional<Move> actual = repository.create(board.getId(), point, MISS);

        Move move = template.queryForObject("SELECT * FROM moves WHERE move_board_id = " + board.getId(),
                moveRowMapper);

        assertThat(actual.get(), is(equalTo(move)));
    }

    @Test
    public void getMoves_shouldGetAllTheMovesForABoard() {
        Point point = new Point(1, 2);
        Point point1 = new Point(1, 3);
        Point point2 = new Point(1, 4);

        Optional<Move> move1 = repository.create(board.getId(), point, HIT);
        Optional<Move> move2 = repository.create(board.getId(), point1, HIT);
        Optional<Move> move3 = repository.create(board.getId(), point2, HIT);

        Optional<List<Move>> actual = repository.getAll(board.getId());

        assertThat(actual.get().size(), is(3));
        assertThat(actual.get(), contains(move1.get(), move2.get(), move3.get()));
    }

    private RowMapper<Move> moveRowMapper = (rs, rowNum) -> Move.builder()
            .withId(BigInteger.valueOf(rs.getLong("id")))
            .withBoardId(BigInteger.valueOf(rs.getLong("move_board_id")))
            .withPoint(Util.toPoint(rs.getInt("point")))
            .withStatus(MoveStatus.valueOf(rs.getString("status"))).build();
}