package com.bship.games;

import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import com.bship.games.domains.Move;
import com.bship.games.domains.Point;
import com.bship.games.endpoints.GameController;
import com.bship.games.exceptions.MoveCollision;
import com.bship.games.services.GameService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GameControllerTest {

    private GameController createGameController;
    private GameService mockService;

    @Before
    public void setup() {
        mockService = mock(GameService.class);
        createGameController = new GameController(mockService);
    }

    @Test
    public void createGame_shouldDefineRequestMappingForPostingToGamesEndpoint() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(createGameController).build();

        mockMvc.perform(post("/games"))
                .andExpect(status().isCreated());
    }

    @Test
    public void createGame_shouldReturnAGame() {
        Game expected = Game.builder().build();
        when(mockService.getNewGame()).thenReturn(expected);
        Game actual = createGameController.createGame();

        assertThat(actual, is(sameInstance(expected)));
    }

    @Test
    public void placeMove_shouldBeAbleToPlaceAMoveOnTheBoard() throws MoveCollision {
        Move move = new Move();
        Point point = new Point();
        when(mockService.placeMove(anyLong(), anyLong(), any(Point.class)))
                .thenReturn(Optional.of(Board.builder().addMove(move).build()));

        Board actual = createGameController.placeMove(1L, 1L, point);

        assertThat(actual.getMoves(), contains(move));
    }
}