package com.bship.contracts.games.endpoint.placeMove;

import com.bship.contracts.PlaceMoveShouldNotBeAbleToPlaceAMoveOnTheBoardWhereMoveAlreadyExistsBase;
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
public class ShouldNotBeAbleToPlaceAMoveOnTheBoardWhereMoveAlreadyExistsTest extends PlaceMoveShouldNotBeAbleToPlaceAMoveOnTheBoardWhereMoveAlreadyExistsBase {

	@Test
	public void validate_0_createGame() throws Exception {
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
public void validate_11_Board_1_placeCarrier() throws Exception {
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
assertThatJson(parsedJson).array("ships").field("start").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("ships").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(4);
}

@Test
public void validate_12_Board_1_placeBattleship() throws Exception {
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
assertThatJson(parsedJson).array("ships").field("start").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(3);
}

@Test
public void validate_13_Board_1_placeSubmarine() throws Exception {
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
assertThatJson(parsedJson).array("ships").field("start").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("ships").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(2);
}

@Test
public void validate_14_Board_1_placeCruiser() throws Exception {
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
assertThatJson(parsedJson).array("ships").field("start").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("ships").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(2);
}

@Test
public void validate_15_Board_1_placeDestroyer() throws Exception {
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
assertThatJson(parsedJson).array("ships").field("start").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("ships").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(4);
assertThatJson(parsedJson).field("id").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("id").matches("-?\\d*(\\.\\d+)?");
}

@Test
public void validate_21_Board_2_placeCarrier() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"AIRCRAFT_CARRIER\",\"start\":{\"x\":0,\"y\":0},\"end\":{\"x\":0,\"y\":4}}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/2");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("ships").field("start").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("ships").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("AIRCRAFT_CARRIER");
assertThatJson(parsedJson).array("ships").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).field("id").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(0);
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(4);
}

@Test
public void validate_22_Board_2_placeBattleship() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"BATTLESHIP\",\"start\":{\"x\":1,\"y\":0},\"end\":{\"x\":1,\"y\":3}}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/2");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("ships").field("start").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(1);
assertThatJson(parsedJson).array("ships").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).array("ships").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).field("id").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(3);
}

@Test
public void validate_23_Board_2_placeSubmarine() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"SUBMARINE\",\"start\":{\"x\":2,\"y\":0},\"end\":{\"x\":2,\"y\":2}}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/2");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("BATTLESHIP");
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("start").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("ships").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).array("ships").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).field("id").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(2);
}

@Test
public void validate_24_Board_2_placeCruiser() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"CRUISER\",\"start\":{\"x\":3,\"y\":0},\"end\":{\"x\":3,\"y\":2}}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/2");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("ships").field("start").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("CRUISER");
assertThatJson(parsedJson).array("ships").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).array("ships").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).field("id").isEqualTo(2);
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(3);
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(2);
}

@Test
public void validate_25_Board_2_placeDestroyer() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"type\":\"DESTROYER\",\"start\":{\"x\":4,\"y\":0},\"end\":{\"x\":4,\"y\":1}}");

// when:
ResponseOptions response = given().spec(request)
.put("/boards/2");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("ships").field("end").field("y").isEqualTo(1);
assertThatJson(parsedJson).array("ships").field("end").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("ships").field("start").field("y").isEqualTo(0);
assertThatJson(parsedJson).array("ships").contains("type").isEqualTo("DESTROYER");
assertThatJson(parsedJson).array("ships").contains("sunk").isEqualTo(false);
assertThatJson(parsedJson).array("ships").field("start").field("x").isEqualTo(4);
assertThatJson(parsedJson).array("ships").contains("id").matches("-?\\d*(\\.\\d+)?");
assertThatJson(parsedJson).field("id").isEqualTo(2);
}

@Test
public void validate_30_placeMove() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"x\":0,\"y\":5}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1/boards/1");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("moves").field("point").field("y").isEqualTo(5);
assertThatJson(parsedJson).array("moves").contains("status").isEqualTo("MISS");
assertThatJson(parsedJson).array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).field("id").isEqualTo(1);
}

@Test
public void validate_31_placeMove() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"x\":0,\"y\":5}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1/boards/2");

// then:
assertThat(response.statusCode()).isEqualTo(200);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("moves").field("point").field("y").isEqualTo(5);
assertThatJson(parsedJson).array("moves").contains("status").isEqualTo("MISS");
assertThatJson(parsedJson).array("moves").field("point").field("x").isEqualTo(0);
assertThatJson(parsedJson).field("id").isEqualTo(2);
}

@Test
public void validate_32_placeMove() throws Exception {
// given:
MockMvcRequestSpecification request = given()
.header("Content-Type", "application/json")
.body("{\"x\":0,\"y\":5}");

// when:
ResponseOptions response = given().spec(request)
.put("/games/1/boards/1");

// then:
assertThat(response.statusCode()).isEqualTo(400);
assertThat(response.header("Content-Type")).matches("application/json.*");
// and:
DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
assertThatJson(parsedJson).array("errors").array("validations").contains("code").isEqualTo("MoveCollision");
assertThatJson(parsedJson).array("errors").array("validations").contains("type").isEqualTo("game");
assertThatJson(parsedJson).array("errors").array("validations").contains("message").isEqualTo("Move already exists on board.");
}

}
