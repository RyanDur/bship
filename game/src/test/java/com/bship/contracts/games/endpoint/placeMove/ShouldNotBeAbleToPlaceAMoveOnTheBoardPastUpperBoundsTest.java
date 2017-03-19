package com.bship.contracts.games.endpoint.placeMove;

import com.bship.contracts.PlaceMoveShouldNotBeAbleToPlaceAMoveOnTheBoardPastUpperBoundsBase;
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
public class ShouldNotBeAbleToPlaceAMoveOnTheBoardPastUpperBoundsTest extends PlaceMoveShouldNotBeAbleToPlaceAMoveOnTheBoardPastUpperBoundsBase {

	@Test
	public void validate_0_createGame() throws Exception {
		// given:
			MockMvcRequestSpecification request = given()
					.header("Content-Type", "application/json");

		// when:
			ResponseOptions response = given().spec(request)
					.post("/games");

		// then:
			assertThat(response.statusCode()).isEqualTo(201);
			assertThat(response.header("Content-Type")).matches("application/json.*");
		// and:
			DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
			assertThatJson(parsedJson).array("boards").array("ships").contains("size").isEqualTo(5);
		assertThatJson(parsedJson).array("boards").array("ships").field("end").field("y").isNull();
	assertThatJson(parsedJson).array("boards").array("ships").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("ships").field("start").field("x").isNull();
assertThatJson(parsedJson).array("boards").array("ships").contains("type").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("ships").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("ships").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("ships").contains("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("ships").contains("type").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("ships").contains("type").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("ships").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("ships").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("ships").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("ships").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("ships").contains("id").isEqualTo(10);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("ships").contains("size").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("ships").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("ships").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("ships").contains("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("ships").contains("type").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("ships").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("ships").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("ships").contains("id").isEqualTo(2);
assertThatJson(parsedJson).field("turn").isNull();
assertThatJson(parsedJson).array("boards").array("ships").contains("type").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("ships").field("start").field("y").isNull();
assertThatJson(parsedJson).array("boards").array("ships").field("end").field("x").isNull();
}

@Test
public void validate_11_Board_1_placeCarrier() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"AIRCRAFT_CARRIER\",\"id\":1,\"start\":{\"x\":0,\"y\":0},\"end\":{\"x\":0,\"y\":4}}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(3);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(5);
assertThatJson(parsedJson).array("ships").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(5);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("ships").field("end").field("y").isNull();
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(2);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(4);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("ships").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("ships").field("end").field("x").isNull();
assertThatJson(parsedJson).array("ships").field("start").field("x").isNull();
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("ships").field("start").field("y").isNull();
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("start").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(0);
assertThatJson(parsedJson).field("winner").isEqualTo(false);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(4);
}

@Test
public void validate_12_Board_1_placeBattleship() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"BATTLESHIP\",\"id\":2,\"start\":{\"x\":1,\"y\":0},\"end\":{\"x\":1,\"y\":3}}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(3);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(5);
assertThatJson(parsedJson).array("ships").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(5);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("ships").field("end").field("y").isNull();
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(2);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(4);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("ships").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("ships").field("end").field("x").isNull();
assertThatJson(parsedJson).array("ships").field("start").field("x").isNull();
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("start").field("y").isNull();
assertThatJson(parsedJson).array("ships").field("start").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(0);
assertThatJson(parsedJson).field("winner").isEqualTo(false);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(4);
}

@Test
public void validate_13_Board_1_placeSubmarine() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"SUBMARINE\",\"id\":3,\"start\":{\"x\":2,\"y\":0},\"end\":{\"x\":2,\"y\":2}}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(3);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(5);
assertThatJson(parsedJson).array("ships").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(5);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("ships").field("end").field("y").isNull();
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(4);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("ships").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("ships").field("end").field("x").isNull();
assertThatJson(parsedJson).array("ships").field("start").field("x").isNull();
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("start").field("y").isNull();
assertThatJson(parsedJson).array("ships").field("start").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(0);
assertThatJson(parsedJson).field("winner").isEqualTo(false);
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(4);
}

@Test
public void validate_14_Board_1_placeCruiser() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"CRUISER\",\"id\":4,\"start\":{\"x\":3,\"y\":0},\"end\":{\"x\":3,\"y\":2}}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(3);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(5);
assertThatJson(parsedJson).array("ships").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(5);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("ships").field("end").field("y").isNull();
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(4);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("ships").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("ships").field("end").field("x").isNull();
assertThatJson(parsedJson).array("ships").field("start").field("x").isNull();
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("start").field("y").isNull();
assertThatJson(parsedJson).array("ships").field("start").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(0);
assertThatJson(parsedJson).field("winner").isEqualTo(false);
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(4);
}

@Test
public void validate_15_Board_1_placeDestroyer() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"DESTROYER\",\"id\":5,\"start\":{\"x\":4,\"y\":0},\"end\":{\"x\":4,\"y\":1}}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(3);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(5);
assertThatJson(parsedJson).array("ships").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(5);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(4);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("ships").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("start").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(0);
assertThatJson(parsedJson).field("winner").isEqualTo(false);
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(4);
}

@Test
public void validate_21_Board_2_placeCarrier() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"AIRCRAFT_CARRIER\",\"id\":6,\"start\":{\"x\":0,\"y\":0},\"end\":{\"x\":0,\"y\":4}}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/2");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(3);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(5);
assertThatJson(parsedJson).array("ships").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("ships").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("ships").field("end").field("y").isNull();
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(2);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(4);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(10);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("ships").field("end").field("x").isNull();
assertThatJson(parsedJson).array("ships").field("start").field("x").isNull();
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("ships").field("start").field("y").isNull();
assertThatJson(parsedJson).array("ships").field("start").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(6);
assertThatJson(parsedJson).field("id").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(0);
assertThatJson(parsedJson).field("winner").isEqualTo(false);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(4);
}

@Test
public void validate_22_Board_2_placeBattleship() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"BATTLESHIP\",\"id\":7,\"start\":{\"x\":1,\"y\":0},\"end\":{\"x\":1,\"y\":3}}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/2");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(3);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(5);
assertThatJson(parsedJson).array("ships").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("ships").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("ships").field("end").field("y").isNull();
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(2);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(4);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(10);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("ships").field("end").field("x").isNull();
assertThatJson(parsedJson).array("ships").field("start").field("x").isNull();
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("ships").field("start").field("y").isNull();
assertThatJson(parsedJson).array("ships").field("start").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(6);
assertThatJson(parsedJson).field("id").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(0);
assertThatJson(parsedJson).field("winner").isEqualTo(false);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(4);
}

@Test
public void validate_23_Board_2_placeSubmarine() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"SUBMARINE\",\"id\":8,\"start\":{\"x\":2,\"y\":0},\"end\":{\"x\":2,\"y\":2}}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/2");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(3);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(5);
assertThatJson(parsedJson).array("ships").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("ships").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("ships").field("end").field("y").isNull();
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(4);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(10);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("ships").field("end").field("x").isNull();
assertThatJson(parsedJson).array("ships").field("start").field("x").isNull();
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("ships").field("start").field("y").isNull();
assertThatJson(parsedJson).array("ships").field("start").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(6);
assertThatJson(parsedJson).field("id").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(0);
assertThatJson(parsedJson).field("winner").isEqualTo(false);
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(4);
}

@Test
public void validate_24_Board_2_placeCruiser() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"CRUISER\",\"id\":9,\"start\":{\"x\":3,\"y\":0},\"end\":{\"x\":3,\"y\":2}}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/2");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(3);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(5);
assertThatJson(parsedJson).array("ships").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("ships").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("ships").field("end").field("y").isNull();
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(4);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(10);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("ships").field("end").field("x").isNull();
assertThatJson(parsedJson).array("ships").field("start").field("x").isNull();
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("ships").field("start").field("y").isNull();
assertThatJson(parsedJson).array("ships").field("start").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(6);
assertThatJson(parsedJson).field("id").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(0);
assertThatJson(parsedJson).field("winner").isEqualTo(false);
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(4);
}

@Test
public void validate_25_Board_2_placeDestroyer() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"DESTROYER\",\"id\":10,\"start\":{\"x\":4,\"y\":0},\"end\":{\"x\":4,\"y\":1}}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/2");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(3);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(5);
assertThatJson(parsedJson).array("ships").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("ships").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("ships").contains("size").isEqualTo(4);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(10);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("ships").field("start").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(0);
assertThatJson(parsedJson).field("winner").isEqualTo(false);
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(4);
}

@Test
public void validate_30_placeMove() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"boardId\":1,\"point\":{\"x\":0,\"y\":15}}");

// when:
ResponseOptions response = given().spec(request)
.patch("/games/1");

// then:
assertThat(response.statusCode()).isEqualTo(400);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("errors").array("validations").contains("field").isEqualTo("point");
assertThatJson(parsedJson).array("errors").array("validations").field("value").field("y").isEqualTo(15);
assertThatJson(parsedJson).array("errors").array("validations").field("value").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("errors").array("validations").contains("message").isEqualTo("out of bounds.");
assertThatJson(parsedJson).array("errors").array("validations").contains("code").isEqualTo("BoundsCheck");
}

}
