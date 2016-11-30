package com.bship.integration;

import com.bship.BattleshipApplication;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    private RestTemplate restTemplate;

    private final String host = "http://localhost:";

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

        restTemplate = new RestTemplate();
    }

    @Test
    public void postGame_shouldCreateNewGameAndReturnsBoards() {
        String url = host + port + "/games";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, "", String.class);

        String actualResponseBody = responseEntity.getBody();
        String expectedResponseBody = "{" +
                "\"boards\":[" +
                "{\"id\":1}," +
                "{\"id\":2}" +
                "]," +
                "\"id\":1" +
                "}";
        assertEquals(expectedResponseBody, actualResponseBody);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void placeShip_shouldBeAbleToPlaceAShipOnTheBoard() {
        String url = host + port + "/games/1/boards/1/ship";

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
