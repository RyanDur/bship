package com.bship.games.endpoints;

import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import com.bship.games.domains.Move;
import com.bship.games.domains.Point;
import com.bship.games.endpoints.RequestErrors.FieldValidation;
import com.bship.games.endpoints.RequestErrors.GameErrors;
import com.bship.games.endpoints.RequestErrors.ObjectValidation;
import com.bship.games.exceptions.MoveCollision;
import com.bship.games.exceptions.TurnCheck;
import com.bship.games.services.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.math.BigInteger;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableWebMvc
public class GameControllerTest {

    private GameService mockService;
    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @Before
    public void setup() {
        mockService = mock(GameService.class);
        GameController gameController = new GameController(mockService);
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
        mapper = new ObjectMapper();
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
        Point point = new Point(0, 0);
        Move move = Move.builder().withPoint(point).build();

        when(mockService.placeMove(any(BigInteger.class), any(Move.class)))
                .thenReturn(Game.builder()
                        .withBoards(Collections.singletonList(Board.builder().addMove(move).build())).build());

        String content = mockMvc.perform(patch("/games/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(move.toString()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Game actual = mapper.readValue(content, Game.class);

        assertThat(actual.getBoards().get(0).getMoves(), contains(move));
    }

    @Test
    public void placeMove_shouldNotAllowTheXToBeLessThanTheWidthOfTheBoard() throws Exception {
        Point point = new Point(-1, 0);
        Move move = Move.builder().withPoint(point).build();

        GameErrors actual = mapper.readValue(mockMvc.perform(patch("/games/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(move.toString()))
                .andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), is("out of bounds."));
    }

    @Test
    public void placeMove_shouldNotAllowTheXToBeGreaterThanTheHeightOfTheBoard() throws Exception {
        Point point = new Point(10, 0);
        Move move = Move.builder().withPoint(point).build();

        when(mockService.placeMove(any(BigInteger.class), any(Move.class)))
                .thenReturn(Game.builder()
                        .withBoards(Collections.singletonList(Board.builder().addMove(move).build())).build());

        GameErrors actual = mapper.readValue(mockMvc.perform(patch("/games/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(move.toString()))
                .andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), is("out of bounds."));
    }

    @Test
    public void placeMove_shouldNotAllowTheYToBeLessThanTheWidthOfTheBoard() throws Exception {
        Point point = new Point(0, -1);
        Move move = Move.builder().withPoint(point).build();

        when(mockService.placeMove(any(BigInteger.class), any(Move.class)))
                .thenReturn(Game.builder()
                        .withBoards(Collections.singletonList(Board.builder().addMove(move).build())).build());

        GameErrors actual = mapper.readValue(mockMvc.perform(patch("/games/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(move.toString()))
                .andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), is("out of bounds."));
    }

    @Test
    public void placeMove_shouldNotAllowTheYToBeGreaterThanTheHeightOfTheBoard() throws Exception {
        Point point = new Point(0, 10);
        Move move = Move.builder().withPoint(point).build();

        when(mockService.placeMove(any(BigInteger.class), any(Move.class)))
                .thenReturn(Game.builder()
                        .withBoards(Collections.singletonList(Board.builder().addMove(move).build())).build());

        GameErrors actual = mapper.readValue(mockMvc.perform(patch("/games/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(move.toString()))
                .andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), is("out of bounds."));
    }

    @Test
    public void placeMove_shouldHandleMoveCollisions() throws Exception {
        Point point = new Point(0, 0);
        Move move = Move.builder().withPoint(point).build();

        doThrow(new MoveCollision())
                .when(mockService).placeMove(any(BigInteger.class), any(Move.class));

        GameErrors actual = mapper.readValue(mockMvc.perform(patch("/games/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(move.toString()))
                .andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        ObjectValidation error = mapper.convertValue(actual.getErrors().get(0), ObjectValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), is("Move already exists on board."));
    }

    @Test
    public void placeMove_shouldHandleTurnChecks() throws Exception {
        Point point = new Point(0, 0);
        Move move = Move.builder().withPoint(point).build();

        doThrow(new TurnCheck())
                .when(mockService).placeMove(any(BigInteger.class), any(Move.class));

        GameErrors actual = mapper.readValue(mockMvc.perform(patch("/games/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(move.toString()))
                .andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        ObjectValidation error = mapper.convertValue(actual.getErrors().get(0), ObjectValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), is("It is not your turn."));
    }
}