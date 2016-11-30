package com.bship.integration;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.flywaydb.core.Flyway;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=RANDOM_PORT)
public class CreateGamesIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        DataSource dataSource = new DataSource();
        dataSource.setUrl("jdbc:mysql://localhost/bs");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.clean();
        flyway.migrate();
    }

    @Test
    public void postGame_shouldCreateNewGameAndReturnsBoards() throws JSONException {
        String url = "/games";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, "", String.class);

        String actualResponseBody = responseEntity.getBody();
        String expectedResponseBody = "{\n" +
                "\"id\":1, \n" +
                "\"boards\": [\n" +
                "  {\"id\": 1},\n" +
                "  {\"id\": 2}\n" +
                "]}";
        JSONAssert.assertEquals(expectedResponseBody, actualResponseBody, true);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void placeShip_shouldBeAbleToPlaceAShipOnTheBoard() {
        String url = "/games/1/boards/1";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> stringHttpEntity = new HttpEntity<>("{\n" +
                "  \"type\": \"AIRCRAFT_CARRIER\",\n" +
                "  \"start\": {\n" +
                "    \"x\": 0,\n" +
                "    \"y\": 0\n" +
                "  },\n" +
                "  \"end\": {\n" +
                "    \"x\": 0,\n" +
                "    \"y\": 4\n" +
                "  }\n" +
                "}", headers
        );
        ResponseEntity<String> entity = restTemplate.exchange(url, HttpMethod.POST, stringHttpEntity, String.class);

        assertEquals("{}", entity.getBody());
        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
    }
}
