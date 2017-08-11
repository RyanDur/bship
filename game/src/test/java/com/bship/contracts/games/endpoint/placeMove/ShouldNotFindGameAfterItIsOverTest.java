package com.bship.contracts.games.endpoint.placeMove;

import com.bship.contracts.PlaceMoveShouldNotFindGameAfterItIsOverBase;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import com.jayway.restassured.response.ResponseOptions;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShouldNotFindGameAfterItIsOverTest extends PlaceMoveShouldNotFindGameAfterItIsOverBase {

	@Test
	public void validate_0_createGame() throws Exception {
		// given:
			MockMvcRequestSpecification request = given()
					.header("Content-Type", "application/json");

		// when:
			ResponseOptions response = given().spec(request)
					.post("/games/BATTLESHIP");

		// then:
			assertThat(response.statusCode()).isEqualTo(201);
			assertThat(response.header("Content-Type")).matches("application/json.*");
		// and:
			DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
			assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
		assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
	assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isNull();
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("NONE");
assertThatJson(parsedJson).field("turn").isNull();
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isNull();
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
}

@Test
public void validate_11_Board_1_placeCarrier() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"AIRCRAFT_CARRIER\",\"id\":1,\"placement\":{\"x\":0,\"y\":0},\"orientation\":\"DOWN\"}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isNull();
assertThatJson(parsedJson).array("pieces").contains("orientation").isEqualTo("NONE");
assertThatJson(parsedJson).array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("pieces").field("placement").field("y").isNull();
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).field("winner").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(5);
}

@Test
public void validate_12_Board_1_placeBattleship() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"BATTLESHIP\",\"id\":2,\"placement\":{\"x\":1,\"y\":0},\"orientation\":\"DOWN\",\"size\":4}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isNull();
assertThatJson(parsedJson).array("pieces").contains("orientation").isEqualTo("NONE");
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("pieces").field("placement").field("y").isNull();
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).field("winner").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(4);
}

@Test
public void validate_13_Board_1_placeSubmarine() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"SUBMARINE\",\"id\":3,\"placement\":{\"x\":2,\"y\":0},\"orientation\":\"DOWN\"}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isNull();
assertThatJson(parsedJson).array("pieces").contains("orientation").isEqualTo("NONE");
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("pieces").field("placement").field("y").isNull();
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).field("winner").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(4);
}

@Test
public void validate_14_Board_1_placeCruiser() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"CRUISER\",\"id\":4,\"placement\":{\"x\":3,\"y\":0},\"orientation\":\"DOWN\"}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isNull();
assertThatJson(parsedJson).array("pieces").contains("orientation").isEqualTo("NONE");
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").field("placement").field("y").isNull();
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).field("winner").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(4);
}

@Test
public void validate_15_Board_1_placeDestroyer() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"DESTROYER\",\"id\":5,\"placement\":{\"x\":4,\"y\":0},\"orientation\":\"DOWN\"}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).field("winner").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(4);
}

@Test
public void validate_21_Board_2_placeCarrier() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"AIRCRAFT_CARRIER\",\"id\":6,\"placement\":{\"x\":0,\"y\":0},\"orientation\":\"DOWN\"}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/2");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isNull();
assertThatJson(parsedJson).array("pieces").contains("orientation").isEqualTo("NONE");
assertThatJson(parsedJson).array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("pieces").field("placement").field("y").isNull();
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).field("id").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).field("winner").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(5);
}

@Test
public void validate_22_Board_2_placeBattleship() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"BATTLESHIP\",\"id\":7,\"placement\":{\"x\":1,\"y\":0},\"orientation\":\"DOWN\"}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/2");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isNull();
assertThatJson(parsedJson).array("pieces").contains("orientation").isEqualTo("NONE");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("pieces").field("placement").field("y").isNull();
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).field("id").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).field("winner").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(4);
}

@Test
public void validate_23_Board_2_placeSubmarine() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"SUBMARINE\",\"id\":8,\"placement\":{\"x\":2,\"y\":0},\"orientation\":\"DOWN\"}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/2");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isNull();
assertThatJson(parsedJson).array("pieces").contains("orientation").isEqualTo("NONE");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("pieces").field("placement").field("y").isNull();
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).field("id").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).field("winner").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(4);
}

@Test
public void validate_24_Board_2_placeCruiser() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"CRUISER\",\"id\":9,\"placement\":{\"x\":3,\"y\":0},\"orientation\":\"DOWN\"}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/2");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isNull();
assertThatJson(parsedJson).array("pieces").contains("orientation").isEqualTo("NONE");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").field("placement").field("y").isNull();
assertThatJson(parsedJson).field("id").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).field("winner").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(4);
}

@Test
public void validate_25_Board_2_placeDestroyer() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"DESTROYER\",\"id\":10,\"placement\":{\"x\":4,\"y\":0},\"orientation\":\"DOWN\"}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/2");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).field("id").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).field("winner").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").field("type").field("size").isEqualTo(4);
}

@Test
public void validate_301_placeMovePlayer1() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":1,\"point\":{\"x\":0,\"y\":4}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).field("turn").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
}

@Test
public void validate_302_placeMovePlayer2() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":2,\"point\":{\"x\":4,\"y\":0}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).field("turn").isEqualTo(1);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
}

@Test
public void validate_311_placeMovePlayer1() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":1,\"point\":{\"x\":0,\"y\":3}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).field("turn").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
}

@Test
public void validate_312_placeMovePlayer2() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":2,\"point\":{\"x\":4,\"y\":1}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).field("turn").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
}

@Test
public void validate_321_placeMovePlayer1() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":1,\"point\":{\"x\":0,\"y\":2}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).field("turn").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
}

@Test
public void validate_322_placeMovePlayer2() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":2,\"point\":{\"x\":3,\"y\":0}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).field("turn").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
}

@Test
public void validate_331_placeMovePlayer1() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":1,\"point\":{\"x\":0,\"y\":1}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).field("turn").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
}

@Test
public void validate_332_placeMovePlayer2() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":2,\"point\":{\"x\":3,\"y\":1}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).field("turn").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
}

@Test
public void validate_341_placeMovePlayer1() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":1,\"point\":{\"x\":0,\"y\":0}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).field("turn").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
}

@Test
public void validate_342_placeMovePlayer2() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":2,\"point\":{\"x\":3,\"y\":2}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).field("turn").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
}

@Test
public void validate_351_placeMovePlayer1() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":1,\"point\":{\"x\":1,\"y\":0}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).field("turn").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
}

@Test
public void validate_352_placeMovePlayer2() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":2,\"point\":{\"x\":2,\"y\":0}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).field("turn").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(2);
}

@Test
public void validate_361_placeMovePlayer1() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":1,\"point\":{\"x\":1,\"y\":1}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).field("turn").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(2);
}

@Test
public void validate_362_placeMovePlayer2() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":2,\"point\":{\"x\":2,\"y\":1}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).field("turn").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(2);
}

@Test
public void validate_371_placeMovePlayer1() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":1,\"point\":{\"x\":1,\"y\":2}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).field("turn").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(2);
}

@Test
public void validate_372_placeMovePlayer2() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":2,\"point\":{\"x\":2,\"y\":2}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).field("turn").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(2);
}

@Test
public void validate_381_placeMovePlayer1() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":1,\"point\":{\"x\":1,\"y\":3}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).field("turn").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(2);
}

@Test
public void validate_382_placeMovePlayer2() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":2,\"point\":{\"x\":1,\"y\":0}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).field("turn").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(2);
}

@Test
public void validate_391_placeMovePlayer1() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":1,\"point\":{\"x\":2,\"y\":0}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).field("turn").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(2);
}

@Test
public void validate_392_placeMovePlayer2() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":2,\"point\":{\"x\":1,\"y\":1}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).field("turn").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(2);
}

@Test
public void validate_401_placeMovePlayer1() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":1,\"point\":{\"x\":2,\"y\":1}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).field("turn").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(2);
}

@Test
public void validate_402_placeMovePlayer2() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":2,\"point\":{\"x\":1,\"y\":2}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).field("turn").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(2);
}

@Test
public void validate_411_placeMovePlayer1() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":1,\"point\":{\"x\":2,\"y\":2}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).field("turn").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(2);
}

@Test
public void validate_412_placeMovePlayer2() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":2,\"point\":{\"x\":1,\"y\":3}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).field("turn").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(2);
}

@Test
public void validate_421_placeMovePlayer1() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":1,\"point\":{\"x\":3,\"y\":0}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).field("turn").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(2);
}

@Test
public void validate_422_placeMovePlayer2() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":2,\"point\":{\"x\":0,\"y\":0}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).field("turn").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(2);
}

@Test
public void validate_431_placeMovePlayer1() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":1,\"point\":{\"x\":3,\"y\":1}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).field("turn").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(2);
}

@Test
public void validate_432_placeMovePlayer2() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":2,\"point\":{\"x\":0,\"y\":1}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).field("turn").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(2);
}

@Test
public void validate_441_placeMovePlayer1() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":1,\"point\":{\"x\":3,\"y\":2}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).field("turn").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(2);
}

@Test
public void validate_442_placeMovePlayer2() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":2,\"point\":{\"x\":0,\"y\":2}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).field("turn").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(2);
}

@Test
public void validate_451_placeMovePlayer1() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":1,\"point\":{\"x\":4,\"y\":0}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).field("turn").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(2);
}

@Test
public void validate_452_placeMovePlayer2() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":2,\"point\":{\"x\":0,\"y\":3}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).field("turn").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(2);
}

@Test
public void validate_461_placeMovePlayer1() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":1,\"point\":{\"x\":4,\"y\":1}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).field("over").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(true);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("moves").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(true);
assertThatJson(parsedJson).field("turn").isNull();
assertThatJson(parsedJson).array("boards").array("opponentMoves").contains("status").isEqualTo("HIT");
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("opponentPieces").field("type").field("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentMoves").field("point").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("opponentPieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("moves").field("point").field("x").isEqualTo(2);
}

@Test
public void validate_462_placeMovePlayer2() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":2,\"point\":{\"x\":0,\"y\":4}}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(400);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("errors").array("validations").contains("type").isEqualTo("game");
assertThatJson(parsedJson).array("errors").array("validations").contains("message").isEqualTo("Game Does Not Exist!");
assertThatJson(parsedJson).array("errors").array("validations").contains("code").isEqualTo("InvalidGame");
}

}
