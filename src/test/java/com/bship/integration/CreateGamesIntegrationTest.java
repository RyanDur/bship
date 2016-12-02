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
public class CreateGamesIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        DBHelper.reset();
    }

    @Test
    public void shouldCreateNewGameWithBoards() throws Exception {
        mockMvc.perform(post("/games")
                .accept(APPLICATION_JSON_VALUE))

                .andExpect(status().is(201))
                .andExpect(content().json("{\"id\": 1, \"boards\": [{\"id\": 1}, {\"id\": 2}]}"))
                .andDo(document("new-game"));
    }
}
