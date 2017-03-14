package com.bship.contracts.boards.endpoint;

import com.bship.contracts.EndpointShouldNotBeAbleToPlaceAShipOfIncorrectSizeBase;
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
public class ShouldNotBeAbleToPlaceAShipOfIncorrectSizeTest extends EndpointShouldNotBeAbleToPlaceAShipOfIncorrectSizeBase {

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
public void validate_2_placeShip() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"AIRCRAFT_CARRIER\",\"id\":1,\"start\":{\"x\":0,\"y\":0},\"end\":{\"x\":0,\"y\":1}}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/1");

// then:
assertThat(response.statusCode()).isEqualTo(400);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("errors").array("validations").contains("code").isEqualTo("PlacementCheck");
assertThatJson(parsedJson).array("errors").array("validations").contains("type").isEqualTo("ship");
assertThatJson(parsedJson).array("errors").array("validations").contains("message").isEqualTo("Incorrect ship placement.");
}

}
