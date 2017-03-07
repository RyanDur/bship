package com.bship.games.services;

import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import com.bship.games.domains.Move;
import com.bship.games.domains.Point;
import com.bship.games.exceptions.MoveCollision;
import com.bship.games.exceptions.TurnCheck;
import com.bship.games.repositories.GameRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GameServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private GameService gameService;
    private GameRepository gameRepository;
    private GameLogic logic;

    @Before
    public void setup() {
        gameRepository = mock(GameRepository.class);
        logic = mock(GameLogic.class);
        gameService = new GameService(gameRepository, logic);
    }

    @Test
    public void getNewGame_shouldReturnANewGame() {
        when(gameRepository.createGame()).thenReturn(Game.builder().build());
        Game actualGame = gameService.getNewGame();

        assertThat(actualGame, is(instanceOf(Game.class)));
    }

    @Test
    public void placeMove_shouldThrowExceptionIfItIsNotTheBoardsTurn() throws MoveCollision, TurnCheck {
        thrown.expect(TurnCheck.class);
        thrown.expectMessage("It is not your turn.");

        Move point = Move.builder().withPoint(new Point(0, 0)).build();
        long boardId = 1L;
        long gameId = 1L;

        when(gameRepository.getGame(anyLong())).thenReturn(Optional.of(Game.builder().build()));
        when(logic.turnCheck(boardId)).thenReturn(e -> false);

        gameService.placeMove(gameId, boardId, point);
    }

    @Test
    public void placeMove_shouldPlaceAMoveOnTheBoard() throws MoveCollision, TurnCheck {
        Move point = Move.builder().withPoint(new Point(0, 0)).build();
        long boardId = 1L;
        long gameId = 1L;
        Game game = Game.builder().withId(gameId).withBoards(singletonList(
                Board.builder().withId(boardId).build()
        )).build();
        Optional<Game> gameOptional = Optional.of(game);

        when(logic.turnCheck(boardId)).thenReturn(e -> true);
        when(logic.nextTurn(anyLong())).thenReturn(e -> true);
        when(logic.playMove(any(Game.class), anyLong(), any(Move.class))).thenReturn(gameOptional);
        when(gameRepository.getGame(anyLong())).thenReturn(gameOptional);
        when(gameRepository.save(any(Game.class))).thenReturn(game);

        Game actual = gameService.placeMove(gameId, boardId, point);
        assertThat(actual, is(equalTo(game)));
    }

    @Test
    public void placeMove_shouldNotBeAbleToPlaceAMoveAtopAnother() throws MoveCollision, TurnCheck {
        thrown.expect(MoveCollision.class);
        thrown.expectMessage("Move already exists on board.");

        Move point = Move.builder().withPoint(new Point(0, 0)).build();
        long boardId = 1L;
        long gameId = 1L;
        Game game = Game.builder().withId(gameId).withBoards(singletonList(
                Board.builder().withId(boardId).build()
        )).build();
        Optional<Game> gameOptional = Optional.of(game);

        when(logic.turnCheck(boardId)).thenReturn(e -> true);
        when(gameRepository.getGame(anyLong())).thenReturn(gameOptional);
        doThrow(new MoveCollision()).when(logic).playMove(any(Game.class), anyLong(), any(Move.class));

        gameService.placeMove(gameId, boardId, point);
    }

    @Test
    public void placeMove_shouldSaveTheNextBoardsTurn() throws MoveCollision, TurnCheck {
        Move point = Move.builder().withPoint(new Point(0, 0)).build();
        long boardId = 1L;
        long otherBoardId = 2L;
        long gameId = 1L;

        Game game = Game.builder().withId(gameId).withBoards(asList(
                Board.builder().withId(boardId).build(),
                Board.builder().withId(otherBoardId).build()
        )).withTurn(boardId).build();
        Optional<Game> gameOptional = Optional.of(game);
        Game nextGame = game.copy().withTurn(otherBoardId).build();

        when(logic.turnCheck(boardId)).thenReturn(e -> true);
        when(logic.nextTurn(boardId)).thenReturn(e -> true);
        when(logic.playMove(any(Game.class), anyLong(), any(Move.class))).thenReturn(gameOptional);
        when(gameRepository.getGame(gameId)).thenReturn(gameOptional);
        when(gameRepository.save(game)).thenReturn(nextGame);

        Game actual = gameService.placeMove(gameId, boardId, point);

        verify(gameRepository).save(game);
        assertThat(actual, is(equalTo(nextGame)));
    }
}