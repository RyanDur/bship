package com.bship.games.endpoints;

import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import com.bship.games.domains.Move;
import com.bship.games.domains.Point;
import com.bship.games.endpoints.RequestErrors.GameErrors;
import com.bship.games.endpoints.RequestErrors.ObjectValidation;
import com.bship.games.services.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableWebMvc
public class GameControllerTest {

    private GameController gameController;
    private GameService mockService;
    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @Before
    public void setup() {
        mockService = mock(GameService.class);
        gameController = new GameController(mockService);
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void createGame_shouldDefineRequestMappingForPostingToGamesEndpoint() throws Exception {
        mockMvc.perform(post("/games"));
    }

    @Test
    public void createGame_shouldRespondWith201() throws Exception {
        mockMvc.perform(post("/games"))
                .andExpect(status().isCreated());
    }

    @Test
    public void createGame_shouldProduceJSONWithAUTF8Charset() throws Exception {
        Game expected = Game.builder().build();
        when(mockService.getNewGame()).thenReturn(expected);
        mockMvc.perform(post("/games"))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void createGame_shouldReturnAGame() throws Exception {
        Game expected = Game.builder().withBoards(Collections.emptyList()).build();
        when(mockService.getNewGame()).thenReturn(expected);

        Game actual = mapper.readValue(mockMvc.perform(post("/games"))
                .andReturn()
                .getResponse()
                .getContentAsString(), Game.class);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void placeMove_shouldDefineRequestMappingForPuttingAMoveOntoABoardWithRespectToAGame() throws Exception {
        mockMvc.perform(put("/games/1/boards/1"));
    }

    @Test
    public void placeMove_shouldBeAbleToPlaceAMoveOnTheBoard() throws Exception {
        Move move = new Move();
        Point point = new Point(0,0);

        when(mockService.placeMove(anyLong(), anyLong(), any(Point.class)))
                .thenReturn(Board.builder().addMove(move).build());

        Board actual = mapper.readValue(mockMvc.perform(put("/games/1/boards/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(point.toString()))
                .andReturn()
                .getResponse()
                .getContentAsString(), Board.class);

        assertThat(actual.getMoves(), contains(move));
    }

    @Test
    public void placeMove_shouldNotAllowTheXToBeLessThanTheWithOfTheBoard() throws Exception {
        Move move = new Move();
        Point point = new Point(-1,0);

        when(mockService.placeMove(anyLong(), anyLong(), any(Point.class)))
                .thenReturn(Board.builder().addMove(move).build());

        GameErrors actual = mapper.readValue(mockMvc.perform(put("/games/1/boards/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(point.toString()))
                .andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        ObjectMapper mapper = new ObjectMapper();

        List errors = actual.getErrors();
        ObjectValidation error = mapper.convertValue(errors.get(0), ObjectValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), is("out of bounds."));
    }
}