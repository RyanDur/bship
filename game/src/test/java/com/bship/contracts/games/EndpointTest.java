package com.bship.contracts.games;

import com.bship.contracts.GamesEndpointBase;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import com.jayway.restassured.response.ResponseOptions;
import org.junit.Test;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

public class EndpointTest extends GamesEndpointBase {

	@Test
	public void validate_shouldCreateANewGame() throws Exception {
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
		assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(10);
	assertThatJson(parsedJson).field("rules").field("boardDimensions").field("width").isEqualTo(10);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("x").isNull();
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(4);
assertThatJson(parsedJson).field("rules").field("boardDimensions").field("height").isEqualTo(10);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(6);
assertThatJson(parsedJson).field("rules").array("pieces").contains("name").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).field("rules").array("pieces").contains("name").isEqualTo("DESTROYER");
assertThatJson(parsedJson).field("rules").array("pieces").contains("size").isEqualTo(3);
assertThatJson(parsedJson).field("rules").array("pieces").contains("size").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("type").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).field("over").isEqualTo(false);
assertThatJson(parsedJson).field("rules").array("pieces").contains("name").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(8);
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(2);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("type").isEqualTo("SUBMARINE");
assertThatJson(parsedJson).array("boards").array("pieces").contains("type").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(3);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("boards").array("pieces").contains("taken").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("winner").isEqualTo(false);
assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(7);
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(5);
assertThatJson(parsedJson).array("boards").array("pieces").contains("type").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("boards").array("pieces").contains("type").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).field("rules").array("pieces").contains("size").isEqualTo(2);
assertThatJson(parsedJson).field("rules").array("pieceOrientations").arrayField().isEqualTo("RIGHT").value();
assertThatJson(parsedJson).array("boards").array("pieces").contains("orientation").isEqualTo("NONE");
assertThatJson(parsedJson).field("rules").array("pieces").contains("size").isEqualTo(4);
assertThatJson(parsedJson).field("rules").array("pieces").contains("name").isEqualTo("CRUISER");
assertThatJson(parsedJson).field("rules").array("pieceOrientations").arrayField().isEqualTo("UP").value();
assertThatJson(parsedJson).field("turn").isNull();
assertThatJson(parsedJson).field("rules").array("pieces").contains("name").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).field("rules").field("numberOfPlayers").isEqualTo(2);
assertThatJson(parsedJson).array("boards").array("pieces").field("placement").field("y").isNull();
assertThatJson(parsedJson).array("boards").array("pieces").contains("id").isEqualTo(9);
assertThatJson(parsedJson).field("rules").array("pieceOrientations").arrayField().isEqualTo("LEFT").value();
assertThatJson(parsedJson).field("rules").field("movesPerTurn").isEqualTo(1);
assertThatJson(parsedJson).field("rules").array("pieceOrientations").arrayField().isEqualTo("DOWN").value();
assertThatJson(parsedJson).array("boards").array("pieces").contains("boardId").isEqualTo(1);
}

}
