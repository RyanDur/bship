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
			assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(1);
		assertThatJson(parsedJson).array("boards").contains("id").isEqualTo(2);
	assertThatJson(parsedJson).field("id").isEqualTo(1);
}

@Test
public void validate_2_placeCarrier() throws Exception {
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
public void validate_3_placeBattleship() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"BATTLESHIP\",\"start\":{\"x\":1,\"y\":0},\"end\":{\"x\":1,\"y\":3}}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("ships").field("start").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(3);
}

@Test
public void validate_4_placeSubmarine() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"SUBMARINE\",\"start\":{\"x\":2,\"y\":0},\"end\":{\"x\":2,\"y\":2}}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("ships").field("start").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("ships").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(2);
}

@Test
public void validate_5_placeCruiser() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"CRUISER\",\"start\":{\"x\":3,\"y\":0},\"end\":{\"x\":3,\"y\":2}}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("ships").field("start").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("ships").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(2);
}

@Test
public void validate_6_placeDestroyer() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"DESTROYER\",\"start\":{\"x\":4,\"y\":0},\"end\":{\"x\":4,\"y\":1}}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("ships").contains("id").isEqualTo(1);
assertThatJson(parsedJson).array("ships").field("start").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("ships").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
}

}