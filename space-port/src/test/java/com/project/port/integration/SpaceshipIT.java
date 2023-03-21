package com.project.port.integration;

import static com.project.port.common.Constant.buildAddSpaceshipRequest;
import static com.project.port.common.Constant.buildAddSpaceshipResponse;
import static com.project.port.common.Constant.buildSpaceshipResponse;
import static com.project.port.common.Constant.buildUpdateSpaceshipRequest;
import static com.project.port.common.Constant.buildUpdateSpaceshipResponse;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;
import java.util.UUID;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.project.port.common.BaseMockIT;
import com.project.port.dto.spaceship.AddSpaceshipRequest;
import com.project.port.dto.spaceship.SpaceshipResponse;
import com.project.port.dto.spaceship.UpdateSpaceshipRequest;
import com.project.port.dto.spaceship.UpdateSpaceshipResponse;

@AutoConfigureMockMvc
public class SpaceshipIT extends BaseMockIT {

  private static final String TEST_API = "/api/v1/spaceships";

  private static final String TEST_API_WITH_ID = "/api/v1/spaceships/{id}";

  private SpaceshipResponse spaceshipResponse;

  private SpaceshipResponse addSpaceshipResponse;

  private AddSpaceshipRequest addSpaceshipRequest;

  private UpdateSpaceshipRequest updateSpaceshipRequest;

  private UpdateSpaceshipResponse updateSpaceshipResponse;

  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    RestAssuredMockMvc.mockMvc(mockMvc);
    spaceshipResponse = buildSpaceshipResponse();
    addSpaceshipResponse = buildAddSpaceshipResponse();
    addSpaceshipRequest = buildAddSpaceshipRequest();
    updateSpaceshipRequest = buildUpdateSpaceshipRequest();
    updateSpaceshipResponse = buildUpdateSpaceshipResponse();
  }

  @Test
  void getSpaceships_happyPath() {
    stubForGetAllSpaceships();
    stubForGetAllPilots();

    given()
        .when()
        .get(TEST_API)
        .then()
        .log()
        .body()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body(equalTo(objectToJsonString(List.of(spaceshipResponse))));
  }

  @Test
  void getSpaceshipById_happyPath() {
    stubForGetSpaceshipById();
    final UUID reqId = spaceshipResponse.getId();

    given()
        .when()
        .get(TEST_API_WITH_ID, reqId)
        .then()
        .log()
        .body()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body(equalTo(objectToJsonString(spaceshipResponse)));
  }

  @Test
  void addSpaceship_happyPath() {
    stubForAddPilot();
    stubForAddSpaceship();

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectToJsonString(addSpaceshipRequest))
        .when()
        .post(TEST_API)
        .then()
        .log()
        .body()
        .assertThat()
        .statusCode(HttpStatus.CREATED.value())
        .body(equalTo(objectToJsonString(addSpaceshipResponse)));
  }

  @Test
  void updateSpaceshipById_happyPath() {
    stubForGetSpaceshipById();
    stubForUpdateSpaceship();
    final UUID reqId = updateSpaceshipResponse.getId();

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectToJsonString(updateSpaceshipRequest))
        .when()
        .put(TEST_API_WITH_ID, reqId)
        .then()
        .log()
        .body()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body(equalTo(objectToJsonString(updateSpaceshipResponse)));
  }

}
