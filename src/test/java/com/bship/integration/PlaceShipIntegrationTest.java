package com.bship.integration;

import com.bship.DBHelper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.cloud.contract.wiremock.restdocs.WireMockRestDocs.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureRestDocs(outputDir = "build/snippets")
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
        mockMvc.perform(put("/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": \"AIRCRAFT_CARRIER\", " +
                        "\"start\": {\"x\": 0, \"y\": 0}, " +
                        "\"end\": {\"x\": 0, \"y\": 4}}"
                ))

                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(content().json("{\"id\":1," +
                        "\"ships\":[" +
                        "{\"start\":{\"x\":0,\"y\":0}," +
                        "\"end\":{\"x\":0,\"y\":4}," +
                        "\"type\":\"AIRCRAFT_CARRIER\"}]}"))
                .andDo(verify().wiremock(WireMock.put(WireMock
                        .urlMatching("/boards/(\\d+)"))
                        .withRequestBody(matchingJsonPath("$[?(@.type == 'AIRCRAFT_CARRIER')]"))
                        .withRequestBody(matchingJsonPath("$.start[?(@.x == 0)]"))
                        .withRequestBody(matchingJsonPath("$.start[?(@.y == 0)]"))
                        .withRequestBody(matchingJsonPath("$.end[?(@.x == 0)]"))
                        .withRequestBody(matchingJsonPath("$.end[?(@.y == 4)]"))
                        .withHeader("Content-Type", equalTo(APPLICATION_JSON_UTF8_VALUE))
                ).stub("place-ship"));

    }

    @Test
    public void shouldNotBeAbleToPlaceAShipsStartOutsideTheBoard() throws Exception {
        mockMvc.perform(put("/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": \"AIRCRAFT_CARRIER\", " +
                        "\"start\": {\"x\": -1, \"y\": 0}, " +
                        "\"end\": {\"x\": 3, \"y\": 0}}"
                ))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"errors\":[" +
                        "{\"fieldValidations\":[" +
                        "{\"code\":\"BoundsCheck\"," +
                        "\"field\":\"start\"," +
                        "\"value\":{\"x\":-1,\"y\":0}," +
                        "\"message\":\"out of bounds.\"}]}]}"))
                .andDo(document("place-ship-start-out-of-bounds"));
    }

    @Test
    public void shouldNotBeAbleToPlaceAShipsEndOutsideTheBoard() throws Exception {
        mockMvc.perform(put("/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": \"AIRCRAFT_CARRIER\", " +
                        "\"start\": {\"x\": 9, \"y\": 6}, " +
                        "\"end\": {\"x\": 9, \"y\": 10}}"
                ))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"errors\":[" +
                        "{\"fieldValidations\":[" +
                        "{\"code\":\"BoundsCheck\"," +
                        "\"field\":\"end\"," +
                        "\"value\":{\"x\":9,\"y\":10}," +
                        "\"message\":\"out of bounds.\"}]}]}"))
                .andDo(document("place-ship-end-out-of-bounds"));
    }

    @Test
    public void shouldNotBeAbleToPlaceAShipOfIncorrectSize() throws Exception {
        mockMvc.perform(put("/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": \"AIRCRAFT_CARRIER\", " +
                        "\"start\": {\"x\": 0, \"y\": 0}, " +
                        "\"end\": {\"x\": 0, \"y\": 1}}"
                ))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"errors\": " +
                        "[{\"objectValidations\": " +
                        "[{\"code\": \"PlacementCheck\", " +
                        "\"type\": \"ship\", " +
                        "\"message\": \"Incorrect ship placement.\"}]}]}"))
                .andDo(document("place-ship-of-incorrect-size"));
    }

    @Test
    public void shouldNotBeAbleToPlaceAShipThatDoesNotExist() throws Exception {
        mockMvc.perform(put("/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": \"SCHOONER\", " +
                        "\"start\": {\"x\": 0, \"y\": 0}, " +
                        "\"end\": {\"x\": 0, \"y\": 1}}"
                ))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"errors\": " +
                        "[{\"fieldValidations\": " +
                        "[{\"code\": \"ShipExists\", " +
                        "\"field\": \"type\", " +
                        "\"value\": \"INVALID_SHIP\", " +
                        "\"message\": \"Ship does not exist.\"}]}]}"))
                .andDo(document("place-ship-ship-existence"));
    }

    @Test
    public void shouldNotBeAbleToPlaceAShipWithANullStart() throws Exception {
        mockMvc.perform(put("/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": \"AIRCRAFT_CARRIER\"," +
                        "\"start\": null," +
                        "\"end\": {\"x\": 0, \"y\": 1}}"
                ))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"errors\":[" +
                        "{\"fieldValidations\":[" +
                        "{\"code\":\"NonEmpty\"," +
                        "\"field\":\"start\"," +
                        "\"value\":null," +
                        "\"message\":\"Cannot be empty or null.\"}]}]}"))
                .andDo(document("place-ship-of-null-start"));
    }

    @Test
    public void shouldNotBeAbleToPlaceAShipWithANullEnd() throws Exception {
        mockMvc.perform(put("/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": \"AIRCRAFT_CARRIER\"," +
                        "\"start\": {\"x\": 0, \"y\": 1}," +
                        "\"end\": null}"
                ))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"errors\":[" +
                        "{\"fieldValidations\":[" +
                        "{\"code\":\"NonEmpty\"," +
                        "\"field\":\"end\"," +
                        "\"value\":null," +
                        "\"message\":\"Cannot be empty or null.\"}]}]}"))
                .andDo(document("place-ship-of-null-start"));
    }

    @Test
    public void shouldNotBeAbleToPlaceAShipWithANullType() throws Exception {
        mockMvc.perform(put("/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": null," +
                        "\"start\": {\"x\": 0, \"y\": 1}," +
                        "\"end\": {\"x\": 0, \"y\": 1}}"
                ))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"errors\":[" +
                        "{\"fieldValidations\":[" +
                        "{\"code\":\"NonEmpty\"," +
                        "\"field\":\"type\"," +
                        "\"value\":null," +
                        "\"message\":\"Cannot be empty or null.\"}]}]}"))
                .andDo(document("place-ship-of-null-start"));
    }

    @Test
    public void shouldNotBeAbleToPlaceAShipMoreThanOnce() throws Exception {
        mockMvc.perform(put("/boards/2")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": \"AIRCRAFT_CARRIER\", " +
                        "\"start\": {\"x\": 0, \"y\": 0}, " +
                        "\"end\": {\"x\": 0, \"y\": 4}}"
                )).andExpect(status().is(200));

        mockMvc.perform(put("/boards/2")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": \"AIRCRAFT_CARRIER\", " +
                        "\"start\": {\"x\": 9, \"y\": 0}, " +
                        "\"end\": {\"x\": 9, \"y\": 4}}"
                ))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"errors\": " +
                        "[{\"objectValidations\": " +
                        "[{\"code\": \"ShipExistsCheck\", " +
                        "\"type\": \"board\", " +
                        "\"message\": \"Ship already exists on board.\"}]}]}"))
                .andDo(document("place-ship-more-than-once"));
    }

    @Test
    public void shouldNotBeAbleToPlaceAShipUponAnotherShip() throws Exception {
        mockMvc.perform(put("/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": \"AIRCRAFT_CARRIER\", " +
                        "\"start\": {\"x\": 3, \"y\": 3}, " +
                        "\"end\": {\"x\": 7, \"y\": 3}}"
                )).andExpect(status().is(200));

        mockMvc.perform(put("/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"type\": \"BATTLESHIP\", " +
                        "\"start\": {\"x\": 4, \"y\": 2}, " +
                        "\"end\": {\"x\": 4, \"y\": 5}}"
                ))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"errors\": " +
                        "[{\"objectValidations\": " +
                        "[{\"code\": \"ShipCollisionCheck\", " +
                        "\"type\": \"board\", " +
                        "\"message\": \"Ship collision.\"}]}]}"))
                .andDo(document("place-ship-collision"));
    }
}
