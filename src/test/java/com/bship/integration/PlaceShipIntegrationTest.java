package com.bship.integration;

import com.bship.DBHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureRestDocs(outputDir = "src/test/resources/contracts")
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class PlaceShipIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        DBHelper.reset();
        mockMvc.perform(post("/games"));
    }

    @Test
    public void shouldBeAbleToPlaceAShipOnTheBoard() throws Exception {
        mockMvc.perform(post("/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": \"AIRCRAFT_CARRIER\", " +
                        "\"start\": {\"x\": 0, \"y\": 0}, " +
                        "\"end\": {\"x\": 0, \"y\": 4}}"
                ))

                .andExpect(status().is(201))
                .andExpect(content().json("{\"id\":1," +
                        "\"ships\":[" +
                        "{\"start\":{\"x\":0,\"y\":0}," +
                        "\"end\":{\"x\":0,\"y\":4}," +
                        "\"boardId\":1," +
                        "\"type\":\"AIRCRAFT_CARRIER\"}]}"))
                .andDo(document("place-ship"));
    }

    @Test
    public void shouldNotBeAbleToPlaceAShipsStartOutsideTheBoard() throws Exception {
        mockMvc.perform(post("/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": \"AIRCRAFT_CARRIER\", " +
                        "\"start\": {\"x\": -1, \"y\": 0}, " +
                        "\"end\": {\"x\": 3, \"y\": 0}}"
                ))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"errors\": " +
                        "[{\"fieldErrors\": " +
                        "[{\"code\": \"BoundsCheck\", " +
                        "\"field\": \"start\", " +
                        "\"value\": \"Point{x=-1, y=0}\", " +
                        "\"message\": \"out of bounds.\"}]}]}"))
                .andDo(document("place-ship-start-out-of-bounds"));
    }

    @Test
    public void shouldNotBeAbleToPlaceAShipsEndOutsideTheBoard() throws Exception {
        mockMvc.perform(post("/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": \"AIRCRAFT_CARRIER\", " +
                        "\"start\": {\"x\": 9, \"y\": 6}, " +
                        "\"end\": {\"x\": 9, \"y\": 10}}"
                ))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"errors\": " +
                        "[{\"fieldErrors\": " +
                        "[{\"code\": \"BoundsCheck\", " +
                        "\"field\": \"end\", " +
                        "\"value\": \"Point{x=9, y=10}\", " +
                        "\"message\": \"out of bounds.\"}]}]}"))
                .andDo(document("place-ship-end-out-of-bounds"));
    }

    @Test
    public void shouldNotBeAbleToPlaceAShipOfIncorrectSize() throws Exception {
        mockMvc.perform(post("/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": \"AIRCRAFT_CARRIER\", " +
                        "\"start\": {\"x\": 0, \"y\": 0}, " +
                        "\"end\": {\"x\": 0, \"y\": 1}}"
                ))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"errors\": " +
                        "[{\"globalErrors\": " +
                        "[{\"code\": \"PlacementCheck\", " +
                        "\"type\": \"ship\", " +
                        "\"message\": \"Incorrect ship placement.\"}]}]}"))
                .andDo(document("place-ship-of-incorrect-size"));
    }

    @Test
    public void shouldNotBeAbleToPlaceAShipThatDoesNotExist() throws Exception {
        mockMvc.perform(post("/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": \"SCHOONER\", " +
                        "\"start\": {\"x\": 0, \"y\": 0}, " +
                        "\"end\": {\"x\": 0, \"y\": 1}}"
                ))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"errors\": " +
                        "[{\"fieldErrors\": " +
                        "[{\"code\": \"ShipExists\", " +
                        "\"field\": \"type\", " +
                        "\"value\": \"INVALID_SHIP\", " +
                        "\"message\": \"Ship does not exist.\"}]}]}"))
                .andDo(document("place-ship-ship-existence"));
    }
}
