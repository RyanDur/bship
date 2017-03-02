package com.bship.contracts.boards.endpoint;

import com.bship.contracts.EndpointShouldNotBeAbleToPlaceAShipsEndOutsideTheBoardBase;
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
public class ShouldNotBeAbleToPlaceAShipsEndOutsideTheBoardTest extends EndpointShouldNotBeAbleToPlaceAShipsEndOutsideTheBoardBase {

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
public void validate_2_placeShip() throws Exception {
// given:
MockMvcRequestSpecification request = given()
		.header("Content-Type", "application/json")
		.body("{\"type\":\"AIRCRAFT_CARRIER\",\"start\":{\"x\":9,\"y\":6},\"end\":{\"x\":9,\"y\":10}}");

// when:
ResponseOptions response = given().spec(request)
		.put("/boards/1");

// then:
assertThat(response.statusCode()).isEqualTo(400);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("errors").array("fieldValidations").field("value").field("y").isEqualTo(10);
assertThatJson(parsedJson).array("errors").array("fieldValidations").contains("field").isEqualTo("end");
assertThatJson(parsedJson).array("errors").array("fieldValidations").field("value").field("x").isEqualTo(9);
assertThatJson(parsedJson).array("errors").array("fieldValidations").contains("message").isEqualTo("out of bounds.");
assertThatJson(parsedJson).array("errors").array("fieldValidations").contains("code").isEqualTo("BoundsCheck");
}

}
