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

    @Test
    public void shouldNotBeAbleToPlaceAShipWithANullStart() throws Exception {
        mockMvc.perform(post("/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": \"AIRCRAFT_CARRIER\"," +
                        "\"start\": null," +
                        "\"end\": {\"x\": 0, \"y\": 1}}"
                ))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"errors\": " +
                        "[{\"fieldErrors\": " +
                        "[{\"code\": \"NonEmpty\", " +
                        "\"field\": \"start\", " +
                        "\"value\": \"null\", " +
                        "\"message\": \"Cannot be empty or null.\"}]}]}"))
                .andDo(document("place-ship-of-null-start"));
    }

    @Test
    public void shouldNotBeAbleToPlaceAShipWithANullEnd() throws Exception {
        mockMvc.perform(post("/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": \"AIRCRAFT_CARRIER\"," +
                        "\"start\": {\"x\": 0, \"y\": 1}," +
                        "\"end\": null}"
                ))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"errors\": " +
                        "[{\"fieldErrors\": " +
                        "[{\"code\": \"NonEmpty\", " +
                        "\"field\": \"end\", " +
                        "\"value\": \"null\", " +
                        "\"message\": \"Cannot be empty or null.\"}]}]}"))
                .andDo(document("place-ship-of-null-start"));
    }

    @Test
    public void shouldNotBeAbleToPlaceAShipWithANullType() throws Exception {
        mockMvc.perform(post("/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": null," +
                        "\"start\": {\"x\": 0, \"y\": 1}," +
                        "\"end\": {\"x\": 0, \"y\": 1}}"
                ))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"errors\": " +
                        "[{\"fieldErrors\": " +
                        "[{\"code\": \"NonEmpty\", " +
                        "\"field\": \"type\", " +
                        "\"value\": \"null\", " +
                        "\"message\": \"Cannot be empty or null.\"}]}]}"))
                .andDo(document("place-ship-of-null-start"));
    }

    @Test
    public void shouldNotBeAbleToPlaceAShipMoreThanOnce() throws Exception {
        mockMvc.perform(post("/boards/2")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": \"AIRCRAFT_CARRIER\", " +
                        "\"start\": {\"x\": 0, \"y\": 0}, " +
                        "\"end\": {\"x\": 0, \"y\": 4}}"
                )).andExpect(status().is(201));

        mockMvc.perform(post("/boards/2")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": \"AIRCRAFT_CARRIER\", " +
                        "\"start\": {\"x\": 9, \"y\": 0}, " +
                        "\"end\": {\"x\": 9, \"y\": 4}}"
                ))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"errors\": " +
                        "[{\"globalErrors\": " +
                        "[{\"code\": \"ShipExistsCheck\", " +
                        "\"type\": \"ship\", " +
                        "\"message\": \"Ship already exists on board.\"}]}]}"))
                .andDo(document("place-ship-more-than-once"));
    }

    @Test
    public void shouldNotBeAbleToPlaceAShipUponAnotherShip() throws Exception {
        mockMvc.perform(post("/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": \"AIRCRAFT_CARRIER\", " +
                        "\"start\": {\"x\": 3, \"y\": 3}, " +
                        "\"end\": {\"x\": 7, \"y\": 3}}"
                )).andExpect(status().is(201));

        mockMvc.perform(post("/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": \"BATTLESHIP\", " +
                        "\"start\": {\"x\": 4, \"y\": 2}, " +
                        "\"end\": {\"x\": 4, \"y\": 5}}"
                ))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"errors\": " +
                        "[{\"globalErrors\": " +
                        "[{\"code\": \"ShipCollisionCheck\", " +
                        "\"type\": \"ship\", " +
                        "\"message\": \"Ship collision.\"}]}]}"))
                .andDo(document("place-ship-collision"));
    }

    @Test
    public void shouldKnowIfTheBoardIsReadyToPlay() throws Exception {
        mockMvc.perform(post("/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": \"AIRCRAFT_CARRIER\", " +
                        "\"start\": {\"x\": 0, \"y\": 0}, " +
                        "\"end\": {\"x\": 0, \"y\": 4}}"
                )).andExpect(status().is(201));

        mockMvc.perform(post("/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": \"BATTLESHIP\", " +
                        "\"start\": {\"x\": 1, \"y\": 0}, " +
                        "\"end\": {\"x\": 1, \"y\": 3}}"
                )).andExpect(status().is(201));

        mockMvc.perform(post("/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": \"SUBMARINE\", " +
                        "\"start\": {\"x\": 2, \"y\": 0}, " +
                        "\"end\": {\"x\": 2, \"y\": 2}}"
                )).andExpect(status().is(201));

        mockMvc.perform(post("/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": \"CRUISER\", " +
                        "\"start\": {\"x\": 3, \"y\": 0}, " +
                        "\"end\": {\"x\": 3, \"y\": 2}}"
                )).andExpect(status().is(201));

        mockMvc.perform(post("/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": \"DESTROYER\", " +
                        "\"start\": {\"x\": 4, \"y\": 0}, " +
                        "\"end\": {\"x\": 4, \"y\": 1}}"
                ))
                .andExpect(status().is(201))
                .andExpect(content().json("{\"id\":1," +
                        "\"ready\": true," +
                        "\"ships\":[" +
                        "{\"start\":{\"x\":0,\"y\":0}," +
                        "\"end\":{\"x\":0,\"y\":4}," +
                        "\"type\":\"AIRCRAFT_CARRIER\"}," +

                        "{\"start\":{\"x\":1,\"y\":0}," +
                        "\"end\":{\"x\":1,\"y\":3}," +
                        "\"type\":\"BATTLESHIP\"}," +

                        "{\"start\":{\"x\":2,\"y\":0}," +
                        "\"end\":{\"x\":2,\"y\":2}," +
                        "\"type\":\"SUBMARINE\"}," +

                        "{\"start\":{\"x\":3,\"y\":0}," +
                        "\"end\":{\"x\":3,\"y\":2}," +
                        "\"type\":\"CRUISER\"}," +

                        "{\"start\":{\"x\":4,\"y\":0}," +
                        "\"end\":{\"x\":4,\"y\":1}," +
                        "\"type\":\"DESTROYER\"}" +
                        "]}"))
                .andDo(document("place-ship"));
    }
}
