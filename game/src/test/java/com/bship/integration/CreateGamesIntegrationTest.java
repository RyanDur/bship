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
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.cloud.contract.wiremock.restdocs.WireMockRestDocs.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureRestDocs(outputDir = "build/snippets")
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
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(content().json("{" +
                        "\"id\": 1, " +
                        "\"boards\": [" +
                        "{\"id\":1,\"ships\":[],\"moves\":[]}," +
                        "{\"id\":2,\"ships\":[],\"moves\":[]}]}"))
                .andDo(verify().wiremock(post(WireMock
                        .urlEqualTo("/games"))
                        .withRequestBody(equalTo(""))
                        .withHeader("Accept", equalTo("application/json"))
                ).stub("new-game"));
    }
}
