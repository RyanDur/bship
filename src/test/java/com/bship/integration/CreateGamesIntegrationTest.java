package com.bship.integration;

import com.bship.BattleshipApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(BattleshipApplication.class)
@WebAppConfiguration
@IntegrationTest({"server.port=1337"})
public class CreateGamesIntegrationTest {

    @Value("${server.port}")
    private Integer port;

    @Test
    public void postGame_shouldCreateNewGameAndReturnsBoards() {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:" + port + "/game";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, "", String.class);

        String actualResponseBody = responseEntity.getBody();
        String expectedResponseBody = "{\n" +
                "  \"id\": 1,\n" +
                "  \"boards\": [\n" +
                "    {\"id\": 1},\n" +
                "    {\"id\": 2}\n" +
                "  ]\n" +
                "}";

        assertEquals(expectedResponseBody, actualResponseBody);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }
}
