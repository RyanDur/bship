package com.bship.games;

import com.bship.games.domains.Game;
import com.bship.games.domains.Point;
import com.bship.games.domains.Ship;
import com.bship.games.services.GameService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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

        mockMvc
                .perform(post("/games"))
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
    public void placeShip_methodSignatureBindToPathParamsAndRequestBody() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(createGameController).build();
        mockMvc.perform(post("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"type\": \"BATTLESHIP\",\n" +
                        "  \"start\": {\n" +
                        "    \"x\": 9,\n" +
                        "    \"y\": 5\n" +
                        "  },\n" +
                        "  \"end\": {\n" +
                        "    \"x\": 1,\n" +
                        "    \"y\": 5\n" +
                        "  }\n" +
                        "}"
                ));

        ArgumentCaptor<Ship> captor = ArgumentCaptor.forClass(Ship.class);

        verify(mockService).placeShip( eq(9L), captor.capture());
        Ship capturedShip = captor.getValue();
        assertEquals(Harbor.BATTLESHIP, capturedShip.getShipType());
        assertEquals(9, capturedShip.getStart().getX());
        assertEquals(5, capturedShip.getStart().getY());
        assertEquals(1, capturedShip.getEnd().getX());
        assertEquals(5, capturedShip.getEnd().getY());
    }

    @Test
    public void placeShip_passesTheShipObjectToTheServiceLayerForTheGivenGameAndBoard() {
        Ship ship = Ship.builder()
                .withShipType(Harbor.SUBMARINE)
                .withStart(new Point())
                .withEnd(new Point()).build();

        Long boardId = 90L;
        createGameController.placeShip(boardId, ship);

        verify(mockService).placeShip(boardId, ship);
    }
}