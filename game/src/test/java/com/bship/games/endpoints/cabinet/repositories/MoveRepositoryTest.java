package com.bship.games.endpoints.cabinet.repositories;

import com.bship.DBHelper;
import com.bship.games.endpoints.cabinet.entity.Move;
import com.bship.games.endpoints.cabinet.entity.Point;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;

import static com.bship.games.logic.rules.MoveStatus.HIT;
import static com.bship.games.logic.rules.MoveStatus.MISS;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class MoveRepositoryTest {

    private NamedParameterJdbcTemplate template;
    private MoveRepository moves;

    @Before
    public void setup() {
        template = new NamedParameterJdbcTemplate(DBHelper.reset());
        moves = new MoveRepository(template);
        template.update("INSERT INTO games(id, name) VALUE (default, 'BATTLESHIP')", new HashMap<>());
        template.update("INSERT INTO boards(game_id) VALUE (1)", new HashMap<>());
        template.update("INSERT INTO boards(game_id) VALUE (1)", new HashMap<>());

        template.update("INSERT INTO games(id, name) VALUE (default, 'BATTLESHIP')", new HashMap<>());
        template.update("INSERT INTO boards(game_id) VALUE (2)", new HashMap<>());
        template.update("INSERT INTO boards(game_id) VALUE (2)", new HashMap<>());
    }

    @Test
    public void shouldSaveTheListOfMoves() {
        long boardId = 1L;
        Move move1 = Move.builder()
                .withBoardId(boardId)
                .withPoint(new Point(0, 0))
                .withStatus(HIT)
                .build();
        Move move2 = Move.builder()
                .withBoardId(boardId)
                .withPoint(new Point(1, 0))
                .withStatus(MISS)
                .build();
        Move move3 = Move.builder()
                .withBoardId(boardId)
                .withPoint(new Point(0, 1))
                .withStatus(MISS)
                .build();

        List<Move> moveList = asList(move1, move2, move3);
        moves.save(moveList);
        List<Move> actual = moves.getAll(boardId);

        assertThat(actual, containsInAnyOrder(
                move1.copy().withId(1L).build(),
                move2.copy().withId(2L).build(),
                move3.copy().withId(3L).build()
        ));
    }

    @Test
    public void shouldReturnEmptyIfNoMoves() {
        List<Move> actual = moves.getAll(1L);

        assertThat(actual, is(empty()));
    }

    @Test
    public void shouldSaveTheNewMove() {
        long boardId = 1L;
        Move move1 = Move.builder()
                .withBoardId(boardId)
                .withPoint(new Point(0, 0))
                .withStatus(HIT)
                .build();
        Move move2 = Move.builder()
                .withBoardId(boardId)
                .withPoint(new Point(1, 0))
                .withStatus(MISS)
                .build();
        Move move3 = Move.builder()
                .withBoardId(boardId)
                .withPoint(new Point(0, 1))
                .withStatus(MISS)
                .build();

        List<Move> moveList = asList(move1, move2, move3);
        moves.save(moveList);
        List<Move> actual = moves.getAll(boardId);

        assertThat(actual.size(), is(3));
        assertThat(actual, containsInAnyOrder(
                move1.copy().withId(1L).build(),
                move2.copy().withId(2L).build(),
                move3.copy().withId(3L).build()
        ));

        Move move4 = Move.builder()
                .withBoardId(boardId)
                .withPoint(new Point(1, 1))
                .withStatus(MISS)
                .build();
        actual.add(move4);
        moves.save(actual);
        List<Move> newActual = moves.getAll(boardId);

        assertThat(newActual.size(), is(4));
        assertThat(newActual, containsInAnyOrder(
                move1.copy().withId(1L).build(),
                move2.copy().withId(2L).build(),
                move3.copy().withId(3L).build(),
                move4.copy().withId(4L).build()
        ));
    }

    @Test
    public void shouldReturnTheOpponentsMoves() {
        long boardId1 = 1L;
        Move move11 = Move.builder()
                .withBoardId(boardId1)
                .withPoint(new Point(0, 0))
                .withStatus(HIT)
                .build();

        Move move21 = Move.builder()
                .withBoardId(boardId1)
                .withPoint(new Point(1, 0))
                .withStatus(MISS)
                .build();

        Move move31 = Move.builder()
                .withBoardId(boardId1)
                .withPoint(new Point(0, 1))
                .withStatus(MISS)
                .build();

        long boardId2 = 2L;
        Move move12 = Move.builder()
                .withBoardId(boardId2)
                .withPoint(new Point(5, 5))
                .withStatus(HIT)
                .build();

        Move move22 = Move.builder()
                .withBoardId(boardId2)
                .withPoint(new Point(9, 3))
                .withStatus(MISS)
                .build();

        Move move32 = Move.builder()
                .withBoardId(boardId2)
                .withPoint(new Point(2, 2))
                .withStatus(HIT)
                .build();

        List<Move> players = asList(move11, move21, move31);
        List<Move> opponents = asList(move12, move22, move32);

        moves.save(players);
        moves.save(opponents);

        List<Move> expected = asList(
                move12.copy().withId(4L).build(),
                move22.copy().withId(5L).build(),
                move32.copy().withId(6L).build());

        List<Move> actual = moves.getAllOpponents(1L, boardId1);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void shouldReturnTheOpponentsMovesFromSpecificGame() {
        long boardId11 = 1L;
        Move move111 = Move.builder()
                .withBoardId(boardId11)
                .withPoint(new Point(0, 0))
                .withStatus(HIT)
                .build();
        Move move211 = Move.builder()
                .withBoardId(boardId11)
                .withPoint(new Point(1, 0))
                .withStatus(MISS)
                .build();
        Move move311 = Move.builder()
                .withBoardId(boardId11)
                .withPoint(new Point(0, 1))
                .withStatus(MISS)
                .build();

        long boardId21 = 2L;
        Move move121 = Move.builder()
                .withBoardId(boardId21)
                .withPoint(new Point(5, 5))
                .withStatus(HIT)
                .build();
        Move move221 = Move.builder()
                .withBoardId(boardId21)
                .withPoint(new Point(9, 3))
                .withStatus(MISS)
                .build();
        Move move321 = Move.builder()
                .withBoardId(boardId21)
                .withPoint(new Point(2, 2))
                .withStatus(HIT)
                .build();

        moves.save(asList(move111, move211, move311));
        moves.save(asList(move121, move221, move321));

        long boardId32 = 3L;
        Move move112 = Move.builder()
                .withBoardId(boardId32)
                .withPoint(new Point(0, 0))
                .withStatus(HIT)
                .build();
        Move move212 = Move.builder()
                .withBoardId(boardId32)
                .withPoint(new Point(1, 0))
                .withStatus(MISS)
                .build();
        Move move312 = Move.builder()
                .withBoardId(boardId32)
                .withPoint(new Point(0, 1))
                .withStatus(MISS)
                .build();

        long boardId42 = 4L;
        Move move122 = Move.builder()
                .withBoardId(boardId42)
                .withPoint(new Point(5, 5))
                .withStatus(HIT)
                .build();
        Move move222 = Move.builder()
                .withBoardId(boardId42)
                .withPoint(new Point(9, 3))
                .withStatus(MISS)
                .build();
        Move move322 = Move.builder()
                .withBoardId(boardId42)
                .withPoint(new Point(2, 2))
                .withStatus(HIT)
                .build();

        moves.save(asList(move112, move212, move312));
        moves.save(asList(move122, move222, move322));

        List<Move> opponents = asList(
                move122.copy().withId(10L).build(),
                move222.copy().withId(11L).build(),
                move322.copy().withId(12L).build());

        List<Move> actual = moves.getAllOpponents(2L, boardId32);
        assertThat(actual, is(equalTo(opponents)));
    }
}