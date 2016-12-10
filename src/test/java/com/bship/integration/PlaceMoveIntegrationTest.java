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
public class PlaceMoveIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        DBHelper.reset();
        mockMvc.perform(post("/games"));
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
                .andExpect(status().is(201));
    }

    @Test
    public void shouldBeAbleToPlaceAMoveOnTheBoard() throws Exception {
        mockMvc.perform(post("/games/1/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"x\": 0, \"y\": 5}"
                ))
                .andExpect(status().is(201))
                .andExpect(content().json("{" +
                        "\"status\": \"MISS\", " +
                        "\"point\": {\"x\": 0, \"y\": 5}}"))
                .andDo(document("place-move"));
    }

    @Test
    public void shouldNotBeAbleToPlaceAMoveOnTheBoardPastLowerBounds() throws Exception {
        mockMvc.perform(post("/games/1/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"x\": 0, \"y\": -5}"
                ))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"errors\": " +
                        "[{\"objectValidation\": " +
                        "[{\"code\": \"BoundsCheck\", " +
                        "\"type\": \"point\", " +
                        "\"message\": \"out of bounds.\"}]}]}"))
                .andDo(document("place-move-outside-lower-bounds"));
    }

    @Test
    public void shouldNotBeAbleToPlaceAMoveOnTheBoardPastUpperBounds() throws Exception {
        mockMvc.perform(post("/games/1/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"x\": 0, \"y\": 15}"
                ))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"errors\": " +
                        "[{\"objectValidation\": " +
                        "[{\"code\": \"BoundsCheck\", " +
                        "\"type\": \"point\", " +
                        "\"message\": \"out of bounds.\"}]}]}"))
                .andDo(document("place-move-outside-upper-bounds"));
    }

    @Test
    public void shouldNotBeAbleToPlaceAMoveOnTheBoardWhereXDoesNotExist() throws Exception {
        mockMvc.perform(post("/games/1/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"x\": null, \"y\": 1}"
                ))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"errors\": " +
                        "[{\"objectValidation\": " +
                        "[{\"code\": \"BoundsCheck\", " +
                        "\"type\": \"point\", " +
                        "\"message\": \"out of bounds.\"}]}]}"))
                .andDo(document("place-move-null-x"));
    }

    @Test
    public void shouldNotBeAbleToPlaceAMoveOnTheBoardWhereYDoesNotExist() throws Exception {
        mockMvc.perform(post("/games/1/boards/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content("{\"x\": 1, \"y\": null}"
                ))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"errors\": " +
                        "[{\"objectValidation\": " +
                        "[{\"code\": \"BoundsCheck\", " +
                        "\"type\": \"point\", " +
                        "\"message\": \"out of bounds.\"}]}]}"))
                .andDo(document("place-move-null-y"));
    }
}
