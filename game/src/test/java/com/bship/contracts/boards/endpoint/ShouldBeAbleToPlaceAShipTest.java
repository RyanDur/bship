package com.bship.contracts.boards.endpoint;

import com.bship.contracts.EndpointShouldBeAbleToPlaceAShipBase;
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
public class ShouldBeAbleToPlaceAShipTest extends EndpointShouldBeAbleToPlaceAShipBase {

	@Test
	public void validate_1_createGame() throws Exception {
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
			assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(2);
		assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
	assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isNull();
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).array("boards").array("pieces").contains("size").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("type").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("type").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").contains("type").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("type").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("type").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("NONE");
assertThatJson(parsedJson).array("boards").array("pieces").contains("size").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("size").isEqualTo(4);
assertThatJson(parsedJson).field("turn").isNull();
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isNull();
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).array("boards").array("pieces").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
}

@Test
public void validate_2_placeCarrier() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"AIRCRAFT_CARRIER\",\"id\":1,\"placement\":{\"x\":0,\"y\":0},\"orientation\":\"DOWN\",\"size\":5}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").contains("type").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isNull();
assertThatJson(parsedJson).array("pieces").contains("orientation").isEqualTo("NONE");
assertThatJson(parsedJson).array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").contains("type").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").contains("type").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").contains("size").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").contains("size").isEqualTo(4);
assertThatJson(parsedJson).array("pieces").field("placement").field("y").isNull();
assertThatJson(parsedJson).array("pieces").contains("type").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("pieces").contains("size").isEqualTo(2);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").contains("type").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("pieces").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).field("winner").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").contains("size").isEqualTo(5);
}

@Test
public void validate_3_placeBattleship() throws Exception {
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
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").contains("type").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").contains("size").isEqualTo(4);
assertThatJson(parsedJson).array("pieces").contains("type").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("pieces").contains("size").isEqualTo(2);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").contains("type").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("pieces").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").contains("type").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isNull();
assertThatJson(parsedJson).array("pieces").contains("orientation").isEqualTo("NONE");
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").contains("type").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("pieces").contains("size").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").field("placement").field("y").isNull();
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).field("winner").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").contains("size").isEqualTo(5);
}

@Test
public void validate_4_placeSubmarine() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"SUBMARINE\",\"id\":3,\"placement\":{\"x\":2,\"y\":0},\"orientation\":\"DOWN\",\"size\":3}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").contains("type").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").contains("size").isEqualTo(4);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").contains("type").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("pieces").contains("size").isEqualTo(2);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").contains("type").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("pieces").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").contains("type").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isNull();
assertThatJson(parsedJson).array("pieces").contains("orientation").isEqualTo("NONE");
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").contains("type").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("pieces").contains("size").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").field("placement").field("y").isNull();
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).field("winner").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").contains("size").isEqualTo(5);
}

@Test
public void validate_5_placeCruiser() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"CRUISER\",\"id\":4,\"placement\":{\"x\":3,\"y\":0},\"orientation\":\"DOWN\",\"size\":3}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").contains("type").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").contains("size").isEqualTo(4);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").contains("type").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("pieces").contains("size").isEqualTo(2);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").contains("type").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("pieces").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").contains("type").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isNull();
assertThatJson(parsedJson).array("pieces").contains("orientation").isEqualTo("NONE");
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").contains("type").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("pieces").contains("size").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").field("placement").field("y").isNull();
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).field("winner").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").contains("size").isEqualTo(5);
}

@Test
public void validate_6_placeDestroyer() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"DESTROYER\",\"id\":5,\"placement\":{\"x\":4,\"y\":0},\"orientation\":\"DOWN\",\"size\":2}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").contains("boardId").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").contains("orientation").isEqualTo("DOWN");
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").contains("type").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").field("placement").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("pieces").contains("size").isEqualTo(4);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").contains("type").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("pieces").contains("size").isEqualTo(2);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("pieces").contains("type").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("pieces").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").contains("type").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).array("pieces").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("pieces").contains("type").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("pieces").contains("size").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("pieces").field("placement").field("x").isEqualTo(1);
assertThatJson(parsedJson).field("winner").isEqualTo(false);
assertThatJson(parsedJson).array("pieces").contains("size").isEqualTo(5);
}

}
