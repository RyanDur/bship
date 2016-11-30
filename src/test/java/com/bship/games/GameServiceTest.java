package com.bship.games;

import com.bship.games.models.Board;
import com.bship.games.models.Game;
import com.bship.games.repositories.BoardRepository;
import com.bship.games.repositories.GameRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

public class GameServiceTest {

    private GameService gameService;
    private BoardRepository mockBoardRepository;
    private GameRepository mockGameRepository;
    private Board firstStubbedBoard;
    private Board secondStubbedBoard;

    @Before
    public void setup() {
        mockBoardRepository = Mockito.mock(BoardRepository.class);
        mockGameRepository = Mockito.mock(GameRepository.class);
        Game game = Game.builder().build();
        firstStubbedBoard = Board.builder().build();
        secondStubbedBoard = Board.builder().build();

        when(mockGameRepository.createGame()).thenReturn(game);
        when(mockBoardRepository.createBoards(game)).thenReturn(Arrays.asList(firstStubbedBoard, secondStubbedBoard));

        gameService = new GameService(mockBoardRepository, mockGameRepository);
    }

    @Test
    public void getNewGame_shouldReturnANewGame() {
        Game actualGame = gameService.getNewGame();

        assertThat(actualGame, is(instanceOf(Game.class)));
    }

    @Test
    public void getNewGame_shouldCreateTwoNewBoardsAndAssociateThemWithTheGame() {
        Game actualGame = gameService.getNewGame();

        assertThat(actualGame.getBoards(), contains(firstStubbedBoard, secondStubbedBoard));
    }
}