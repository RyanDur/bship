package com.bship.contracts.boards.endpoint;

import com.bship.contracts.EndpointShouldNotBeAbleToPlaceAShipUponAnotherShipBase;
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
public class ShouldNotBeAbleToPlaceAShipUponAnotherShipTest extends EndpointShouldNotBeAbleToPlaceAShipUponAnotherShipBase {

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
			assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
		assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
	assertThatJson(parsedJson).field("id").isEqualTo(1);
}

@Test
public void validate_2_placeCarrier() throws Exception {
// given:
MockMvcRequestSpecification request = given()
		.header("Content-Type", "application/json")
		.body("{\"type\":\"AIRCRAFT_CARRIER\",\"start\":{\"x\":3,\"y\":3},\"end\":{\"x\":7,\"y\":3}}");

// when:
ResponseOptions response = given().spec(request)
		.put("/boards/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(7);
assertThatJson(parsedJson).array("ships").field("start").field("y").isEqualTo(3);
assertThatJson(parsedJson).array("ships").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(3);
}

@Test
public void validate_3_placeIntersectingBattleship() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"BATTLESHIP\",\"start\":{\"x\":4,\"y\":2},\"end\":{\"x\":4,\"y\":5}}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/1");

// then:
assertThat(response.statusCode()).isEqualTo(400);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("errors").array("objectValidations").contains("type").isEqualTo("board");
assertThatJson(parsedJson).array("errors").array("objectValidations").contains("message").isEqualTo("Ship collision.");
assertThatJson(parsedJson).array("errors").array("objectValidations").contains("code").isEqualTo("ShipCollisionCheck");
}

}
