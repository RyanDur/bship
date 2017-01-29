package com.bship.contracts.boards;

import com.bship.contracts.BoardsEndpointBase;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import com.jayway.restassured.response.ResponseOptions;
import org.junit.Test;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

public class EndpointTest extends BoardsEndpointBase {

	@Test
	public void validate_shouldBeAbleToPlaceAnAircraftCarrier() throws Exception {
		// given:
			MockMvcRequestSpecification request = given()
					.header("Content-Type", "application/json")
					.body("{\"type\":\"AIRCRAFT_CARRIER\",\"start\":{\"x\":0,\"y\":0},\"end\":{\"x\":0,\"y\":4}}");

		// when:
			ResponseOptions response = given().spec(request)
					.put("/boards/1");

		// then:
			assertThat(response.statusCode()).isEqualTo(200);
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

}
