package com.bship.games.endpoints;

import com.bship.games.domains.Board;
import com.bship.games.domains.Piece;
import com.bship.games.domains.Point;
import com.bship.games.endpoints.RequestErrors.FieldValidation;
import com.bship.games.endpoints.RequestErrors.GameErrors;
import com.bship.games.endpoints.RequestErrors.ObjectValidation;
import com.bship.games.exceptions.ShipCollisionCheck;
import com.bship.games.exceptions.ShipExistsCheck;
import com.bship.games.services.BoardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.bship.games.domains.Direction.DOWN;
import static com.bship.games.domains.Direction.LEFT;
import static com.bship.games.domains.Direction.UP;
import static com.bship.games.domains.Harbor.BATTLESHIP;
import static com.bship.games.domains.Harbor.DESTROYER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
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
    public void placeShip_shouldHaveARoute() throws Exception {
        mockMvc.perform(put("/boards/9"));
    }

    @Test
    public void placeShip_shouldProduceJSONWithCharsetUTF8() throws Exception {
        when(mockService.placePiece(anyLong(), any(Piece.class)))
                .thenReturn(Board.builder().build());
        Piece piece = Piece.builder()
                .withId(1L)
                .withType(BATTLESHIP)
                .withPlacement(new Point(4, 5))
                .withOrientation(LEFT).build();

        mockMvc.perform(put("/boards/9").contentType(MediaType.APPLICATION_JSON)
                .content(piece.toString()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void placeShip_shouldRespondWith200() throws Exception {
        when(mockService.placePiece(anyLong(), any(Piece.class)))
                .thenReturn(Board.builder().build());
        Piece piece = Piece.builder()
                .withId(1L)
                .withType(DESTROYER)
                .withPlacement(new Point(0, 0))
                .withOrientation(DOWN)
                .build();

        mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(piece.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void placeShip_shouldRequireATypeOfShip() throws Exception {
        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"id\": 1," +
                        "  \"size\": 2," +
                        "  \"orientation\": \"DOWN\"," +
                        "  \"placement\": {" +
                        "    \"x\": 1," +
                        "    \"y\": 5" +
                        "  }" +
                        "}"
                )).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("Cannot be empty or null."));
    }

    @Test
    public void placeShip_shouldRequireAnExistingTypeOfShip() throws Exception {
        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"id\": 1," +
                        "  \"type\": \"GAS_STATION\"," +
                        "  \"size\": 2," +
                        "  \"orientation\": \"DOWN\"," +
                        "  \"placement\": {" +
                        "    \"x\": 1," +
                        "    \"y\": 5" +
                        "  }" +
                        "}"
                )).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("Ship does not exist."));
    }

    @Test
    public void placeShip_shouldRequireAStartingPointForAShip() throws Exception {
        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"id\": 1," +
                        "  \"type\": {\"name\":\"DESTROYER\", \"size\": 2}," +
                        "  \"orientation\": \"DOWN\"" +
                        "}"
                )).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("Cannot be empty or null."));
    }

    @Test
    public void placeShip_shouldRequireAnEndingPointForAShip() throws Exception {
        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"id\": 1," +
                        "  \"type\": {\"name\":\"DESTROYER\", \"size\": 2}," +
                        "  \"placement\": {" +
                        "    \"x\": 1," +
                        "    \"y\": 5" +
                        "  }" +
                        "}"
                )).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("Cannot be empty or null."));
    }

    @Test
    public void placeShip_shouldNotAllowTheStartXToBeLessThanTheWidthOfTheBoard() throws Exception {
        Piece piece = Piece.builder()
                .withId(1L)
                .withType(DESTROYER)
                .withPlacement(new Point(-1, 0))
                .withOrientation(DOWN)
                .build();

        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(piece.toString())).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("out of bounds."));
    }

    @Test
    public void placeShip_shouldNotAllowTheStartXToBeGreaterThanTheWidthOfTheBoard() throws Exception {
        Piece piece = Piece.builder()
                .withId(1L)
                .withType(DESTROYER)
                .withPlacement(new Point(11, 0))
                .withOrientation(DOWN)
                .build();

        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(piece.toString())).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("out of bounds."));
    }

    @Test
    public void placeShip_shouldNotAllowTheStartYToBeLessThanTheHeightOfTheBoard() throws Exception {
        Piece piece = Piece.builder()
                .withId(1L)
                .withType(DESTROYER)
                .withPlacement(new Point(0, -1))
                .withOrientation(DOWN)
                .build();
        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(piece.toString())).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("out of bounds."));
    }

    @Test
    public void placeShip_shouldNotAllowTheStartYToBeGreaterThanTheHeightOfTheBoard() throws Exception {
        Piece piece = Piece.builder()
                .withId(1L)
                .withType(DESTROYER)
                .withPlacement(new Point(0, 11))
                .withOrientation(DOWN)
                .build();

        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(piece.toString())).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("out of bounds."));
    }

    @Test
    public void placeShip_shouldNotAllowTheEndYToBeLessThanTheHeightOfTheBoard() throws Exception {
        Piece piece = Piece.builder()
                .withId(1L)
                .withType(DESTROYER)
                .withPlacement(new Point(0, 0))
                .withOrientation(UP)
                .build();

        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(piece.toString())).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        ObjectValidation error = mapper.convertValue(actual.getErrors().get(0), ObjectValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("Incorrect ship placement."));
    }

    @Test
    public void placeShip_shouldHandleShipExistence() throws Exception {
        doThrow(new ShipExistsCheck()).when(mockService).placePiece(anyLong(), any(Piece.class));
        Piece piece = Piece.builder()
                .withId(1L)
                .withType(DESTROYER)
                .withPlacement(new Point(0, 0))
                .withOrientation(DOWN)
                .build();

        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(piece.toString())).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        ObjectValidation error = mapper.convertValue(actual.getErrors().get(0), ObjectValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("Ship already exists on board."));
    }

    @Test
    public void placeShip_shouldHandleShipCollisions() throws Exception {
        doThrow(new ShipCollisionCheck()).when(mockService).placePiece(anyLong(), any(Piece.class));
        Piece piece = Piece.builder()
                .withId(1L)
                .withType(DESTROYER)
                .withPlacement(new Point(0, 0))
                .withOrientation(DOWN)
                .build();

        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(piece.toString())).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        ObjectValidation error = mapper.convertValue(actual.getErrors().get(0), ObjectValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("Ship collision."));
    }

    @Test
    public void placeShip_shouldNeedAnId() throws Exception {
        doThrow(new ShipCollisionCheck()).when(mockService).placePiece(anyLong(), any(Piece.class));
        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "  \"type\": {\"name\":\"DESTROYER\", \"size\": 2}," +
                        "  \"start\": {" +
                        "    \"x\": 0," +
                        "    \"y\": 0" +
                        "  }," +
                        "  \"orientation\": \"DOWN\"" +
                        "}"
                )).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("Cannot be empty or null."));
    }
}