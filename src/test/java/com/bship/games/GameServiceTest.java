package com.bship.games;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GameServiceTest {

    private GameService gameService;
    private BoardRepository mockBoardRepository;

    @Before
    public void setup() {
        mockBoardRepository = Mockito.mock(BoardRepository.class);
        gameService = new GameService(mockBoardRepository);
    }

    @Test
    public void getNewGame_shouldReturnANewGame() {
        Game actualGame = gameService.getNewGame();

        assertThat(actualGame, is(instanceOf(Game.class)));
    }

    @Test
    public void getNewGame_shouldCreateTwoNewBoardsAndAssociateThemWithTheGame() {
        Board firstStubbedBoard = new Board();
        Board secondStubbedBoard = new Board();
        Mockito.when(mockBoardRepository.createBoards()).thenReturn(Arrays.asList(firstStubbedBoard, secondStubbedBoard));

        Game actualGame = gameService.getNewGame();

        assertThat(actualGame.getBoards().get(0), is(sameInstance(firstStubbedBoard)));
        assertThat(actualGame.getBoards().get(1), is(sameInstance(secondStubbedBoard)));
    }
}