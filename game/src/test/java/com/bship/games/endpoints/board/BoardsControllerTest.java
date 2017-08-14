package com.bship.games.endpoints.board;

import com.bship.games.endpoints.cabinet.entity.Board;
import com.bship.games.endpoints.cabinet.entity.Piece;
import com.bship.games.endpoints.cabinet.entity.Point;
import com.bship.games.endpoints.errors.RequestErrors.FieldValidation;
import com.bship.games.endpoints.errors.RequestErrors.GameErrors;
import com.bship.games.endpoints.errors.RequestErrors.ObjectValidation;
import com.bship.games.endpoints.errors.RequestErrors.ValidationFieldError;
import com.bship.games.endpoints.board.errors.exceptions.ShipCollisionCheck;
import com.bship.games.endpoints.board.errors.exceptions.ShipExistsCheck;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.bship.games.logic.definitions.Direction.DOWN;
import static com.bship.games.logic.definitions.Direction.LEFT;
import static com.bship.games.logic.definitions.Direction.RIGHT;
import static com.bship.games.logic.definitions.Direction.UP;
import static com.bship.games.logic.definitions.Harbor.BATTLESHIP;
import static com.bship.games.logic.definitions.Harbor.DESTROYER;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BoardsControllerTest {

    private BoardService mockService;
    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @Before
    public void setup() {
        mockService = mock(BoardService.class);
        BoardsController boardsController = new BoardsController(mockService);
        mockMvc = MockMvcBuilders.standaloneSetup(boardsController).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void shouldHaveARoute() throws Exception {
        mockMvc.perform(put("/boards/9"));
    }

    @Test
    public void shouldProduceJSONWithCharsetUTF8() throws Exception {
        when(mockService.placePiece(anyLong(), anyListOf(Piece.class)))
                .thenReturn(Board.builder().build());
        List<Piece> pieces = singletonList(Piece.builder()
                .withId(1L)
                .withType(BATTLESHIP)
                .withPlacement(new Point(4, 5))
                .withOrientation(LEFT).build());

        mockMvc.perform(put("/boards/9").contentType(MediaType.APPLICATION_JSON)
                .content(pieces.toString()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void shouldRespondWith200() throws Exception {
        when(mockService.placePiece(anyLong(), anyListOf(Piece.class)))
                .thenReturn(Board.builder().build());

        List<Piece> pieces = singletonList(Piece.builder()
                .withId(1L)
                .withType(DESTROYER)
                .withPlacement(new Point(0, 0))
                .withOrientation(DOWN)
                .build());

        mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pieces.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldRequireATypeOfShip() throws Exception {
        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{" +
                        "  \"id\": 1," +
                        "  \"size\": 2," +
                        "  \"orientation\": \"DOWN\"," +
                        "  \"placement\": {" +
                        "    \"x\": 1," +
                        "    \"y\": 5" +
                        "  }" +
                        "}]"
                )).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        List<String> messages = getFieldErrorMessages(actual);
        assertThat(messages.contains("Missing piece type."), Matchers.is(true));
    }

    @Test
    public void shouldRequireAnExistingTypeOfShip() throws Exception {
        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{" +
                        "    \"id\": 1," +
                        "    \"type\": \"GAS_STATION\"," +
                        "    \"orientation\": \"DOWN\"," +
                        "    \"placement\": {" +
                        "      \"x\": 0," +
                        "      \"y\": 0" +
                        "    }" +
                        "}]"
                )).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        List<String> messages = getFieldErrorMessages(actual);

        assertThat(messages.contains("Invalid piece type."), Matchers.is(true));
    }

    @Test
    public void shouldRequireAPlacementPointForAShip() throws Exception {
        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{" +
                        "  \"id\": 1," +
                        "  \"type\": \"DESTROYER\"," +
                        "  \"orientation\": \"DOWN\"" +
                        "}]"
                )).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);
        List<String> messages = getFieldErrorMessages(actual);
        assertThat(messages.contains("Missing placement."), Matchers.is(true));
    }

    @Test
    public void shouldRequireAnOrientationForAShip() throws Exception {
        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{" +
                        "  \"id\": 1," +
                        "  \"type\": \"AIRCRAFT_CARRIER\"," +
                        "  \"placement\": {" +
                        "    \"x\": 0," +
                        "    \"y\": 4" +
                        "  }" +
                        "}]"
                )).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("Missing orientation."));
    }

    @Test
    public void shouldNotAllowTheStartXToBeLessThanTheWidthOfTheBoard() throws Exception {
        List<Piece> pieces = singletonList(Piece.builder()
                .withId(1L)
                .withType(DESTROYER)
                .withPlacement(new Point(-1, 0))
                .withOrientation(DOWN)
                .build());

        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pieces.toString())).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        List<String> messages = getFieldErrorMessages(actual);
        assertThat(messages.contains("Incorrect placement of X axis."), Matchers.is(true));
    }

    @Test
    public void shouldNotAllowTheStartXToBeGreaterThanTheWidthOfTheBoard() throws Exception {
        List<Piece> pieces = singletonList(Piece.builder()
                .withId(1L)
                .withType(DESTROYER)
                .withPlacement(new Point(11, 0))
                .withOrientation(DOWN)
                .build());

        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pieces.toString())).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("Incorrect placement of X axis."));
    }

    @Test
    public void shouldNotAllowTheStartYToBeLessThanTheHeightOfTheBoard() throws Exception {
        List<Piece> pieces = singletonList(Piece.builder()
                .withId(1L)
                .withType(DESTROYER)
                .withPlacement(new Point(0, -1))
                .withOrientation(DOWN)
                .build());
        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pieces.toString())).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("Incorrect placement of Y axis."));
    }

    @Test
    public void shouldNotAllowTheStartYToBeGreaterThanTheHeightOfTheBoard() throws Exception {
        List<Piece> pieces = singletonList(Piece.builder()
                .withId(1L)
                .withType(DESTROYER)
                .withPlacement(new Point(0, 11))
                .withOrientation(DOWN)
                .build());

        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pieces.toString())).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("Incorrect placement of Y axis."));
    }

    @Test
    public void shouldNotAllowThePieceToGoOffTheTopOfTheBoard() throws Exception {
        List<Piece> pieces = singletonList(Piece.builder()
                .withId(1L)
                .withType(DESTROYER)
                .withPlacement(new Point(0, 0))
                .withOrientation(UP)
                .build());

        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pieces.toString())).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("Incorrect orientation."));
    }

    @Test
    public void shouldNotAllowThePieceToGoOffTheBottomOfTheBoard() throws Exception {
        List<Piece> pieces = singletonList(Piece.builder()
                .withId(1L)
                .withType(DESTROYER)
                .withPlacement(new Point(0, 9))
                .withOrientation(DOWN)
                .build());

        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pieces.toString())).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("Incorrect orientation."));
    }

    @Test
    public void shouldNotAllowThePieceToGoOffTheLeftOfTheBoard() throws Exception {
        List<Piece> pieces = singletonList(Piece.builder()
                .withId(1L)
                .withType(DESTROYER)
                .withPlacement(new Point(0, 5))
                .withOrientation(LEFT)
                .build());

        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pieces.toString())).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("Incorrect orientation."));
    }

    @Test
    public void shouldNotAllowThePieceToGoOffTheRightOfTheBoard() throws Exception {
        List<Piece> pieces = singletonList(Piece.builder()
                .withId(1L)
                .withType(DESTROYER)
                .withPlacement(new Point(9, 5))
                .withOrientation(RIGHT)
                .build());

        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pieces.toString())).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("Incorrect orientation."));
    }

    @Test
    public void shouldHandleShipExistence() throws Exception {
        doThrow(new ShipExistsCheck()).when(mockService).placePiece(anyLong(), anyListOf(Piece.class));
        List<Piece> pieces = singletonList(Piece.builder()
                .withId(1L)
                .withType(DESTROYER)
                .withPlacement(new Point(0, 0))
                .withOrientation(DOWN)
                .build());

        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pieces.toString())).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        ObjectValidation error = mapper.convertValue(actual.getErrors().get(0), ObjectValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("Ship already exists on board."));
    }

    @Test
    public void shouldHandleShipCollisions() throws Exception {
        doThrow(new ShipCollisionCheck()).when(mockService).placePiece(anyLong(), anyListOf(Piece.class));
        List<Piece> pieces = singletonList(Piece.builder()
                .withId(1L)
                .withType(DESTROYER)
                .withPlacement(new Point(0, 0))
                .withOrientation(DOWN)
                .build());

        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pieces.toString())).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        ObjectValidation error = mapper.convertValue(actual.getErrors().get(0), ObjectValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("Ship collision."));
    }

    @Test
    public void shouldNeedAnId() throws Exception {
        doThrow(new ShipCollisionCheck()).when(mockService).placePiece(anyLong(), anyListOf(Piece.class));
        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{\n" +
                        "    \"type\": \"DESTROYER\",\n" +
                        "    \"placement\": {\n" +
                        "      \"x\": 8,\n" +
                        "      \"y\": 0\n" +
                        "    },\n" +
                        "    \"orientation\": \"DOWN\"\n" +
                        "}]"
                )).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("An Id is missing."));
    }

    private List<String> getFieldErrorMessages(GameErrors actual) {
        return Arrays.stream(mapper.convertValue(actual.getErrors(), FieldValidation[].class)).map(FieldValidation::getValidations)
                .flatMap(Collection::stream)
                .map(ValidationFieldError::getMessage)
                .collect(Collectors.toList());
    }
}