package com.bship.games.endpoints;

import com.bship.games.domains.Board;
import com.bship.games.domains.Harbor;
import com.bship.games.domains.Ship;
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
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigInteger;

import static java.util.Optional.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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
        when(mockService.placeShip(any(BigInteger.class), any(Ship.class)))
                .thenReturn(of(Board.builder().build()));

        mockMvc.perform(put("/boards/9").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"type\": \"BATTLESHIP\",\n" +
                        "  \"start\": {\n" +
                        "    \"x\": 4,\n" +
                        "    \"y\": 5\n" +
                        "  },\n" +
                        "  \"end\": {\n" +
                        "    \"x\": 1,\n" +
                        "    \"y\": 5\n" +
                        "  }\n" +
                        "}"
                ))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void placeShip_shouldRespondWith200() throws Exception {
        when(mockService.placeShip(any(BigInteger.class), any(Ship.class)))
                .thenReturn(of(Board.builder().build()));

        mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"type\": \"BATTLESHIP\",\n" +
                        "  \"start\": {\n" +
                        "    \"x\": 4,\n" +
                        "    \"y\": 5\n" +
                        "  },\n" +
                        "  \"end\": {\n" +
                        "    \"x\": 1,\n" +
                        "    \"y\": 5\n" +
                        "  }\n" +
                        "}"
                ))
                .andExpect(status().isOk());
    }

    @Test
    public void placeShip_methodSignatureBindToPathParamsAndRequestBody() throws Exception {
        mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"type\": \"BATTLESHIP\",\n" +
                        "  \"start\": {\n" +
                        "    \"x\": 4,\n" +
                        "    \"y\": 5\n" +
                        "  },\n" +
                        "  \"end\": {\n" +
                        "    \"x\": 1,\n" +
                        "    \"y\": 5\n" +
                        "  }\n" +
                        "}"
                ));

        ArgumentCaptor<Ship> captor = ArgumentCaptor.forClass(Ship.class);

        verify(mockService).placeShip(BigInteger.valueOf(eq(9L)), captor.capture());
        Ship capturedShip = captor.getValue();

        assertEquals(Harbor.BATTLESHIP, capturedShip.getType());
        assertThat(4, is(equalTo(capturedShip.getStart().getX())));
        assertThat(5, is(equalTo(capturedShip.getStart().getY())));
        assertThat(1, is(equalTo(capturedShip.getEnd().getX())));
        assertThat(5, is(equalTo(capturedShip.getEnd().getY())));
    }


    @Test
    public void placeShip_shouldRequireATypeOfShip() throws Exception {
        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"start\": {\n" +
                        "    \"x\": 4,\n" +
                        "    \"y\": 5\n" +
                        "  },\n" +
                        "  \"end\": {\n" +
                        "    \"x\": 1,\n" +
                        "    \"y\": 5\n" +
                        "  }\n" +
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
                .content("{\n" +
                        "  \"type\": \"GAS_STATION\",\n" +
                        "  \"start\": {\n" +
                        "    \"x\": 4,\n" +
                        "    \"y\": 5\n" +
                        "  },\n" +
                        "  \"end\": {\n" +
                        "    \"x\": 1,\n" +
                        "    \"y\": 5\n" +
                        "  }\n" +
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
                .content("{\n" +
                        "  \"type\": \"DESTROYER\",\n" +
                        "  \"end\": {\n" +
                        "    \"x\": 1,\n" +
                        "    \"y\": 5\n" +
                        "  }\n" +
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
                .content("{\n" +
                        "  \"type\": \"DESTROYER\",\n" +
                        "  \"start\": {\n" +
                        "    \"x\": 1,\n" +
                        "    \"y\": 5\n" +
                        "  }\n" +
                        "}"
                )).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("Cannot be empty or null."));
    }

    @Test
    public void placeShip_shouldNotAllowTheStartXToBeLessThanTheWidthOfTheBoard() throws Exception {
        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"type\": \"DESTROYER\",\n" +
                        "  \"start\": {\n" +
                        "    \"x\": -1,\n" +
                        "    \"y\": 0\n" +
                        "  },\n" +
                        "  \"end\": {\n" +
                        "    \"x\": 0,\n" +
                        "    \"y\": 0\n" +
                        "  }\n" +
                        "}"
                )).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("out of bounds."));
    }

    @Test
    public void placeShip_shouldNotAllowTheStartXToBeGreaterThanTheWidthOfTheBoard() throws Exception {
        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"type\": \"DESTROYER\",\n" +
                        "  \"start\": {\n" +
                        "    \"x\": 11,\n" +
                        "    \"y\": 0\n" +
                        "  },\n" +
                        "  \"end\": {\n" +
                        "    \"x\": 0,\n" +
                        "    \"y\": 0\n" +
                        "  }\n" +
                        "}"
                )).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("out of bounds."));
    }

    @Test
    public void placeShip_shouldNotAllowTheStartYToBeLessThanTheHeightOfTheBoard() throws Exception {
        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"type\": \"DESTROYER\",\n" +
                        "  \"start\": {\n" +
                        "    \"x\": 0,\n" +
                        "    \"y\": -1\n" +
                        "  },\n" +
                        "  \"end\": {\n" +
                        "    \"x\": 0,\n" +
                        "    \"y\": 0\n" +
                        "  }\n" +
                        "}"
                )).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("out of bounds."));
    }

    @Test
    public void placeShip_shouldNotAllowTheStartYToBeGreaterThanTheHeightOfTheBoard() throws Exception {
        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"type\": \"DESTROYER\",\n" +
                        "  \"start\": {\n" +
                        "    \"x\": 0,\n" +
                        "    \"y\": 11\n" +
                        "  },\n" +
                        "  \"end\": {\n" +
                        "    \"x\": 0,\n" +
                        "    \"y\": 0\n" +
                        "  }\n" +
                        "}"
                )).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("out of bounds."));
    }

    @Test
    public void placeShip_shouldNotAllowTheEndXToBeLessThanTheWidthOfTheBoard() throws Exception {
        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"type\": \"DESTROYER\",\n" +
                        "  \"start\": {\n" +
                        "    \"x\": 0,\n" +
                        "    \"y\": 0\n" +
                        "  },\n" +
                        "  \"end\": {\n" +
                        "    \"x\": -1,\n" +
                        "    \"y\": 0\n" +
                        "  }\n" +
                        "}"
                )).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("out of bounds."));
    }

    @Test
    public void placeShip_shouldNotAllowTheEndXToBeGreaterThanTheWidthOfTheBoard() throws Exception {
        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"type\": \"DESTROYER\",\n" +
                        "  \"start\": {\n" +
                        "    \"x\": 0,\n" +
                        "    \"y\": 0\n" +
                        "  },\n" +
                        "  \"end\": {\n" +
                        "    \"x\": 11,\n" +
                        "    \"y\": 0\n" +
                        "  }\n" +
                        "}"
                )).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("out of bounds."));
    }

    @Test
    public void placeShip_shouldNotAllowTheEndYToBeLessThanTheHeightOfTheBoard() throws Exception {
        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"type\": \"DESTROYER\",\n" +
                        "  \"start\": {\n" +
                        "    \"x\": 0,\n" +
                        "    \"y\": 0\n" +
                        "  },\n" +
                        "  \"end\": {\n" +
                        "    \"x\": 0,\n" +
                        "    \"y\": -1\n" +
                        "  }\n" +
                        "}"
                )).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("out of bounds."));
    }

    @Test
    public void placeShip_shouldNotAllowTheEndYToBeGreaterThanTheHeightOfTheBoard() throws Exception {
        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"type\": \"DESTROYER\",\n" +
                        "  \"start\": {\n" +
                        "    \"x\": 0,\n" +
                        "    \"y\": 0\n" +
                        "  },\n" +
                        "  \"end\": {\n" +
                        "    \"x\": 0,\n" +
                        "    \"y\": 11\n" +
                        "  }\n" +
                        "}"
                )).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        FieldValidation error = mapper.convertValue(actual.getErrors().get(0), FieldValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("out of bounds."));
    }

    @Test
    public void placeShip_shouldNotAllowAnIncorrectLengthOfShipToBePlacedOnTheBoard() throws Exception {
        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"type\": \"DESTROYER\",\n" +
                        "  \"start\": {\n" +
                        "    \"x\": 0,\n" +
                        "    \"y\": 0\n" +
                        "  },\n" +
                        "  \"end\": {\n" +
                        "    \"x\": 0,\n" +
                        "    \"y\": 4\n" +
                        "  }\n" +
                        "}"
                )).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        ObjectValidation error = mapper.convertValue(actual.getErrors().get(0), ObjectValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("Incorrect ship placement."));
    }

    @Test
    public void placeShip_shouldHandleShipExistence() throws Exception {
        doThrow(new ShipExistsCheck()).when(mockService).placeShip(any(BigInteger.class), any(Ship.class));
        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"type\": \"DESTROYER\",\n" +
                        "  \"start\": {\n" +
                        "    \"x\": 0,\n" +
                        "    \"y\": 0\n" +
                        "  },\n" +
                        "  \"end\": {\n" +
                        "    \"x\": 0,\n" +
                        "    \"y\": 1\n" +
                        "  }\n" +
                        "}"
                )).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        ObjectValidation error = mapper.convertValue(actual.getErrors().get(0), ObjectValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("Ship already exists on board."));
    }

    @Test
    public void placeShip_shouldHandleShipCollisions() throws Exception {
        doThrow(new ShipCollisionCheck()).when(mockService).placeShip(any(BigInteger.class), any(Ship.class));
        GameErrors actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"type\": \"DESTROYER\",\n" +
                        "  \"start\": {\n" +
                        "    \"x\": 0,\n" +
                        "    \"y\": 0\n" +
                        "  },\n" +
                        "  \"end\": {\n" +
                        "    \"x\": 0,\n" +
                        "    \"y\": 1\n" +
                        "  }\n" +
                        "}"
                )).andReturn()
                .getResponse()
                .getContentAsString(), GameErrors.class);

        ObjectValidation error = mapper.convertValue(actual.getErrors().get(0), ObjectValidation.class);
        assertThat(error.getValidations().get(0).getMessage(), CoreMatchers.is("Ship collision."));
    }
}