package com.bship.contracts.boards.endpoint;

import com.bship.contracts.EndpointShouldNotBeAbleToPlaceAShipMoreThanOnceBase;
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
public class ShouldNotBeAbleToPlaceAShipMoreThanOnceTest extends EndpointShouldNotBeAbleToPlaceAShipMoreThanOnceBase {

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
public void validate_2_placeAircraftCarrier() throws Exception {
// given:
MockMvcRequestSpecification request = given()
		.header("Content-Type", "application/json")
		.body("{\"type\":\"AIRCRAFT_CARRIER\",\"start\":{\"x\":0,\"y\":0},\"end\":{\"x\":0,\"y\":4}}");

// when:
ResponseOptions response = given().spec(request)
		.put("/boards/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("ships").field("start").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("ships").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(4);
}

@Test
public void validate_3_placeAircraftCarrierAgain() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"AIRCRAFT_CARRIER\",\"start\":{\"x\":9,\"y\":0},\"end\":{\"x\":9,\"y\":4}}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/1");

// then:
assertThat(response.statusCode()).isEqualTo(400);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("errors").array("validations").contains("type").isEqualTo("board");
assertThatJson(parsedJson).array("errors").array("validations").contains("code").isEqualTo("ShipExistsCheck");
assertThatJson(parsedJson).array("errors").array("validations").contains("message").isEqualTo("Ship already exists on board.");
}

}
