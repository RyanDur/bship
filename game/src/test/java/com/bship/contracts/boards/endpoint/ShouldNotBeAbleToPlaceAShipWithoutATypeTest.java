package com.bship.contracts.boards.endpoint;

import com.bship.contracts.EndpointShouldNotBeAbleToPlaceAShipWithoutATypeBase;
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
public class ShouldNotBeAbleToPlaceAShipWithoutATypeTest extends EndpointShouldNotBeAbleToPlaceAShipWithoutATypeBase {

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
		.body("{\"start\":{\"x\":0,\"y\":0},\"end\":{\"x\":0,\"y\":4}}");

// when:
ResponseOptions response = given().spec(request)
		.put("/boards/1");

// then:
assertThat(response.statusCode()).isEqualTo(400);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("errors").array("fieldValidations").contains("value").isNull();
assertThatJson(parsedJson).array("errors").array("fieldValidations").contains("message").isEqualTo("Cannot be empty or null.");
assertThatJson(parsedJson).array("errors").array("fieldValidations").contains("field").isEqualTo("type");
assertThatJson(parsedJson).array("errors").array("fieldValidations").contains("code").isEqualTo("NonEmpty");
}

}
