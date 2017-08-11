package com.bship.games.services;

import com.bship.games.domains.Game;
import com.bship.games.domains.Move;
import com.bship.games.domains.GameRules;
import com.bship.games.exceptions.GameValidation;
import com.bship.games.exceptions.InvalidGame;
import com.bship.games.logic.GameLogic;
import com.bship.games.repositories.GameRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Optional;

import static com.bship.games.domains.GameRules.BATTLESHIP;
import static java.util.Optional.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GameServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private GameService gameService;
    private GameRepository gameRepository;
    private GameLogic logic;
    private GameRules game;

    @Before
    public void setup() {
        game = BATTLESHIP;
        gameRepository = mock(GameRepository.class);
        logic = mock(GameLogic.class);
        gameService = new GameService(gameRepository, logic);
    }

    @Test
    public void getNewGame_shouldReturnANewGame() {
        when(gameRepository.create(game)).thenReturn(Game.builder().build());
        Game actualGame = gameService.getNewGame(BATTLESHIP);

        assertThat(actualGame, is(instanceOf(Game.class)));
    }

    @Test
    public void placeMove_shouldGetTheGame() throws GameValidation {
        Optional<Game> game = of(Game.builder().build());
        when(gameRepository.get(1L)).thenReturn(game);
        when(logic.valid(any(Move.class))).thenReturn(g -> g);
        when(logic.play(any(Move.class))).thenReturn(Optional::of);
        when(logic.setNextTurn(any(Move.class))).thenReturn(g -> g);
        when(gameRepository.save(any(Game.class))).thenReturn(game);

        gameService.placeMove(1L, Move.builder().build());
        verify(gameRepository).get(1L);
    }

    @Test
    public void placeMove_shouldThrowExceptionIfFetchedGameDoesNotExist() throws GameValidation {
        thrown.expect(InvalidGame.class);
        thrown.expectMessage("Game Does Not Exist!");

        Optional<Game> game = of(Game.builder().build());
        when(gameRepository.get(1L)).thenReturn(Optional.empty());
        when(logic.valid(any(Move.class))).thenReturn(g -> g);
        when(logic.play(any(Move.class))).thenReturn(Optional::of);
        when(logic.setNextTurn(any(Move.class))).thenReturn(g -> g);
        when(gameRepository.save(any(Game.class))).thenReturn(game);

        gameService.placeMove(1L, Move.builder().build());
    }

    @Test
    public void placeMove_shouldCheckTheMovesTurn() throws GameValidation {
        Optional<Game> game = of(Game.builder().build());
        Move move = Move.builder().build();
        when(gameRepository.get(1L)).thenReturn(game);
        when(logic.valid(any(Move.class))).thenReturn(g -> g);
        when(logic.play(any(Move.class))).thenReturn(Optional::of);
        when(logic.setNextTurn(any(Move.class))).thenReturn(g -> g);
        when(gameRepository.save(any(Game.class))).thenReturn(game);

        gameService.placeMove(1L, move);
        verify(logic).valid(move);
    }

    @Test
    public void placeMove_shouldPlayTheMove() throws GameValidation {
        Optional<Game> game = of(Game.builder().build());
        Move move = Move.builder().build();
        when(gameRepository.get(1L)).thenReturn(game);
        when(logic.valid(any(Move.class))).thenReturn(g -> g);
        when(logic.play(any(Move.class))).thenReturn(Optional::of);
        when(logic.setNextTurn(any(Move.class))).thenReturn(g -> g);
        when(gameRepository.save(any(Game.class))).thenReturn(game);

        gameService.placeMove(1L, move);
        verify(logic).play(move);
    }

    @Test
    public void placeMove_shouldSetTheNextTurn() throws GameValidation {
        Optional<Game> game = of(Game.builder().build());
        Move move = Move.builder().build();
        when(gameRepository.get(1L)).thenReturn(game);
        when(logic.valid(any(Move.class))).thenReturn(g -> g);
        when(logic.play(any(Move.class))).thenReturn(Optional::of);
        when(logic.setNextTurn(any(Move.class))).thenReturn(g -> g);
        when(gameRepository.save(any(Game.class))).thenReturn(game);

        gameService.placeMove(1L, move);
        verify(logic).setNextTurn(move);
    }

    @Test
    public void placeMove_shouldSaveTheGame() throws GameValidation {
        Optional<Game> game = of(Game.builder().build());
        Move move = Move.builder().build();
        when(gameRepository.get(1L)).thenReturn(game);
        when(logic.valid(any(Move.class))).thenReturn(g -> g);
        when(logic.play(any(Move.class))).thenReturn(Optional::of);
        when(logic.setNextTurn(any(Move.class))).thenReturn(g -> g);
        when(gameRepository.save(any(Game.class))).thenReturn(game);

        gameService.placeMove(1L, move);
        verify(gameRepository).save(game.get());
    }

    @Test
    public void placeMove_shouldDeleteTheGameIfItIsOver() throws GameValidation {
        Optional<Game> game = of(Game.builder().withOver(true).build());
        Move move = Move.builder().build();
        when(gameRepository.get(1L)).thenReturn(game);
        when(logic.valid(any(Move.class))).thenReturn(g -> g);
        when(logic.play(any(Move.class))).thenReturn(Optional::of);
        when(logic.setNextTurn(any(Move.class))).thenReturn(g -> g);
        when(gameRepository.delete(any(Game.class))).thenReturn(game);

        gameService.placeMove(1L, move);
        verify(gameRepository, never()).save(game.get());
        verify(gameRepository).delete(game.get());
    }

    @Test
    public void placeMove_shouldThrowExceptionIfSavedGameDoesNotExist() throws GameValidation {
        thrown.expect(InvalidGame.class);
        thrown.expectMessage("Game Does Not Exist!");

        Optional<Game> game = of(Game.builder().build());
        when(gameRepository.get(1L)).thenReturn(game);
        when(logic.valid(any(Move.class))).thenReturn(g -> g);
        when(logic.play(any(Move.class))).thenReturn(Optional::of);
        when(logic.setNextTurn(any(Move.class))).thenReturn(g -> g);
        when(gameRepository.save(any(Game.class))).thenReturn(Optional.empty());

        gameService.placeMove(1L, Move.builder().build());
    }

    @Test
    public void placeMove_shouldReturnTheGame() throws GameValidation {
        Optional<Game> game = of(Game.builder().build());
        Move move = Move.builder().build();
        when(gameRepository.get(1L)).thenReturn(game);
        when(logic.valid(any(Move.class))).thenReturn(g -> g);
        when(logic.play(any(Move.class))).thenReturn(Optional::of);
        when(logic.setNextTurn(any(Move.class))).thenReturn(g -> g);
        when(gameRepository.save(any(Game.class))).thenReturn(game);

        Game actual = gameService.placeMove(1L, move);
        assertThat(actual, is(equalTo(game.get())));
    }
}