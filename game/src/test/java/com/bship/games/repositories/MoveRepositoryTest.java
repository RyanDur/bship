package com.bship.games.repositories;

import com.bship.DBHelper;
import com.bship.games.domains.Move;
import com.bship.games.domains.Point;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import static com.bship.games.domains.MoveStatus.HIT;
import static com.bship.games.domains.MoveStatus.MISS;
import static java.math.BigInteger.ONE;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
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
        template.update("INSERT INTO games(id) VALUE (default)", new HashMap<>());
        template.update("INSERT INTO boards(game_id) VALUE (1)", new HashMap<>());
        template.update("INSERT INTO boards(game_id) VALUE (1)", new HashMap<>());

        template.update("INSERT INTO games(id) VALUE (default)", new HashMap<>());
        template.update("INSERT INTO boards(game_id) VALUE (2)", new HashMap<>());
        template.update("INSERT INTO boards(game_id) VALUE (2)", new HashMap<>());
    }

    @Test
    public void save_shouldSaveTheListOfMoves() {
        BigInteger boardId = ONE;
        Move move1 = Move.builder()
                .withBoardId(boardId)
                .withPoint(new Point(0, 0))
                .withStatus(HIT)
                .withId(ONE)
                .build();
        Move move2 = Move.builder()
                .withBoardId(boardId)
                .withPoint(new Point(1, 0))
                .withStatus(MISS)
                .withId(ONE.add(ONE))
                .build();
        Move move3 = Move.builder()
                .withBoardId(boardId)
                .withPoint(new Point(0, 1))
                .withStatus(MISS)
                .withId(ONE.add(ONE).add(ONE))
                .build();

        List<Move> moveList = asList(move1, move2, move3);
        moves.save(moveList);
        List<Move> actual = moves.getAll(boardId);

        assertThat(actual, is(equalTo(moveList)));
    }

    @Test
    public void getAll_shouldGetAllTheMoves() {
        BigInteger boardId = ONE;
        Move move1 = Move.builder()
                .withBoardId(boardId)
                .withPoint(new Point(5, 5))
                .withStatus(HIT)
                .withId(ONE)
                .build();
        Move move2 = Move.builder()
                .withBoardId(boardId)
                .withPoint(new Point(9, 3))
                .withStatus(MISS)
                .withId(ONE.add(ONE))
                .build();
        Move move3 = Move.builder()
                .withBoardId(boardId)
                .withPoint(new Point(2, 2))
                .withStatus(HIT)
                .withId(ONE.add(ONE).add(ONE))
                .build();

        List<Move> moveList = asList(move1, move2, move3);
        moves.save(moveList);
        List<Move> actual = moves.getAll(boardId);

        assertThat(actual, is(equalTo(moveList)));
    }

    @Test
    public void getAll_shouldReturnEmptyIfNoMoves() {
        List<Move> actual = moves.getAll(ONE);

        assertThat(actual, is(empty()));
    }

    @Test
    public void getAllOpponents_shouldReturnTheOpponentsMoves() {
        BigInteger boardId1 = ONE;
        Move move11 = Move.builder()
                .withBoardId(boardId1)
                .withPoint(new Point(0, 0))
                .withStatus(HIT)
                .withId(ONE)
                .build();
        Move move21 = Move.builder()
                .withBoardId(boardId1)
                .withPoint(new Point(1, 0))
                .withStatus(MISS)
                .withId(ONE.add(ONE))
                .build();
        Move move31 = Move.builder()
                .withBoardId(boardId1)
                .withPoint(new Point(0, 1))
                .withStatus(MISS)
                .withId(ONE.add(ONE).add(ONE))
                .build();

        BigInteger boardId2 = ONE.add(ONE);
        Move move12 = Move.builder()
                .withBoardId(boardId2)
                .withPoint(new Point(5, 5))
                .withStatus(HIT)
                .withId(ONE.add(ONE).add(ONE).add(ONE))
                .build();
        Move move22 = Move.builder()
                .withBoardId(boardId2)
                .withPoint(new Point(9, 3))
                .withStatus(MISS)
                .withId(ONE.add(ONE).add(ONE).add(ONE).add(ONE))
                .build();
        Move move32 = Move.builder()
                .withBoardId(boardId2)
                .withPoint(new Point(2, 2))
                .withStatus(HIT)
                .withId(ONE.add(ONE).add(ONE).add(ONE).add(ONE).add(ONE))
                .build();
        List<Move> players = asList(move11, move21, move31);
        List<Move> opponents = asList(move12, move22, move32);

        moves.save(players);
        moves.save(opponents);

        List<Move> actual = moves.getAllOpponents(ONE, boardId1);
        assertThat(actual, is(equalTo(opponents)));
    }

    @Test
    public void getAllOpponents_shouldReturnTheOpponentsMovesFromSpecificGame() {
        BigInteger boardId11 = ONE;
        Move move111 = Move.builder()
                .withBoardId(boardId11)
                .withPoint(new Point(0, 0))
                .withStatus(HIT)
                .withId(ONE)
                .build();
        Move move211 = Move.builder()
                .withBoardId(boardId11)
                .withPoint(new Point(1, 0))
                .withStatus(MISS)
                .withId(ONE.add(ONE))
                .build();
        Move move311 = Move.builder()
                .withBoardId(boardId11)
                .withPoint(new Point(0, 1))
                .withStatus(MISS)
                .withId(ONE.add(ONE).add(ONE))
                .build();

        BigInteger boardId21 = ONE.add(ONE);
        Move move121 = Move.builder()
                .withBoardId(boardId21)
                .withPoint(new Point(5, 5))
                .withStatus(HIT)
                .withId(ONE.add(ONE).add(ONE).add(ONE))
                .build();
        Move move221 = Move.builder()
                .withBoardId(boardId21)
                .withPoint(new Point(9, 3))
                .withStatus(MISS)
                .withId(ONE.add(ONE).add(ONE).add(ONE).add(ONE))
                .build();
        Move move321 = Move.builder()
                .withBoardId(boardId21)
                .withPoint(new Point(2, 2))
                .withStatus(HIT)
                .withId(ONE.add(ONE).add(ONE).add(ONE).add(ONE).add(ONE))
                .build();
        List<Move> players1 = asList(move111, move211, move311);
        List<Move> opponents1 = asList(move121, move221, move321);

        moves.save(players1);
        moves.save(opponents1);

        BigInteger boardId32 = ONE.add(ONE).add(ONE);
        Move move112 = Move.builder()
                .withBoardId(boardId32)
                .withPoint(new Point(0, 0))
                .withStatus(HIT)
                .withId(ONE
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE))
                .build();
        Move move212 = Move.builder()
                .withBoardId(boardId32)
                .withPoint(new Point(1, 0))
                .withStatus(MISS)
                .withId(ONE
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE))
                .build();
        Move move312 = Move.builder()
                .withBoardId(boardId32)
                .withPoint(new Point(0, 1))
                .withStatus(MISS)
                .withId(ONE
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE))
                .build();

        BigInteger boardId42 = ONE.add(ONE).add(ONE).add(ONE);
        Move move122 = Move.builder()
                .withBoardId(boardId42)
                .withPoint(new Point(5, 5))
                .withStatus(HIT)
                .withId(ONE
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE))
                .build();
        Move move222 = Move.builder()
                .withBoardId(boardId42)
                .withPoint(new Point(9, 3))
                .withStatus(MISS)
                .withId(ONE
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE))
                .build();
        Move move322 = Move.builder()
                .withBoardId(boardId42)
                .withPoint(new Point(2, 2))
                .withStatus(HIT)
                .withId(ONE
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE)
                        .add(ONE))
                .build();
        List<Move> players2 = asList(move112, move212, move312);
        List<Move> opponents2 = asList(move122, move222, move322);

        moves.save(players2);
        moves.save(opponents2);

        List<Move> actual = moves.getAllOpponents(ONE.add(ONE), boardId32);
        assertThat(actual, is(equalTo(opponents2)));
    }
}