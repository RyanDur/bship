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

import static org.hamcrest.MatcherAssert.assertThat;
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
        Game game = Game.builder().withId(1L).build();
        board = Board.builder().withId(1L).withGameId(game.getId()).build();
        template.update("INSERT INTO games(id) VALUE(?) ", game.getId());
        template.update("INSERT INTO boards(id, game_id) VALUE(?, ?) ", board.getId(), board.getGameId());
    }

    @Test
    public void create_shouldSaveAMoveInTheRepo() {
        Point point = new Point(1, 2);

        Move actual = repository.create(board.getId(), point);

        Move move = template.queryForObject("SELECT * FROM moves WHERE move_board_id = " + board.getId(),
                (rs, rowNum) -> Move.builder()
                        .withId(rs.getLong("id"))
                        .withBoardId(rs.getLong("move_board_id"))
                        .withPoint(Util.toPoint(rs.getInt("point")))
                        .withStatus(MoveStatus.valueOf(rs.getString("status"))).build());

        assertThat(actual, is(equalTo(move)));
    }
}