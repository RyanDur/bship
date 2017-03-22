package com.bship.contracts.boards.endpoint;

import com.bship.contracts.EndpointShouldNotBeAbleToPlaceAShipThatDoesNotExistBase;
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
public class ShouldNotBeAbleToPlaceAShipThatDoesNotExistTest extends EndpointShouldNotBeAbleToPlaceAShipThatDoesNotExistBase {

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
public void validate_2_placeShip() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"SCHOONER\",\"id\":1,\"placement\":{\"x\":0,\"y\":0},\"orientation\":\"RIGHT\",\"size\":1}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/1");

// then:
assertThat(response.statusCode()).isEqualTo(400);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("errors").array("validations").contains("message").isEqualTo("Ship does not exist.");
assertThatJson(parsedJson).array("errors").array("validations").contains("value").isEqualTo("INVALID_SHIP");
assertThatJson(parsedJson).array("errors").array("validations").contains("field").isEqualTo("type");
assertThatJson(parsedJson).array("errors").array("validations").contains("code").isEqualTo("ShipExists");
}

}
