package com.project.port.integration;

import com.project.port.common.BaseMockIT;
import com.project.port.dto.SpaceshipResponse;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static com.project.port.common.Constant.buildSpaceshipResponse;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;

@AutoConfigureMockMvc
public class SpaceshipIT extends BaseMockIT {

  private static final String TEST_API = "/api/v1/spaceships";

  private static final String TEST_API_WITH_ID = "/api/v1/spaceships/{id}";

  private SpaceshipResponse spaceshipResponse;

  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    RestAssuredMockMvc.mockMvc(mockMvc);
    spaceshipResponse = buildSpaceshipResponse();
  }

  @Test
  void getSpaceships_happyPath() {
    stubForGetAllSpaceships();
    stubForGetPilotsById();

    given()
        .when()
        .get(TEST_API)
        .then()
        .log().body()
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
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body(equalTo(objectToJsonString(spaceshipResponse)));
  }
}
